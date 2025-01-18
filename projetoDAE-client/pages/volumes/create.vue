<template>
  <Navbar activePage="volumes" />
  <div class="container">
    <h2 class="title">Add New Volume</h2>
    <form @submit.prevent="create" class="form">
      <div class="form-group">
        <label for="id">ID:</label>
        <input id="id" type="text" v-model="volumeForm.id" class="input" />
        <span v-if="idError" class="error">ERROR: {{ idError }}</span>
      </div>
    <div class="form-group">
      <label for="tipo">Tipo de Embalagem:</label>
      <select v-model="volumeForm.tipo_embalagem">
        <option value="">--- Please select Tipo de Embalagem ---</option>
        <option value="Normal">Normal</option>
        <option value="Isotérmica">Isotérmica</option>
      </select>
      <span v-if="tipo_embalagemError" class="error">
        ERROR: {{ tipo_embalagemError }}</span>
    </div>

    <div class="form-group">
      <label for="id_encomenda">Id Encomenda:</label>
      <input v-model.trim="volumeForm.encomenda_id" type="number">
      <span v-if="encomendaError" class="error">
        ERROR: {{ encomendaError }}</span>
    </div>
    <div class="form-group">
      <label for="produtos"> Produtos:</label>
      <div v-for="(produto, index) in volumeForm.produtos" :key="index" class="produto-group">
        <label>ID Produto:
          <input v-model.number="produto.id_produto" type="number">
        </label>
        <span v-if="produtosErrors[index]?.id_produto" class="error">
          {{ produtosErrors[index].id_produto }}
        </span>
        <label>Quantidade:
          <input v-model.number="produto.quantidade" type="number">
        </label>
        <span v-if="produtosErrors[index]?.quantidade" class="error">
          {{ produtosErrors[index].quantidade }}
        </span>
        <div>
          <button type="button" @click="removeProduto(index)">Remove</button>
        </div>
      </div>

      <button type="button" @click="addProduto">Add Produto</button>
    </div>

    <div class="form-group">
      <label for="sensores"> Sensores:</label>
      <div v-for="(sensor, index) in volumeForm.sensores" :key="index" class="sensor-group">
        <label>ID Sensor:
          <input v-model.number="sensor.id" type="number">
        </label>
        <span v-if="sensoresErrors[index]?.id" class="error">
          {{ sensoresErrors[index].id }}
        </span>
        <label>Estado:
          <input v-model.number="sensor.estado" type="number">
        </label>
        <span v-if="sensoresErrors[index]?.estado" class="error">
          {{ sensoresErrors[index].estado }}
        </span>
        <label>Tipo:
          <input v-model.number="sensor.tipo" type="number">
        </label>
        <span v-if="sensoresErrors[index]?.tipo" class="error">
          {{ sensoresErrors[index].tipo }}
        </span>
        <div>
          <button type="button" @click="removeProduto(index)">Remove</button>
        </div>
      </div>
      <button type="button" @click="addSensor">Add Sensor</button>
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
import { reactive, ref, computed } from 'vue';
import Navbar from "~/components/navbar.vue";

const volumeForm = reactive({
  id: null,
  estado: 1,
  tipo_embalagem: null,
  data_entrega: null,
  encomenda_id: null,
  produtos: [],
  sensores: [],
});
const successMessage = ref("")
const errorMessage = ref("")
const config = useRuntimeConfig();
const api = config.public.API_URL;

// Validation rules
const idError = computed(() => {
  //  if (volumeForm.id === null) return null;
  if (!volumeForm.id) return 'ID is required';
  if (volumeForm.id < 0) return 'ID must be greater than 0';
  return null;
});
const encomendaError = computed(() => {
  //  if (volumeForm.encomenda_id === null) return null;
  if (!volumeForm.encomenda_id) return 'Encomenda ID is required';
  if (volumeForm.encomenda_id < 0) return 'Encomenda ID must be greater than 0';
  return null;
});
const tipo_embalagemError = computed(() => {
  //  if (volumeForm.tipo_embalagem === null) return null;
  if (!volumeForm.tipo_embalagem) return 'Tipo de Embalagem is required';
  return null;
});

// Validation rules for products
const produtosErrors = computed(() =>
  volumeForm.produtos.map((produto, index) => {
    const errors = {};
    if (!produto.id_produto || produto.id_produto <= 0) {
      errors.id_produto = `ID Produto in row ${index + 1} is required and must be greater than 0.`;
    }
    if (!produto.quantidade || produto.quantidade <= 0) {
      errors.quantidade = `Quantidade in row ${index + 1} is required and must be greater than 0.`;
    }
    return errors;
  })
);

// Validation rules for sensors
const sensoresErrors = computed(() =>
  volumeForm.sensores.map((sensor, index) => {
    const errors = {};
    if (!sensor.id || sensor.id <= 0) {
      errors.id = `ID Sensor in row ${index + 1} is required and must be greater than 0.`;
    }
    if (!sensor.estado) {
      errors.estado = `Estado in row ${index + 1} is required.`;
    }
    if (!sensor.tipo || sensor.tipo <= 0) {
      errors.tipo = `Tipo in row ${index + 1} is required and must be greater than 0.`;
    }
    return errors;
  })
);

// Combined form validation
const isFormInvalid = computed(() => {
  const hasProdutoErrors = produtosErrors.value.some(
    (error) => Object.keys(error).length > 0
  );
  const hasSensorErrors = sensoresErrors.value.some(
    (error) => Object.keys(error).length > 0
  );
  return (
    idError.value ||
    encomendaError.value ||
    tipo_embalagemError.value ||
    hasProdutoErrors ||
    hasSensorErrors
  );
});


// Handlers for dynamic arrays
function addProduto() {
  volumeForm.produtos.push({ id_produto: null, quantidade: null });
}
function removeProduto(index) {
  volumeForm.produtos.splice(index, 1);
}
function addSensor() {
  volumeForm.sensores.push({ id: null, estado: 1, tipo: null, volume_id: volumeForm.id });
}
function removeSensor(index) {
  volumeForm.sensores.splice(index, 1);
}

async function create() {
  try {
    const payload = { ...volumeForm };

    // Set `volume_id` in each sensor
    payload.sensores.forEach(sensor => {
      sensor.volume_id = payload.id;
    });

    await $fetch(`${api}/volumes`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
      body: payload,
      onResponse({ request, response, options }) {
        messages.value.push({
          method: options.method,
          request: request,
          status: response.status,
          statusText: response.statusText,
          payload: response._data
        })
      }
    });
    successMessage.value = "Volume created successfully!"
    errorMessage.value = ""

  } catch (e) {
    console.error(e);
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
