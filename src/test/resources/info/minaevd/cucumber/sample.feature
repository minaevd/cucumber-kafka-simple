Feature: Count words
  Scenario: Count number of word occurrences
    Given that we know a number of words processed previously
    When I send a new word "satisfactory"
    Then I should receive count for "satisfactory" word increased by 1

  Scenario: Count other words
    Given that we know a number of words processed previously
    When I send a new word "excellence"
    Then I should receive count for "excellence" word increased by 1