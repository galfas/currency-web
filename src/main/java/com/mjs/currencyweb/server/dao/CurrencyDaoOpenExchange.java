package com.mjs.currencyweb.server.dao;

import java.io.IOException;
import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.mjs.currencyweb.server.utils.CurrencyHelper;

import feign.FeignException;

@Component
public class CurrencyDaoOpenExchange implements CurrencyDao {

  private static final Logger logger = LoggerFactory.getLogger(CurrencyDaoOpenExchange.class);

  @Autowired
  private OpenExchangeProvider openExchangeProvider;

  @Value("${openexchange.api.id}")
  private String openExchangeApiKey;

  @Override
  public BigDecimal calculateCurrencyFor(String desirableCurrency) throws IOException {

    logger.debug(String.format("The fetch method of data will be performed from currency '%s'", desirableCurrency));

    BigDecimal value = null;
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

    return fetchValueFromJson(desirableCurrency, resultAsJson);
  }

  private BigDecimal fetchValueFromJson(String desirableCurrency, JsonObject responseAsJson) {
    JsonObject rateAsJson = responseAsJson.getAsJsonObject("rates");

    return CurrencyHelper.convertToBigDecimal(rateAsJson.get(desirableCurrency).getAsBigDecimal());
  }
}
