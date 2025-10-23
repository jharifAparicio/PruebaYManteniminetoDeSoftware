Feature: Login Test
    Login to the application: OrangeHRM as a user
    Scenario: Login to OrangeHRM application with valid credentials
        Given Launch the OrangeHRM application
        When I enter "Admin" as username
        And I enter "admin123" as password
        And I click the login button
        Then I should see the dashboard page