package com.ocean.showdoc.common;

import lombok.Getter;

/** @author oceanBin on 2020/3/30 */
@Getter
public enum RspCode {
  // System
  SUCCESS(0, "成功"),
  AUTH_FAIL(10206, "用户名或密码错误"),
  PWD_DIFF(10000, "两次密码不一致"),
  NEED_LOGIN(10102, "需要登陆"),
  PARAM_ERR(11111, "参数错误");

  RspCode(int code, String message) {
    this.code = code;
    this.message = message;
  }

  private String message;
  private int code;
}
