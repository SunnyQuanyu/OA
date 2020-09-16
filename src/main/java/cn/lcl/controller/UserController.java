package cn.lcl.controller;

import cn.lcl.pojo.dto.SearchPageDTO;
import cn.lcl.pojo.dto.IdDTO;
import cn.lcl.pojo.SysUserRole;
import cn.lcl.pojo.User;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.UserService;
import cn.lcl.service.WxService;
import cn.lcl.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    WxService wxService;


    @PostMapping
    public Result getUser() {
        return userService.getUser();
    }

    @PostMapping("/get")
    public Result getUser(@RequestBody IdDTO idDTO, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> userService.getUser(idDTO.getId()));
    }
    @PostMapping("/getUserMessage")
    public Result getUserMessage(@RequestBody @Valid IdDTO userId, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> userService.getUserMessage(userId));
    }

    @PostMapping("/update")
    public Result updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @PostMapping("/updatePassWaord")
    public Result updatePassWaord(@RequestBody User user) {
        return userService.updatePassWord(user);
    }

    @PostMapping("/addUsers")
    public Result addUsers(@RequestBody List<User> users) {
        return userService.saveUsers(users);
    }

    @PostMapping("/login")
    public Result login(@RequestBody @Valid User user, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> userService.login(user));
    }

    @PostMapping("/logout")
    public Result logout() {
        return userService.logout();
    }

    @PostMapping("/addRole")
    public Result addRole(@RequestBody @Valid SysUserRole userRole, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> userService.saveRole(userRole));
    }

    @PostMapping("/delRole")
    public Result delRole(@RequestBody @Valid SysUserRole userRole, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> userService.deleteRole(userRole));
    }

    @PostMapping("/getRoles")
    public Result getRoles() {
        return ResultUtil.success(userService.listRoles());
    }

    @PostMapping("/getUsers")
    public Result getUsers(@RequestBody @Valid SearchPageDTO<User> searchPageDTO, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> userService.listUsers(searchPageDTO));
    }
    @PostMapping("/deleteUsers")
    public Result deleteThing(@RequestBody @Valid IdDTO userId, BindingResult result){
        return ResultUtil.vaildFieldError(result, () -> userService.deleteUsers(userId));
    }

    @PostMapping("/bindWX")
    public Result bindWX(@RequestParam String code) {
        return wxService.updateUserOpenid(code);
    }


    /**
     * 忘记密码模块的发送邮箱验证码
     * 传入邮箱即可如"toEmail":"123@qq.com"

     */
    @PostMapping("/sendEmail")
    public Result sendEmail(@RequestParam String email) {
        return  userService.sendEmailCode(email);
    }


}
