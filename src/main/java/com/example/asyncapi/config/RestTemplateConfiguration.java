package com.example.asyncapi.config;

import org.apache.http.client.HttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

  @Bean
  public HttpClientFactory httpClientFactory() {
    return new HttpClientFactory();
  }

  @Bean
  public RestTemplate restTemplate() {
    return getRestTemplate(httpClientFactory().createHttpClient());
  }

  public static RestTemplate getRestTemplate(HttpClient httpClient) {
    return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
  }

}
