package com.stove.ipd.sample.config.listener;

import io.undertow.server.HandlerWrapper;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.GracefulShutdownHandler;
import org.springframework.stereotype.Component;

/*
 * Project : sample
 * Class : com.stove.dcos.sample.config.listener.GracefulShutdownHandlerWrapper
 * Version : 2020-03-18 v0.0.1
 * Created by thyim on 2020-03-18.
 * *** 저작권 주의 ***
 */
@Component
public class GracefulShutdownHandlerWrapper implements HandlerWrapper {

  private GracefulShutdownHandler gracefulShutdownHandler;

  @Override
  public HttpHandler wrap(final HttpHandler handler) {

    if (gracefulShutdownHandler == null) {
      synchronized (this) {
        if (gracefulShutdownHandler == null) {
          this.gracefulShutdownHandler = new GracefulShutdownHandler(handler);
        }
      }
    }

    return gracefulShutdownHandler;
  }

  GracefulShutdownHandler getGracefulShutdownHandler() {
    return gracefulShutdownHandler;
  }
}
