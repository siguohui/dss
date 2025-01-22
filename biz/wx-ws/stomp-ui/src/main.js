import './assets/main.css'
import ElementPlus from 'element-plus';
import 'element-plus/theme-chalk/index.css'

import { createApp } from 'vue'
import App from './App.vue'
import uploader from 'vue-simple-uploader';
import 'vue-simple-uploader/dist/style.css';
createApp(App).use(uploader).use(ElementPlus).mount('#app')
