package com.mjs.currencyweb.server.business;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mjs.currencyweb.server.business.impl.ActivityBOimpl;
import com.mjs.currencyweb.server.dao.historyRepository.ActivityRepository;
import com.mjs.currencyweb.server.model.Result;
import com.mjs.currencyweb.server.utils.CurrencyHelper;

import static org.mockito.Mockito.times;

@RunWith(SpringJUnit4ClassRunner.class)
public class ActivityBOimplTest {

  @Mock
  private ActivityRepository activityRepository;

  @InjectMocks
  ActivityBO activityBO = new ActivityBOimpl();

  @Test
  public void shouldSaveTheObjectResult() {
    Result expectedResult = new Result();

    activityBO.save(expectedResult);
    Mockito.verify(activityRepository, times(1)).save(expectedResult);
  }

  @Test
  public void shouldRetreiveResults() {
    Mockito.when(activityRepository.findAll()).thenReturn(builderAcitivityList());
    List<Result> resultList = activityBO.getLastActivities();
    Assert.assertNotNull(resultList);
    Assert.assertEquals(2, resultList.size());
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
