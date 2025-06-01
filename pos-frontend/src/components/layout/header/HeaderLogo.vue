<template>
  <router-link to="/" class="lg:hidden">
    <img class="dark:hidden w-8 h-8" src="/images/logo/logo.png" alt="Logo" />
    <img class="hidden dark:block w-8 h-8" src="/images/logo/logo.png" alt="Logo" />
  </router-link>
</template>

<script setup>
import { RouterLink } from 'vue-router'
</script>
