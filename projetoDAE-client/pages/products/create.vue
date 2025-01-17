<template>
  <h2>Add new Product</h2>
  <form @submit.prevent="create">
    <div>Name:
      <input v-model.trim="productForm.name" type="text">
      <span v-if="nameError" class="error">
 ERROR: {{ nameError }}</span></div>
    <div>Category:
      <select v-model="productForm.category">
        <option value="">--- Please select Category ---</option>
        <option value="Alimentar">Alimentar</option>
        <option value="Eletrodomestico">Eletrodomestico</option>
        <option value="Eletronico">Eletronico</option>
      </select>
      <span v-if="categoryError" class="error">
 ERROR: {{ categoryError }}</span></div>
    <div>Price:
      <input v-model.trim="productForm.price" type="text">
      <span v-if="priceError" class="error">
 ERROR: {{ priceError }}</span></div>
    <button type="reset">RESET</button>
    <button type="submit" :disabled="isFormInvalid">CREATE</button>
  </form>
  <hr>
  <pre>{{ messages }}</pre>
</template>
<script setup>
const productForm = reactive({
  name: null,
  category: null,
  price: null
})
const messages = ref([])
const config = useRuntimeConfig()
const api = config.public.API_URL
const { data: products } = await useFetch(`${api}/products`)
// Field validation rules...
const nameError = computed(() => {
  if (productForm.name === null) return null
  if (! productForm.name )
    return 'Name is required'
  if ( productForm.name.length < 3 )
    return 'Name must be at least 3 characters'
  return null
})
const categoryError = computed(() => {
  if (productForm.category === null) return null
  if ( ! productForm.category )
    return 'Category is required'

  return null
})

const priceError = computed(() => {
  if (productForm.price === null) return null
  if ( ! productForm.price )
    return 'price is required'
  if ( ! productForm.price <0 )
    return 'price is required to be greater than 0'
  return null
})

const isFormInvalid = computed(() => {
  return nameError.value
      || categoryError.value
      || priceError.value
})
async function create() {
  try {
    await $fetch(`${api}/products`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: productForm,
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