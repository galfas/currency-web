Feature: currency coverter

  Background:
    Given app has started
    And app body status is "ok"

  Scenario: Convert BRL to EUR
    Given I want to convert "100" "USD" to "EUR"
    When When I request the application
    Then It Should answer me with "0.871992" "EUR"
