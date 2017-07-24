package com.mjs.currencyweb.currency;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.Assert;

import com.mjs.currencyweb.AbstractIntegrationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FetchAvaliableCurrencies extends AbstractIntegrationTest {


  @Test
  public void shouldAvailableCurrencies() throws Exception {
    String currencyFrom = "USD";
    String currencyTo = "EUR";
    BigDecimal value = BigDecimal.valueOf(100);

    MvcResult mvcResult = mockMvc.perform(get("/currencies")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk()).andReturn();

    Map<String, String> result = mapper.readValue(mvcResult.getResponse().getContentAsByteArray(), Map.class);
    Assert.notNull(result, "should not be null");
    assertEquals(168, result.size());
    assertEquals("Brazilian Real", result.get("BRL"));
    assertNull(result.get("AAA"));
  }
}
