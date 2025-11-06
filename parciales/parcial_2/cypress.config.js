const { defineConfig } = require("cypress");
const createBundler = require("@bahmutov/cypress-esbuild-preprocessor");
const { addCucumberPreprocessorPlugin } = require("@badeball/cypress-cucumber-preprocessor");
const { createEsbuildPlugin } = require("@badeball/cypress-cucumber-preprocessor/esbuild");

module.exports = defineConfig({
  e2e: {
    async setupNodeEvents(on, config) {
      // Esta línea registra el preprocesador de Cucumber
      await addCucumberPreprocessorPlugin(on, config);

      on(
        "file:preprocessor",
        createBundler({
          // Esta línea le dice al preprocesador que use 'esbuild'
          plugins: [createEsbuildPlugin(config)],
        })
      );

      // Devuelve la configuración
      return config;
    },
    // Le dice a Cypress que busque archivos .feature
    specPattern: "cypress/e2e/Tests/**/*.feature",
    stepDefinitions: "cypress/e2e/Tests/*.{js,ts}", 
  },
});