// https://nuxt.com/docs/api/configuration/nuxt-config

export default defineNuxtConfig({
  compatibilityDate: '2024-11-01',
  devtools: { enabled: true },
<<<<<<< HEAD
  runtimeConfig: {
    public: {
      API_URL: process.env.API_URL || 'http://localhost:8080/projetoDAE/api'
    }
=======

  runtimeConfig: { 
    public: { 
    API_URL: process.env.API_URL || 'http://localhost:8080/projetoDAE/api'
    } 
>>>>>>> origin/main
  }
})
