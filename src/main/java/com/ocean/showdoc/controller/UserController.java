package com.ocean.showdoc.controller;

import com.ocean.showdoc.common.AssertParam;
import com.ocean.showdoc.common.GlobalConst;
import com.ocean.showdoc.common.Rsp;
import com.ocean.showdoc.common.RspCode;
import com.ocean.showdoc.controller.param.RegisterReq;
import com.ocean.showdoc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.validation.Valid;

/** @author oceanBin on 2020/3/30 */
@RestController
@RequestMapping("/v1/api/user")
@Slf4j
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }
  //{"error_code":10101,"error_message":"\u7528\u6237\u540d\u5df2\u7ecf\u5b58\u5728\u5566\uff01"}
    //{"error_code":0,"data":[]}
    //{"error_code":0,"data":[{"item_id":"739463588733794","uid":"217888","item_name":"API\u6587\u6863\u793a\u4f8b","item_domain":"","item_type":"1","last_update_time":"0","item_description":"API\u6587\u6863\u793a\u4f8b","is_del":"0","creator":1},{"item_id":"739463588733795","uid":"217888","item_name":"\u6570\u636e\u5b57\u5178\u793a\u4f8b","item_domain":"","item_type":"1","last_update_time":"0","item_description":"\u6570\u636e\u5b57\u5178\u793a\u4f8b","is_del":"0","creator":1},{"item_id":"739463588733796","uid":"217888","item_name":"\u6280\u672f\u56e2\u961f\u6587\u6863\u793a\u4f8b","item_domain":"","item_type":"1","last_update_time":"0","item_description":"\u6280\u672f\u56e2\u961f\u6587\u6863\u793a\u4f8b","is_del":"0","creator":1}]}
    //{"error_code":0,"data":{"uid":"217888","username":"ocean002","email":"","email_verify":"0","name":"","avatar":"","avatar_small":"","groupid":"2","reg_time":"1585564553"}}

  @PostMapping("/register")
  public Rsp<Void> register(@Valid RegisterReq registerReq, ServletRequest request) {
    log.info("有人注册，name=" + registerReq.getUsername());
    if(AssertParam.StringLength(registerReq.getUsername(),2,64))
      return Rsp.buildFail(RspCode.PARAM_ERR,"名字长度2-64");
    if(AssertParam.StringLength(registerReq.getConfirm_password(),6,32))
      return Rsp.buildFail(RspCode.PARAM_ERR,"密码长度6-32");
    if(!registerReq.getConfirm_password().trim().equals(registerReq.getPassword().trim()))
      return Rsp.buildFail(RspCode.PWD_DIFF,RspCode.PWD_DIFF.getMessage());

    int uid = userService.register(registerReq);
    request.setAttribute(GlobalConst.USER_SESSION_KEY,String.valueOf(uid));
    return Rsp.buildSuccessEmpty();
  }
}
