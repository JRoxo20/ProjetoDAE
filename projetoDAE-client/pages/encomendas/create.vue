<template>
  <Navbar activePage="orders" />
  <div class="container">
    <h2 class="title">Create Encomenda</h2>
    <form @submit.prevent="createEncomenda" class="form">
      <div class="form-group">
        <label>ID Encomenda:</label>
        <input v-model="encomendaForm.id" type="number" required />
      </div>
      <div class="form-group">
        <label>Username Cliente:</label>
        <input v-model="encomendaForm.usernameCliente" type="text" required />
      </div>

      <div class="form-group">
        <h3>Volumes</h3>
        <div v-for="(volume, index) in encomendaForm.volumes" :key="index" class="volume-group">
          <h4>Volume {{ index + 1 }}</h4>
          <label>ID Volume:</label>
          <input v-model="volume.id" type="number" required />
          <label>Tipo de Embalagem:</label>
          <select v-model="volume.tipo_embalagem">
            <option value="">Selecione</option>
            <option value="Normal">Normal</option>
            <option value="Isotérmica">Isotérmica</option>
          </select>

          <h5>Produtos</h5>
          <div v-for="(produto, pIndex) in volume.produtos" :key="pIndex" class="produto-group">
            <label>ID Produto:</label>
            <input v-model="produto.id_produto" type="number" required />
            <label>Quantidade:</label>
            <input v-model="produto.quantidade" type="number" required />
            <button @click="removeProduto(index, pIndex)">Remover Produto</button>
          </div>
          <button @click="addProduto(index)">Adicionar Produto</button>

          <h5>Sensores</h5>
          <div v-for="(sensor, sIndex) in volume.sensores" :key="sIndex" class="sensor-group">
            <label>ID Sensor:</label>
            <input v-model="sensor.id" type="number" required />
            <label>Tipo:</label>
            <input v-model="sensor.tipo" type="text" required />
            <button @click="removeSensor(index, sIndex)">Remover Sensor</button>
          </div>
          <button @click="addSensor(index)">Adicionar Sensor</button>

          <button @click="removeVolume(index)">Remover Volume</button>
        </div>
        <button @click="addVolume">Adicionar Volume</button>
      </div>

      <button type="submit">Criar Encomenda</button>

    </form>
  </div>
  <hr class="divider" />

  <<pre>{{ messages }}</pre>
</template>

<script setup>
import { reactive, ref } from 'vue';

const config = useRuntimeConfig();
const apiBaseUrl = config.public.API_URL; // Substitua pela URL correta.

const encomendaForm = reactive({
  id: null,
  usernameCliente: '',
  volumes: [],
});
const messages = ref([]);

function addVolume() {
  encomendaForm.volumes.push({
    id: null,
    estado: 1,
    tipo_embalagem: null,
    data_entrega: null,
    encomenda_id: null,
    produtos: [],
    sensores: [],
  });
}

function removeVolume(index) {
  encomendaForm.volumes.splice(index, 1);
}

function addProduto(volumeIndex) {
  encomendaForm.volumes[volumeIndex].produtos.push({
    id_produto: null,
    quantidade: null
  });
}

function removeProduto(volumeIndex, produtoIndex) {
  encomendaForm.volumes[volumeIndex].produtos.splice(produtoIndex, 1);
}

function addSensor(volumeIndex) {
  encomendaForm.volumes[volumeIndex].sensores.push({
    id: null, estado: 1, tipo: null, volume_id: null
  });
}

function removeSensor(volumeIndex, sensorIndex) {
  encomendaForm.volumes[volumeIndex].sensores.splice(sensorIndex, 1);
}

async function createEncomenda() {
  try {
    const payload = { ...encomendaForm };
    console.log(payload)
    await $fetch(`${apiBaseUrl}/encomendas`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token.value}`,
      },
      body: JSON.stringify(payload),
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

  } catch (error) {
    console.error('Erro ao criar encomenda:', error);
  }
}
const token = ref(null);
onMounted(() => {
   token.value = sessionStorage.getItem('authToken');
  if (!token) {
    window.location.href = '/login';
  }
});
</script>

<style scoped>
.volume-group,
.produto-group,
.sensor-group {
  margin-bottom: 15px;
  border: 1px solid #ccc;
  padding: 10px;
}

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