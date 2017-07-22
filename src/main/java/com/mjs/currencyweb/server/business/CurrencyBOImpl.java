package com.mjs.currencyweb.server.business;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mjs.currencyweb.server.dao.CurrencyDao;
import com.mjs.currencyweb.server.model.Result;

@Component
public class CurrencyBOImpl implements CurrencyBO {

  @Autowired
  private CurrencyDao currencyDao;

  @Value("#{\"${openexchange.api.available.rates}\".split(\",\")}")
  private List<String> availableCurrencies;

  @Override
  public Result calculateCurrencyFor(String fromCurrency, BigDecimal value, String toCurrency) throws IOException {
    BigDecimal response = currencyDao.calculateCurrencyFor(toCurrency);
    return new Result("Euro", toCurrency, response);
  }
}
