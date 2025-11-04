describe("Automatización TestingYes OnlineShop", () => {

  it("Caso 1: Validar la página principal", () => {
    cy.visit("/");
    // 1. Validar título del sitio
    cy.title().should("include", "My e-commerce");
    // 2. validar si existe el logo a la vista
    cy.get(".logo").should("be.visible");
    // 3. probar que el carrito de compras exista
    cy.get("#_desktop_cart").contains("Cart");
    // 4. validar que exista el sign in
    cy.get("#_desktop_user_info").should("exist");
    // 5. validacion la existencia de footer
    cy.get("footer").should("be.visible");
  });

  it("Caso 2: ")
});
