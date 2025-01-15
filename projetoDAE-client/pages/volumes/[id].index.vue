<template>
    <div v-if="volume">
        <h2>Details of Volume {{ id }}</h2>
        {{ volume }}
    </div>
    <!-- <div v-if="volume.subjects">
        <h2>Enrolled in:</h2>
        {{ volume.subjects }}
    </div> -->
    <h2>Error messages:</h2>
    {{ messages }}
</template>
<script setup>
const route = useRoute()
const id = route.params.id
const config = useRuntimeConfig()
const api = config.public.API_URL

const token = sessionStorage.getItem('authToken');
console.log(token)
if (!token) {
    throw new Error('Token não encontrado. Faça login novamente.');
}

// Faz a requisição com o token no cabeçalho Authorization
const { data: volume, error: volumeErr } = await useFetch(`${api}/volumes/${id}`, {
    method: 'GET',
    headers: {
        Accept: 'application/json',
        Authorization: `Bearer ${token}` // Adiciona o token ao cabeçalho
    }
});

const messages = ref([])
if (volumeErr.value) messages.value.push(volumeErr.value)
// if (subjectsErr.value) messages.value.push(subjectsErr.value)

</script>