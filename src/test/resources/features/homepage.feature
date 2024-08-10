Feature: Home page Tests

  @US100
  Scenario: Search field on home page
    And "Search field" is visible

  @US101
  Scenario: Search results on home page
    When I input "aws" in search field
    Then Verify the results contain word "aws"