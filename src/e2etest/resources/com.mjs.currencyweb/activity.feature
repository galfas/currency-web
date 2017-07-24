Feature: currency coverter

  Background:
    Given app has started
    And app is health
    And mongo is pre populate

  Scenario: Retrieve the last ten activity
    When I request the last activities
    Then I Should see "10" results