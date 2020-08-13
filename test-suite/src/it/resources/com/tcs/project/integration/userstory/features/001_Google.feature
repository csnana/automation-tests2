@Demo
Feature: Google Search

  @google @reset
  Scenario Outline: Verify user is able to do a normal search in google
    Given I launch the google url
    And enter the search text "<search>"
    When click on search button
    Then the result page is loaded


    Examples:
      | search             |
      | sajeev rajagopalan |

