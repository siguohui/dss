import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from "@/router";
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
// import 'element-plus/theme-chalk/src/message.scss'
// import 'element-plus/theme-chalk/src/message-box.scss'

// import VuePhotoPreview from 'vue-photo-preview'
// import 'vue-photo-preview/dist/skin.css'

createApp(App)
    .use(router)
    .use(ElementPlus)
    // .use(VuePhotoPreview)
    .mount('#app')
