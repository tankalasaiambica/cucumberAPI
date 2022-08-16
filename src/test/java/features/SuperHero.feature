Feature: Super Hero Feature

  Background: Verify user details
    Given a user details

  Scenario:Verify list of superHero
    When baseurl is given
    Then display list

  Scenario:Verify displaying single superHero
    Given create superhero
    When baseurl is given with created Id
    Then display single superHero

  Scenario:Verify invalid superHero id
    When searching using invalid id
    Then display error

  Scenario:Verify invalid url
    When searching using invalid url
    Then display message


  Scenario:Verify that a superHero is created
    When post the superhero
    Then the super hero is created

  Scenario:Verify that a superHero is not created with empty name value
    When  creating a superhero with no name value
    Then the super hero is not created


  Scenario:Verify that a superHero is not created with empty super value
    When  creating a superhero with no super name value
    Then cannot create without super name


  Scenario:Verify that a superHero is not created with empty profession value
    When  creating a superhero with no profession value
    Then cannot create without profession


  Scenario:Verify that a superHero is not created with empty age value
    When  creating a superhero with no age value
    Then cannot create without age

  Scenario:Verify that a superHero is  created without canFly value
    When  creating a superhero with no canFly value
    Then create with default canFly value



  Scenario: Verify that the user name is updated
    Given Create a user
    When updating a user name
    Then User name must be updated


  Scenario: Verify that the user super name is updated
    Given Create a user
    When updating super name of user
    Then User super name must be updated

  Scenario: Verify that the user profession is updated
    Given Create a user
    When updating profession of user
    Then User profession must be updated


  Scenario: Verify that the user age is updated
    Given Create a user
    When updating age of user
    Then User age must be updated


  Scenario: Verify that the user canFly status is updated
    Given Create a user
    When updating canFly  status of user
    Then User canFly status must be updated

  Scenario:Verify that a invalid id cannot update
    When  update invalid id
    Then display invalid error


  Scenario: Verify that the user is deleted
    Given Create a user to delete
    When delete the super hero
    Then  super hero Deleted

  Scenario: Verify that the invalid user is deleted
    When deleted invalid super hero
    Then display deleted msg



