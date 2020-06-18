package com.stove.ipd.sample.config.handler;

import com.stove.ipd.sample.api.exception.ApplicationException;
import com.stove.ipd.sample.utils.enums.RtCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/*
 * Project : sample
 * Class : com.stove.dcos.sample.config.handler.CustomResponseErrorHandler
 * Version : 2020-03-18 v0.0.1
 * Created by thyim on 2020-03-18.
 * *** 저작권 주의 ***
 */
public class CustomResponseErrorHandler implements ResponseErrorHandler {

  private static final Logger logger = LoggerFactory.getLogger(CustomResponseErrorHandler.class);

  @Override
  public boolean hasError(ClientHttpResponse response) throws IOException {

    final HttpStatus status = response.getStatusCode();
    return !status.is2xxSuccessful();
  }

  @Override
  public void handleError(ClientHttpResponse response) throws IOException {

    // hasError 에서 true 가 return 되면, 해당 메소드가 실행된다.
    String responseAsString = toString(response.getBody());
    logger.error("# ResponseBody: {}", responseAsString);

    // error handling logic 를 이 부분에 작성

    throw new ApplicationException(RtCode.RT_INTERNAL_ERROR, responseAsString);
  }

  String toString(InputStream inputStream) {
    Scanner s = new Scanner(inputStream).useDelimiter("\\A");
    return s.hasNext() ? s.next() : "";
  }
}
