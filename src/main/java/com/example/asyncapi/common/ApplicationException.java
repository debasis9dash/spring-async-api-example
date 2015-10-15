package com.example.asyncapi.common;

public class ApplicationException extends RuntimeException {
  public ApplicationException() {
  }

  public ApplicationException(String message) {
    super(message);
  }
}
