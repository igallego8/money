Feature: Bank Deposit


  Scenario Outline: periodic deposit
    Given a saving account with amount of "<amount>"
    And a periodic deposit per <period_type>  of "<deposit_amount>"
    When the time passes by <period>
    Then I see the account balance "<amount_balance>"
    And ledger must have credit for "<ledger_credit>"

    Examples:
      |amount|period_type|period|amount_balance|ledger_credit|deposit_amount|
      |200   |1          |1     |300           |600          |100           |
      |200   |1          |2     |400           |600          |100           |
      |200   |1          |10    |1200           |600          |100           |