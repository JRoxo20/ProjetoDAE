<template>
  <div class="container">
    <h2 class="title">Add New Sensor</h2>
    <form @submit.prevent="create" class="form">

      <div class="form-group">
        <label for="id">ID:</label>
        <input id="id" type="text" v-model="sensorForm.id" class="input" />
        <span v-if="idError" class="error">ERROR: {{ idError }}</span>
      </div>

      <div class="form-group">
        <label for="tipo">Tipo:</label>
        <select id="tipo" v-model="sensorForm.tipo" class="input">
          <option value="">--- Please select type ---</option>
          <option value="Temperatura">Temperatura</option>
          <option value="Pressao">Pressão atmosférica</option>
          <option value="Gps">Posicionamento global</option>
        </select>
        <span v-if="tipoError" class="error">ERROR: {{ tipoError }}</span>
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
const sensorForm = reactive({
  id: null,
  tipo: null
})
const successMessage = ref("")
const errorMessage = ref("")
const config = useRuntimeConfig()
const api = config.public.API_URL
const { data: sensors } = await useFetch(`${api}/sensors`)

const idError = computed(() => {
  if (sensorForm.id === null) return null
  if (!sensorForm.id) return 'ID is required'
  return null
})
const tipoError = computed(() => {
  if (sensorForm.tipo === null) return null
  if (!sensorForm.tipo) return 'Tipo is required'
  return null
})

const isFormInvalid = computed(() => {
  return idError.value || tipoError.value
})

async function create() {
  try {
    const token = sessionStorage.getItem('authToken');
    const response = await $fetch(`${api}/sensors`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json',Authorization: `Bearer ${token}`, },
      body: sensorForm
    })
    successMessage.value = "Sensor created successfully!"
    errorMessage.value = ""
  } catch (e) {
    successMessage.value = ""
    errorMessage.value = "Failed to create sensor. Please try again."
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
