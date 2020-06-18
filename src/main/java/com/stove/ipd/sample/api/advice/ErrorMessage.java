package com.stove.ipd.sample.api.advice;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/*
 * Project : sample
 * Class : com.stove.dcos.sample.api.advice.ErrorMessage
 * Version : 2020-03-18 v0.0.1
 * Created by thyim on 2020-03-18.
 * *** 저작권 주의 ***
 */
@ToString
@Getter
public class ErrorMessage {

  private String field;
  private String message;

  @Builder
  public ErrorMessage(String field, String message) {
    this.field = field;
    this.message = message;
  }
}
