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
    <div>Quantity:
      <input v-model.trim="productForm.quantity" type="text">
      <span v-if="quantityError" class="error">
 ERROR: {{ quantityError }}</span></div>
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
  quantity: null,
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

  if ( productForm.category != "Alimentar" || productForm.category != "Eletrodomestico"  )
    return 'Category must be Alimentar or Eletrodomestico'

  return null
})
const quantityError = computed(() => {
  if (productForm.quantity === null) return null
  if ( ! productForm.quantity )
    return 'quantity is required'

  if ( productForm.quantity< 1 )
    return 'quantity is required to be greater than 0'

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
      || quantityError.value
      || priceError.value
})
async function create() {
  try {
    await $fetch(`${api}/product`, {
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
</script>
<style scoped>
.error {
  color: red
}
</style>
}