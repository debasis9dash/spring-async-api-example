package com.example.asyncapi.service.internal;

import com.example.asyncapi.common.ApplicationException;

public class AsyncResponseTimedOutException extends ApplicationException {

  public AsyncResponseTimedOutException() {
  }

  public AsyncResponseTimedOutException(String message) {
    super(message);
  }
}
