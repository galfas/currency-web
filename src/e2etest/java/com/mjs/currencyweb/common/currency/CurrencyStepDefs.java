package com.mjs.currencyweb.common.currency;

import java.math.BigDecimal;
import java.util.Map;

import com.jayway.restassured.response.Response;
import com.mjs.currencyweb.common.BaseApiClient;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class CurrencyStepDefs {

  String currencyFrom;
  String currencyTo;
  BigDecimal valueFrom;
  Response response;
  RuntimeException exception;

  @Given("^I want to convert \"([^\"]*)\" \"([^\"]*)\" to \"([^\"]*)\"$")
  public void i_want_to_convert(BigDecimal value, String currencyFrom, String currencyTo) {
    this.currencyFrom = currencyFrom;
    this.currencyTo = currencyTo;
    this.valueFrom = value;
  }

  @Given("^I request the application$")
  public void when_i_request_the_application() {
    response = null;

    response = BaseApiClient.givenApiClient(BaseApiClient.DEFAULT_USER_TYPE).get(
      String.format("/currency/from/%s/value/%s/to/%s", currencyFrom, valueFrom, currencyTo));

    assertThat(response.statusCode(), equalTo(200));
  }

  @Given("^When I request with wrong parameters the application$")
  public void when_i_request_with_wrong_parameters_the_application() {
    response = null;

    try {
      response = BaseApiClient.givenApiClient(BaseApiClient.DEFAULT_USER_TYPE).get(
        String.format("/currency/from/%s/value/%s/to/%s", currencyFrom, valueFrom, currencyTo));
    } catch (RuntimeException r) {
      exception = r;
    }

    assertThat(response.statusCode(), equalTo(400));
  }

  @Then("^It Should had received an exception$")
  public void it_should_have_received_an_exception() {
    assertNotNull(exception);
    exception = null;
  }

  @Then("^It Should answer me with \"([^\"]*)\" \"([^\"]*)\"$")
  public void it_should_answer_with(BigDecimal value, String currencySymbol) {
    assertEquals(200, response.statusCode());
    assertEquals(currencySymbol, response.body().as(Map.class).get("currencyToSymbol"));
    assertEquals(value, BigDecimal.valueOf((Double) response.body().as(Map.class).get("valueTo")));
  }

  @And("^currency input should return the same$")
  public void currency_input_should_return_the_same() {
    assertEquals(currencyFrom, response.body().as(Map.class).get("currencyFromSymbol"));
    assertEquals(valueFrom, BigDecimal.valueOf((Double) response.body().as(Map.class).get("valueFrom")));
  }
}
