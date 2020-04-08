package com.ocean.showdoc.service.impl;

import com.ocean.showdoc.controller.param.user.RegisterReq;
import com.ocean.showdoc.dao.UserMapper;
import com.ocean.showdoc.dao.entity.User;
import com.ocean.showdoc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/** @author oceanBin on 2020/3/30 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserMapper userMapper;

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  public UserServiceImpl(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  @Override
  public int register(RegisterReq req) {
    String pwd = DigestUtils.md5DigestAsHex(req.getConfirm_password().getBytes(StandardCharsets.UTF_8));
    User user =
        User.builder()
            .email(req.getUsername().trim())
            .password(pwd)
            .createdAt(new Date())
            .updatedAt(new Date())
            .build();
    userMapper.insertUseGeneratedKeys(user);
    return user.getId();
  }

  @Override
  public User getById(Integer id) {
    return userMapper.selectByPrimaryKey(id);
  }

  @Override
  public User getByNamePwd(String username, String password) {
    String pwd = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
    return userMapper.selectOne(User.builder().email(username).password(pwd).build());
  }

  @Override
  public User getByName(String name) {
    return userMapper.selectOne(User.builder().email(name).build());
  }
}
