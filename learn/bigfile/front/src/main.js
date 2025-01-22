import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import { setupRouter } from '@/router'
// import uploader from 'vue-simple-uploader';
// import 'vue-simple-uploader/dist/style.css';
import uploader from '../src'
// createApp(App).use(router).use(uploader).mount('#app');
// const app = createApp(App)
// await setupI18n(app)

const setupApp = async () => {
    const app = createApp(App);
    setupRouter(app);
    app.use(uploader)
    app.mount('#app');
};

setupApp();
