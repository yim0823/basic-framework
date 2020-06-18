package com.stove.ipd.sample.config;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/*
 * Project : sample
 * Class : com.stove.dcos.sample.config.HttpClientConfig
 * Version : 2020-03-18 v0.0.1
 * Created by thyim on 2020-03-18.
 * *** 저작권 주의 ***
 */
@Configuration
@EnableScheduling
public class HttpClientConfig {

  private static final Logger logger = LoggerFactory.getLogger(HttpClientConfig.class);

  // Determines the timeout in milliseconds until a connection is established.
  @Value("${spring.httpclient.connect-timeout}")
  private int connectionTimeout;

  // The timeout when requesting a connection from the connection manager.
  @Value("${spring.httpclient.request-timeout}")
  private int requestTimeout;

  // The timeout for waiting for data
  @Value("${spring.httpclient.socket-timeout}")
  private int socketTimeout;

  @Value("${spring.httpclient.maximum-connections}")
  private int totalMaxConnection;

  @Value("${spring.httpclient.keep-alive-time}")
  private int defaultKeepAliveTimeMillis;

  @Value("${spring.httpclient.close-idle-time-sec}")
  private int closeIdleConnectionWaitTimeSecs;

  @Bean
  public PoolingHttpClientConnectionManager poolingConnectionManager() {

    SSLContextBuilder builder = new SSLContextBuilder();
    try {
      builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
    } catch (NoSuchAlgorithmException | KeyStoreException e) {
      logger.error(
          "Pooling Connection Manager Initialisation failure because of " + e.getMessage(), e);
    }

    SSLConnectionSocketFactory sslConnectionSocketFactory = null;
    try {
      sslConnectionSocketFactory = new SSLConnectionSocketFactory(builder.build());
    } catch (NoSuchAlgorithmException | KeyManagementException e) {
      logger.error(
          "Pooling Connection Manager Initialisation failure because of " + e.getMessage(), e);
    }

    Registry<ConnectionSocketFactory> socketFactoryRegistry =
        RegistryBuilder.<ConnectionSocketFactory>create()
            .register("https", sslConnectionSocketFactory)
            .register("http", new PlainConnectionSocketFactory())
            .build();

    PoolingHttpClientConnectionManager poolingHttpClientConnectionManager =
        new PoolingHttpClientConnectionManager(socketFactoryRegistry);
    poolingHttpClientConnectionManager.setMaxTotal(totalMaxConnection);

    return poolingHttpClientConnectionManager;
  }

  @Bean
  public ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {

    return new ConnectionKeepAliveStrategy() {
      @Override
      public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
        HeaderElementIterator iterator =
            new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
        while (iterator.hasNext()) {
          HeaderElement element = iterator.nextElement();
          String param = element.getName();
          String value = element.getValue();

          if (value != null && param.equalsIgnoreCase("timeout")) {
            return Long.parseLong(value) * 1000;
          }
        }

        return defaultKeepAliveTimeMillis;
      }
    };
  }

  @Bean
  public CloseableHttpClient httpClient() {

    RequestConfig requestConfig =
        RequestConfig.custom()
            .setConnectionRequestTimeout(requestTimeout)
            .setConnectTimeout(connectionTimeout)
            .setSocketTimeout(socketTimeout)
            .build();

    return HttpClients.custom()
        .setDefaultRequestConfig(requestConfig)
        .setConnectionManager(poolingConnectionManager())
        .setKeepAliveStrategy(connectionKeepAliveStrategy())
        .setMaxConnPerRoute(50)
        // 서버에서 keepalive 시간동안 미 사용한 커넥션을 죽이는 등의 케이스 방어로 idle 커넥션을 주기적으로 지움
        .evictIdleConnections(3000L, TimeUnit.MILLISECONDS)
        .build();
  }
}
