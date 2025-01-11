<template>
  <div v-if="error" class="error">Error: {{ error.message }}</div>
  <div v-else class="container">
    <nuxt-link to="/products/create" class="create-button">Add a New Product</nuxt-link>
    <h2>Products</h2>
    <table class="product-table">
      <thead>
      <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Brand</th>
        <th>Category</th>
        <th>Quantity</th>
        <th>Price</th>
        <th>Tools</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="product in products" :key="product.id">
        <td>{{ product.id }}</td>
        <td>{{ product.name }}</td>
        <td>{{ product.brand }}</td>
        <td>{{ product.category }}</td>
        <td>{{ product.quantity }}</td>
        <td>{{ product.price }}</td>
        <td class="tools">
          <nuxt-link :to="`/products/${product.id}/edit`" class="update-link" aria-label="Update Product">✏️</nuxt-link>
          <button
              @click.prevent="confirmDelete(product.id)"
              class="delete-button"
              aria-label="Delete Product"
          >
            🗑️
          </button>
        </td>
      </tr>
      </tbody>
    </table>
    <button @click.prevent="refresh" class="refresh-button">Refresh Data</button>
  </div>
</template>

<script setup>
const config = useRuntimeConfig();
const api = config.public.API_URL;
const { data: products, error, refresh } = await useFetch(`${api}/products`);

const confirmDelete = async (id) => {
  if (confirm('Are you sure you want to delete this product?')) {
    await fetch(`${api}/products/${id}`, { method: 'DELETE' });
    refresh();
  }
};
</script>

<style scoped>
.container {
  padding: 20px;
  font-family: Arial, sans-serif;
}

.create-button {
  display: inline-block;
  margin-bottom: 15px;
  padding: 10px 15px;
  background-color: #007bff;
  color: white;
  text-decoration: none;
  border-radius: 5px;
}

.create-button:hover {
  background-color: #0056b3;
}

h2 {
  margin-top: 0;
}

.product-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 15px;
}

.product-table caption {
  font-size: 1.2em;
  margin-bottom: 10px;
  font-weight: bold;
}

.product-table th,
.product-table td {
  border: 1px solid #ddd;
  padding: 10px;
  text-align: left;
}

.product-table tr:nth-child(even) {
  background-color: #f9f9f9;
}

.product-table tr:hover {
  background-color: #f1f1f1;
}

.tools {
  display: flex;
  align-items: center;
  gap: 10px;
  justify-content: flex-start;
}


.update-link {
  text-decoration: none;
  color: #007bff;
  font-size: 1.2em;
}

.update-link:hover {
  color: #0056b3;
}

.delete-button {
  background: none;
  border: none;
  color: red;
  font-size: 1.2em;
  cursor: pointer;
}

.delete-button:hover {
  color: darkred;
}

.refresh-button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.refresh-button:hover {
  background-color: #0056b3;
}

.error {
  color: red;
  font-weight: bold;
}
</style>
