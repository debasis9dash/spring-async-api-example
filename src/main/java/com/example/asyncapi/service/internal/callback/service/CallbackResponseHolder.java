package com.example.asyncapi.service.internal.callback.service;

import com.example.asyncapi.service.internal.AsyncResponseHolder;
import com.example.asyncapi.service.internal.callback.CallbackResponse;

import java.io.Serializable;
import java.time.OffsetDateTime;

class CallbackResponseHolder extends AsyncResponseHolder<CallbackResponse> implements Serializable {

  private static final long serialVersionUID = 12472222622376147L;

  public CallbackResponseHolder(String requestId, OffsetDateTime expiresAt, CallbackResponse response) {
    super(requestId, expiresAt, response);
  }

  public CallbackResponseHolder(String requestId, OffsetDateTime expiresAt) {
    super(requestId, expiresAt);
  }
}
