<template style="background-color:blue;">
  <fwb-navbar>
    <template #logo>
      <fwb-navbar-logo class="logo" alt="logo" image-url="/logotipo.jpg" link="#">

      </fwb-navbar-logo>
    </template>
    <template #default="{isShowMenu}">
      <fwb-navbar-collapse :is-show-menu="isShowMenu">
        <fwb-navbar-link link="/">
          Home
        </fwb-navbar-link>
        <fwb-navbar-link link="/products">
          Products
        </fwb-navbar-link>
        <fwb-navbar-link link="#">
         Orders
        </fwb-navbar-link>
        <fwb-navbar-link link="#">
          Sensors
        </fwb-navbar-link>
      </fwb-navbar-collapse>
    </template>
    <template #right-side>
      <fwb-button>
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
} from 'flowbite-vue'
</script>

<style>
.logo {
  width: 100px;
}
.h-6{
  height:5.5rem;
}
</style>