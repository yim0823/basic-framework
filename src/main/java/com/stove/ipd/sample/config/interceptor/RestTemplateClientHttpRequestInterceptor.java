package com.stove.ipd.sample.config.interceptor;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

/*
 * Project : sample
 * Class : com.stove.dcos.sample.config.interceptor.RestTemplateClientHttpRequestInterceptor
 * Version : 2020-03-18 v0.0.1
 * Created by thyim on 2020-03-18.
 * *** 저작권 주의 ***
 */
public class RestTemplateClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

  private static final Logger logger =
      LoggerFactory.getLogger(RestTemplateClientHttpRequestInterceptor.class);

  @Override
  public ClientHttpResponse intercept(
      HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

    // request log
    URI uri = request.getURI();
    traceRequest(request, body);

    // execute
    ClientHttpResponse response = execution.execute(request, body);
    traceResponse(response, uri); // response log

    return response;
  }

  /**
   * RestTemplate Request 로깅.
   *
   * @param request {@link HttpRequest}
   * @param body HttpRequest Body
   */
  private void traceRequest(HttpRequest request, byte[] body) {

    StringBuilder requestLog = new StringBuilder();
    requestLog
        .append("[REQUEST] ")
        .append("Uri : ")
        .append(request.getURI())
        .append(", Method : ")
        .append(request.getMethod())
        .append(", Request Body : ")
        .append(new String(body, StandardCharsets.UTF_8));

    logger.info(requestLog.toString());
  }

  /**
   * RestTemplate Response 로깅.
   *
   * @param response {@link ClientHttpResponse}
   * @param uri Request Source URI
   * @throws IOException response 스트림을 읽을 수 없을 때 에러 발생
   */
  private void traceResponse(ClientHttpResponse response, URI uri) throws IOException {

    StringBuilder responseLog = new StringBuilder();

    responseLog
        .append("[RESPONSE] ")
        .append("Uri : ")
        .append(uri)
        .append(", Status code : ")
        .append(response.getStatusCode())
        .append(", Response Body : ")
        .append(IOUtils.toString(response.getBody(), StandardCharsets.UTF_8.name()));

    logger.info(responseLog.toString());
  }
}
