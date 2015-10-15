package com.example.asyncapi.service.external.method;

import com.example.asyncapi.AbstractControllerTest;
import com.example.asyncapi.service.internal.callback.CallbackResponse;
import com.example.asyncapi.service.internal.callback.service.CallbackService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MvcResult;

import java.time.OffsetDateTime;

import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MethodControllerTest extends AbstractControllerTest {

  @Mock
  private CallbackService callbackService;

  @InjectMocks
  private MethodController controller;

  @Override
  protected Object getController() {
    return controller;
  }

  @Before
  public void setupController() {
    controller.setServiceTimeout(10);
    controller.setPollingTimeout(2);
  }

  @Test
  public void shouldSucceedGetMethod() throws Exception {
    OffsetDateTime timestamp = OffsetDateTime.now();
    CallbackResponse callbackResponse = new CallbackResponse(timestamp, new CallbackResponse.Response("id", "di"));

    doReturn(callbackResponse)
      .when(callbackService).pollCompletedResponse(anyString());

    MvcResult mvcResult = mvc.perform(get("/service/method"))
      .andExpect(request().asyncStarted())
      .andExpect(request().asyncResult(instanceOf(MethodResponse.class)))
      .andReturn();

    mvc.perform(asyncDispatch(mvcResult))
      .andExpect(status().isOk())
      .andExpect(content().contentType("application/json"))
      .andExpect(jsonPath("$.timestamp").value(timestamp.format(APPLICATION_DATETIME_FORMAT)))
      .andExpect(jsonPath("$.response.requestId").value(callbackResponse.getResponse().getRequestId()))
      .andExpect(jsonPath("$.response.data").value(callbackResponse.getResponse().getData()))
    ;
  }
}