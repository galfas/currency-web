package com.mjs.currencyweb.server.controller;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mjs.currencyweb.server.model.Result;
import com.mjs.currencyweb.server.business.CurrencyBO;
import com.mjs.currencyweb.server.utils.CurrencyHelper;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(SpringJUnit4ClassRunner.class)
public class CurrencyControllerTest extends BaseController {

  @Mock
  CurrencyBO currencyBO;

  @InjectMocks
  CurrencyController currencyController = new CurrencyController();

  @Test
  public void shouldConvertCurrencyTo() throws IOException {
    String fromCurrency = "BRL";
    String toCurrency = "EUR";
    BigDecimal value = new BigDecimal(100);

    Result expectedResult = new Result(toCurrency, "Reais", CurrencyHelper.convertToBigDecimal(370).setScale(12));

    Mockito.when(currencyBO.calculateCurrencyFor(fromCurrency, value, toCurrency)).thenReturn(expectedResult);

    Result result2 = currencyController.getCurrency(fromCurrency, value, toCurrency);

    verify(currencyBO, times(1)).calculateCurrencyFor(fromCurrency, value, toCurrency);

    Assert.assertNotNull(result2);
    Assert.assertSame(expectedResult, result2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldReturnIllegalArgumentsExceptionWithWrongParams() throws IOException {
    String fromCurrency = "BRL";
    String toCurrency = "EURA";
    BigDecimal value = new BigDecimal(100);

    Mockito.when(currencyBO.calculateCurrencyFor(fromCurrency, value, toCurrency))
      .thenThrow(IllegalArgumentException.class);


    currencyController.getCurrency(fromCurrency, value, toCurrency);

    verify(currencyBO, times(1)).calculateCurrencyFor(fromCurrency, value, toCurrency);
  }
}
