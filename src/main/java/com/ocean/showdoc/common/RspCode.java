package com.ocean.showdoc.common;

import lombok.Getter;

/** @author oceanBin on 2020/3/30 */
@Getter
public enum RspCode {
  // System
  SUCCESS(0, "成功"),
  ORIG_PWD_ERR(11101, "原密码错误"),
  AUTH_FAIL(10206, "用户名或密码错误"),
  PWD_DIFF(10000, "两次密码不一致"),
  UN_LOGIN(10000, "用户未登录"),
  NEED_LOGIN(10102, "需要登陆"),
  ALREADY_REGISTER(11100, "用户名已注册"),
  PARAM_ERR(11111, "参数错误");

  RspCode(int code, String message) {
    this.code = code;
    this.message = message;
  }

  private String message;
  private int code;
}
