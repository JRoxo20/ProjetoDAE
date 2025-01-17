<template>
    <h2>Add new Encomenda</h2>
  <form @submit.prevent="create">
    <div>Client Username:
      <input v-model.trim="encomendaForm.name" type="text">
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
  name: null
})
const messages = ref([])
const config = useRuntimeConfig()
const api = config.public.API_URL
const { data: orders } = await useFetch(`${api}/encomendas`)
// Field validation rules...
const nameError = computed(() => {
  if (encomendaForm.name === null) return null
  if (! encomendaForm.name )
    return 'Name is required'
  if ( encomendaForm.name.length < 3 )
    return 'Name must be at least 3 characters'
  return null
})


const isFormInvalid = computed(() => {
  return nameError.value
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