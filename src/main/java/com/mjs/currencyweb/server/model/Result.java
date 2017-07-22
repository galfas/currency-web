package com.mjs.currencyweb.server.model;

import java.math.BigDecimal;

/**
 * Created by marcelosaciloto on 15/07/2017.
 */
public class Result {
  private String currencyName;
  private String currencySymbol;
  private BigDecimal value;

  public Result() {
  }

  public Result(String currencyName, String currencySymbol, BigDecimal value) {
    this.value = value;
    this.currencyName = currencyName;
    this.currencySymbol = currencySymbol;
  }

  public BigDecimal getValue() {
    return value;
  }

  public String getCurrencyName() {
    return currencyName;
  }

  public String getCurrencySymbol() {
    return currencySymbol;
  }
}
