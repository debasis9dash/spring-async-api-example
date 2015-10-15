package com.example.asyncapi.service.internal;

import java.io.Serializable;
import java.time.OffsetDateTime;

public abstract class AsyncResponseHolder<T> implements Serializable {

  private String requestId;
  private OffsetDateTime expiresAt;
  private T response;

  public AsyncResponseHolder(String requestId, OffsetDateTime expiresAt, T response) {
    this(requestId, expiresAt);
    this.response = response;
  }

  public AsyncResponseHolder(String requestId, OffsetDateTime expiresAt) {
    this.requestId = requestId;
    this.expiresAt = expiresAt;
  }

  public boolean isCompleted() {
    return response != null;
  }

  public boolean isExpired(OffsetDateTime compareTo) {
    return expiresAt.isAfter(compareTo);
  }

  public T getResponse() {
    return response;
  }

  public OffsetDateTime getExpiresAt() {
    return expiresAt;
  }

  public String getRequestId() {
    return requestId;
  }
}
