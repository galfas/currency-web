package com.mjs.currencyweb.common.currency;

import java.math.BigDecimal;
import java.util.Map;

import com.jayway.restassured.response.Response;
import com.mjs.currencyweb.common.BaseApiClient;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class CurrencyStepDefs {

  String currencyFrom;
  String currencyTo;
  BigDecimal value;
  Response response;

  @Given("^I want to convert \"([^\"]*)\" \"([^\"]*)\" to \"([^\"]*)\"$")
  public void i_want_to_convert(BigDecimal value, String currencyFrom, String currencyTo) {
    this.currencyFrom = currencyFrom;
    this.currencyTo = currencyTo;
    this.value = value;
  }

  @Given("^When I request the application$")
  public void when_i_request_the_application() {
    response = null;

    response = BaseApiClient.givenApiClient(BaseApiClient.DEFAULT_USER_TYPE).get(
      String.format("/currency/from/%s/value/%s/to/%s", currencyFrom, value, currencyTo));

    assertThat(response.statusCode(), equalTo(200));
  }

  @Then("^It Should answer me with \"([^\"]*)\" \"([^\"]*)\"$")
  public void it_should_answer_with(BigDecimal value, String currencySymbol) {
    assertEquals(200, response.statusCode());
    assertEquals(currencySymbol, response.body().as(Map.class).get("currencySymbol"));
    assertEquals(value, BigDecimal.valueOf((Double) response.body().as(Map.class).get("value")));
  }
}
