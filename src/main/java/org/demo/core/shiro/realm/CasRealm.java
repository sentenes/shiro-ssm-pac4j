package org.demo.core.shiro.realm;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.demo.common.util.JsonUtils;
import org.demo.common.util.LoggerUtils;
import org.demo.core.mybatis.mapper.PermissionMapper;
import org.demo.core.mybatis.model.Permission;
import org.demo.core.mybatis.model.User;
import org.demo.core.shiro.token.TokenManager;
import org.demo.permission.service.PermissionService;
import org.pac4j.core.profile.CommonProfile;

import io.buji.pac4j.realm.Pac4jRealm;
import io.buji.pac4j.subject.Pac4jPrincipal;
import io.buji.pac4j.token.Pac4jToken;

import javax.annotation.Resource;

public class CasRealm extends Pac4jRealm {

    @Resource
    private PermissionService permissionService;


    /**
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     * @Created by ZYJ on 2017年2月15日 上午11:30:56
     * @Version 1.0
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        final Pac4jToken token = (Pac4jToken) authenticationToken;
        final LinkedHashMap<String, CommonProfile> profiles = token.getProfiles();
        final Pac4jPrincipal principal = new Pac4jPrincipal(profiles);

        User user = new User();
        for (Entry<String, CommonProfile> entry : profiles.entrySet()) {
            String name = entry.getKey();
            LoggerUtils.fmtDebug(this.getClass(), "CasRealm 接受到 client名字为:%s", name);
            CommonProfile profile = entry.getValue();
            Map<String, Object> attributeMap = profile.getAttributes();
            String attributeJSON = JsonUtils.objToJson(attributeMap);
            user = JsonUtils.jsonToObjUseGson(attributeJSON, User.class);
        }
        return new SimpleAuthenticationInfo(user, profiles.hashCode(), getName());
    }

    /**
     * @param principals
     * @return
     * @Created by ZYJ on 2017年2月15日 上午11:30:56
     * @Version 1.0
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = TokenManager.getToken();
        Integer userid = TokenManager.getUserId();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        Set<String> permissions = permissionService.queryCodeByUser(user);

        info.addStringPermissions(permissions);

        return info;
    }

    public PermissionService getPermissionService() {
        return permissionService;
    }

    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }
}
