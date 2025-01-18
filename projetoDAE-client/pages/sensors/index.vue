<template>
  <Navbar activePage="sensors" />
  <div v-if="error" class="error">Error: {{ error.message }}</div>
  <div v-else class="container">
    <h1>Sensors</h1>
    <div v-if="userRole" class="buttons">
      <button @click.prevent="refresh" class="create-button">🔄 Refresh Data</button>
    </div>
    <table class="sensor-table">
      <thead>
      <tr>
        <th>Id</th>
        <th>Estado</th>
        <th>Tipo</th>
        <th>Actions</th>

      </tr>
      </thead>
      <tbody>
      <tr v-for="sensor in sensors" :key="sensor.id">
        <td>{{ sensor.id }}</td>
        <td>{{ sensor.estado }}</td>
        <td>{{ sensor.tipo }}</td>
        <td>
          <nuxt-link :to="`/sensors/${sensor.id}`" class="view-details" title="View Details"> 📄</nuxt-link>
        </td>
      </tr>
      </tbody>
    </table>
    <div v-if="userRole === 'GESTOR'" class="buttons-sensor">
      <nuxt-link to="/sensors/ativos" class="sensor-button" aria-label="View available sensors">Available Sensors</nuxt-link>
      <nuxt-link to="/sensors/inativos" class="sensor-button" aria-label="View unavailable sensors">Unavailable Sensors</nuxt-link>
      <nuxt-link to="/sensors/temperatura" class="sensor-button" aria-label="View temperature sensors">Temperature Sensors</nuxt-link>
      <nuxt-link to="/sensors/gps" class="sensor-button" aria-label="View GPS sensors">GPS Sensors</nuxt-link>
      <nuxt-link to="/sensors/pressao" class="sensor-button" aria-label="View pressure sensors">Pressure Sensors</nuxt-link>
      <nuxt-link to="/sensors/aceleracao" class="sensor-button" aria-label="View aceleration sensors">Aceleration Sensors</nuxt-link>
    </div>

  </div>
</template>

<script setup>
import 'flowbite/dist/flowbite.css';
import { onMounted, ref } from "vue";
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
    const response = await $fetch(`${api}/sensors`, {
      method: 'GET',
      headers: {
        Accept: 'application/json',
        Authorization: `Bearer ${token}`,
      },
    });
    sensors.value = response;
  } catch (err) {
    console.error('Error fetching sensors:', err);
    error.value = err;
  }
}

async function changeState(id) {
  try {
    const token = sessionStorage.getItem('authToken');
    const sensor = sensors.value.find(sensor => sensor.id === id);
    const newState = sensor.estado === 'ATIVO' ? 'INATIVO' : 'ATIVO';
    await $fetch(`${api}/sensors/${id}`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
      body: { estado: newState }
    });
    await fetchAllSensors();
  } catch (err) {
    console.error('Error changing sensor state:', err);
    error.value = err;
  }
}

async function refresh() {
  error.value = null;
  await fetchAllSensors();
}

const userRole = ref(null);
onMounted(async () => {
  await fetchAllSensors();
  if(typeof window !== 'undefined') {
    userRole.value = sessionStorage.getItem('role');
  }
});
</script>

<style >

.change-state-button{
  text-decoration: underline;
  color: black;
  border: none;
  cursor: pointer;
}

.view-details{
  text-decoration: underline;
  margin-right: 20px;
}
h1{
  font-size:30px;
  margin-left: 4%;
}

.container {
  padding: 20px;
  font-family: Arial, sans-serif;
}
.create-button{
  display: inline-block;
  padding: 10px 15px;
  background-color: #007bff;
  color: white;
  text-decoration: none;
  border-radius: 5px;
  transition: background-color 0.3s ease;
}

.create-button:hover{
  background-color: #0056b3;
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


.buttons {
  display: flex;
  justify-content: flex-start;
  gap: 10px;
  margin-bottom: 15px;
}

.buttons-sensor {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-top: 15px;

}
.sensor-button {
  background-color: #007bff;
  display: inline-block;
  padding: 6px 12px;
  color: white;
  text-decoration: none;
  border-radius: 5px;
  font-size: 14px;
  transition: background-color 0.3s ease;
}

.sensor-button:hover {
  background-color: #0056b3;
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





.error {
  color: red;
  font-weight: bold;
}
</style>