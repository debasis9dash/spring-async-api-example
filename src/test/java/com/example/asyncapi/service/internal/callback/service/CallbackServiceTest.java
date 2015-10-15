package com.example.asyncapi.service.internal.callback.service;

import com.example.asyncapi.MockitoTest;
import com.example.asyncapi.common.ApplicationException;
import com.example.asyncapi.service.internal.AsyncResponseTimedOutException;
import com.example.asyncapi.service.internal.callback.CallbackResponse;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.OffsetDateTime;

import static org.mockito.Mockito.doReturn;

public class CallbackServiceTest extends MockitoTest {

  @Mock
  private CallbackBackendService callbackBackendService;

  @Mock
  private CallbackResponseRepository callbackResponseRepository;

  @InjectMocks
  private CallbackService callbackService;

  @Test(expected = ApplicationException.class)
  public void storeResponseShouldThrowExceptionWhenRequestIsCompleted() {
    String requestId = "id";
    OffsetDateTime timestamp = OffsetDateTime.now();
    CallbackResponse response = new CallbackResponse(timestamp, new CallbackResponse.Response(requestId, "di"));
    CallbackResponseHolder holder = new CallbackResponseHolder(requestId, timestamp, response);

    doReturn(holder).when(callbackResponseRepository).getResponse(requestId);

    callbackService.storeResponse(response);
  }

  @Test(expected = AsyncResponseTimedOutException.class)
  public void storeResponseShouldThrowExceptionWhenRequestIsExpired() {
    String requestId = "id";
    OffsetDateTime timestamp = OffsetDateTime.now();
    CallbackResponse response = new CallbackResponse(timestamp, new CallbackResponse.Response(requestId, "di"));
    CallbackResponseHolder holder = new CallbackResponseHolder(requestId, timestamp.plusMinutes(1), null);

    doReturn(holder).when(callbackResponseRepository).getResponse(requestId);

    callbackService.storeResponse(response);
  }

  @Test(expected = ApplicationException.class)
  public void storeResponseShouldThrowExceptionWhenRequestDoesNotExist() {
    String requestId = "id";
    OffsetDateTime timestamp = OffsetDateTime.now();
    CallbackResponse response = new CallbackResponse(timestamp, new CallbackResponse.Response(requestId, "di"));

    doReturn(null).when(callbackResponseRepository).getResponse(requestId);

    callbackService.storeResponse(response);
  }

}