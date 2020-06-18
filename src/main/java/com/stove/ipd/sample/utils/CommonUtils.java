package com.stove.ipd.sample.utils;

import java.util.UUID;

/*
 * Project : sample
 * Class : com.stove.dcos.sample.utils.CommonUtils
 * Version : 2020-03-18 v0.0.1
 * Created by thyim on 2020-03-18.
 * *** 저작권 주의 ***
 */
public class CommonUtils {

  public static String generateUuid() {
    return UUID.randomUUID().toString();
  }
}
