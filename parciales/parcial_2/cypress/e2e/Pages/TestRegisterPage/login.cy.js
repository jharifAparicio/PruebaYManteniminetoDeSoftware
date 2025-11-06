describe('template spec', () => {
  it('passes', () => {
    cy.visit('https://magistratura.organojudicial.gob.bo:8888/login.xhtml')
    // iniciamos sesion
    cy.get('#username').type('mgarciaf')
    cy.get('#password').type('123')
    cy.get('.ui-button-text').click()
    // verificamos los componentes
    cy.get(".nav").should('contain', 'Solicitudes')
    cy.get(".nav").should('contain', 'Respuestas')
    cy.get(".nav").should('contain', 'Entregados')
    cy.get(".nav").should('contain', 'Anulados')
    cy.get(".nav").should('contain', 'Reportes')
    //verificamos el usuario
    cy.get(".nav").should('contain', 'usuario: mgarciaf')
  })
})