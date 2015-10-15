package com.example.asyncapi.service.internal.callback.service;

import com.example.asyncapi.common.ApplicationException;
import com.example.asyncapi.service.internal.AsyncResponseTimedOutException;
import com.example.asyncapi.service.internal.callback.CallbackResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class CallbackService {

  @Autowired
  private CallbackBackendService callbackBackendService;

  @Autowired
  private CallbackResponseRepository callbackResponseRepository;


  public CallbackResponse pollCompletedResponse(String requestId) {
    CallbackResponseHolder storedResponse = callbackResponseRepository.getResponse(requestId);
    if (storedResponse != null && storedResponse.isCompleted()) {
      return storedResponse.getResponse();
    }
    return null;
  }

  public String executeCallbackRequest(long timeToLiveMillis) {
    String requestId = java.util.UUID.randomUUID().toString();
    OffsetDateTime expiresAt = OffsetDateTime.now().plusNanos(timeToLiveMillis * 1000);

    callbackBackendService.queryBackend(requestId);

    CallbackResponseHolder holder = callbackResponseRepository.initCallbackResponse(requestId, expiresAt);
    return holder.getRequestId();
  }

  public void storeResponse(CallbackResponse response) {
    String requestId = response.getResponse().getRequestId();
    CallbackResponseHolder storedResponse = callbackResponseRepository.getResponse(requestId);

    if (storedResponse == null) {
      throw new ApplicationException("callback has not been initialized: requestId: " + requestId);
    }
    else if (storedResponse.isCompleted()) {
      throw new ApplicationException("callback has already been completed: requestId: " + requestId);
    }
    else if (storedResponse.isExpired(OffsetDateTime.now())) {
      throw new AsyncResponseTimedOutException("callback has been timed out: requestId: " + requestId);
    }
    callbackResponseRepository.storeCallbackResponse(requestId, storedResponse.getExpiresAt(), response);
  }

}
