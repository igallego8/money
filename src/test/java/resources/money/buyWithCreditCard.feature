Feature: Buying with credit card

  Scenario: buy with credit card
    Given a buy of 24 dollars
    When I select number of share 24
    Then I see the next credit card bill amount
