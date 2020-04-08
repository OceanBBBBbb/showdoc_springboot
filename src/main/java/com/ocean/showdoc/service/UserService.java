package com.ocean.showdoc.service;

import com.ocean.showdoc.controller.param.user.RegisterReq;
import com.ocean.showdoc.dao.entity.User;

/**
 * @author oceanBin on 2020/3/30
 */
public interface UserService {
    int register(RegisterReq registerReq);

    User getById(Integer valueOf);

    User getByNamePwd(String username, String password);

    User getByName(String name);
}
