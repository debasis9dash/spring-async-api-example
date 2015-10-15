package com.example.asyncapi.service.internal.callback;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class CallbackResponseRepository {

  static final String CALLBACK_RESPONSE_CACHE_NAME = "callbackResponses";

  @CachePut(value = CALLBACK_RESPONSE_CACHE_NAME, key = "#response.response.requestId")
  public AsyncCallbackResponse storeResponse(AsyncCallbackResponse response) {
    return response;
  }

  @Cacheable(CALLBACK_RESPONSE_CACHE_NAME)
  public AsyncCallbackResponse getResponse(String requestId) {
    return null;
  }

}
