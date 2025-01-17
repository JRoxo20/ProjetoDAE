<template>
    <h2>Add new Encomenda</h2>
  <form @submit.prevent="create">
      <div>Id:
        <input v-model.trim="encomendaForm.id" type="text">
        <span v-if="idError" class="error">
 ERROR: {{ idError }}</span></div>
    <div>Client Username:
      <input v-model.trim="encomendaForm.usernameCliente" type="text">
      <span v-if="nameError" class="error">
 ERROR: {{ nameError }}</span></div>
    <button type="reset">RESET</button>
    <button type="submit" :disabled="isFormInvalid">CREATE</button>
  </form>
  <hr>
  <pre>{{ messages }}</pre>
</template>
<script setup>
const encomendaForm = reactive({
  id: null,
  usernameCliente: null
})
const messages = ref([])
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
  } catch (e) {
    console.log(e)
  }
}
</script>
<style scoped>
.error {
  color: red
}
</style>
}