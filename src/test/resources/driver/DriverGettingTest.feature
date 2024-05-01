Feature: Driver Getting
  As a developer
  I want to get a Driver through the API
  So that it can look at the application

  Background:
    Given The Driver Endpoint "http://localhost:%d/api/drivers" is available

  @get-all-driver
  Scenario: Get All Drivers
    When All Drivers who are registered in the DB
    Then List of Drivers with status 200 is received

  @get-driver-by-id
  Scenario: Get Driver by ID
    When A Driver Selected is sent with id value "1"
    Then A Driver with status 200 is received