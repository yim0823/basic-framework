package com.stove.ipd.sample.config;

import com.stove.ipd.sample.config.filter.AccessLogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * Project : sample
 * Class : com.stove.dcos.sample.config.ServletConfig
 * Version : 2020-03-18 v0.0.1
 * Created by thyim on 2020-03-18.
 * *** 저작권 주의 ***
 */
@Configuration
@ServletComponentScan // Log Filter
public class ServletConfig {

  /** Filter - AccessLog */
  @Bean
  public FilterRegistrationBean<AccessLogFilter> accessLogFilterRegistration() {

    FilterRegistrationBean<AccessLogFilter> filterRegistrationBean = new FilterRegistrationBean<>();
    filterRegistrationBean.setFilter(new AccessLogFilter());
    filterRegistrationBean.addUrlPatterns("/*");

    return filterRegistrationBean;
  }
}
