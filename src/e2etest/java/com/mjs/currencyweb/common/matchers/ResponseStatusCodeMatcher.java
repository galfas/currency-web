package com.mjs.currencyweb.common.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.springframework.http.HttpStatus;

import com.jayway.restassured.response.Response;

public class ResponseStatusCodeMatcher extends TypeSafeMatcher<Response> {

  private int statusCode;


  public static ResponseStatusCodeMatcher hasStatusCode(int statusCode) {
    return new ResponseStatusCodeMatcher(statusCode);
  }

  public static ResponseStatusCodeMatcher hasStatusCode(HttpStatus statusCode) {
    return new ResponseStatusCodeMatcher(statusCode.value());
  }

  public ResponseStatusCodeMatcher() {
    statusCode = 200;
  }

  public ResponseStatusCodeMatcher(int statusCode) {
    this.statusCode = statusCode;
  }

  @Override
  public boolean matchesSafely(Response item) {
    return item != null && item.statusCode() == statusCode;
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("has status code " + statusCode);
  }

  @Override
  public void describeMismatchSafely(Response item, Description mismatchDescription) {
    mismatchDescription.appendText("was ").appendValue(item.getStatusCode());
  }
}
