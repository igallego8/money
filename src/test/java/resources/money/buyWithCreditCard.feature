Feature: Buying with credit card

  Scenario: buy with credit card
    Given a buy of 24 dollars
    And using a credit card with interest of "50"
    When I select number of share 24
    Then I see the next credit card charge for the month 3
