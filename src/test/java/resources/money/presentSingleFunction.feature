Feature: Present functions for single user

  Scenario Outline: Single function
    Given user "<userName>"
    When user "<userName>" logged in with license for single functions
    Then the user "<userName>" views the functions  "<functions>"



     Examples:
     |userName| functions|
|Test1   | Create Credit,Pay Credit|
     |Test2   | Create Credit|
