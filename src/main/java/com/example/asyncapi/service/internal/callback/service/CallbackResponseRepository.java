package com.example.asyncapi.service.internal.callback.service;

import com.example.asyncapi.service.internal.callback.CallbackResponse;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.annotation.CheckReturnValue;
import javax.annotation.meta.When;
import java.time.OffsetDateTime;

@Repository
class CallbackResponseRepository {

  static final String CALLBACK_RESPONSE_CACHE_NAME = "callbackResponses";

  @CheckReturnValue(when = When.NEVER)
  @CachePut(value = CALLBACK_RESPONSE_CACHE_NAME, key = "#requestId")
  public CallbackResponseHolder storeCallbackResponse(String requestId, OffsetDateTime expiresAt, CallbackResponse response) {
    return new CallbackResponseHolder(requestId, expiresAt, response);
  }

  @CachePut(value = CALLBACK_RESPONSE_CACHE_NAME, key = "#requestId")
  public CallbackResponseHolder initCallbackResponse(String requestId, OffsetDateTime expiresAt) {
    return new CallbackResponseHolder(requestId, expiresAt);
  }

  @Cacheable(CALLBACK_RESPONSE_CACHE_NAME)
  public CallbackResponseHolder getResponse(String requestId) {
    return null;
  }

}
