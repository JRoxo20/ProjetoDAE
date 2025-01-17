<template>
  <div v-if="volume" class="volume-container">
    <h2 class="volume-title">Details of Volume {{ id }}</h2>
    <div class="volume-info">
      <p>{{ volume }}</p>
    </div>

    <div v-if="messages.length" class="error-messages">
      <h3>Error Messages:</h3>
      <ul>
        <li v-for="(msg, index) in messages" :key="index">{{ msg }}</li>
      </ul>
    </div>
  </div>

  <div v-else class="loading-state">
    <p>Loading volume data...</p>
  </div>
</template>

<script setup>
import {onMounted} from "vue";

const route = useRoute()
const id = route.params.id
const config = useRuntimeConfig()
const api = config.public.API_URL

const token = sessionStorage.getItem('authToken');
if (!token) {
  window.location.href = '/login';
}

// Fetch volume data
const { data: volume, error: volumeErr } = await useFetch(`${api}/volumes/${id}`, {
  method: 'GET',
  headers: {
    Accept: 'application/json',
    Authorization: `Bearer ${token}` // Adds the token to the header
  }
});

const messages = ref([])
if (volumeErr.value) messages.value.push(volumeErr.value)

const userRole = ref(null);
onMounted(() => {
  if (typeof window !== "undefined") {
    userRole.value = sessionStorage.getItem("role");
  }

});
</script>

<style scoped>
.volume-container {
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.volume-title {
  font-size: 24px;
  color: #007bff;
  margin-bottom: 16px;
}

.volume-info {
  font-size: 16px;
  color: #333;
  padding: 10px;
  background-color: #fff;
  border-radius: 6px;
  margin-bottom: 20px;
  box-shadow: 0 1px 5px rgba(0, 0, 0, 0.1);
}

.error-messages {
  background-color: #f8d7da;
  color: #721c24;
  padding: 15px;
  border-radius: 6px;
  box-shadow: 0 1px 5px rgba(0, 0, 0, 0.1);
}

.error-messages h3 {
  margin: 0;
  font-size: 18px;
  color: #721c24;
}

.error-messages ul {
  list-style-type: none;
  padding-left: 0;
}

.error-messages li {
  font-size: 14px;
}

.loading-state {
  text-align: center;
  font-size: 18px;
  color: #007bff;
  margin-top: 20px;
}
</style>
