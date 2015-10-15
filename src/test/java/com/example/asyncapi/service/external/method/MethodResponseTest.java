package com.example.asyncapi.service.external.method;

import com.example.asyncapi.service.internal.callback.CallbackResponse;
import org.junit.Test;

import java.time.OffsetDateTime;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MethodResponseTest {

  @Test
  public void shouldConstructSuccessfully() {
    OffsetDateTime timestamp = OffsetDateTime.now();
    MethodResponse methodResponse = new MethodResponse(timestamp, new MethodResponse.Response("id", "data"));

    assertThat(methodResponse.getTimestamp(), is(timestamp));
    assertThat(methodResponse.getResponse().getRequestId(), is("id"));
    assertThat(methodResponse.getResponse().getData(), is("data"));
  }

  @Test
  public void shouldConvertFromAsyncResponse() {
    OffsetDateTime timestamp = OffsetDateTime.now();
    CallbackResponse callbackResponse = new CallbackResponse(timestamp, new CallbackResponse.Response("id", "data"));

    MethodResponse methodResponse = MethodResponse.from(callbackResponse);

    assertThat(methodResponse.getTimestamp(), is(timestamp));
    assertThat(methodResponse.getResponse().getRequestId(), is("id"));
    assertThat(methodResponse.getResponse().getData(), is("data"));
  }

}