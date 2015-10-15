package com.example.asyncapi.config;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;

public class HttpClientFactory {

  @Value("${asyncapi.restHttpPoolMaxTotal}")
  private int connectionPoolMaxTotal;

  @Value("${asyncapi.restHttpPoolMaxPerRoute}")
  private int connectionPoolMaxPerRoute;

  @Value("${asyncapi.restHttpPoolTimeout}")
  private int connectionPoolTimeout;

  @Value("${asyncapi.restHttpTimeout}")
  private int connectionTimeout;

  public HttpClient createHttpClient() {
    RequestConfig.Builder requestBuilder = RequestConfig.custom();
    requestBuilder
      .setConnectTimeout(getConnectionTimeout())
      .setConnectionRequestTimeout(getConnectionTimeout())
      .setSocketTimeout(getConnectionTimeout());

    HttpClientBuilder builder = HttpClientBuilder.create()
      .setDefaultRequestConfig(requestBuilder.build())
      .setConnectionManager(createConnectionManager());

    return builder.build();
  }

  private PoolingHttpClientConnectionManager createConnectionManager() {
    PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();

    manager.setMaxTotal(getConnectionPoolMaxTotal());
    manager.setDefaultMaxPerRoute(getConnectionPoolMaxPerRoute());
    SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(getConnectionPoolTimeout()).build();
    manager.setDefaultSocketConfig(socketConfig);

    return manager;
  }

  public int getConnectionPoolMaxTotal() {
    return connectionPoolMaxTotal;
  }

  public int getConnectionPoolMaxPerRoute() {
    return connectionPoolMaxPerRoute;
  }

  public int getConnectionPoolTimeout() {
    return connectionPoolTimeout;
  }

  public int getConnectionTimeout() {
    return connectionTimeout;
  }
}
