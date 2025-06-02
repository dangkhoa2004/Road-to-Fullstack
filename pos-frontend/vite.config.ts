import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'
import vueDevTools from 'vite-plugin-vue-devtools'
import { VitePWA } from 'vite-plugin-pwa'

export default defineConfig({
  plugins: [
    vue(),
    vueJsx(),
    vueDevTools(),
    VitePWA({
      registerType: 'autoUpdate',
      manifest: {
        "name": "Tên App",
        "short_name": "Tên Ngắn",
        "start_url": ".",
        "display": "standalone",
        "background_color": "#ffffff",
        "theme_color": "#6200ee",
        "icons": [
          {
            "src": "/images/logo/192.png",
            "sizes": "192x192",
            "type": "image/png"
          },
          {
            "src": "/images/logo/logo.png",
            "sizes": "512x512",
            "type": "image/png"
          }
        ],
        "screenshots": [
          {
            "src": "/images/logo/1080x1920.png",
            "sizes": "1080x1920",
            "type": "image/png",
            "form_factor": "narrow"
          },
          {
            "src": "/images/logo/1920x1080.png",
            "sizes": "1920x1080",
            "type": "image/png",
            "form_factor": "wide"
          }
        ]
      }
    })
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
  build: {
    rollupOptions: {
      output: {
        manualChunks(id: string) {
          if (id.includes('node_modules')) {
            if (id.includes('vue')) {
              return 'vendor-vue'
            }
            if (id.includes('chart.js')) {
              return 'vendor-chart'
            }
            return 'vendor'
          }
        },
      },
      // chunkSizeWarningLimit: 600,
    },
  },
})
