package org.demo.permission.service.impl;

import org.demo.core.mybatis.mapper.PermissionMapper;
import org.demo.core.mybatis.model.Permission;
import org.demo.core.mybatis.model.User;
import org.demo.permission.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by NSTL on 2017/2/15.
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;


    @Override
    public Set<String> queryCodeByUser(User user) {
        if (user == null || user.getId() == null) {
            throw new NullPointerException("Argument user or id is null.");
        }
        Set<Permission> permissionSet = permissionMapper.queryCodeByUserid(user.getId());
        Set<String> codeSet = new HashSet<String>();
        for (Permission permission : permissionSet) {
            if (permission == null) {
                continue;
            }
            String code = permission.getCode();
            codeSet.add(code);
        }
        return codeSet;
    }

    public PermissionMapper getPermissionMapper() {
        return permissionMapper;
    }

    public void setPermissionMapper(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }
}
