<template>
  <Navbar  activePage="sensors" />
  <div v-if="error" class="error">Error: {{ error.message }}</div>
  <div v-else class="container">
    <h1>Sensors</h1>
    <div class="buttons">
      <button @click.prevent="refresh" class="create-button">🔄 Refresh Data</button>
    </div>
    <table class="sensor-table">
      <thead>
      <tr>
        <th>Id</th>
        <th>Estado</th>
        <th>Tipo</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="sensor in sensors" :key="sensor.id">
        <td>{{ sensor.id }}</td>
        <td>{{ sensor.estado }}</td>
        <td>{{ sensor.tipo }}</td>
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
const error = ref(null);

const sensors = ref([]);

async function fetchAllSensors() {
  try {
    const token = sessionStorage.getItem('authToken');
    if (!token) {
      window.location.href = '/login';
    }
    const response = await $fetch(`${api}/sensors/gps`, {
      method: 'GET',
      headers: {
        Accept: 'application/json',
        Authorization: `Bearer ${token}`,
      },
    });
    sensors.value = response; // Assign the response to the products variable
  } catch (err) {
    console.error('Error fetching sensors:', err);
    error.value = err;
  }
}

async function refresh() {
  error.value = null;
  await fetchAllSensors();
}

onMounted(async () => {
  await fetchAllSensors();
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

.sensor-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 15px;
}

.sensor-table caption {
  font-size: 1.2em;
  margin-bottom: 10px;
  font-weight: bold;
}

.sensor-table th,
.sensor-table td {
  border: 1px solid #ddd;
  padding: 10px;
  text-align: left;
}

.sensor-table tr:nth-child(even) {
  background-color: #f9f9f9;
}

.sensor-table tr:hover {
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
