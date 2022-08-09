Feature: GO Rest Feature

  Background: Create a user
    Given a user

  @smoke
  Scenario: Verify that a user resource can be updated
    When updating the user
    Then the user is updated

  @regression
  Scenario: Verify that a user resource can be updated
    When updating the user
    Then the user is updated

  @regression
  Scenario Outline: Verify that a user resource cannot be updated
    When updating the user with invalid input "<userName>" and "<password>"
    Then the user cannot be updated
    Examples:
      | userName       | password |
      | test@gmail.com | test123  |
      | test           | Test123  |
      | gmail.com      | test123  |
      | test@gmail.com | Test@123 |


  Scenario: Verify that a user deleted
    Given a user to del
    When delete the user
    Then the user is delete
