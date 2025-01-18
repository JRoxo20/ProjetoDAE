<template>
  <Navbar activePage="orders" />
  <div class="container">
    <h2 class="title">Add New Encomenda</h2>
    <form @submit.prevent="create" class="form">
      <div class="form-group">
        <label for="id">ID:</label>
        <input v-model.trim="encomendaForm.id" type="text">
        <span v-if="idError" class="error">
 ERROR: {{ idError }}</span>
      </div>
    <div class="form-group">
      <label for="usernameCliente">Username Cliente:</label>
      <input v-model.trim="encomendaForm.usernameCliente" type="text">
      <span v-if="nameError" class="error">
 ERROR: {{ nameError }}</span>
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
import {ref} from "vue";
import Navbar from "~/components/navbar.vue";

const encomendaForm = reactive({
  id: null,
  usernameCliente: null
})
const messages = ref([])
const successMessage = ref("")
const errorMessage = ref("")
const config = useRuntimeConfig()
const api = config.public.API_URL
const { data: orders } = await useFetch(`${api}/encomendas`)
// Field validation rules...
const nameError = computed(() => {
  if (encomendaForm.usernameCliente === null) return null
  if (! encomendaForm.usernameCliente )
    return 'Name is required'
  if ( encomendaForm.usernameCliente.length < 3 )
    return 'Name must be at least 3 characters'
  return null
})
const idError = computed(() => {
  if (encomendaForm.id === null) return null
  if ( ! encomendaForm.id )
    return 'id is required'
  if ( ! encomendaForm.id <0 )
    return 'id is required to be greater than 0'
  return null
})


const isFormInvalid = computed(() => {
  return nameError.value || idError.value
})
async function create() {
  try {
    await $fetch(`${api}/encomendas`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: encomendaForm,
      onResponse({ request, response, options }) {
        messages.value.push({
          method: options.method,
          request: request,
          status: response.status,
          statusText: response.statusText,
          payload: response._data
        })
      }
    })
    successMessage.value = "Volume created successfully!"
    errorMessage.value = ""
  } catch (e) {
    console.log(e)
    successMessage.value = ""
    errorMessage.value = "Failed to create sensor. Please try again."

  }
}
  onMounted(() => {
    const token = sessionStorage.getItem('authToken');
    if (!token) {
      window.location.href = '/login';
    }
  });
</script>
<style scoped>
  .back-button {
  width: 30px;
  height: 30px;
  margin: 20px;
  cursor: pointer;
}

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