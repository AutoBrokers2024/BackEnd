Feature: Client Getting
  As a developer
  I want to be able to save a contract
  So that it can be used in the application

  Background:
    Given The contract Endpoint "http://localhost:%d/api/contracts/add" is available

    @add-contract
    Scenario: Add a contract
      When I post a contract to the endpoint with values 10, "60", "2022-02-02", "This is a new contract", "Ancash", "20", "Tourism", "02:00:00", "05:20:00", "Lima", 1, 1, 1, 1, 1
      Then a contract with status code 201 is returned