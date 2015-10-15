package com.example.asyncapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableCaching
public class CacheConfiguration {

  @Value("${asyncapi.cacheConfName}")
  private String cacheConfName;

  @Bean
  public EhCacheCacheManager ehCacheCacheManager() {
    return new EhCacheCacheManager(ehCacheManagerFactoryBean().getObject());
  }

  @Bean
  public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
    EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
    cacheManagerFactoryBean.setConfigLocation(new ClassPathResource(cacheConfName));
    cacheManagerFactoryBean.setShared(true);
    return cacheManagerFactoryBean;
  }

}
