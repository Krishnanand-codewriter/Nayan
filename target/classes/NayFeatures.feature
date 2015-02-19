Feature: NayanFeatures

  Scenario: Open browser
    Given I open a web browser through "CHROMEDRIVER"
    When I enter the url "http://www.google.com"
    Then I should be navigated to the "Google" page.
    And I take the screenshot as "Google Home"
    
    