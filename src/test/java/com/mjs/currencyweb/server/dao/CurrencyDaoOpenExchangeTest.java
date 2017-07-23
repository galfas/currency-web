package com.mjs.currencyweb.server.dao;

import java.io.IOException;
import java.util.Map;

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
import com.mjs.currencyweb.server.model.Quote;
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
    Quote quote = currencyDaoOpenExchange.getQuotes("EUR", "BRL");

    Assert.assertNotNull(quote);
    Assert.assertEquals(quote.getCurrencyFrom(), "EUR");
    Assert.assertEquals(quote.getCurrencyFromValue(), CurrencyHelper.convertToBigDecimal(0.871992));
    Assert.assertEquals(quote.getCurrencyTo(), "BRL");
    Assert.assertEquals(quote.getCurrencyToValue(), CurrencyHelper.convertToBigDecimal(3.1803));
  }

  @Test(expected = IOException.class)
  public void shouldThrowIOExceptionWhenCurrencyServerIsOut() throws IOException {
    ClassLoader classLoader = this.getClass().getClassLoader();
    String jsonAsString = IOUtils.toString(classLoader.getResourceAsStream("latest.json"), "UTF-8");

    JsonObject jsonFile = new Gson().fromJson(jsonAsString, JsonObject.class);

    Mockito.when(openExchangeProvider.getCurrency(Mockito.anyString())).thenThrow(FeignException.class);
    currencyDaoOpenExchange.getQuotes("EUR", "BRL");
  }

  @Test(expected = Exception.class)
  public void shouldThrowExceptionWhenCurrencyServerIsOut() throws IOException {
    ClassLoader classLoader = this.getClass().getClassLoader();
    String jsonAsString = IOUtils.toString(classLoader.getResourceAsStream("latest.json"), "UTF-8");

    JsonObject jsonFile = new Gson().fromJson(jsonAsString, JsonObject.class);

    Mockito.when(openExchangeProvider.getCurrency(Mockito.anyString())).thenThrow(Exception.class);
    currencyDaoOpenExchange.getQuotes("EUR", "BRL");
  }

  @Test
  public void shouldGetAvailableCurrencies() throws IOException {
    ClassLoader classLoader = this.getClass().getClassLoader();
    String jsonAsString = IOUtils.toString(classLoader.getResourceAsStream("currencies.json"), "UTF-8");

    JsonObject jsonFile = new Gson().fromJson(jsonAsString, JsonObject.class);

    Mockito.when(openExchangeProvider.getAvailableCurrencies(Mockito.anyString())).thenReturn(jsonFile);
    Map<String, String> currencies = currencyDaoOpenExchange.fetchAvailableCurrencies();

    Assert.assertNotNull(currencies);
    Assert.assertEquals(currencies.size(), 168);
    Assert.assertEquals(currencies.get("BRL"), "Brazilian Real");
    Assert.assertEquals(currencies.get("ZMW"), "Zambian Kwacha");
    Assert.assertEquals(currencies.get("NNN"), null);
  }

  @Test(expected = IOException.class)
  public void shouldNotGetAvailableCurrenciesWithIOException() throws IOException {
    ClassLoader classLoader = this.getClass().getClassLoader();
    String jsonAsString = IOUtils.toString(classLoader.getResourceAsStream("currencies.json"), "UTF-8");

    JsonObject jsonFile = new Gson().fromJson(jsonAsString, JsonObject.class);

    Mockito.when(openExchangeProvider.getAvailableCurrencies(Mockito.anyString())).thenThrow(FeignException.class);
    currencyDaoOpenExchange.fetchAvailableCurrencies();
  }

  @Test(expected = Exception.class)
  public void shouldNotGetAvailableCurrenciesWithException() throws IOException {
    ClassLoader classLoader = this.getClass().getClassLoader();
    String jsonAsString = IOUtils.toString(classLoader.getResourceAsStream("currencies.json"), "UTF-8");

    JsonObject jsonFile = new Gson().fromJson(jsonAsString, JsonObject.class);

    Mockito.when(openExchangeProvider.getAvailableCurrencies(Mockito.anyString())).thenThrow(Exception.class);
    currencyDaoOpenExchange.fetchAvailableCurrencies();
  }
}
