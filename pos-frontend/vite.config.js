import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// import tailwindcss from '@tailwindcss/vite' // <-- Đảm bảo dòng này đã được ghi chú hoặc xóa

export default defineConfig({
  base: '/', // THÊM DÒNG NÀY ĐỂ ĐẢM BẢO ĐƯỜNG DẪN GỐC CỦA ASSETS
  plugins: [
    // tailwindcss(), // <-- Đảm bảo dòng này đã được ghi chú hoặc xóa
    vue(),
    vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
})