package com.example.asyncapi.service.internal.callback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
class CallbackBackendService {
  @Value("${asyncapi.service.internal.callback.url}")
  private String callbackServiceUrl;

  @Autowired
  private RestTemplate restTemplate;

  public void queryBackend(String requestId) {
    restTemplate.getForEntity(getUrl(), String.class, requestId);
  }

  private String getUrl() {
    return callbackServiceUrl + "?requestId={requestId}";
  }

}
