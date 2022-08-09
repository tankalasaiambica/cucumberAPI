Feature:Go Rest Comment Feature
  Scenario: Verify that a post resource can be updated
    Given a post
    When updating the post
    Then the post is updated

  Scenario: Verify that a post deleted
    Given a post to del
    When delete the post
    Then the post is delete
