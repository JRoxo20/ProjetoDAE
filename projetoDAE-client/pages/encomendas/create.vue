<template>
  <h2>Create Encomenda</h2>
  <form @submit.prevent="createEncomenda">
    <div>
      <label>ID Encomenda:</label>
      <input v-model="encomendaForm.id" type="number" required />
    </div>
    <div>
      <label>Username Cliente:</label>
      <input v-model="encomendaForm.usernameCliente" type="text" required />
    </div>

    <div>
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
  <hr>
  <pre>{{ messages }}</pre>
</template>

<script setup>
import { reactive, ref } from 'vue';

const config = useRuntimeConfig();
const apiBaseUrl = config.public.API_URL; // Substitua pela URL correta.
const token = sessionStorage.getItem('authToken');

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
        Authorization: `Bearer ${token}`,
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
</script>

<style scoped>
.volume-group,
.produto-group,
.sensor-group {
  margin-bottom: 15px;
  border: 1px solid #ccc;
  padding: 10px;
}
</style>