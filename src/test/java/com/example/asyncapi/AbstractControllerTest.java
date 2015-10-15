package com.example.asyncapi;


import com.example.asyncapi.service.GlobalControllerExceptionHandler;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import java.lang.reflect.Method;
import java.time.format.DateTimeFormatter;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class, initializers = ConfigFileApplicationContextInitializer.class)
public abstract class AbstractControllerTest<T> {

  public static final DateTimeFormatter APPLICATION_DATETIME_FORMAT = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

  protected Logger log = LoggerFactory.getLogger(this.getClass());

  protected MockMvc mvc;

  @Autowired
  private MappingJackson2HttpMessageConverter jacksonMessageConverter;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    this.mvc = MockMvcBuilders.standaloneSetup(getController())
      .setHandlerExceptionResolvers(createExceptionResolver())
      .setMessageConverters(jacksonMessageConverter)
      .build();
  }

  private ExceptionHandlerExceptionResolver createExceptionResolver() {
    ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
      protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod, Exception exception) {
        log.debug("Encountered exception: ", exception);
        Method method = new ExceptionHandlerMethodResolver(getController().getClass()).resolveMethod(exception);
        if (method != null) {
          return new ServletInvocableHandlerMethod(getController(), method);
        }

        method = new ExceptionHandlerMethodResolver(GlobalControllerExceptionHandler.class).resolveMethod(exception);
        return new ServletInvocableHandlerMethod(new GlobalControllerExceptionHandler(), method);
      }
    };
    exceptionResolver.afterPropertiesSet();
    return exceptionResolver;
  }

  protected abstract T getController();
}
