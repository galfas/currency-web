Feature: currency coverter

  Background:
    Given app has started
    And app is health

  Scenario: Convert USD to EUR
    Given I want to convert "100" "USD" to "EUR"
    When I request the application
    Then It Should answer me with "85.75" "EUR"
    And currency input should return the same

  Scenario: Convert BRL to XPT with cents
    Given I want to convert "200.40" "BRL" to "TWD"
    When I request the application
    Then It Should answer me with "1941.63" "TWD"
    And currency input should return the same

  Scenario: Should return 0 when input value is Zero
    Given I want to convert "0" "BRL" to "TWD"
    When I request the application
    Then It Should answer me with "0" "TWD"
    And currency input should return the same

  Scenario: Should return the value when input currency and desirable currency is the same
    Given I want to convert "100" "BRL" to "BRL"
    When I request the application
    Then It Should answer me with "100" "BRL"
    And currency input should return the same

  Scenario: Should return a error when the currency does not exist
    Given I want to convert "100" "BRL" to "AAA"
    When I request with wrong parameters the application
    Then It Should had received an exception