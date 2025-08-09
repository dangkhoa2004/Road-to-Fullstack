<template>
  <slot></slot>
</template>

<script lang="ts" setup>
import {useSidebarProvider} from '@/composables/useSidebar'

useSidebarProvider()
</script>
