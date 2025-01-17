<template>
  <Navbar activePage="volumes" />
  <div v-if="error">Error: {{ error.message }}</div>
  <div v-else class="container">
    <h1>Volumes</h1>
    <div v-if="userRole == 'GESTOR'" class="buttons">
      <nuxt-link to="/volumes/create" class="create-button">➕ Create a New Volume</nuxt-link>
      <button @click.prevent="refresh" class="create-button">🔄 Refresh Data</button>
    </div>
    <br>
    <table class="volumes-table">
      <thead>
        <tr>
          <th>Id</th>
          <th>Estado</th>
          <th>Tipo de Embalagem</th>
          <th>Data de Entrega</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="volume in volumes" :key="volume.id">
          <td>{{ volume.id }}</td>
          <td>{{ volume.estado }}</td>
          <td>{{ volume.tipo_embalagem }}</td>
          <td>{{ volume.data_entrega == null ? "por entregar" : volume.data_entrega }}</td>
          <td  class="tools">
            <nuxt-link :to="`/volumes/${volume.id}.index`" class="actions"> Details</nuxt-link>
            <nuxt-link :to="`/volumes/${volume.id}.change_state`">Change State</nuxt-link>
            <nuxt-link :to="`/encomendas/${volume.encomenda_id}.volumes`">Encomenda</nuxt-link>
            <nuxt-link :to="`/volumes/${volume.id}.produtos`">Produtos</nuxt-link>
            <nuxt-link v-if="userRole == 'GESTOR'" :to="`/volumes/${volume.id}.change_state`" class="actions">Change State</nuxt-link>
            <!-- <nuxt-link :to="`/encomendas/${volume.encomenda_id}`">Encomenda</nuxt-link> -->
            <nuxt-link :to="`/volumes/${volume.id}.sensores`">Sensores</nuxt-link>

          </td>

        </tr>
      </tbody>
    </table>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue';
import Navbar from "~/components/navbar.vue";
import 'flowbite/dist/flowbite.css';

const config = useRuntimeConfig();
const api = config.public.API_URL;

// Estados para volumes e erros
const volumes = ref([]);
const error = ref(null);

// Função para buscar volumes da API
async function fetchVolumes() {
  try {
    // Recupera o token do sessionStorage
    const token = sessionStorage.getItem('authToken');
    console.log(token)
    if (!token) {
      window.location.href = '/login';
    }

    // Faz a requisição com o token no cabeçalho Authorization
    const response = await $fetch(`${api}/volumes`, {
      method: 'GET',
      headers: {
        Accept: 'application/json',
        Authorization: `Bearer ${token}` // Adiciona o token ao cabeçalho
      }
    });

    // Atualiza os volumes com os dados recebidos
    volumes.value = response;
  } catch (err) {
    console.error('Erro ao buscar volumes:', err);
    error.value = err;
  }
}

// Atualiza os volumes ao recarregar
async function refresh() {
  error.value = null;
  await fetchVolumes();
}

// Busca volumes ao montar o componente
onMounted(async () => {
  const userRole = ref(null);
  await fetchVolumes();
    if (typeof window !== 'undefined') {
      userRole.value = sessionStorage.getItem('userRole');

    }
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

.volumes-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 15px;
}

.volumes-table caption {
  font-size: 1.2em;
  margin-bottom: 10px;
  font-weight: bold;
}

.volumes-table th,
.volumes-table td {
  border: 1px solid #ddd;
  padding: 10px;
  text-align: left;
}

.volumes-table tr:nth-child(even) {
  background-color: #f9f9f9;
}

.volumes-table tr:hover {
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