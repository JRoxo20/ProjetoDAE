<template>
    <div v-if="error">Error: {{ error.message }}</div>
    <div v-else class="container">
        <h1>Produtos do Volume {{ id }}</h1>
        <table class="volumes-table">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Nome</th>
                    <th>Marca</th>
                    <th>Categoria</th>
                    <th>Preço</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="produto in produtos" :key="produto.id">
                    <td>{{ produto.id_produto }}</td>
                    <td>{{ produto.nome_produto }}</td>
                    <td>{{ produto.brand }}</td>
                    <td>{{ produto.category }}</td>
                    <td>{{ produto.price }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>
<script setup>
import { ref, onMounted } from 'vue';
import Navbar from "~/components/navbar.vue";
import 'flowbite/dist/flowbite.css';

const route = useRoute()
const id = route.params.id
const config = useRuntimeConfig();
const api = config.public.API_URL;

const produtos = ref([]);
const error = ref(null);

async function fetchProdutos() {
    try {
        // Recupera o token do sessionStorage
        const token = sessionStorage.getItem('authToken');
        console.log(token)
        if (!token) {
            window.location.href = '/login';
        }

        // Faz a requisição com o token no cabeçalho Authorization
        const response = await $fetch(`${api}/volumes/${id}/produtos`, {
            method: 'GET',
            headers: {
                Accept: 'application/json',
                Authorization: `Bearer ${token}` // Adiciona o token ao cabeçalho
            }
        });

        produtos.value = response;
    } catch (err) {
        console.error('Erro ao buscar produtos do volume:', err);
        error.value = err;
    }
}

async function refresh() {
    error.value = null;
    await fetchProdutos();
}

const userRole = ref(null);
onMounted(async () => {
    await fetchProdutos();
    if(typeof window !== 'undefined') {
        userRole.value = sessionStorage.getItem('userRole');
    }
});
</script>
<style>
h1 {
    font-size: 30px;
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

.actions {
    margin-right: 10px;
}

.buttons {
    display: flex;
    float: right;
    gap: 10px;
}

h2 {
    margin-top: 0;
}

.volumes-table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 15px;
}

.volumes-table caption {
    font-size: 1.2em;
    margin-bottom: 10px;
    font-weight: bold;
}

.volumes-table th,
.volumes-table td {
    border: 1px solid #ddd;
    padding: 10px;
    text-align: left;
}

.volumes-table tr:nth-child(even) {
    background-color: #f9f9f9;
}

.volumes-table tr:hover {
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