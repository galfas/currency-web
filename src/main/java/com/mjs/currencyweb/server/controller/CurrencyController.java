package com.mjs.currencyweb.server.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mjs.currencyweb.server.model.Result;
import com.mjs.currencyweb.server.business.CurrencyBO;

@RestController
public class CurrencyController extends BaseController {

  @Autowired
  CurrencyBO currencyBO;

  @RequestMapping("/currency/from/{fromCurrency}/value/{value}/to/{toCurrency}")
  public Result getCurrency(@PathVariable String fromCurrency,
                            @PathVariable BigDecimal value,
                            @PathVariable String toCurrency) throws IOException {



    return currencyBO.calculateCurrencyFor(fromCurrency, value, toCurrency);
  }

  @RequestMapping("/availables")
  public Map<String, String> getCurrency() throws IOException {
    return currencyBO.getAvailableCurrencies();
  }
}
