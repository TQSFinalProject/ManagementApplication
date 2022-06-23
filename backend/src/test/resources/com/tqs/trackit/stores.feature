Feature: Check list of all stores
    Scenario: Navigate to the website and check the list of all stores
        When I navigate to "http://localhost:3001/"
        And I choose "Tasks" from the navbar
        Then I should see the "All Tasks" title
        And I should see the COVID data for the country "Portugal"