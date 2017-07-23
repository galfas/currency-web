package com.mjs.currencyweb.server.dao;


import com.google.gson.JsonObject;

import feign.Param;
import feign.RequestLine;

public interface OpenExchangeProvider {

  @RequestLine("GET /latest.json?app_id={appId}")
  JsonObject getCurrency(@Param("appId") String appId);

  @RequestLine("GET /currencies.json?app_id={appId}")
  JsonObject getAvailableCurrencies(@Param("appId") String appId);
}
