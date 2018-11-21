Feature: Adding products to shipping cart

  Scenario: Adding products to shipping cart get total
    Given a shipping cart
    When I add product to shipping card
    Then I see the the total amount
