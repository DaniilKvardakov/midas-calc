import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig(({mode}) => {
  const env = loadEnv(mode, process.cwd(), "");
  return {
      plugins: [vue()],
      server: {
        proxy: {
          "/api": {
            target: 'http://176.212.127.212:8888',
            changeOrigin: true,
            rewrite: (path) => path.replace(/^\/api/, ''),
          },
        },
        cors: false,
      },
  }
})
