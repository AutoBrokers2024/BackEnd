Feature: Client Deleting
  As a developer
  I want to delete a Client through the API
  So that it can't be available to application

  Background:
    Given The Client Endpoint "http://localhost:%d/api/clients" is available

  @delete-client
  Scenario: Delete Client
    When A Client Delete is sent with id value "1"
    Then A Client with status 200 is received