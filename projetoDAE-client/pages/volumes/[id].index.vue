<template>
    <div v-if="volume">
        <h2>Details of Volume {{ id }}</h2>
        {{ volume }}
    </div>
    <div v-if="volume.subjects">
        <h2>Enrolled in:</h2>
        {{ volume.subjects }}
    </div>
    <h2>Error messages:</h2>
    {{ messages }}
</template>
<script setup>
    const route = useRoute()
    const id = route.params.id
    const config = useRuntimeConfig()
    const api = config.public.API_URL
    const { data: volume, error: volumeErr } = await
    useFetch(`${api}/volumes/${id}`)
    // const { data: subjects, error: subjectsErr } = await
    // useFetch(`${api}/volume/${id}/subjects`)
    const messages = ref([])
    if (volumeErr.value) messages.value.push(volumeErr.value)
    // if (subjectsErr.value) messages.value.push(subjectsErr.value)
</script>