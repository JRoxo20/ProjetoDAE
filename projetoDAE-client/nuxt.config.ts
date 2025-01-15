// https://nuxt.com/docs/api/configuration/nuxt-config

export default defineNuxtConfig({
  compatibilityDate: '2024-11-01',
  css: ['flowbite/dist/flowbite.css'],
  devtools: { enabled: true },
  runtimeConfig: {
    public: {
      API_URL: process.env.API_URL || 'http://localhost:8080/projetoDAE/api'
    }
  }

})
