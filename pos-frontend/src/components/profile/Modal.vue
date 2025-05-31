<template>
  <div class="fixed inset-0 flex items-center justify-center overflow-y-auto modal z-99999">
    <div class="fixed inset-0 h-full w-full bg-gray-400/50 backdrop-blur-[32px]" aria-hidden="true"></div>
    <div class="relative z-10 bg-white dark:bg-gray-800 rounded-xl shadow-lg p-4 max-w-[700px] w-full mx-4">
      <div v-if="headerTitle || headerDescription" class="px-2 pr-14">
        <h4 v-if="headerTitle" class="mb-2 text-2xl font-semibold text-gray-800 dark:text-white/90">
          {{ headerTitle }}
        </h4>
        <p v-if="headerDescription" class="mb-6 text-sm text-gray-500 dark:text-gray-400 lg:mb-7">
          {{ headerDescription }}
        </p>
      </div>
      <button @click="$emit('close')"
        class="transition-color absolute right-5 top-5 z-999 flex h-11 w-11 items-center justify-center rounded-full bg-gray-100 text-gray-400 hover:bg-gray-200 hover:text-gray-600 dark:bg-gray-700 dark:bg-white/[0.05] dark:text-gray-400 dark:hover:bg-white/[0.07] dark:hover:text-gray-300">
        <ClosebuttonSvg />
      </button>
      <slot name="body"></slot>
    </div>
  </div>
</template>

<script setup>
import ClosebuttonSvg from '@/assets/svg-icon/closebutton-svg.vue'
const props = defineProps({
  headerTitle: String,
  headerDescription: String
})
</script>
