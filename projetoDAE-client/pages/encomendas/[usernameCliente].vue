<template>
  <Navbar  activePage="orders" />
  <div v-if="error" class="error">Error: {{ error.message }}</div>
  <div v-else class="container">
    <h1>Encomendas</h1>
    <div class="buttons">
      <button @click.prevent="refresh" class="create-button">🔄 Refresh Data</button>
    </div>
    <table class="encomenda-table">
      <thead>
      <tr>
        <th>Id</th>
        <th>Client Username</th>
        <th>Volumes</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="encomenda in encomendas" :key="encomenda.id">
        <td>{{ encomenda.id }}</td>
        <td>{{ encomenda.usernameCliente }}</td>
        <td><nuxt-link :to="`/encomendas/${encomenda.id}.volumes`">Details</nuxt-link></td>
        <td><nuxt-link :to="`/encomendas/${encomenda.id}.volumes`">Volumes</nuxt-link></td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>

import 'flowbite/dist/flowbite.css';
import {onMounted, ref} from "vue";
import Navbar from "~/components/navbar.vue";


const config = useRuntimeConfig();
const api = config.public.API_URL;
const route = useRoute();
const usernameCliente = route.params.usernameCliente;
const error = ref(null);

const encomendas = ref([]);
async function fetchAllEncomendas() {
  try {
    const token = sessionStorage.getItem('authToken');
    if (!token || !usernameCliente) {
      window.location.href = "/login";
    }
    const response = await $fetch(`${api}/encomendas/${usernameCliente}/myencomendas`, {
      method: 'GET',
      headers: {
        Accept: 'application/json',
        Authorization: `Bearer ${token}`,
      },
    });
    encomendas.value = response;
  } catch (err) {
    console.error('Error fetching encomendas:', err);
    error.value = err;
  }
}


async function refresh() {
  error.value = null;
  await fetchAllEncomendas();
}

onMounted(async () => {
  await fetchAllEncomendas();
});
</script>

<style >
h1{
  font-size:30px;
  margin-left: 4%;
}

.container {
  padding: 20px;
  font-family: Arial, sans-serif;
}

.create-button {
  display: inline-block;
  margin-bottom: 15px;
  padding: 10px 15px;
  background-color: #007bff;
  color: white;
  text-decoration: none;
  border-radius: 5px;
}

.create-button:hover {
  background-color: #0056b3;
}


.buttons{
  display: flex;
  float:right;
  gap:10px;
}

h2 {
  margin-top: 0;
}

.encomenda-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 15px;
}

.encomenda-table caption {
  font-size: 1.2em;
  margin-bottom: 10px;
  font-weight: bold;
}

.encomenda-table th,
.encomenda-table td {
  border: 1px solid #ddd;
  padding: 10px;
  text-align: left;
}

.encomenda-table tr:nth-child(even) {
  background-color: #f9f9f9;
}

.encomenda-table tr:hover {
  background-color: #f1f1f1;
}

.tools {
  display: flex;
  align-items: center;
  justify-content: center;
}


.delete-button {
  background: none;
  border: none;
  color: red;
  font-size: 1.2em;
  cursor: pointer;
}

.delete-button:hover {
  color: darkred;
}

.error {
  color: red;
  font-weight: bold;
}
</style>