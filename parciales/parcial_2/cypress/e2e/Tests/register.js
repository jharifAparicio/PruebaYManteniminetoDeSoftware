import { Given, When, Then, And } from "@badeball/cypress-cucumber-preprocessor";

// --- Background (Sin cambios, funciona bien) ---
Given('que estoy en la página de login', () => {
    cy.visit('https://magistratura.organojudicial.gob.bo:8888/login.xhtml');
});

When('inicio sesión con mis credenciales', () => {
    cy.get('#username').type('mgarciaf');
    cy.get('#password').type('123');
    cy.get('.ui-button-text').click();
});

Then('estoy en el dashboard', () => {
    cy.get(".active").should('contain', "Solicitudes");
    cy.get(".nav").should('contain', 'usuario: mgarciaf');
});

// --- Escenario: Paso 2 (Registro) ---
Given('estoy en el dashboard', () => {
    cy.get(".active").should('contain', "Solicitudes");
    cy.get(".nav").should('contain', 'usuario: mgarciaf');
});

When('hago clic en el botón "Nuevo"', () => {
    cy.get('.ui-button-text').should('contain', 'Nuevo').click();
});

// --- CAMBIO CRÍTICO 2: Parámetro y Selector ---
// 1. Se usa {string} para capturar la variable (no "{ci}").
// 2. Se escapa el ":" con "\\" en el ID.
When('lleno el formulario principal con los datos del interesado: {string}', (ci) => {
    cy.get('#formulariosolicitudes\\:docid').type(ci).type('{enter}');
});

// --- CAMBIO CRÍTICO 3: Lógica del Dropdown (Select) ---
// No se puede usar .select() en una <label>.
// Esta es la forma de manejar un dropdown de PrimeFaces/JSF:
When('selecciono el motivo "CERTIFICACION LEY Nº 348 Y 1153"', () => {
    // 1. Clic en la etiqueta para abrir el panel de opciones
    cy.get('#formulariosolicitudes\\:motivo_label').click();
    
    // 2. Clic en la opción deseada dentro del panel que se abre
    // (Este selector asume el panel estándar de PrimeFaces)
    cy.get('.ui-selectonemenu-items-wrapper').contains('CERTIFICACION LEY Nº 348 Y 1153').click();
});

// --- CAMBIO (Igual que #2): Parámetro y Selector ---
When('inserto el numero de valorado {string}', (valorado) => {
    cy.get('#formulariosolicitudes\\:valorado_input').type(valorado).type('{enter}');
});

When('hago clic en el botón "Guardar"', () => {
    // Usamos 'contains' para asegurar que es el botón correcto
    cy.contains('.ui-button-text', 'Guardar').click();
});

// --- CAMBIO CRÍTICO 4: Paso Faltante ---
// Implementación del paso que faltaba en tu .js
When('registro el motivo minimo 15 caracteres {string} y hago clic en {string}', (motivo, botonSi) => {
    // Asumo que esto abre un modal (diálogo)
    // 1. Escribir en el textarea del motivo
    // (Debes ajustar este selector)
    cy.get('textarea[name="j_idt100:motivo"]').type(motivo);
    
    // 2. Hacer clic en el botón "Si"
    cy.contains('.ui-button-text', botonSi).click();
});


// --- Escenario: Paso 3 (Respuesta y Verificación) ---
When('navego a la página de {string}', (nombrePagina) => {
    cy.get('.nav').contains(nombrePagina).click();
});

// --- CAMBIO: Texto del paso actualizado ---
// El texto ahora coincide con el del .feature
When('extraigo y guardo el ID de la solicitud usando el CI {string}', (ci) => {
  cy.contains('td', ci.trim()) // Usar .trim() por si acaso
    .parent('tr')
    .invoke('attr', 'data-rk')
    .then((dataRkValue) => {
      const cleanId = dataRkValue.trim();
      cy.log(`CI encontrado: ${ci}. ID de Solicitud extraído: ${cleanId}`);
      cy.wrap(cleanId).as('idSolicitudGuardado');
    });
});

// Tu lógica de alias aquí es EXCELENTE, no la cambio.
When('selecciono la solicitud usando el ID guardado', function () {
    // 'this.idSolicitudGuardado' es la forma correcta de usar el alias
    cy.get(`tr[data-rk="${this.idSolicitudGuardado}"]`).within(() => {
        cy.get('td').first().click();
    });
});

When('presiono "Imprimir" para la solicitud seleccionada', () => {
    cy.contains('.ui-button-text', 'Imprimir').click();
});

When('navego a la página de "Entregados"', () => {
    cy.get('.nav').contains('Entregados').click();
});

// Tu lógica de alias aquí también es PERFECTA.
Then('el trámite debe estar visible en la lista de entregados usando el ID guardado', function () {
    // Verificamos que exista un <td> en cualquier tabla que contenga el ID
    cy.get('table').contains('td', this.idSolicitudGuardado).should('be.visible');
});