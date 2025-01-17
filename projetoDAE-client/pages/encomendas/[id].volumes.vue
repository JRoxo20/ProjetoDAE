<template>
  <div v-if="error">Error: {{ error.message }}</div>
  <div v-else class="container">
    <h1>Volumes da Encomenda {{ id }}</h1>
    <table class="encomendas-table">
      <thead>
      <tr>
        <th>Volume Id</th>
        <th>Estado</th>
        <th>Tipo de Embalagem</th>
        <th>Data de Entrega</th>
        <th>Sensores</th>
        <th>Produtos</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="volume in volumes" :key="volume.id">
        <td>{{ volume.id }}</td>
        <td>{{ volume.estado }}</td>
        <td>{{ volume.tipo_embalagem }}</td>
        <td>{{ volume.data_entrega }}</td>
        <td>{{ volume.sensores }}</td>
        <td>{{ volume.produtos }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue';
import Navbar from "~/components/navbar.vue";
import 'flowbite/dist/flowbite.css';

const route = useRoute()
const id = route.params.id
const config = useRuntimeConfig();
const api = config.public.API_URL;

const volumes = ref([]);
const error = ref(null);

async function fetchVolumes() {
  try {
    // Recupera o token do sessionStorage
    const token = sessionStorage.getItem('authToken');
    console.log(token)
    if (!token) {
      window.location.href = '/login';
    }

    // Faz a requisição com o token no cabeçalho Authorization
    const response = await $fetch(`${api}/encomendas/${id}/volumes`, {
      method: 'GET',
      headers: {
        Accept: 'application/json',
        Authorization: `Bearer ${token}` // Adiciona o token ao cabeçalho
      }
    });

    volumes.value = response;
  } catch (err) {
    console.error('Erro ao buscar volumes da encomenda:', err);
    error.value = err;
  }
}

async function refresh() {
  error.value = null;
  await fetchVolumes();
}

onMounted(async () => {
  await fetchVolumes();
});
</script>
<style>
h1 {
  font-size: 30px;
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

.actions {
  margin-right: 10px;
}

.buttons {
  display: flex;
  float: right;
  gap: 10px;
}

h2 {
  margin-top: 0;
}

.encomendas-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 15px;
}

.encomendas-table caption {
  font-size: 1.2em;
  margin-bottom: 10px;
  font-weight: bold;
}

.encomendas-table th,
.encomendas-table td {
  border: 1px solid #ddd;
  padding: 10px;
  text-align: left;
}

.encomendas-table tr:nth-child(even) {
  background-color: #f9f9f9;
}

.encomendas-table tr:hover {
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