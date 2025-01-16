<template>
    <h2>Chage Volume State</h2>
    <form @submit.prevent="create">
        <div>State:
            <input v-model.trim="stateForm.estado" type="text">
            <span v-if="estadoError" class="error">
                ERROR: {{ estadoError }}</span>
        </div>

        <button type="reset">RESET</button>
        <button type="submit" :disabled="isFormInvalid">UPDATE</button>
    </form>
    <hr>
    <pre>{{ messages }}</pre>
</template>

<script setup>
const route = useRoute()
const id = route.params.id

const token = sessionStorage.getItem('authToken');
console.log(token)
if (!token) {
  window.location.href = '/login';
}

const stateForm = reactive({ 
  estado: "entregue"
}) 

const messages = ref([])

const config = useRuntimeConfig()
const api = config.public.API_URL
//const { data: courses } = await useFetch(`${api}/course`)

// Field validation rules... 
const estadoError = computed(() => {
    if (stateForm.estado === null) return null 
    if (!stateForm.estado)
        return 'state is required'
    return null
})

const isFormInvalid = computed(() => {
    return estadoError.value
})

async function create() {
    try {
        await $fetch(`${api}/volumes/${id}/entrega`, {
            method: 'PATCH',
            headers: { 
                Accept: 'application/json',
                Authorization: `Bearer ${token}` // Adiciona o token ao cabeçalho
             },
            body: stateForm,
            onResponse({ request, response, options }) {
                messages.value.push({
                    method: options.method,
                    request: request,
                    status: response.status,
                    statusText: response.statusText,
                    payload: response._data
                })
            }
        })
    } catch (e) {
        console.log(e)
    }
} 
</script>

<style scoped>
.error {
    color: red
}
</style>