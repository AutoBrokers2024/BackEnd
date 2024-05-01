Feature: Client Getting
  As a developer
  I want to get a Client through the API
  So that it can look at the application

  Background:
    Given The Client Endpoint "http://localhost:%d/api/clients" is available

  @get-all-client
  Scenario: Get All Clients
    When All Clients who are registered in the DB
    Then List of Clients with status 200 is received

  @get-client-by-id
  Scenario: Get Client by ID
    When A Client Selected is sent with id value "1"
    Then A Client with status 200 is received