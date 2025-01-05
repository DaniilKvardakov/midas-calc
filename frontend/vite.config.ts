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
            target: 'https://ru.dotabuff.com/players/425890796',
            changeOrigin: true,
            rewrite: (path) => path.replace(/^\/api/, ''),
          },
        },
        cors: false,
      },
  }
})
