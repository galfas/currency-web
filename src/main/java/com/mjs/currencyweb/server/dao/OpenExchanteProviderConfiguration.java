package com.mjs.currencyweb.server.dao;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

@Configuration("openExchangeAppConfig")
public class OpenExchanteProviderConfiguration {

  @Value("${openexchange.api.host}")
  private String apiEndpoint;

  @Value("${openexchange.api.timeout}")
  private int TIME_OUT;


  private OkHttpClient configureClient() {
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    builder.connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS);
    builder.connectionPool(new ConnectionPool(3, 5, TimeUnit.MINUTES));

    return builder.build();
  }

  @Bean(name = "openExchangeHttpClient")
  public OpenExchangeProvider buildStatsProvider() {
    return Feign.builder()
      .encoder(new GsonEncoder())
      .decoder(new GsonDecoder())
      .client(new feign.okhttp.OkHttpClient(configureClient()))
      .target(OpenExchangeProvider.class, apiEndpoint);
  }
}
