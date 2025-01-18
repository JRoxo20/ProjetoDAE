<template>
  <Navbar activePage="sensors" />
  <div class="container">
    <h1>Dados do Sensor {{ id }}</h1>
    <form @submit.prevent="create" class="form">

      <div class="form-group">
        <label for="valor">Valor:</label>
        <input id="valor" type="text" v-model="sensorForm.valor" class="input" />
        <span v-if="valorError" class="error">ERROR: {{ valorError }}</span>
      </div>
      <div class="form-group">
        <label for="mensagem">Mensagem:</label>
        <input id="mensagem" type="text" v-model="sensorForm.mensagem" class="input" />
        <span v-if="mensagemError" class="error">ERROR: {{ mensagemError }}</span>
      </div>

      <div class="button-group">
        <button type="reset" class="button reset">RESET</button>
        <button type="submit" :disabled="isFormInvalid" class="button create">CREATE</button>
      </div>
    </form>

    <hr class="divider" />

    <div v-if="successMessage" class="success">{{ successMessage }}</div>
    <div v-if="errorMessage" class="error">{{ errorMessage }}</div>
  </div>
</template>

<script setup>
import Navbar from "~/components/navbar.vue";

const sensorForm = reactive({
  mensagem: null,
  valor: null
})
const successMessage = ref("")
const errorMessage = ref("")
const config = useRuntimeConfig()
const api = config.public.API_URL
const route = useRoute();
const id = route.params.id;
const { data: sensors } = await useFetch(`${api}/sensors`)

const valorError = computed(() => {
  if (sensorForm.valor === null) return null
  if (!sensorForm.valor) return 'Valor is required'
  return null
})
const mensagemError = computed(() => {
  if (sensorForm.mensagem === null) return null
  if (!sensorForm.mensagem) return 'Mensagem is required'
  return null
})


const isFormInvalid = computed(() => {
  return valorError.value || mensagemError.value
})

async function create() {
  try {
    const token = sessionStorage.getItem('authToken');
    const response = await $fetch(`${api}/sensors/${id}/novoDado`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json',Authorization: `Bearer ${token}`, },
      body: sensorForm
    })
    successMessage.value = "Dado created successfully!"
    errorMessage.value = ""
  } catch (e) {
    successMessage.value = ""
    errorMessage.value = "Failed to create dado. Please try again."
    console.log(e)
  }
}

onMounted(() => {
  const token = sessionStorage.getItem('authToken');
  if (!token) {
    window.location.href = '/login';
  }
})
</script>

<style scoped>
.container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.title {
  font-size: 1.8rem;
  margin-bottom: 1.5rem;
  text-align: center;
  color: #333;
}

.form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.form-group {
  display: flex;
  flex-direction: column;
}

.label {
  margin-bottom: 0.5rem;
  font-weight: bold;
  color: #555;
}

.input {
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 1rem;
}

.input:focus {
  outline: none;
  border-color: #007bff;
  box-shadow: 0 0 4px rgba(0, 123, 255, 0.3);
}

.error {
  color: red;
  font-size: 0.875rem;
  margin-top: 0.25rem;
}

.success {
  color: green;
  font-size: 1rem;
  margin-top: 1rem;
}

.button-group {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
}

.button {
  padding: 0.75rem 1.5rem;
  font-size: 1rem;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.button.reset {
  background-color: #6c757d;
}

.button.reset:hover {
  background-color: #5a6268;
}

.button.create {
  background-color: #007bff;
}

.button.create:hover {
  background-color: #0056b3;
}

.button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.divider {
  margin: 2rem 0;
  border: none;
  border-top: 1px solid #ddd;
}

.messages {
  background-color: #f1f1f1;
  padding: 1rem;
  border-radius: 4px;
  font-size: 0.9rem;
  color: #333;
}
</style>
