Feature: Driver Adding
  As a developer
  I want to add a Driver through the API
  So that it can be available to application

  Background:
    Given The Driver Endpoint "http://localhost:%d/api/drivers" is available

  @post-adding
  Scenario: Add Driver
    When A Driver Request is sent with values "Mario", "Gomez", "Mario Gomez", "photo", "mg@gmail.com", "983654313", "Lima", "12/7/1998", "pass432", "I'm a driver"
    Then A Driver with status 201 is received