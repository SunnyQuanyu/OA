package cn.lcl.service;

import cn.lcl.pojo.dto.IdDTO;
import cn.lcl.pojo.dto.SearchPageDTO;
import cn.lcl.pojo.SysUserRole;
import cn.lcl.pojo.User;
import cn.lcl.pojo.result.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {
    // 保存全部用户
    Result saveUsers(List<User> users);

    // 从session中获取当前用户
    Result getUser();

    // 根据id获取某个用户信息
    Result getUser(Integer uid);

    //根据用户ID获取用户信息
    Result getUserMessage(IdDTO userId);

    //更新用户信息
    Result updateUser(User user);

    //忘记密码，进行密码更新
    Result updatePassWord(User user);

    // 根据账号查询某个用户
    User queryUserByNumber(String userNumber);

    // 登录
    Result login(User user);

    // 注销
    Result logout();

    // 对某个用户保存一个角色
    Result saveRole(SysUserRole userRole);

    // 对某个用户删除一个角色
    Result deleteRole(SysUserRole userRole);

    // 获取当前用户全部角色
    Result listRoles();

    // 根据查询条件获取用户
    Result listUsers(SearchPageDTO<User> SearchPageDTO);

    //删除某个用户
    Result deleteUsers(IdDTO userId);

    //发送邮件
    Result sendEmailCode(String email);

}
