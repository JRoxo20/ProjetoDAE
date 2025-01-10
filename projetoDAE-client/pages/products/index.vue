<template>
  <div v-if="error">Error: {{ error.message }}</div>
  <div v-else>
   <nuxt-link to="/products/create">Create a New Product</nuxt-link>
    <h2>Products</h2>
    <table>
      <thead>
      <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Category</th>
        <th>Quantity</th>
        <th>Price</th>
        <th>Tools</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="product in products">
        <td>{{ product.id }}</td>
        <td>{{ product.name }}</td>
        <td>{{ product.category }}</td>
        <td>{{product.quantity}}</td>
        <td>{{product.price}}</td>
        <td>
          <nuxt-link :to="`/products/${product.id}/edit`">Update</nuxt-link>
          <br>
        </td>
        <td>
          <button @click.prevent="removeProduct(product.id)">Delete</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
  <button @click.prevent="refresh">Refresh Data</button>
</template>
<script setup>

const config = useRuntimeConfig()
const api = config.public.API_URL;
const { data: products, error, refresh } = await useFetch(`${api}/products`)

const removeProduct = async (id) => {
  await fetch(`${api}/products/${id}`, { method: 'DELETE' })
  refresh()
}
</script>
<style>
table {
  font-family: arial, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

td, th {
  border: 1px solid #dddddd;
  text-align: left;
  padding: 8px;
}

tr:nth-child(even) {
  background-color: #dddddd;
}
</style>

