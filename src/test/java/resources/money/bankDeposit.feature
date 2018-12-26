cvFeature: Purchase credit


  Scenario Outline: purchase credit
    Given buy of products for amount of "<product_amount_1>" , "<product_amount_2>" and "<product_amount_3>"
    And using a credit card with interest of "<interest>" and number of share <shares>
    When an debt is purchased with interest of "<purchase_interest>" and number of share <purchase_shares>
    Then I see new credit with debt of "<debt>" and shares <purchase_shares>
    And the old ledger must have credit for "<ledger_credit>" and new debit for "<ledger_debit>"





    Examples:
      |product_amount_1|product_amount_2|product_amount_3|shares|ledger_credit|ledger_debit|interest|debt|purchase_interest|purchase_shares|
      |200             |100             |300             |5     |600          |600          |2       |0   |1               |12             |
