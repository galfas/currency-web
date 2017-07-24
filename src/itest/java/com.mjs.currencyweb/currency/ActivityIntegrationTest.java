package com.mjs.currencyweb.currency;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.Assert;

import com.mjs.currencyweb.AbstractIntegrationTest;
import com.mjs.currencyweb.server.dao.historyRepository.ActivityRepository;
import com.mjs.currencyweb.server.model.Result;
import com.mjs.currencyweb.server.utils.CurrencyHelper;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ActivityIntegrationTest extends AbstractIntegrationTest {

  @Autowired
  ActivityRepository activityRepository;

  @Before
  public void setupActivity() {
    populate();
  }

  @Test
  public void shouldFetchLastActivities() throws Exception {

    MvcResult mvcResult = mockMvc.perform(get("/activities")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk()).andReturn();

    List<Map> result = mapper.readValue(mvcResult.getResponse().getContentAsByteArray(), List.class);
    System.out.println(result.get(0));
    System.out.println(result.get(0).get("currencyFromName"));
    Assert.notNull(result, "should not be null");
    assertEquals(2, result.size());
    assertEquals("Brazilian Real", result.get(0).get("currencyFromName"));
    assertEquals("American Dollar", result.get(1).get("currencyFromName"));
  }

  private void populate() {
    Result result = new Result("Brazilian Real", "BRL", CurrencyHelper.convertToBigDecimal(100),
      "American Dollar", "USD", CurrencyHelper.convertToBigDecimal(100));
    Result result2 = new Result("American Dollar", "USD", CurrencyHelper.convertToBigDecimal(200),
      "Euro", "EUR", CurrencyHelper.convertToBigDecimal(200));
    activityRepository.save(result);
    activityRepository.save(result2);
  }
}
