package com.example.asyncapi.service.internal.callback;

import com.example.asyncapi.AbstractControllerTest;
import com.example.asyncapi.common.ApplicationException;
import com.example.asyncapi.service.internal.callback.service.CallbackService;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.ResultActions;

import java.time.OffsetDateTime;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CallbackControllerTest extends AbstractControllerTest {

  @Mock
  private CallbackService callbackService;

  @InjectMocks
  private CallbackController controller;

  @Override
  protected Object getController() {
    return controller;
  }

  @Test
  public void shouldSucceedForCorrectPost() throws Exception {
    ArgumentCaptor<CallbackResponse> captor = ArgumentCaptor.forClass(CallbackResponse.class);
    ResultActions resultActions = mvc.perform(post("/callback.service/method")
      .contentType(APPLICATION_JSON)
      .content("{\"timestamp\": \"2015-10-15T11:17:41+03:00\", \"response\": {\"requestId\": \"123\", \"data\": \"321\"}}"));


    resultActions.andExpect(status().isOk());

    verify(callbackService, times(1)).storeResponse(captor.capture());

    CallbackResponse response = captor.getValue();
    assertThat(response.getTimestamp().toEpochSecond(), equalTo(OffsetDateTime.parse("2015-10-15T11:17:41+03:00").toEpochSecond()));
    assertThat(response.getResponse().getRequestId(), equalTo("123"));
    assertThat(response.getResponse().getData(), equalTo("321"));
  }

  @Test
  public void shouldNotIgnoreApplicationException() throws Exception {
    doThrow(new ApplicationException()).when(callbackService).storeResponse(any());

    ResultActions resultActions = mvc.perform(post("/callback.service/method")
      .contentType(APPLICATION_JSON)
      .content("{\"timestamp\": \"2015-10-15T11:17:41+03:00\", \"response\": {\"requestId\": \"123\", \"data\": \"321\"}}"));

    resultActions.andExpect(status().isServiceUnavailable());
  }

  @Test
  public void shouldIgnoreApplicationException() throws Exception {
    doThrow(new ApplicationException()).when(callbackService).storeResponse(any());
    ReflectionTestUtils.setField(controller, "ignoreExceptions", true);

    ResultActions resultActions = mvc.perform(post("/callback.service/method")
      .contentType(APPLICATION_JSON)
      .content("{\"timestamp\": \"2015-10-15T11:17:41+03:00\", \"response\": {\"requestId\": \"123\", \"data\": \"321\"}}"));

    resultActions.andExpect(status().isOk());
  }
}