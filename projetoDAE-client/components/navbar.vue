<template>
  <fwb-navbar>
    <template #logo>
      <fwb-navbar-logo class="logo" alt="logo" image-url="/logotipo.jpg" link="/">
      </fwb-navbar-logo>
    </template>
    <template #default="{ isShowMenu }">
      <fwb-navbar-collapse :is-show-menu="isShowMenu">
        <fwb-navbar-link
            link="/"
            :class="{ active: activePage === 'home' }"
        >
          Home
        </fwb-navbar-link>
        <fwb-navbar-link
            v-if="userRole !== 'CLIENTE'"
            link="/products"
            :class="{ active: activePage === 'products' }"
        >
          Products
        </fwb-navbar-link>

        <fwb-navbar-link
            v-if="userRole !== 'CLIENTE'"
            link="/volumes/show"
            :class="{ active: activePage === 'volumes' }"
        >
          Volumes
        </fwb-navbar-link>
        <fwb-navbar-link
            v-if="userRole === 'CLIENTE'"
            :link="`/volumes/${username}`"
            :class="{ active: activePage === 'volumes' }"
        >
          Volumes
        </fwb-navbar-link>

        <fwb-navbar-link
            v-if="userRole === 'GESTOR'"
            link="/encomendas/show"
            :class="{ active: activePage === 'orders' }"
        >
          Orders
        </fwb-navbar-link>
        <fwb-navbar-link
            v-if="userRole === 'CLIENTE'"
            :link="`/encomendas/${username}`"
            :class="{ active: activePage === 'orders' }"
        >
          Orders
        </fwb-navbar-link>

        <fwb-navbar-link
            v-if="userRole !== 'CLIENTE'"
            link="/sensors"
            :class="{ active: activePage === 'sensors' }"
        >
          Sensors
        </fwb-navbar-link>
        <fwb-navbar-link
            v-if="userRole === 'CLIENTE'"
            :link="`/sensors/${username}.mysensors`"
            :class="{ active: activePage === 'sensors' }"
        >
          Sensors
        </fwb-navbar-link>
      </fwb-navbar-collapse>
    </template>
    <template #right-side>
      <fwb-button @click="logout()">
        Logout
      </fwb-button>
    </template>
  </fwb-navbar>
</template>

<script setup>
import {
  FwbButton,
  FwbNavbar,
  FwbNavbarCollapse,
  FwbNavbarLink,
  FwbNavbarLogo,
} from 'flowbite-vue';


const userRole = ref(null);
const username = ref(null);

defineProps({
  activePage: {
    type: String,
    default: 'home',
  },
});

const logout = () => {
  sessionStorage.removeItem('authToken');
  sessionStorage.removeItem('username');
  sessionStorage.removeItem('role');
  sessionStorage.clear();
  window.location.href = '/login';
};
onMounted(() => {
  if (typeof window !== 'undefined') {
    userRole.value = sessionStorage.getItem('role');
    username.value = sessionStorage.getItem('username');
  }
});

</script>

<style>
.logo {
  width: 100px;
}

.h-6 {
  height: 5.5rem;
}

.active {
  color: #0056b3;
  font-weight: bold;

}
</style>
