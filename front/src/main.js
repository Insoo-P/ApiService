import { createApp } from 'vue'
import router from './router'
import App from './App.vue'
import MainPage from './views/MainPage.vue'

createApp(App)
    .component('main-page', MainPage)
    .use(router)
    .mount('#app')