Feature: Defer credit

  Scenario Outline: defer debt
    Given buy of products for amount of "<product_amount_1>" , "<product_amount_2>" and "<product_amount_3>"
    And using a credit card with interest of "<interest>" and number of share <shares>
    When I defer the debt with interest of "<interest>" and number of share 10
    Then I see the next credit card charge for the month <months_paid> for "<expected_increment_payment>" dollars
    And the ledger must have debits for "<ledger_debits>" and Credits for "<ledger_credits>"


    Examples:
      |product_amount_1|product_amount_2|product_amount_3|shares|months_paid|expected_increment_payment|interest|ledger_debits|ledger_credits|
      |200             |100             |300             |5     |0          |72                        |2       |-1200.00          |600             |
      |200             |100             |300             |5     |0          |78                        |3       |-1200.00          |600             |
      |200             |100             |200             |5     |0          |55                        |1       |-1000.00          |500             |
      |200             |100             |200             |5     |0          |50                        |0       |-1000.00          |500             |
