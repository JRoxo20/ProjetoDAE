<template>
    <div v-if="error">Error: {{ error.message }}</div>
    <div v-else>
        <nuxt-link to="/volumes/create">Create a New Volume</nuxt-link>
        <h2>Volumes</h2>
        <table>
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Estado</th>
                    <th>Tipo de Embalagem</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="volume in volumes" :key="volume.id">
                    <td>{{ volume.id }}</td>
                    <td>{{ volume.estado }}</td>
                    <td>{{ volume.tipo_embalagem }}</td>
                    <td>
                        <nuxt-link :to="`/volumes/${volume.id}.index`">Details</nuxt-link>
                        <nuxt-link :to="`/volumes/${volume.id}.change_state`">Change State</nuxt-link>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>

    <button @click.prevent="refresh">Refresh Data</button>
</template>

<script setup>
import { ref, onMounted } from 'vue';

const config = useRuntimeConfig();
const api = config.public.API_URL;

// Estados para volumes e erros
const volumes = ref([]);
const error = ref(null);

// Função para buscar volumes da API
async function fetchVolumes() {
    try {
        // Recupera o token do sessionStorage
        const token = sessionStorage.getItem('authToken');
        console.log(token)
        if (!token) {
            throw new Error('Token não encontrado. Faça login novamente.');
        }

        // Faz a requisição com o token no cabeçalho Authorization
        const response = await $fetch(`${api}/volumes`, {
            method: 'GET',
            headers: {
                Accept: 'application/json',
                Authorization: `Bearer ${token}` // Adiciona o token ao cabeçalho
            }
        });

        // Atualiza os volumes com os dados recebidos
        volumes.value = response;
    } catch (err) {
        console.error('Erro ao buscar volumes:', err);
        error.value = err;
    }
}

// Atualiza os volumes ao recarregar
async function refresh() {
    error.value = null;
    await fetchVolumes();
}

// Busca volumes ao montar o componente
onMounted(async () => {
    await fetchVolumes();
});
</script>