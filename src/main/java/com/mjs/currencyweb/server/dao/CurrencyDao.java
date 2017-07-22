package com.mjs.currencyweb.server.dao;

import java.io.IOException;
import java.math.BigDecimal;


public interface CurrencyDao {
  BigDecimal calculateCurrencyFor(String toCurrency) throws IOException;
}
