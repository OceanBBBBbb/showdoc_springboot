package com.ocean.showdoc.service.impl;

import com.ocean.showdoc.controller.param.RegisterReq;
import com.ocean.showdoc.dao.UserMapper;
import com.ocean.showdoc.dao.entity.User;
import com.ocean.showdoc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import sun.security.krb5.internal.crypto.dk.AesDkCrypto;
import sun.security.rsa.RSASignature;

import java.nio.charset.Charset;
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
}
