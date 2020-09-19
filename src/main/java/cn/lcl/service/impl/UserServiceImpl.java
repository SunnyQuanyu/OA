package cn.lcl.service.impl;

import cn.lcl.pojo.dto.IdDTO;
import cn.lcl.pojo.dto.SearchPageDTO;
import cn.lcl.exception.MyException;
import cn.lcl.exception.enums.ResultEnum;
import cn.lcl.mapper.SysUserRoleMapper;
import cn.lcl.mapper.UserMapper;
import cn.lcl.pojo.SysPermission;
import cn.lcl.pojo.SysRole;
import cn.lcl.pojo.SysUserRole;
import cn.lcl.pojo.User;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.UserService;
import cn.lcl.util.AuthcUtil;
import cn.lcl.util.FieldStringUtil;
import cn.lcl.util.ResultUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@CrossOrigin
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;


    private JavaMailSender mailSender;


    @Transactional
    @Override
    public Result saveUsers(List<User> users) {
        for (User user : users) {
            User eq = new LambdaQueryChainWrapper<User>(userMapper)
                    .eq(User::getNumber, user.getNumber()).one();
            if (eq != null) {
                throw new MyException(ResultEnum.USER_LIST_HAS_REPEAT);
            }
            user.setPassword(user.getNumber());
            int insert = userMapper.insert(user);
            if (insert != 1) {
                throw new MyException(ResultEnum.USER_INSERT_FAILED);
            }
        }
        return ResultUtil.success(users.size());
    }

    @Override
    public Result getUser() {
        User user = AuthcUtil.getUser();
        getRolesAndPermissions(user);
        return ResultUtil.success(user);
    }

    @Override
    public Result getUser(Integer uid) {
        User user = userMapper.selectById(uid);
        getRolesAndPermissions(user);
        return ResultUtil.success(user);
    }

    @Override
    public Result getUserMessage(IdDTO userId) {
        User user = userMapper.selectUserMessage(userId.getId());
        return ResultUtil.success(user);
    }

    @Override
    public Result updateUser(User user) {
        if (user.getId() == null) {
            throw new MyException(ResultEnum.MISS_FIELD);
        }
        int i = userMapper.updateById(user);
        if (i == 0) {
            throw new MyException(ResultEnum.TEAM_UPDATE_FAILED);
        }
        return ResultUtil.success(user);
    }

    @Override
    public Result updatePassWord(User user) {
        User updateUser = userMapper.selectUserByNumber(user.getNumber());
        updateUser.setPassword(user.getPassword());

        int i = userMapper.updateById(updateUser);
        if (i == 0) {
            throw new MyException(ResultEnum.TEAM_UPDATE_FAILED);
        }
        return ResultUtil.success(updateUser);
    }

    @Override
    public User queryUserByNumber(String userNumber) {
        return new LambdaQueryChainWrapper<User>(userMapper).eq(User::getNumber, userNumber).one();
    }


    @Override
    public Result login(User user) {
        try {
            UsernamePasswordToken token =
                    new UsernamePasswordToken(String.valueOf(user.getNumber()), user.getPassword());

            Subject subject = SecurityUtils.getSubject();

            subject.login(token); // 执行登录的方法，如果没有异常说明没问题了
           // subject.getSession();
        //    subject.getSession(true).setTimeout(10);
            User userAfterLogin = (User) subject.getPrincipal();
            getRolesAndPermissions(userAfterLogin);

            Session shrioSession = subject.getSession();
            shrioSession.setTimeout(10000);

            return ResultUtil.success(userAfterLogin);

        } catch (IncorrectCredentialsException e) {
            throw new MyException(ResultEnum.USER_PASSWORD_FAILED);
        } catch (UnknownAccountException e) {
            throw new MyException(ResultEnum.USER_NOT_FOUND);
        }
    }

    @Override
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResultUtil.success();
    }

    @Override
    public Result saveRole(SysUserRole userRole) {
        sysUserRoleMapper.insert(userRole);

        return ResultUtil.success(userRole);
    }

    @Override
    public Result deleteRole(SysUserRole userRole) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysUserRole::getUserId, userRole.getUserId())
                .eq(SysUserRole::getRoleId, userRole.getRoleId());
        int delete = sysUserRoleMapper.delete(queryWrapper);

        if (delete != 1) {
            throw new MyException(ResultEnum.DELETE_USER_ROLE_FAILED);
        }

        return ResultUtil.success(delete);
    }

    @Override
    public Result listRoles() {
        User user = AuthcUtil.getUser();
        getRolesAndPermissions(user);
        return ResultUtil.success(user);
    }

    @Override
    public Result listUsers(SearchPageDTO<User> searchPageDTO) {
        User user = searchPageDTO.getData();
        QueryWrapper<User> query = Wrappers.query();
        Map<String, String> map = null;
        // user may have some null field, so we should filter the null field
        // and search by the notnull field and its values
        try {
            map = BeanUtils.describe(user);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (entry.getValue() != null) {
                    query.like(FieldStringUtil.HumpToUnderline(entry.getKey()), entry.getValue());
                }
            }
            return ResultUtil.success(userMapper.selectPage(
                    new Page<>(searchPageDTO.getPageCurrent(), searchPageDTO.getPageSize()), query));
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResultEnum.USERS_FIND_ERROR);

        }

    }


    @Override
    public Result deleteUsers(IdDTO userId) {
        return ResultUtil.success(userMapper.deleteUsers(userId.getId()));
    }

    private void getRolesAndPermissions(User user) {
        List<SysRole> userRoleList = sysUserRoleMapper.getUserRoleList(user.getId());
        user.setRoleList(userRoleList);
        HashSet<SysPermission> sysPermissions = new HashSet<>();
        for (SysRole role : userRoleList) {
            sysPermissions.addAll(role.getPermissionList());
        }
        user.setPermissionSet(sysPermissions);
    }


    /*@Value("${spring.mail.username}")
    public String fromEmail;*/
   /* @Autowired*/
/*    private JavaMailSender mailSender;*/

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 给邮箱发送验证码
     *

     * @return
     */
    @Override

    public Result sendEmailCode( String email) {
        //生成随机验证码
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("1519804390@qq.com");
        message.setTo(email);
        message.setSubject("这是一个邮件主题——系统邮件");
        message.setText("您正在修改您的密码，本次验证码为：" + checkCode + "\n 如非本人操作，请忽略！谢谢");
        mailSender.send(message);
        logger.info("邮件发送成功");
        return ResultUtil.success(checkCode);

    }

}
