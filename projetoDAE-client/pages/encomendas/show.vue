<template>
  <div v-if="error">Error: {{ error.message }}</div>
  <div v-else>
    <!--<nuxt-link to="/encomendas/create">Create a New Encomenda</nuxt-link>-->
    <h2>Encomendas</h2>
    <table>
      <thead>
      <tr>
        <th>Id</th>
        <th>Id Cliente</th>
        <th>Actions</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="encomenda in encomendas">
        <td>{{ encomenda.id }}</td>
        <td>{{ encomenda.idCliente }}</td>
        <!--<td><nuxt-link :to="`/encomendas/${encomenda.id}.index`">Details</nuxt-link><nuxt-link :to="`/encomendas/${encomenda.id}.change_state`">Change State</nuxt-link></td>-->
      </tr>
      </tbody>
    </table>
  </div>

  <button @click.prevent="refresh">Refresh Data</button>
</template>
<script setup>
const config = useRuntimeConfig()
const api = config.public.API_URL
const { data: encomenda, error, refresh } = await useFetch(`${api}/encomendas`)
</script>