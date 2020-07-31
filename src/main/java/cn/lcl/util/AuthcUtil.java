package cn.lcl.util;

import cn.lcl.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class AuthcUtil {

    public static User getUser() {
        Subject subject = SecurityUtils.getSubject();
        return (User) subject.getPrincipal();
    }

}
