package com.mjs.currencyweb.server.model;


import java.math.BigDecimal;

public class Quote {
  private BigDecimal currencyFromValue;
  private BigDecimal currencyToValue;
  private String currencyFrom;
  private String currencyTo;


  public Quote(String currencyFrom, BigDecimal currencyFromValue, String currencyTo, BigDecimal currencyToValue) {
    this.currencyFromValue = currencyFromValue;
    this.currencyToValue = currencyToValue;
    this.currencyFrom = currencyFrom;
    this.currencyTo = currencyTo;
  }

  public BigDecimal getCurrencyFromValue() {
    return currencyFromValue;
  }

  public BigDecimal getCurrencyToValue() {
    return currencyToValue;
  }

  public String getCurrencyFrom() {
    return currencyFrom;
  }

  public String getCurrencyTo() {
    return currencyTo;
  }
}
