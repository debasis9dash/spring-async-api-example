package com.example.asyncapi.service.internal.callback;

import com.example.asyncapi.common.ApplicationException;
import com.example.asyncapi.service.internal.callback.service.CallbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallbackController {

  @Autowired
  private CallbackService callbackService;

  @Value("${asyncapi.service.internal.callback.ignoreExceptions:false}")
  private boolean ignoreExceptions;

  @RequestMapping(value = "/callback.service/method", method = RequestMethod.POST)
  public ResponseEntity<Void> callbackResponse(@RequestBody CallbackResponse response) {
    try {
      callbackService.storeResponse(response);
    }
    catch (ApplicationException ex) {
      if (!ignoreExceptions) {
        throw ex;
      }
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
