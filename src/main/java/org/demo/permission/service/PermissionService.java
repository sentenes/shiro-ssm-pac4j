package org.demo.permission.service;

import org.demo.core.mybatis.model.User;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by NSTL on 2017/2/15.
 */
public interface PermissionService {

    Set<String> queryCodeByUser(User user);

}
