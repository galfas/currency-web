package com.mjs.currencyweb.server.utils;

import java.math.BigDecimal;

public class CurrencyHelper {

  private static int DECIMALS_HANDLE = 10;

  public static BigDecimal convertToBigDecimal(int value) {
    return new BigDecimal(value).setScale(DECIMALS_HANDLE, BigDecimal.ROUND_HALF_UP);
  }

  public static BigDecimal convertToBigDecimal(Double value) {
    return new BigDecimal(value).setScale(DECIMALS_HANDLE, BigDecimal.ROUND_HALF_UP);
  }

  public static BigDecimal convertToBigDecimal(BigDecimal value) {
    return value.setScale(DECIMALS_HANDLE, BigDecimal.ROUND_HALF_UP);
  }
}
