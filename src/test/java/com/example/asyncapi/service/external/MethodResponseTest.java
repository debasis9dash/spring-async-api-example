package com.example.asyncapi.service.external;

import com.example.asyncapi.service.internal.callback.AsyncCallbackResponse;
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
    AsyncCallbackResponse asyncCallbackResponse = new AsyncCallbackResponse(timestamp, new AsyncCallbackResponse.Response("id", "data"));

    MethodResponse methodResponse = MethodResponse.from(asyncCallbackResponse);

    assertThat(methodResponse.getTimestamp(), is(timestamp));
    assertThat(methodResponse.getResponse().getRequestId(), is("id"));
    assertThat(methodResponse.getResponse().getData(), is("data"));
  }

}