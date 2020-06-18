package com.stove.ipd.sample.api.exception;

import com.stove.ipd.sample.utils.enums.RtCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/*
 * Project : sample
 * Class : com.stove.dcos.sample.api.exception.ApplicationException
 * Version : 2020-03-18 v0.0.1
 * Created by thyim on 2020-03-18.
 * *** 저작권 주의 ***
 */
@Getter
public class ApplicationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private long errorCode;
  private String errorMessage;
  private HttpStatus httpStatus;

  public ApplicationException(RtCode rtCode) {
    super(rtCode.toString());
    this.errorCode = rtCode.getRtCode();
    this.errorMessage = rtCode.getRtMessage();
    this.httpStatus = rtCode.getHttpStatus();
  }

  public ApplicationException(RtCode rtCode, String additionalMessage) {
    super(rtCode.toString() + additionalMessage);
    this.errorCode = rtCode.getRtCode();
    this.errorMessage = rtCode.getRtMessage() + additionalMessage;
    this.httpStatus = rtCode.getHttpStatus();
  }

  public ApplicationException(RtCode rtCode, Throwable th) {
    super(rtCode.toString(), th);
    this.errorCode = rtCode.getRtCode();
    this.errorMessage = rtCode.getRtMessage();
    this.httpStatus = rtCode.getHttpStatus();
  }

  public ApplicationException(RtCode rtCode, String additionalMessage, Throwable th) {
    super(rtCode.toString() + additionalMessage, th);
    this.errorCode = rtCode.getRtCode();
    this.errorMessage = rtCode.getRtMessage();
    this.httpStatus = rtCode.getHttpStatus();
  }
}
