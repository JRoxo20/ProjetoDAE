<template>
  <div class="login-page">
    <div class="login-container">
      <img src="/logotipo.jpg" alt="logotipo" class="logotipo">

        <div class="form-group">
          Username:
          <input v-model="loginFormData.username">
        </div>
        <div class="form-group">
          Password:
        <input v-model="loginFormData.password" type="password">
        </div>
      <button class="btn-login" @click="login">LOGIN</button>
      <button @click="reset">Reset</button>
    </div>
    <footer class="footer">
      <div class="footer-container">
        <p class="footer-text">
          Project by Cristiano Marques, João Roxo, Maria Malato, and Vasco Fernandes.
        </p>
      </div>
    </footer>
</div>
</template>
<script setup>
const config = useRuntimeConfig()
const api = config.public.API_URL || 'http://localhost:8080/projetoDAE/api';
const loginFormData = reactive({
  username: null,
  password: null
})
const apiFormData = reactive({
  path: "auth/user"
})
const token =ref(null)
const messages = ref([])
async function login() {
  reset()
  try {
    await $fetch(`${api}/auth/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
      },
      body: loginFormData,
      onResponse({ request, response, options }) {
        messages.value.push({
          method: options.method,
          request: request,
          status: response.status,
          statusText: response.statusText,
          payload: response._data
        })
        if (response.status == 200)
          token.value = response._data
        config.public.token = token.value
        console.log(config.public.token, token.value)
        fetchUser()
         // Armazena o token no sessionStorage
        console.log("wefw", sessionStorage.getItem('authToken'))
        window.location.href = '/';
      }
    })
  } catch (e) {
    console.error('login request failed: ', e)
  }
}
function reset() {
  token.value = null
  messages.value = []
}
async function fetchUser() {
  try {
    await $fetch(`${api}/${apiFormData.path}`, {
      method: 'GET',
      headers: {
        Accept: 'application/json',
        Authorization: `Bearer ${token.value}`
      },
      onResponse({ request, response, options }) {
        messages.value.push({
          method: options.method,
          request: request,
          status: response.status,
          statusText: response.statusText,
          payload: response._data
        })
        const role = response._data.role;
        if (role) {
          sessionStorage.setItem('role', role);
        }
        sessionStorage.setItem('username', loginFormData.username);
        sessionStorage.setItem('authToken', token.value);
      }
    })
  } catch (e) {
    console.error('api request failed: ', e)
  }
}
</script>
<style>
/* Footer styling */
.footer {
  left: 0;
  right: 0;
  text-align: center;
  padding: 20px 0;
  position: absolute;
  bottom: 0;
  margin-bottom: 2%;
}
.footer-text {
  font-size: 14px;
  color: rgba(0, 0, 0, 0.5);
}

.footer-text.dark-mode {
  color: rgba(255, 255, 255, 0.5);
}

.footer-container {
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 16px;
}

.logotipo {
  width: 60%;
  height: auto;
  display: block;
  margin: 0 auto 1.5rem auto;
}

.login-page {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: radial-gradient(circle at center, #e0f7fa, #b3e5fc, #81d4fa); /* Softer gradient */
}


.login-container {
background: white;
padding: 2rem;
border-radius: 8px;
box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
text-align: center;
width: 100%;
max-width: 400px;
}

h1 {
margin-bottom: 1.5rem;
font-size: 24px;
color: #333;
}

.form-group {
margin-bottom: 1rem;
text-align: left;
}

label {
display: block;
font-size: 14px;
color: #666;
margin-bottom: 0.5rem;
}

input {
width: 100%;
padding: 0.75rem;
border: 1px solid #ddd;
border-radius: 4px;
font-size: 14px;
}

input:focus {
outline: none;
border-color: #007bff;
box-shadow: 0 0 4px rgba(0, 123, 255, 0.2);
}

.btn-login {
width: 100%;
padding: 0.75rem;
border: none;
border-radius: 4px;
background-color: #007bff;
color: white;
font-size: 16px;
cursor: pointer;
  margin-bottom: 2%;
}

.btn-login:hover {
background-color: #0056b3;
}


</style>




