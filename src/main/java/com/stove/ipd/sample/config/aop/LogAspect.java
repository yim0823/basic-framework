package com.stove.ipd.sample.config.aop;

import com.google.common.collect.Maps;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

/*
 * Project : sample
 * Class : com.stove.dcos.sample.config.aop.LogAspect
 * Version : 2020-03-18 v0.0.1
 * Created by thyim on 2020-03-18.
 * *** 저작권 주의 ***
 */
@Component
@Aspect
public class LogAspect {

  private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

  /**
   * request 에 담긴 정보를 JSONObject 형태로 반환한다.
   *
   * @param request Servlet으로 들어온 {@link HttpServletRequest}
   * @return {@link HttpServletRequest#getParameterNames()} To JsonObject
   */
  @SuppressWarnings("unchecked")
  private static JSONObject getParams(HttpServletRequest request) {

    JSONObject jsonObject = new JSONObject();

    Enumeration<String> params = request.getParameterNames();
    while (params.hasMoreElements()) {
      String param = params.nextElement();
      String replaceParam = param.replaceAll("\\.", "-");
      jsonObject.put(replaceParam, request.getParameter(param));
    }

    return jsonObject;
  }

  @Pointcut("execution(* com.stove.dcos..*Controller.*(..))")
  // || execution(* com.stove.dcos..*Service.*(..))")
  public void loggerPointCut() {}

  @Around("loggerPointCut()")
  public Object methodLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

    try {

      Object result = proceedingJoinPoint.proceed();

      // Get the information of request
      HttpServletRequest request =
          ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

      String controllerName = proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName();
      String methodName = proceedingJoinPoint.getSignature().getName();

      Map<String, Object> params = Maps.newHashMap();
      try {
        params.put("class", controllerName);
        params.put("method", methodName);
        params.put("params", getParams(request));
        params.put("log_time", new Date());
        params.put("request_uri", request.getRequestURI());
        params.put("http_method", request.getMethod());
      } catch (Exception e) {
        logger.error("##\tLoggerAspect error\t##", e);
      }

      logger.info("##\tParams : {}\t##", params);

      return result;

    } catch (Throwable throwable) {
      throw throwable;
    }
  }
}
