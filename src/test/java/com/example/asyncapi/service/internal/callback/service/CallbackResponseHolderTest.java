package com.example.asyncapi.service.internal.callback.service;

import com.example.asyncapi.service.internal.callback.CallbackResponse;
import org.junit.Test;

import java.time.OffsetDateTime;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CallbackResponseHolderTest {

  @Test
  public void shouldBeExpiredForPast() {
    OffsetDateTime expiresAt = OffsetDateTime.now();
    CallbackResponseHolder holder = new CallbackResponseHolder("id", expiresAt);

    assertThat(holder.isExpired(expiresAt.minusNanos(1)), is(true));
  }

  @Test
  public void shouldNotBeExpiredForSameTime() {
    OffsetDateTime expiresAt = OffsetDateTime.now();
    CallbackResponseHolder holder = new CallbackResponseHolder("id", expiresAt);

    assertThat(holder.isExpired(expiresAt), is(false));
  }

  @Test
  public void shouldBeCompletedWhenResponseExists() {
    CallbackResponseHolder holder = new CallbackResponseHolder("id", OffsetDateTime.now(), new CallbackResponse(null, null));

    assertThat(holder.isCompleted(), is(true));
  }

  @Test
  public void shouldNotBeCompletedWhenResponseDoesNotExists() {
    CallbackResponseHolder holder = new CallbackResponseHolder("id", OffsetDateTime.now());

    assertThat(holder.isCompleted(), is(false));
  }
}