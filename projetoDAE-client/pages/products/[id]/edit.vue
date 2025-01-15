<template>
  <div>
    <h1>Update Product</h1>
    <div v-if="error">Error: {{ error.message }}</div>
    <div v-else>
      <form @submit.prevent="updateProduct">
        <div>
          <label for="name">Name:</label>
          <input type="text" id="name" v-model="product.name" required />
        </div>
        <div>
          <label for="brand">Brand:</label>
          <input type="text" id="brand" v-model="product.brand" required />
        </div>
        <div>
          <label for="category">Category:</label>
          <input type="text" id="category" v-model="product.category" required />
        </div>
        <div>
          <label for="quantity">Quantity:</label>
          <input type="number" id="quantity" v-model="product.quantity" required />
        </div>
        <div>
          <label for="price">Price:</label>
          <input type="number" id="price" v-model="product.price" step="0.01" required />
        </div>
        <button type="submit">Save Changes</button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
const router = useRouter()
const config = useRuntimeConfig()
const api = config.public.API_URL

const product = ref({
  name: '',
  brand: '',
  category: '',
  quantity: 0,
  price: 0,
})

const error = ref(null)

const route = useRoute()
const productId = route.params.id

onMounted(async () => {
  try {
    const response = await fetch(`${api}/products/${productId}`)
    if (!response.ok) throw new Error('Failed to fetch product details')
    Object.assign(product.value, await response.json())
  } catch (err) {
    error.value = err
  }
})

const updateProduct = async () => {
  try {
    const response = await fetch(`${api}/products/${productId}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(product.value),
    })
    if (!response.ok) throw new Error('Failed to update product')
    router.push('/products') // Redirect to products list after update
  } catch (err) {
    error.value = err
  }
}
</script>

<style>
form {
  max-width: 400px;
}

div {
  margin-bottom: 1rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
}

input {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

button {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}
</style>
