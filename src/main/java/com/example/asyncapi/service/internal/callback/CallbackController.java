package com.example.asyncapi.service.internal.callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallbackController {

  @Autowired
  private CallbackResponseRepository callbackResponseRepository;

  @RequestMapping(value = "/callback.service/method", method = RequestMethod.POST)
  public ResponseEntity<Void> callbackResponse(@RequestBody AsyncCallbackResponse response) {
    callbackResponseRepository.storeResponse(response);

    return new ResponseEntity<>(HttpStatus.OK);
  }
}
