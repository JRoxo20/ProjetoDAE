<template>
  <Navbar  activePage="products" />
  <div v-if="error" class="error">Error: {{ error.message }}</div>
  <div v-else class="container">
    <h1>Products</h1>
    <div class="buttons">
      <nuxt-link to="/products/create" class="create-button">➕ Add a New Product</nuxt-link>
      <button @click.prevent="refresh" class="create-button">🔄 Refresh Data</button>
      <button type="button"
              class="create-button"
              @click="importCSV">
        ⬆️
        <span>Import CSV</span>
      </button>
      <button type="button"
              class="create-button"
              @click="exportCSV">
        ⬇️
        <span>Export CSV</span>
      </button>
    </div>
    <table class="product-table">
      <thead>
      <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Brand</th>
        <th>Category</th>
        <th>Price</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="product in products" :key="product.id">
        <td>{{ product.id }}</td>
        <td>{{ product.name }}</td>
        <td>{{ product.brand }}</td>
        <td>{{ product.category }}</td>
        <td>{{ product.price }} €</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>

import 'flowbite/dist/flowbite.css';
import {onMounted, ref} from "vue";
import Navbar from "~/components/navbar.vue";


const config = useRuntimeConfig();
const api = config.public.API_URL;
const error = ref(null);

const products = ref([]);

async function fetchAllProducts() {
  try {
    const token = sessionStorage.getItem('authToken');
    if (!token) {
      window.location.href = '/login';
    }
    const response = await $fetch(`${api}/products`, {
      method: 'GET',
      headers: {
        Accept: 'application/json',
        Authorization: `Bearer ${token}`,
      },
    });
    products.value = response; // Assign the response to the products variable
  } catch (err) {
    console.error('Error fetching products:', err);
    error.value = err;
  }
}

const confirmDelete = async (id) => {
  if (confirm('Are you sure you want to delete this product?')) {
    await fetch(`${api}/products/${id}`, { method: 'DELETE' });
    refresh();
  }
};

const exportCSV = async () => {
  const response = await fetch(`${api}/productCSV/productsASCSV`, {
    headers: {
      authorization: "Bearer " + token.value,
    },
  })
  if (!response.ok) {
    alert("error exporting")
    return
  }
  const string = await response.text()
  const link = document.createElement("a");
  const file = new Blob([string], {type: 'text/csv'});
  link.href = URL.createObjectURL(file);
  link.download = "products.csv";
  link.click();
  URL.revokeObjectURL(link.href);
}
const importCSV = async () => {
  const fileInput = document.createElement("input");
  fileInput.type = "file";
  fileInput.accept = ".csv";
  fileInput.click();

  fileInput.addEventListener("change", async (event) => {
    const files = event.target.files;
    for (const file of files) {
      const reader = new FileReader();
      reader.addEventListener("load", async (event) => {
        let csvData = event.target.result.trim();

        csvData = csvData.replace(/^\s+|\s+$/g, "");

        console.log("CSV enviado:", csvData);

        const response = await fetch(`${api}/productCSV/productsFROMCSV`, {
          method: "POST",
          headers: {
            authorization: "Bearer " + token.value,
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ csv: csvData }),
        });

        if (response.ok) {
          alert("Products imported successfully");
        } else {
          const errorText = await response.text();
          console.error("Erro do servidor:", errorText);
          alert("Error importing products: " + errorText);
        }
      });
      reader.readAsText(file);
    }
  });
};



async function refresh() {
  error.value = null;
  await fetchAllProducts();
}
const token = ref(null);
onMounted(async () => {
  await fetchAllProducts();
  if(typeof window !== 'undefined') {
    token.value = sessionStorage.getItem('authToken');
  }
});
</script>

<style >
h1{
  font-size:30px;
  margin-left: 4%;
}

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


.buttons{
  display: flex;
  float:right;
  gap:10px;
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
  justify-content: center;
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

.error {
  color: red;
  font-weight: bold;
}
</style>
