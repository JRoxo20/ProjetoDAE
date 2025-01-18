<template>
    <h2>Add new Volume to Encomenda {{ id }}</h2>
    <form @submit.prevent="create">
      <div>Id:
        <input v-model.trim="volumeForm.id" type="number">
        <span v-if="idError" class="error">
          ERROR: {{ idError }}</span>
      </div>
      <div>Tipo de Embalagem:
        <select v-model="volumeForm.tipo_embalagem">
          <option value="">--- Please select Tipo de Embalagem ---</option>
          <option value="Normal">Normal</option>
          <option value="Isotérmica">Isotérmica</option>
        </select>
        <span v-if="tipo_embalagemError" class="error">
          ERROR: {{ tipo_embalagemError }}</span>
      </div>
      <div>
        Produtos:
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
          <button type="button" @click="removeProduto(index)">Remove</button>
        </div>
        <button type="button" @click="addProduto">Add Produto</button>
      </div>
  
      <div>
        Sensores:
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
          <button type="button" @click="removeSensor(index)">Remove</button>
        </div>
        <button type="button" @click="addSensor">Add Sensor</button>
      </div>
  
      <button type="reset">RESET</button>
      <button type="submit" :disabled="isFormInvalid">CREATE</button>
    </form>
    <hr>
    <pre>{{ messages }}</pre>
  </template>
  
  <script setup>
  import { reactive, ref, computed } from 'vue';

  const route = useRoute()
  const id = route.params.id
  
  const token = sessionStorage.getItem('authToken');
  const volumeForm = reactive({
    id: null,
    estado: 1,
    tipo_embalagem: null,
    data_entrega: null,
    encomenda_id: id,
    produtos: [],
    sensores: [],
  });
  const messages = ref([]);
  const config = useRuntimeConfig();
  const api = config.public.API_URL;
  
  // Validation rules
  const idError = computed(() => {
    //  if (volumeForm.id === null) return null;
    if (!volumeForm.id) return 'ID is required';
    if (volumeForm.id < 0) return 'ID must be greater than 0';
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
  
      await $fetch(`${api}/encomendas/${id}`, {
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
  
    } catch (e) {
      console.error(e);
    }
  }
  
  onMounted(() => {
    if (!token) {
      window.location.href = '/login';
    }
  });
  </script>
  
  <style scoped>
  .error {
    color: red;
  }
  
  .produto-group,
  .sensor-group {
    margin-bottom: 10px;
  }
  </style>
  