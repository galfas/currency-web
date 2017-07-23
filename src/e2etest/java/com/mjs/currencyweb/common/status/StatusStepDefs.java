package com.mjs.currencyweb.common.status;

import java.math.BigDecimal;

import com.jayway.restassured.response.ResponseOptions;
import com.mjs.currencyweb.common.BaseApiClient;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

import static com.mjs.currencyweb.common.BaseApiClient.givenApiClient;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class StatusStepDefs {
  ResponseOptions responseOptions;

  @Given("^app has started$")
  public void app_has_started() {
    responseOptions = givenApiClient(BaseApiClient.DEFAULT_USER_TYPE).get("/status");
    assertThat(responseOptions.statusCode(), equalTo(200));
  }

  @And("^app is health$")
  public void app_body_status_is() {
    String body = responseOptions.body().print();
    assertThat("ok", equalTo(body));
  }
}