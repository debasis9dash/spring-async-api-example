package com.example.asyncapi.service.external.method;

import com.example.asyncapi.service.internal.callback.CallbackResponse;
import com.example.asyncapi.service.internal.callback.service.CallbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.concurrent.Callable;

@RestController
public class MethodController {

  @Autowired
  private CallbackService callbackService;

  @Value("${asyncapi.service.pollingTimeout:500}")
  private long pollingTimeout;

  @Value("${asyncapi.service.timeout:5000}")
  private long serviceTimeout;

  @RequestMapping(value = "/service/method", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public WebAsyncTask<MethodResponse> method() {

    Callable<MethodResponse> callable = () -> {
      String requestId = callbackService.executeCallbackRequest(getPollingTimeout());

      CallbackResponse callbackResponse = null;
      do {
        Thread.sleep(getPollingTimeout());
        callbackResponse = callbackService.pollCompletedResponse(requestId);
      } while (callbackResponse == null);

      return MethodResponse.from(callbackResponse);
    };

    return new WebAsyncTask<>(getServiceTimeout(), callable);
  }

  public long getPollingTimeout() {
    return pollingTimeout;
  }

  public void setPollingTimeout(long pollingTimeout) {
    this.pollingTimeout = pollingTimeout;
  }

  public long getServiceTimeout() {
    return serviceTimeout;
  }

  public void setServiceTimeout(long serviceTimeout) {
    this.serviceTimeout = serviceTimeout;
  }
}
