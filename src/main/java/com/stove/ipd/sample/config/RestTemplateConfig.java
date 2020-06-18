package com.stove.ipd.sample.config;

import com.google.common.collect.Lists;
import com.stove.ipd.sample.config.handler.CustomResponseErrorHandler;
import com.stove.ipd.sample.config.interceptor.RestTemplateClientHttpRequestInterceptor;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/*
 * Project : sample
 * Class : com.stove.dcos.sample.config.RestTemplateConfig
 * Version : 2020-03-18 v0.0.1
 * Created by thyim on 2020-03-18.
 * *** 저작권 주의 ***
 */
@Configuration
public class RestTemplateConfig {

  private CloseableHttpClient httpClient;

  @Autowired
  public RestTemplateConfig(CloseableHttpClient httpClient) {
    this.httpClient = httpClient;
  }

  @Bean
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();

    // 로깅 인터셉터에서 Stream 을 소비할 것이기에 business logic 에서 body 가 사라지기 때문에
    // BufferingClientHttpRequestFactory 을 꼭 써야한다.
    restTemplate.setRequestFactory(
        new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
    restTemplate.setMessageConverters(httpMessageConverters());
    restTemplate.getInterceptors().add(new RestTemplateClientHttpRequestInterceptor());
    // response 에 대해 custom 한 error handling 이 필요한 경우
    restTemplate.setErrorHandler(new CustomResponseErrorHandler());

    return restTemplate;
  }

  @Bean
  public List<HttpMessageConverter<?>> httpMessageConverters() {

    List<HttpMessageConverter<?>> messageConverters = Lists.newArrayList();
    messageConverters.add(new StringHttpMessageConverter());
    messageConverters.add(new MappingJackson2HttpMessageConverter());
    messageConverters.add(new FormHttpMessageConverter());

    return messageConverters;
  }
}
