// @ts-ignore
import express from "express"
import httpDevServer from "vavite/http-dev-server"
import viteDevServer from "vavite/vite-dev-server"
// @ts-ignore
import { createSSRApp, h, defineComponent } from "vue";
import { renderToString } from "vue/server-renderer"
import App from "./App.vue";
import {createPinia} from "pinia";


const app = express();

if(import.meta.env.PROD) {
    app.use(express.static('dist/client'))
}

app.get('/', (req, res) => {
    render(req, res)
})



async function render(req, res) {
    let clientEntryPath: string;

    if(viteDevServer) {
        clientEntryPath = "src/client-entry.ts";
    } else {
        // @ts-ignore
        const manifest = (await import("./dist/client/.vite/manifest.json"))
            .default;
        clientEntryPath = manifest["client-entry.ts"].file;
    }

    const Content = defineComponent({
        render() {
            return h(App)
        }
    })
    const ssrApp = createSSRApp(Content);
    const pinia = createPinia();
    ssrApp.use(pinia)
    const content = await renderToString(ssrApp);
    console.log(content, 'content')
    let html = `
	<!doctype html>
<html lang="ru">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Midas Calculator</title>
    <!--preload-links-->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link rel="icon" type="image/x-icon" href="src/assets/favicon.png">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">
  </head>
  <body>
    <div id="root">
        <div class="" style="opacity: 0; user-select: none; position: absolute; z-index: -1; display: none">
            ${content}
        </div>
    </div>
    <script type="module" src="${clientEntryPath}"></script>
  </body>
</html>`;

    if (viteDevServer) {
        // This will inject the Vite client and React fast refresh in development
        html = await viteDevServer.transformIndexHtml(req.url, html);
    }

    res.send(html);
}


if (viteDevServer) {
    httpDevServer!.on("request", app);
} else {
    console.log("Starting production server");
    app.listen(3000);
}
