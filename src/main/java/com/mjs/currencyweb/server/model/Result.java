package com.mjs.currencyweb.server.model;

import java.math.BigDecimal;


public class Result {

  private String currencyFromName;
  private String currencyFromSymbol;
  private String currencyToName;
  private String currencyToSymbol;
  private BigDecimal valueFrom;
  private BigDecimal valueTo;

  public Result() {
  }

  public Result(String currencyFromName, String currencyFromSymbol, BigDecimal valueFrom, String currencyToName,
                String currencyToSymbol, BigDecimal valueTo) {

    this.currencyFromName = currencyFromName;
    this.currencyFromSymbol = currencyFromSymbol;
    this.currencyToName = currencyToName;
    this.currencyToSymbol = currencyToSymbol;
    this.valueFrom = valueFrom;
    this.valueTo = valueTo;
  }

  public String getCurrencyFromName() {
    return currencyFromName;
  }

  public String getCurrencyFromSymbol() {
    return currencyFromSymbol;
  }

  public String getCurrencyToName() {
    return currencyToName;
  }

  public String getCurrencyToSymbol() {
    return currencyToSymbol;
  }

  public BigDecimal getValueFrom() {
    return valueFrom;
  }

  public BigDecimal getValueTo() {
    return valueTo;
  }
}
