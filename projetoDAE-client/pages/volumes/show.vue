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
                <tr v-for="volume in volumes">
                    <td>{{ volume.id }}</td>
                    <td>{{ volume.estado }}</td>
                    <td>{{ volume.tipo_embalagem }}</td>
                    <td><nuxt-link :to="`/volumes/${volume.id}.index`">Details</nuxt-link><nuxt-link :to="`/volumes/${volume.id}.change_state`">Change State</nuxt-link></td>
                </tr>
            </tbody>
        </table>
    </div>

    <button @click.prevent="refresh">Refresh Data</button>
</template>
<script setup>
const config = useRuntimeConfig()
const api = config.public.API_URL
const { data: volumes, error, refresh } = await useFetch(`${api}/volumes`)
</script>