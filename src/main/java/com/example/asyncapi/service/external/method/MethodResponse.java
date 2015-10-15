package com.example.asyncapi.service.external.method;

import com.example.asyncapi.service.internal.callback.CallbackResponse;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

public class MethodResponse {
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime timestamp;
  private Response response;

  public MethodResponse(OffsetDateTime timestamp, Response response) {
    this.timestamp = timestamp;
    this.response = response;
  }

  public OffsetDateTime getTimestamp() {
    return timestamp;
  }

  public Response getResponse() {
    return response;
  }

  public static MethodResponse from(CallbackResponse source) {
    //We should never expose internal structures externally. internal changes should not change public api for clients by mistake
    return new MethodResponse(source.getTimestamp(), Response.from(source.getResponse()));
  }

  public static class Response {
    private String requestId;
    private String data;

    public Response(String requestId, String data) {
      this.requestId = requestId;
      this.data = data;
    }

    public String getRequestId() {
      return requestId;
    }

    public String getData() {
      return data;
    }

    public static Response from(CallbackResponse.Response source) {
      return new Response(source.getRequestId(), source.getData());
    }
  }
}