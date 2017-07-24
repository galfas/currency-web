package com.mjs.currencyweb.common.activity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jayway.restassured.response.Response;
import com.mjs.currencyweb.common.BaseApiClient;
import com.mjs.currencyweb.server.dao.historyRepository.ActivityRepository;
import com.mjs.currencyweb.server.model.Result;
import com.mjs.currencyweb.server.utils.CurrencyHelper;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ActivityStepDefs {

  Response response;

  @When("^I request the last activities$")
  public void i_request_the_last_activities() {
    response = null;

    response = BaseApiClient.givenApiClient(BaseApiClient.DEFAULT_USER_TYPE).get("/activities");

    assertThat(response.statusCode(), equalTo(200));
  }

  @Then("^I Should see \"([^\"]*)\" results$")
  public void it_should_have_received_an_exceptiony(int quantity) {
    assertEquals(200, response.statusCode());
    List<Result> resultList = response.body().as(List.class);

    assertEquals(quantity, resultList.size());
  }

  @And("^mongo is pre populate$")
  public void mongo_is_pre_populate() {
    populate();
  }

  private void populate() {

    BaseApiClient.givenApiClient(BaseApiClient.DEFAULT_USER_TYPE).get("/currency/from/USD/value/100/to/EUR");
    BaseApiClient.givenApiClient(BaseApiClient.DEFAULT_USER_TYPE).get("/currency/from/BRL/value/200/to/USD");
    BaseApiClient.givenApiClient(BaseApiClient.DEFAULT_USER_TYPE).get("/currency/from/EUR/value/300/to/BRL");
    BaseApiClient.givenApiClient(BaseApiClient.DEFAULT_USER_TYPE).get("/currency/from/EUR/value/400/to/USD");
    BaseApiClient.givenApiClient(BaseApiClient.DEFAULT_USER_TYPE).get("/currency/from/USD/value/500/to/EUR");
    BaseApiClient.givenApiClient(BaseApiClient.DEFAULT_USER_TYPE).get("/currency/from/USD/value/600/to/BRL");
    BaseApiClient.givenApiClient(BaseApiClient.DEFAULT_USER_TYPE).get("/currency/from/BRL/value/700/to/USD");
    BaseApiClient.givenApiClient(BaseApiClient.DEFAULT_USER_TYPE).get("/currency/from/USD/value/800/to/BRL");
    BaseApiClient.givenApiClient(BaseApiClient.DEFAULT_USER_TYPE).get("/currency/from/BRL/value/900/to/EUR");
    BaseApiClient.givenApiClient(BaseApiClient.DEFAULT_USER_TYPE).get("/currency/from/USD/value/1000/to/EUR");
  }


}
