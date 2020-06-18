package com.stove.ipd.sample.config;

import com.stove.ipd.sample.config.listener.GracefulShutdownHandlerWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * Project : sample
 * Class : com.stove.dcos.sample.config.GracefulShutdownConfig
 * Version : 2020-03-18 v0.0.1
 * Created by thyim on 2020-03-18.
 * *** 저작권 주의 ***
 */
@Configuration
public class GracefulShutdownConfig {

  private GracefulShutdownHandlerWrapper gracefulShutdownHandlerWrapper;

  @Autowired
  public GracefulShutdownConfig(GracefulShutdownHandlerWrapper gracefulShutdownHandlerWrapper) {
    this.gracefulShutdownHandlerWrapper = gracefulShutdownHandlerWrapper;
  }

  @Bean
  public UndertowServletWebServerFactory servletWebServerFactory() {

    UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();
    factory.addDeploymentInfoCustomizers(
        deploymentInfo ->
            deploymentInfo.addOuterHandlerChainWrapper(gracefulShutdownHandlerWrapper));

    return factory;
  }
}
