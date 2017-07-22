package com.mjs.currencyweb.server.utils;

import java.math.BigDecimal;

public class CurrencyHelper {

  public static BigDecimal convertToBigDecimal(int value) {
    return new BigDecimal(value).setScale(12, BigDecimal.ROUND_HALF_UP);
  }

  public static BigDecimal convertToBigDecimal(Double value) {
    return new BigDecimal(value).setScale(12, BigDecimal.ROUND_HALF_UP);
  }

  public static BigDecimal convertToBigDecimal(BigDecimal value) {
    return value.setScale(12, BigDecimal.ROUND_HALF_UP);
  }
}
