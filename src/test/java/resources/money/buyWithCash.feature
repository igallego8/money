Feature: Buying with cash

  Scenario Outline: Buy product with cash
    Given with amount of "<bank_amount>" in bank
    When I buy  products amount of "<product_amount_1>" and "<product_amount_2>" paid with cash
    Then I see the cash ledger with debits for "<ledger_debits>" and  credits for"<ledger_credits>"

  Examples:
    |product_amount_1|product_amount_2|bank_amount|ledger_credits|ledger_debits|
    |200             |30              |1000       |1000          |-230         |
    |50              |250             |500000     |500000        |-300         |
    |50              |0               |500000     |500000        |-50          |
    |0               |50              |500000     |500000        |-50          |
    |0               |0               |500000     |500000        |0            |

