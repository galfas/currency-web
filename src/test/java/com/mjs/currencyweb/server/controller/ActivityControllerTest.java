package com.mjs.currencyweb.server.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mjs.currencyweb.server.business.ActivityBO;
import com.mjs.currencyweb.server.model.Result;
import com.mjs.currencyweb.server.utils.CurrencyHelper;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class ActivityControllerTest {

  @Mock
  ActivityBO activityBO;

  @InjectMocks
  ActivityController activityController = new ActivityController();

  @Test
  public void shouldReturnListOfActivities() throws IOException {

    Mockito.when(activityBO.getLastActivities()).thenReturn(builderAcitivityList());
    List<Result> resultList = activityController.lastActitivities();
    Assert.assertNotNull(resultList);
    assertEquals(2, resultList.size());
  }

  @Test
  public void shouldReturnEmptyListWhenDoesNotFindResults() throws IOException {

    Mockito.when(activityBO.getLastActivities()).thenReturn(Arrays.asList());
    List<Result> resultList = activityController.lastActitivities();
    Assert.assertNotNull(resultList);
    assertEquals(0, resultList.size());
  }

  public List<Result> builderAcitivityList() {

    Result result = new Result("Brazilian Real", "BRL",
      CurrencyHelper.convertToBigDecimal(100), "USD", "American Dollar",
      CurrencyHelper.convertToBigDecimal(200));

    Result result2 = new Result("Euro", "EUR",
      CurrencyHelper.convertToBigDecimal(100), "USD", "American Dollar",
      CurrencyHelper.convertToBigDecimal(120));

    return Arrays.asList(result, result2);
  }
}