package com.stove.ipd.sample.config.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/*
 * Project : sample
 * Class : com.stove.dcos.sample.config.filter.AccessLogFilter
 * Version : 2020-03-18 v0.0.1
 * Created by thyim on 2020-03-18.
 * *** 저작권 주의 ***
 */
@WebFilter(
    urlPatterns = {"/sample/*"},
    description = "Access Log Filter",
    asyncSupported = true)
public class AccessLogFilter implements Filter {

  private static final Logger logger = LoggerFactory.getLogger(AccessLogFilter.class);

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    if (logger.isDebugEnabled()) {
      logger.debug("##\tInit log filter\t##");
    }
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) request;

    String remoteAddr = StringUtils.defaultString(request.getRemoteAddr(), "-");
    String url = (req.getRequestURL() == null) ? "" : req.getRequestURL().toString();
    String queryString = StringUtils.defaultIfEmpty(req.getQueryString(), "");
    String refer = StringUtils.defaultString(req.getHeader("Refer"), "-");
    String agent = StringUtils.defaultString(req.getHeader("UserDto-Agent"), "-");
    String fullUrl = url;

    fullUrl += StringUtils.isNotEmpty(queryString) ? "?" + queryString : queryString;
    StringBuilder result = new StringBuilder();
    result
        .append(":")
        .append(remoteAddr)
        .append(":")
        .append(fullUrl)
        .append(":")
        .append(refer)
        .append(":")
        .append(agent);

    logger.info("##\tLog Filter : {}\t##", result.toString());

    long startDate = System.currentTimeMillis();
    chain.doFilter(req, response);
    long endDate = System.currentTimeMillis();
    String uri = req.getRequestURI();
    if (!uri.contains("swagger")) {
      logger.info("##\tRestAPI Access Time: {}s\t##", (double) (endDate - startDate) / 1000);
    }
  }

  @Override
  public void destroy() {

    if (logger.isDebugEnabled()) {
      logger.debug("##\tDestroy log filter\t##");
    }
  }
}
