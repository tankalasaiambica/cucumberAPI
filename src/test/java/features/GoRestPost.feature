Feature: GO Rest post Feature
  Scenario: Verify that a user resource can be updated
    Given a user
    When updating the user
    Then the user is updated

  Scenario: Verify that a user deleted
    Given a user to del
    When delete the user
    Then the user is delete
