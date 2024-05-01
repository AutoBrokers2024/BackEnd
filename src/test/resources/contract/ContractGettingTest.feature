Feature: Contract getting
  As a developer
  I want to be able to get a contract
  So that it can be used in the application

  Background:
    Given The contract Endpoint "http://localhost:%d/api/contracts" is available

  @get-all-contracts
  Scenario: Get all contracts
    When there are contracts registered in the DB
    Then list of contracts with status code 200 is returned

  @get-contract-by-id
  Scenario: Get contract by id
    When there are contracts registered in the DB
    And I need to get a contract with id 1
    Then contract with id 1 and status code 200 is returned

  @get-contract-by-status-offer-and-driverId
  Scenario: Get contract by status offer
    When there are contracts registered in the DB
    And I need to get a list of contracts with status "offer" and "driver" id 1
    Then list of contracts with status code 200 is returned