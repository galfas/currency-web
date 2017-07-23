package com.mjs.currencyweb.server.dao;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.mjs.currencyweb.server.model.Quote;
import com.mjs.currencyweb.server.utils.CurrencyHelper;

import feign.FeignException;

@Component
public class CurrencyDaoOpenExchange implements CurrencyDao {

  private static final Logger logger = LoggerFactory.getLogger(CurrencyDaoOpenExchange.class);
  public static final String RATES_FIELD = "rates";

  @Autowired
  private OpenExchangeProvider openExchangeProvider;

  @Value("${openexchange.api.id}")
  private String openExchangeApiKey;

  @Override
  public Quote getQuotes(String currencyFrom, String currencyTo) throws IOException {

    logger.debug(String.format("Fetch method of data will be performed from '%s' to '%s'", currencyFrom, currencyTo));

    JsonObject resultAsJson = null;
    try {
      resultAsJson = openExchangeProvider.getCurrency(openExchangeApiKey);
    } catch (FeignException ex) {
      logger.error(String.format("Client return the error '%s'", ex.getCause()));
      throw new IOException(ex.getCause());
    } catch (Exception ex) {
      logger.error(String.format("Unexpected error '%s'", ex.getCause()));
      throw ex;
    }

    return fetchValueFromJson(currencyFrom, currencyTo, resultAsJson);
  }

  @Override
  public Map<String, String> fetchAvailableCurrencies() throws IOException {
    Map<String, String> map = new HashMap<String, String>();

    logger.debug(String.format("The available currencies data will be performed from currency"));

    JsonObject resultAsJson = null;
    try {
      resultAsJson = openExchangeProvider.getAvailableCurrencies(openExchangeApiKey);
    } catch (FeignException ex) {
      logger.error(String.format("Client return the error '%s'", ex.getCause()));
      throw new IOException(ex.getCause());
    } catch (Exception ex) {
      logger.error(String.format("Unexpected error '%s'", ex.getCause()));
      throw ex;
    }

    return fetchAvailableCurrenciesFrom(resultAsJson);
  }

  private Map<String, String> fetchAvailableCurrenciesFrom(JsonObject responseAsJson) throws IOException {
    return new ObjectMapper().readValue(
      responseAsJson.toString(), new TypeReference<Map<String, String>>() {
      });
  }

  private Quote fetchValueFromJson(String currencyFrom, String currencyTo, JsonObject responseAsJson) {
    JsonObject rateAsJson = responseAsJson.getAsJsonObject(RATES_FIELD);

    return new Quote(currencyFrom, CurrencyHelper.convertToBigDecimal(rateAsJson.get(currencyFrom).getAsBigDecimal()),
      currencyTo, CurrencyHelper.convertToBigDecimal(rateAsJson.get(currencyTo).getAsBigDecimal()));
  }
}
