<template>
  <Navbar active-page="products" />
  <div class="container">
    <h2 class="title">Add New Product</h2>
    <form @submit.prevent="create" class="form">
      <div class="form-group">
        <label for="name">Name:</label>
        <input id="name" type="text" v-model="productForm.name" class="input" />
        <span v-if="nameError" class="error">ERROR: {{ nameError }}</span>
      </div>
      <div class="form-group">
        <label for="category">Category:</label>
        <select id="category" v-model="productForm.category" class="input">
          <option value="">--- Please select Category ---</option>
          <option value="Alimentar">Alimentar</option>
          <option value="Eletrodomestico">Eletrodomestico</option>
          <option value="Eletronico">Eletronico</option>
        </select>
        <span v-if="categoryError" class="error">ERROR: {{ categoryError }}</span>
      </div>
      <div class="form-group">
        <label for="price">Price:</label>
        <input id="price" type="text" v-model="productForm.price" class="input" />
        <span v-if="priceError" class="error">ERROR: {{ priceError }}</span>
      </div>

      <div class="button-group">
        <button type="reset" class="button reset">RESET</button>
        <button type="submit" :disabled="isFormInvalid" class="button create">CREATE</button>
      </div>
  </form>
  <hr>
    <hr class="divider" />

    <div v-if="successMessage" class="success">{{ successMessage }}</div>
    <div v-if="errorMessage" class="error">{{ errorMessage }}</div>
  </div>
</template>
<script setup>
const productForm = reactive({
  name: null,
  category: null,
  price: null
})
const successMessage = ref("")
const errorMessage = ref("")
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
    const token = sessionStorage.getItem('authToken');
    const response = await $fetch(`${api}/products`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json',Authorization: `Bearer ${token}`, },
      body: productForm
    })
      successMessage.value = "Sensor created successfully!"
      errorMessage.value = ""
  } catch (e) {
    successMessage.value = ""
    errorMessage.value = "Failed to create product. Please try again."
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
