<template>
  <div class="fixed inset-0 flex items-center justify-center overflow-y-auto z-99999">
    <div v-if="fullScreenBackdrop"
         aria-hidden="true"
         class="fixed inset-0 h-full w-full bg-gray-400/50 backdrop-blur-[32px]" @click="$emit('close')"></div>
    <slot name="body"></slot>
  </div>
</template>

<script lang="ts" setup>
interface ModalProps {
  fullScreenBackdrop?: boolean
}

defineProps<ModalProps>()
defineEmits(['close'])
</script>
