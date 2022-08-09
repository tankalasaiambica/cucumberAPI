Feature:Go Rest Todo Feature
  Scenario: Verify that a Todo resource can be updated
    Given a Todo
    When updating the Todo
    Then the Todo is updated

  Scenario: Verify that a Todo deleted
    Given a Todo to del
    When delete the del
    Then the Todo is delete