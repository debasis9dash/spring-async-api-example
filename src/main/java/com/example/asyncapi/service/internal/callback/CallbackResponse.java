package com.example.asyncapi.service.internal.callback;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CallbackResponse implements Serializable {
  private static final long serialVersionUID = 7526472295622776147L;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime timestamp;
  private Response response;

  @JsonCreator
  public CallbackResponse(@JsonProperty("timestamp") OffsetDateTime timestamp,
                          @JsonProperty("response") Response response) {
    this.timestamp = timestamp;
    this.response = response;
  }

  public OffsetDateTime getTimestamp() {
    return timestamp;
  }

  public Response getResponse() {
    return response;
  }


  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Response implements Serializable {
    private static final long serialVersionUID = 1952358793540268673L;

    private String requestId;
    private String data;

    @JsonCreator
    public Response(@JsonProperty("requestId") String requestId,
                    @JsonProperty("data") String data) {
      this.requestId = requestId;
      this.data = data;
    }

    public String getRequestId() {
      return requestId;
    }

    public String getData() {
      return data;
    }
  }
}
