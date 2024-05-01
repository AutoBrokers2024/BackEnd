Feature: Driver Deleting
  As a developer
  I want to delete a Driver through the API
  So that it can't be available to application

  Background:
    Given The Driver Endpoint "http://localhost:%d/api/drivers" is available

  @delete-driver
  Scenario: Delete Driver
    When A Driver Delete is sent with id value "1"
    Then A Driver with status 200 is received