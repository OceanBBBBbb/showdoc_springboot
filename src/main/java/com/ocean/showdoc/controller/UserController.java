package com.ocean.showdoc.controller;

import com.ocean.showdoc.common.AssertParam;
import com.ocean.showdoc.common.GlobalConst;
import com.ocean.showdoc.common.Rsp;
import com.ocean.showdoc.common.RspCode;
import com.ocean.showdoc.controller.param.user.LoginReq;
import com.ocean.showdoc.controller.param.user.RegisterReq;
import com.ocean.showdoc.controller.param.user.ResetPwdReq;
import com.ocean.showdoc.dao.entity.User;
import com.ocean.showdoc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/** @author oceanBin on 2020/3/30 */
@RestController
@RequestMapping("/v1/api/user")
@Slf4j
public class UserController {

  @Value("${user.register.forbidden}")
  private boolean registerFbd;

  private final UserService userService;
  private final StringRedisTemplate redisTemplate;

  public UserController(UserService userService, StringRedisTemplate redisTemplate) {
    this.userService = userService;
    this.redisTemplate = redisTemplate;
  }
  // {"error_code":10101,"error_message":"\u7528\u6237\u540d\u5df2\u7ecf\u5b58\u5728\u5566\uff01"}
  // {"error_code":0,"data":[]}
  // {"error_code":0,"data":[{"item_id":"739463588733794","uid":"217888","item_name":"API\u6587\u6863\u793a\u4f8b","item_domain":"","item_type":"1","last_update_time":"0","item_description":"API\u6587\u6863\u793a\u4f8b","is_del":"0","creator":1},{"item_id":"739463588733795","uid":"217888","item_name":"\u6570\u636e\u5b57\u5178\u793a\u4f8b","item_domain":"","item_type":"1","last_update_time":"0","item_description":"\u6570\u636e\u5b57\u5178\u793a\u4f8b","is_del":"0","creator":1},{"item_id":"739463588733796","uid":"217888","item_name":"\u6280\u672f\u56e2\u961f\u6587\u6863\u793a\u4f8b","item_domain":"","item_type":"1","last_update_time":"0","item_description":"\u6280\u672f\u56e2\u961f\u6587\u6863\u793a\u4f8b","is_del":"0","creator":1}]}
  // {"error_code":0,"data":{"uid":"217888","username":"ocean002","email":"","email_verify":"0","name":"","avatar":"","avatar_small":"","groupid":"2","reg_time":"1585564553"}}

  @PostMapping("/register")
  public Rsp<String> register(@Valid RegisterReq registerReq, ServletRequest request) {
    // 线上不开放
    if (registerFbd) {
      return Rsp.buildFail(RspCode.PARAM_ERR, "暂未开放");
    }
    String name = registerReq.getUsername().trim();
    log.info("有人注册，name=" + name);
    if (AssertParam.StringLength(name, 2, 64))
      return Rsp.buildFail(RspCode.PARAM_ERR, "名字长度2-64");
    if (AssertParam.StringLength(registerReq.getConfirm_password(), 6, 32))
      return Rsp.buildFail(RspCode.PARAM_ERR, "密码长度6-32");
    if (!registerReq.getConfirm_password().equals(registerReq.getPassword()))
      return Rsp.buildFail(RspCode.PWD_DIFF, RspCode.PWD_DIFF.getMessage());

    User byName = userService.getByName(name);
    if(null != byName){
      return Rsp.buildFail(RspCode.ALREADY_REGISTER,RspCode.ALREADY_REGISTER.getMessage());
    }
    int uid = userService.register(registerReq);
    String token = UUID.randomUUID().toString().replaceAll("-", "");
    redisTemplate.opsForValue().set(token,String.valueOf(uid),2, TimeUnit.DAYS);
    return Rsp.buildSuccess(token);
  }


  @PostMapping("/info")
  public Rsp<User> info(HttpServletRequest request) {
    String token = request.getHeader(GlobalConst.USER_SESSION_KEY);
    String uid = redisTemplate.opsForValue().get(token);
    log.info("uid={}",uid);
    if (StringUtils.isEmpty(uid)) {
      return Rsp.buildFail(RspCode.UN_LOGIN, RspCode.UN_LOGIN.getMessage());
    }
    User user = userService.getById(Integer.valueOf(uid));
    if(null == user){
      return Rsp.buildFail(RspCode.UN_LOGIN, RspCode.UN_LOGIN.getMessage());
    }else{
      return Rsp.buildSuccess(user);
    }
  }

  @PostMapping("/login")
  public Rsp<String> login(@Valid LoginReq loginReq) {
    String username = loginReq.getUsername().trim();
    log.info("有人登陆，name=" + username);
    if (AssertParam.StringLength(username, 2, 64))
      return Rsp.buildFail(RspCode.PARAM_ERR, "名字长度2-64");
    if (AssertParam.StringLength(loginReq.getPassword(), 6, 32))
      return Rsp.buildFail(RspCode.PARAM_ERR, "密码长度6-32");

    User user = userService.getByNamePwd(username,loginReq.getPassword());
    if(null == user){
      return Rsp.buildFail(RspCode.AUTH_FAIL, RspCode.AUTH_FAIL.getMessage());
    }
    String token = UUID.randomUUID().toString().replaceAll("-", "");
    redisTemplate.opsForValue().set(token,String.valueOf(user.getId()),2, TimeUnit.DAYS);
    return Rsp.buildSuccess(token);
  }

  @GetMapping("/logout")
  public Rsp<Void> logout(HttpServletRequest request) {
    String token = request.getHeader(GlobalConst.USER_SESSION_KEY);
    redisTemplate.delete(token);
    return Rsp.buildSuccessEmpty();
  }

  @PostMapping("/resetPassword")
  public Rsp<Void> resetPassword(ResetPwdReq req, HttpServletRequest request) {
    String password = req.getPassword();
    if (AssertParam.StringLength(password, 6, 32))
      return Rsp.buildFail(RspCode.PARAM_ERR, "密码长度6-32");
    if (AssertParam.StringLength(req.getNew_password(), 6, 32))
      return Rsp.buildFail(RspCode.PARAM_ERR, "密码长度6-32");
    String token = request.getHeader(GlobalConst.USER_SESSION_KEY);
    String uid = redisTemplate.opsForValue().get(token);
    log.info("resetPassword uid={}",uid);
    User user = userService.getById(Integer.valueOf(uid));
    String s = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));

    if (!s.equals(user.getPassword())) {
      return Rsp.buildFail(RspCode.ORIG_PWD_ERR, RspCode.ORIG_PWD_ERR.getMessage());
    }
    return Rsp.buildSuccessEmpty();
  }


}
