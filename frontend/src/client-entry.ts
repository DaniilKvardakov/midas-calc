import {createSSRApp, h} from "vue";
import {createPinia} from "pinia";
import App from "./App.vue";

async function render() {
    const app = createSSRApp({
        render() {
            return h(App)
        }
    })
    const pinia = createPinia();
    app.use(pinia)
    app.mount("#root");
}

render();
