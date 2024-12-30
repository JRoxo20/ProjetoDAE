<template>
  <h2>Create User</h2>
  <form @submit.prevent="create">
    <div>Username:
      <input v-model.trim="userForm.username" type="text">
      <span v-if="usernameError" class="error">
 ERROR: {{ usernameError }}</span></div>
    <div>Password:
      <input v-model.trim="studentForm.password" type="password">
      <span v-if="passwordError" class="error">
 ERROR: {{ passwordError }}</span></div>
    <div>Name:
      <input v-model.trim="studentForm.name" type="text">
      <span v-if="nameError" class="error">
 ERROR: {{ nameError }}</span></div>
    <div>E-mail:
      <input v-model.trim="studentForm.email" type="text">
      <span v-if="emailError" class="error">
 ERROR: {{ emailError }}</span></div>
    <button type="reset">RESET</button>
    <button type="submit" :disabled="isFormInvalid">CREATE</button>
  </form>
  <hr>
  <pre>{{ messages }}</pre>
</template>
<script setup>
const userForm = reactive({
  username: null,
  password: null,
  email: null,
  name: null
})
const messages = ref([])
const config = useRuntimeConfig()
const api = config.public.API_URL
const { data: courses } = await useFetch(`${api}/course`)
// Field validation rules...
const usernameError = computed(() => {
  if (! userForm.username )
    return 'username is required'
  if ( userForm.username.length < 3 )
    return 'username must be at least 3 characters'
  if ( userForm.username.length > 15 )
    return 'username must be at most 15 characters'
  return null
})
const passwordError = computed(() => {
  if ( ! userForm.password )
    return 'password is required'
  if ( userForm.password.length < 6 )
    return 'password must be at least 8 characters'

  return null
})
const nameError = computed(() => {
  if ( ! userForm.name )
    return 'name is required'

  if ( userForm.name.length < 3 )
    return 'name must be at least 3 characters'

  return null
})
const emailError = computed(() => {
  if ( ! userForm.email )
    return 'email is required'

  if ( ! userForm.email.includes('@') )
  return null
})
const isFormInvalid = computed(() => {
  return usernameError.value
      || passwordError.value
      || nameError.value
      || emailError.value
})
async function create() {
  try {
    await $fetch(`${api}/user`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: studentForm,
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

}