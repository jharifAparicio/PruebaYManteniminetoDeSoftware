const { defineConfig } = require("cypress");

module.exports = defineConfig({
  e2e: {
    baseUrl: "http://www.testingyes.com/onlineshop",
    viewportWidth: 1280,
    viewportHeight: 720,
  },
});
