<template>
  <Navbar activePage="volumes" />
  <div class="container">
    <h2>Change Volume State</h2>
    <form @submit.prevent="updateState" class="form">
      <div class="form-group">
        <label for="state">State:</label>
        <input
            id="state"
            v-model.trim="stateForm.estado"
            type="text"
            class="form-control"
            :class="{'is-invalid': estadoError}"
        />
        <div v-if="estadoError" class="error-feedback">
          ERROR: {{ estadoError }}
        </div>
      </div>

      <div class="button-group">
        <button type="reset" class="btn btn-secondary">Reset</button>
        <button type="submit" :disabled="isFormInvalid" class="btn btn-primary">Update</button>
      </div>
    </form>
    <hr />
    <pre class="messages">{{ messages }}</pre>
  </div>
</template>

<script setup>
import Navbar from "~/components/navbar.vue";
import { onMounted, ref, reactive, computed } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
const id = route.params.id;
const stateForm = reactive({ estado: "Entregue" });
const messages = ref([]);
const userRole = ref(null);

const config = useRuntimeConfig();
const api = config.public.API_URL;

// Validation rules
const estadoError = computed(() => {
  if (!stateForm.estado) return "State is required";
  return null;
});

const isFormInvalid = computed(() => !!estadoError.value);

async function updateState() {
  try {
    const response = await $fetch(`${api}/volumes/${id}/entrega`, {
      method: "PATCH",
      headers: {
        Accept: "application/json",
        Authorization: `Bearer ${sessionStorage.getItem('token')}`,
      },
      body: stateForm,
      onResponse({ request, response, options }) {
        messages.value.push({
          method: options.method,
          request,
          status: response.status,
          statusText: response.statusText,
          payload: response._data,
        });
      },
    });
  } catch (error) {
    console.error("Error updating state:", error);
  }
}

onMounted(() => {
  if (typeof window !== "undefined") {
    userRole.value = sessionStorage.getItem("userRole");
  }
});
</script>

<style scoped>
.container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  font-family: Arial, sans-serif;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  margin-bottom: 20px;
}

.form {
  display: flex;
  flex-direction: column;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

.form-control {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 14px;
}

.form-control.is-invalid {
  border-color: red;
}

.error-feedback {
  color: red;
  font-size: 12px;
  margin-top: 5px;
}

.button-group {
  display: flex;
  justify-content: space-between;
}

.btn {
  padding: 10px 15px;
  font-size: 14px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-primary {
  background-color: #007bff;
  color: #fff;
}

.btn-secondary {
  background-color: #6c757d;
  color: #fff;
}

.btn:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.messages {
  background: #e9ecef;
  padding: 10px;
  border-radius: 4px;
  overflow-x: auto;
}
</style>
