import {Given, When, Then} from 'cypress-cucumber-preprocessor/steps';
Given('Launch the OrangeHRM application', () => {
  cy.visit('https://opensource-demo.orangehrmlive.com/web/index.php/auth/login');
});
When('I enter {string} as username', () => {
  cy.get('input[name="username"]').type('Admin');
})
And('I enter {string} as password', () => {
  cy.get('input[name="password"]').type('admin123');
});
And('I click the login button', () => {
  cy.get('button[type="submit"]').click();
});
Then('I should see the dashboard page', () => {
  cy.url().should('include', '/dashboard/index');
  cy.get('.oxd-topbar-header-breadcrumb > .oxd-text').should('contain', 'Dashboard');
  
});