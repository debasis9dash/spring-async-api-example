package com.example.asyncapi;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

public abstract class MockitoTest {

  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

}
