package com.mjs.currencyweb.server.business;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import com.mjs.currencyweb.server.model.Result;

public interface CurrencyBO {
  Result calculateCurrencyFor(String fromCurrency, BigDecimal value, String toCurrency) throws IOException;

  Map<String, String> getAvailableCurrencies() throws IOException;
}
