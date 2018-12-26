Feature: Interest Credit Card


  Scenario Outline: interest credit
    Given a credit card with interest of "<interest>" and cutoff day <day>
    And something was bought for "<product_amount>"with shares of <shares>
    When number of months passed by <months>
    Then I see the interest amount "<interest_amount>"

    Examples:
      |day            |interest|product_amount|shares|months|interest_amount|
      |25             |100     |300           |5     |600   |600            |
