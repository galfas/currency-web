package com.mjs.currencyweb.server.business.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjs.currencyweb.server.business.ActivityBO;
import com.mjs.currencyweb.server.business.CurrencyBO;
import com.mjs.currencyweb.server.dao.CurrencyDao;
import com.mjs.currencyweb.server.model.Quote;
import com.mjs.currencyweb.server.model.Result;

@Component
public class CurrencyBOImpl implements CurrencyBO {

  @Autowired
  private CurrencyDao currencyDao;

  @Autowired
  private ActivityBO activityBO;

  @Override
  public Result calculateCurrencyFor(String currencyFrom, BigDecimal value, String currencyTo) throws IOException {
    Map<String, String> availableCurrencies = getAvailableCurrencies();

    validateCurrencyInput(currencyFrom, currencyTo, availableCurrencies);

    Quote quote = currencyDao.getQuotes(currencyFrom, currencyTo);

    BigDecimal exchangedValue = calculateExchange(quote, value);

    Result result = new Result(availableCurrencies.get(quote.getCurrencyFrom()), quote.getCurrencyFrom(), value,
      availableCurrencies.get(quote.getCurrencyTo()), quote.getCurrencyTo(), exchangedValue);

    activityBO.save(result);
    return result;
  }

  private void validateCurrencyInput(String currencyFrom, String currencyTo, Map<String, String> availableCurrencies) {
    if (!availableCurrencies.containsKey(currencyFrom) ||
      !availableCurrencies.containsKey(currencyTo)) {

      throw new IllegalArgumentException();
    }
  }

  private BigDecimal calculateExchange(Quote quote, BigDecimal value) {
    BigDecimal factor = quote.getCurrencyToValue();
    BigDecimal rate = factor.divide(quote.getCurrencyFromValue(), 10, BigDecimal.ROUND_HALF_UP);

    BigDecimal exchangedValue = value.multiply(rate);
    return exchangedValue.setScale(2, BigDecimal.ROUND_HALF_UP);
  }

  @Override
  public Map<String, String> getAvailableCurrencies() throws IOException {
    return currencyDao.fetchAvailableCurrencies();
  }
}
