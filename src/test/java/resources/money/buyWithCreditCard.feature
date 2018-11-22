Feature: Buying with credit card

  Scenario Outline: buy with credit card
    Given a buy of <amount> dollars
    And using a credit card with interest of "<interest>"
    When I select number of share <shares>
    Then I see the next credit card charge for the month <months> for "<expected>" dollars

  Examples:
    | amount   |  interest  | shares  | months | expected |
    |100       |50          |10       |1       |60        |
    |100       |0           |1        |0       |100       |
    |100       |1           |0        |0       |101       |
    |100       |1           |10       |2       |10.9      |
    |100       |1           |10       |1       |11        |
