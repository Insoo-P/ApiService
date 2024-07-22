import { createApp } from 'vue'
import router from './router.js'
import App from './App.vue'
import MainPage from './views/MainPage.vue'
import axiosPlugin  from './axios.js'

const app = createApp(App);

app.component('main-page', MainPage)
app.use(router)
app.use(axiosPlugin)
app.mount('#app')

// createApp(App)
//     .component('main-page', MainPage)
//     .use(router)
//     .mount('#app')