<template>
  <Navbar activePage="sensors" />
  <div v-if="error" class="error">Error: {{ error.message }}</div>
  <div v-else class="container">

    <div v-if="sensor">
      <h2 class="title">Sensor Details</h2>

      <div class="sensor-info">
        <p><strong>ID:</strong> {{ sensor.id }}</p>
        <p><strong>Estado:</strong> {{ sensor.estado }}</p>
        <p><strong>Tipo:</strong> {{ sensor.tipo }}</p>
      </div>

      <h3>Registros</h3>
      <table v-if="Array.isArray(sensor.dados)" class="dados-table">
        <thead>
        <tr>
          <th>Valor</th>
          <th>Timestamp</th>
          <th>Mensagem</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="dado in sensor.dados" :key="dado.id">
          <td>{{ dado.valor }}</td>
          <td>{{ new Date(dado.timestamp).toLocaleString() }}</td>
          <td>{{ dado.mensagem }}</td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import Navbar from '~/components/navbar.vue';

const route = useRoute();
const sensorId = route.params.id;
const sensor = ref(null);
const error = ref(null);

const config = useRuntimeConfig();
const api = config.public.API_URL;

async function fetchSensorDetails() {
  try {
    const token = sessionStorage.getItem('authToken');
    if (!token) {
      window.location.href = '/login';
    }
    const response = await $fetch(`${api}/sensors/${sensorId}`, {
      method: 'GET',
      headers: {
        Accept: 'application/json',
        Authorization: `Bearer ${token}`,
      },
    });
    sensor.value = response;
  } catch (err) {
    console.error('Error fetching sensor details:', err);
    error.value = err;
  }
}

onMounted(async () => {
  await fetchSensorDetails();
});
</script>

<style scoped>
.container {
  padding: 20px;
  font-family: Arial, sans-serif;
  background-color: #f8f9fa;
  border-radius: 10px;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
}

.title {
  font-size: 1.8rem;
  color: #333;
  margin-bottom: 15px;
  text-align: center;
}

.sensor-info {
  margin-bottom: 20px;
  padding: 10px;
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);
}

.sensor-info p {
  margin: 5px 0;
  font-size: 1rem;
  color: #555;
}

.dados-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 15px;
  background-color: #ffffff;
  border-radius: 8px;
  overflow: hidden;
}

.dados-table th, .dados-table td {
  padding: 10px;
  text-align: left;
  border: 1px solid #ddd;
}

.dados-table th {
  background-color: #007bff;
  color: #ffffff;
  font-weight: bold;
}

.dados-table td {
  color: #333;
}

.dados-table tr:nth-child(even) {
  background-color: #f2f2f2;
}

.error {
  color: red;
  font-weight: bold;
  margin: 20px;
  text-align: center;
}
</style>
