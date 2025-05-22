<script setup>
import { ref } from 'vue'

defineProps({
  msg: String,
})

const count = ref(0)
</script>

<template>
  <h1 class="text-5xl font-bold leading-tight text-gray-900 mb-6">{{ msg }}</h1>

  <div class="p-6 bg-white rounded-lg shadow-md mb-8">
    <button
      type="button"
      @click="count++"
      class="px-6 py-3 bg-blue-500 text-white font-semibold rounded-lg shadow-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:ring-opacity-75 transition duration-200 ease-in-out"
    >
      count is {{ count }}
    </button>
    <p class="mt-4 text-gray-700">
      Edit
      <code class="bg-gray-100 text-purple-700 px-2 py-1 rounded">components/HomeView.vue</code> to test HMR
    </p>
  </div>

  <p class="mb-4 text-gray-700">
    Check out
    <a href="https://vuejs.org/guide/quick-start.html#local" target="_blank" class="text-blue-600 hover:text-blue-800 underline">create-vue</a>, the official Vue + Vite
    starter
  </p>
  <p class="mb-8 text-gray-700">
    Learn more about IDE Support for Vue in the
    <a href="https://vuejs.org/guide/scaling-up/tooling.html#ide-support" target="_blank" class="text-blue-600 hover:text-blue-800 underline">Vue Docs Scaling up Guide</a>.
  </p>
  <p class="text-gray-500 text-sm">Click on the Vite and Vue logos to learn more</p>
</template>