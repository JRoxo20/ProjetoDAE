<template>
  <h2>Add new Volume</h2>
  <form @submit.prevent="create">
    <div>Id:
      <input v-model.trim="volumeForm.id" type="text">
      <span v-if="idError" class="error">
 ERROR: {{ idError }}</span></div>
    <div>Tipo de Embalagem:
      <select v-model="volumeForm.tipo_embalagem">
        <option value="">--- Please select Tipo de Embalagem ---</option>
        <option value="Normal">Normal</option>
        <option value="Isotérmica">Isotérmica</option>
      </select>
      <span v-if="tipo_embalagemError" class="error">
 ERROR: {{ tipo_embalagemError }}</span></div>
    <div>Id Encomenda:
      <input v-model.trim="volumeForm.encomenda_id" type="text">
      <span v-if="encomendaError" class="error">
 ERROR: {{ encomendaError }}</span></div>
    <button type="reset">RESET</button>
    <button type="submit" :disabled="isFormInvalid">CREATE</button>
  </form>
  <hr>
  <pre>{{ messages }}</pre>
</template>
<script setup>
const token = sessionStorage.getItem('authToken');
const volumeForm = reactive({
  id: null,
  tipo_embalagem: null,
  encomenda_id: null,
})
const messages = ref([])
const config = useRuntimeConfig()
const api = config.public.API_URL
const { data: volumes } = await useFetch(`${api}/volumes`)
// Field validation rules...
const idError = computed(() => {
  if (volumeForm.id === null) return null
  if ( ! volumeForm.id )
    return 'id is required'
  if ( ! volumeForm.id <0 )
    return 'id is required to be greater than 0'
  return null
})
const encomendaError = computed(() => {
  if (volumeForm.encomenda_id === null) return null
  if ( ! volumeForm.encomenda_id )
    return 'id is required'
  if ( ! volumeForm.encomenda_id <0 )
    return 'id is required to be greater than 0'
  return null
})

const tipo_embalagemError = computed(() => {
  if (volumeForm.tipo_embalagem === null) return null
  if ( ! volumeForm.tipo_embalagem )
    return 'Tipo de Embalagem is required'

  return null
})


const isFormInvalid = computed(() => {
  return idError.value
      || encomendaError.value
      || tipo_embalagemError.value
})
async function create() {
  try {
    await $fetch(`${api}/volumes`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json', Authorization: `Bearer ${token}` },
      body: volumeForm,
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
  } catch (e) {
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
.error {
  color: red
}
</style>
}