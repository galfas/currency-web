package com.mjs.currencyweb.currency;


import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.mjs.currencyweb.AbstractIntegrationTest;
import com.mjs.currencyweb.server.model.Result;
import com.mjs.currencyweb.server.utils.CurrencyHelper;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CurrencyConverterIntegrationTest extends AbstractIntegrationTest {

  @Test
  public void shouldtranslateValueToCurrency() throws Exception {
    String currencyFrom = "USD";
    String currencyTo = "EUR";
    BigDecimal value = BigDecimal.valueOf(100);

    MvcResult mvcResult = mockMvc.perform(get(
      String.format("/currency/from/%s/value/%s/to/%s", currencyFrom, value, currencyTo))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk()).andReturn();

    Result result = mapper.readValue(mvcResult.getResponse().getContentAsByteArray(), Result.class);

    assertEquals("Euro", result.getCurrencyToName());
    assertEquals("EUR", result.getCurrencyToSymbol());
    assertEquals(convertToBigDecimal(85.75), result.getValueTo());
  }

  private static BigDecimal convertToBigDecimal(Double value) {
    return CurrencyHelper.convertToBigDecimal(value).setScale(2);
  }
}
