package com.mjs.currencyweb.server.dao;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mjs.currencyweb.server.utils.CurrencyHelper;

import feign.FeignException;


@RunWith(SpringJUnit4ClassRunner.class)
public class CurrencyDaoOpenExchangeTest {

  @Mock
  private OpenExchangeProvider openExchangeProvider;

  @InjectMocks
  CurrencyDaoOpenExchange currencyDaoOpenExchange = new CurrencyDaoOpenExchange();


  @Test
  public void shouldCalculateCurrencyFor() throws IOException {
    ClassLoader classLoader = this.getClass().getClassLoader();
    String jsonAsString = IOUtils.toString(classLoader.getResourceAsStream("latest.json"), "UTF-8");

    JsonObject jsonFile = new Gson().fromJson(jsonAsString, JsonObject.class);

    Mockito.when(openExchangeProvider.getCurrency(Mockito.anyString())).thenReturn(jsonFile);
    BigDecimal result = currencyDaoOpenExchange.calculateCurrencyFor("EUR");

    Assert.assertNotNull(result);
    Assert.assertEquals(result, CurrencyHelper.convertToBigDecimal(0.871992));
  }

  @Test(expected = IOException.class)
  public void shouldThrowIOExceptionWhenCurrencyServerIsOut() throws IOException {
    ClassLoader classLoader = this.getClass().getClassLoader();
    String jsonAsString = IOUtils.toString(classLoader.getResourceAsStream("latest.json"), "UTF-8");

    JsonObject jsonFile = new Gson().fromJson(jsonAsString, JsonObject.class);

    Mockito.when(openExchangeProvider.getCurrency(Mockito.anyString())).thenThrow(FeignException.class);
    BigDecimal result = currencyDaoOpenExchange.calculateCurrencyFor("EUR");
  }

  @Test(expected = Exception.class)
  public void shouldThrowExceptionWhenCurrencyServerIsOut() throws IOException {
    ClassLoader classLoader = this.getClass().getClassLoader();
    String jsonAsString = IOUtils.toString(classLoader.getResourceAsStream("latest.json"), "UTF-8");

    JsonObject jsonFile = new Gson().fromJson(jsonAsString, JsonObject.class);

    Mockito.when(openExchangeProvider.getCurrency(Mockito.anyString())).thenThrow(Exception.class);
    BigDecimal result = currencyDaoOpenExchange.calculateCurrencyFor("EUR");
  }
}
