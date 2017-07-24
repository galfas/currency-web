package com.mjs.currencyweb.server.business;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mjs.currencyweb.server.business.impl.CurrencyBOImpl;
import com.mjs.currencyweb.server.dao.CurrencyDao;
import com.mjs.currencyweb.server.model.Quote;
import com.mjs.currencyweb.server.model.Result;
import com.mjs.currencyweb.server.utils.CurrencyHelper;

@RunWith(SpringJUnit4ClassRunner.class)
public class CurrencyBOImplTest {

  @Mock
  private CurrencyDao currencyDao;

  @Mock
  private ActivityBO activityBO;


  @InjectMocks
  CurrencyBO currencyBO = new CurrencyBOImpl();

  @Test
  public void shouldCalculateCurrencyExchange() throws IOException {
    String currencyFrom = "BRL";
    String currencyFromName = "Brazilian Real";
    String currencyTo = "USD";
    String currencyToName = "American Dollar";
    BigDecimal valueFrom = CurrencyHelper.convertToBigDecimal(100.20);

    Quote quote = new Quote(currencyFrom, CurrencyHelper.convertToBigDecimal(3.1425),
      currencyTo, CurrencyHelper.convertToBigDecimal(1));

    Mockito.when(currencyDao.fetchAvailableCurrencies()).thenReturn(mapOfCurrenciesBuilder());
    Mockito.when(currencyDao.getQuotes(currencyFrom, currencyTo)).thenReturn(quote);

    Result result = currencyBO.calculateCurrencyFor(currencyFrom, valueFrom, currencyTo);

    Assert.assertNotNull(result);
    Assert.assertEquals(currencyFromName, result.getCurrencyFromName());
    Assert.assertEquals(currencyFrom, result.getCurrencyFromSymbol());
    Assert.assertEquals(currencyToName, result.getCurrencyToName());
    Assert.assertEquals(currencyTo, result.getCurrencyToSymbol());
    Assert.assertEquals(CurrencyHelper.convertToBigDecimal(31.89).setScale(2), result.getValueTo());
    Assert.assertEquals(valueFrom, result.getValueFrom());
  }

  @Test
  public void shouldCalculateCurrencyExchangeWithoutBaseCurrency() throws IOException {
    String currencyFrom = "EUR";
    String currencyFromName = "Euro";
    String currencyTo = "BRL";
    String currencyToName = "Brazilian Real";
    BigDecimal valueFrom = CurrencyHelper.convertToBigDecimal(210.20);

    Quote quote = new Quote(currencyFrom, CurrencyHelper.convertToBigDecimal(0.85752),
      currencyTo, CurrencyHelper.convertToBigDecimal(3.1425));

    Mockito.when(currencyDao.fetchAvailableCurrencies()).thenReturn(mapOfCurrenciesBuilder());
    Mockito.when(currencyDao.getQuotes(currencyFrom, currencyTo)).thenReturn(quote);

    Result result = currencyBO.calculateCurrencyFor(currencyFrom, valueFrom, currencyTo);

    Assert.assertNotNull(result);
    Assert.assertEquals(currencyFromName, result.getCurrencyFromName());
    Assert.assertEquals(currencyFrom, result.getCurrencyFromSymbol());
    Assert.assertEquals(currencyToName, result.getCurrencyToName());
    Assert.assertEquals(currencyTo, result.getCurrencyToSymbol());
    Assert.assertEquals(CurrencyHelper.convertToBigDecimal(770.31).setScale(2), result.getValueTo());
    Assert.assertEquals(valueFrom, result.getValueFrom());
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldNotCalculateCurrencyExchangeForUnknownCurrencyFrom() throws IOException {
    String currencyFrom = "BRL";
    String currencyTo = "USD";
    BigDecimal valueFrom = CurrencyHelper.convertToBigDecimal(100.20);

    Map<String, String> map = new HashMap<String, String>();
    map.put("USD", "American Dollar");
    map.put("EUR", "Euro");

    Mockito.when(currencyDao.fetchAvailableCurrencies()).thenReturn(map);

    currencyBO.calculateCurrencyFor(currencyFrom, valueFrom, currencyTo);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldNotCalculateCurrencyExchangeForUnknownCurrencyTo() throws IOException {
    String currencyFrom = "BRL";
    String currencyTo = "USD";
    BigDecimal valueFrom = CurrencyHelper.convertToBigDecimal(100.20);

    Map<String, String> map = new HashMap<String, String>();
    map.put("BRL", "Brazilian Real");
    map.put("EUR", "Euro");

    Mockito.when(currencyDao.fetchAvailableCurrencies()).thenReturn(map);

    currencyBO.calculateCurrencyFor(currencyFrom, valueFrom, currencyTo);
  }

  @Test
  public void shouldFetchAllCurrencies() throws IOException {
    Mockito.when(currencyDao.fetchAvailableCurrencies()).thenReturn(mapOfCurrenciesBuilder());

    Map<String, String> mapResult = currencyBO.getAvailableCurrencies();

    Assert.assertNotNull(mapResult);
    Assert.assertEquals(7, mapResult.size());
    Assert.assertTrue(mapResult.containsKey("BRL"));
    Assert.assertFalse(mapResult.containsKey("AAA"));
  }


  private Map<String, String> mapOfCurrenciesBuilder() {
    Map<String, String> mapOfCurrencies = new HashMap<String, String>();
    mapOfCurrencies.put("AED", "United Arab Emirates Dirham");
    mapOfCurrencies.put("AFN", "Afghan Afghani");
    mapOfCurrencies.put("BRL", "Brazilian Real");
    mapOfCurrencies.put("EUR", "Euro");
    mapOfCurrencies.put("DOP", "Dominican Peso");
    mapOfCurrencies.put("NAD", "Namibian Dollar");
    mapOfCurrencies.put("USD", "American Dollar");

    return mapOfCurrencies;
  }

}
