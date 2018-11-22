Feature: Buying with credit card

  Scenario Outline: buy with credit card
    Given a buy of <amount> dollars
    And using a credit card with interest of "<interest>"
    When I select number of share <shares>
    Then I see the next credit card charge for the month <months_paid> for "<expected_increment_payment>" dollars
    And the ledger must have debits for "<ledger_debits>" and Credits for "<ledger_credits>"

    Examples:
      | amount   |  interest  | shares  | months_paid | expected_increment_payment |ledger_debits   |ledger_credits  |
      |100       |50          |10       |1            |60                          |-100            |0               |
      |100       |0           |1        |0            |100                         |-100            |0               |
      |100       |1           |0        |0            |101                         |-100            |0               |
      |100       |1           |10       |2            |10.9                        |-100            |11              |
      |100       |1           |10       |3            |10.8                        |-100            |21.9            |
      |100       |1           |10       |11           |0.0                         |-100            |105.50          |



  Scenario Outline: buy with credit card
    Given buy of products for amount of "<product_amount_1>" , "<product_amount_2>" and "<product_amount_3>"
    And using a credit card with interest of "<interest>" and number of share <shares>
    When pay credit for <months_paid> paid
    Then I see the percentage of <product_1_paid> , <product_2_paid> and <product_3_paid>
    
  Examples:
    |product_amount_1|product_amount_2|product_amount_3|shares|months_paid|product_1_paid|product_2_paid|product_3_paid|interest|
    |200             |100             |300             |5     |6          |100           |100           |100           |2.0     |
    |200             |100             |300             |5     |5          |80            |80            |80            |2.0     |
    |200             |100             |300             |5     |4          |60            |60            |60            |2.0     |
    |200             |100             |300             |5     |3          |40            |40            |40            |2.0     |
    |200             |100             |300             |5     |2          |20            |20            |20            |2.0     |
    |200             |100             |300             |5     |1          |0             |0             |0             |2.0     |