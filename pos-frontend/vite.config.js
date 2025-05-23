import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'
// import tailwindcss from '@tailwindcss/vite' // <-- REMOVE THIS LINE IF IT'S NOT USED ELSEWHERE

export default defineConfig({
  plugins: [
    // tailwindcss(), // <-- REMOVE THIS LINE
    vue(),
    vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
})