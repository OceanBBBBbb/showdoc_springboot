package com.ocean.showdoc.common;

/** @author oceanBin on 2020/3/30 */
public class AssertParam {

  public static boolean StringLength(String str, int min, int max) {
    str = str.trim();
    return str.length() < min || str.length() > max;
  }
}
