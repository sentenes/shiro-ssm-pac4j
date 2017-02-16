package org.demo.core.shiro.token;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.demo.core.mybatis.model.User;

/**
 * Created by NSTL on 2017/2/15.
 */
public class TokenManager {
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static Session getSession() {
        return getSubject().getSession();
    }

    public static User getToken() {
        // 由于在realm 中 doGetAuthenticationInfo 方法返回值中设置了 principal 为user类型,所以这里可以直接强转
        User user = (User) getSubject().getPrincipal();
        return user;
    }

    public static Integer getUserId() {
        User user = getToken();
        Integer userid = 0;
        userid = null == user ? null : user.getId();
        return userid;
    }
}
