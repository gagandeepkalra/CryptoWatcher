package com.cryptocurrency.watcher;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@SpringBootApplication
@EnableBatchProcessing
public class CryptocurrencyApplication {

  public static void main(String[] args) {
    SpringApplication.run(CryptocurrencyApplication.class, args);
  }

  /**
   * Executor for Http Requests.
   *
   * @return executor with specified poolSize.
   */
  @Bean
  @Qualifier("AsyncHttp")
  @PostConstruct
  public Executor getAsyncHttpExecutor() {
    final ThreadFactory threadFactory = new ThreadFactoryBuilder()
        .setNameFormat("Async-Http-pool-%d")
        .build();
    return new ConcurrentTaskExecutor(Executors.newFixedThreadPool(10, threadFactory));
  }

  @Bean
  public RestTemplate getRestTemplate() {
    final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(getHttpClient());
    return new RestTemplate(requestFactory);
  }

  @Bean
  HttpClient getHttpClient() {
    return HttpClients.custom()
        .setConnectionManager(getPoolingHttpClientConnectionManager())
        .setDefaultRequestConfig(getRequestConfig())
        .build();
  }

  @Bean
  public PoolingHttpClientConnectionManager getPoolingHttpClientConnectionManager() {
    final PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
    connectionManager.setMaxTotal(100);
    connectionManager.setDefaultMaxPerRoute(100);
    return connectionManager;
  }

  private RequestConfig getRequestConfig() {
    return RequestConfig.custom()
        .setConnectTimeout(5000)
        .setSocketTimeout(25000)
        .setConnectionRequestTimeout(2000)
        .setCookieSpec(CookieSpecs.IGNORE_COOKIES)
        .build();
  }
}
