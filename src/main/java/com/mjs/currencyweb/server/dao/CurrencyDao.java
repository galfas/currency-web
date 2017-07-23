package com.mjs.currencyweb.server.dao;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import com.mjs.currencyweb.server.model.Quote;


public interface CurrencyDao {
  Quote getQuotes(String fromCurrency, String toCurrency) throws IOException;

  Map<String, String> fetchAvailableCurrencies() throws IOException;
}
