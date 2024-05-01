Feature: Client Adding
  As a developer
  I want to add a Client through the API
  So that it can be available to application

  Background:
    Given The Client Endpoint "http://localhost:%d/api/clients" is available

  @post-adding
  Scenario: Add Client
    When A Client Request is sent with values "Antonio", "Martinez", "Antonio Martinez", "photo", "am@gmail.com", "983654313", "Amazonas", "12/7/1998", "pass321", "I want to have the best service"
    Then A Client with status 201 is received
