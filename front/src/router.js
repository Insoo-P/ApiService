import { createRouter, createWebHistory } from 'vue-router';
import MainPage from './views/MainPage.vue';
import ApiService1 from '@/components/apiService/ApiService1.vue';
import ApiService2 from './components/apiService/ApiService2.vue';
import ApiService3 from './components/apiService/ApiService3.vue';
import ApiService4 from './components/apiService/ApiService4.vue';
import ApiService5 from './components/apiService/ApiService5.vue';
import ApiService6 from './components/apiService/ApiService6.vue';
import ApiService7 from './components/apiService/ApiService7.vue';
import ApiService8 from './components/apiService/ApiService8.vue';
import ApiService9 from './components/apiService/ApiService9.vue';
import ApiService10 from './components/apiService/ApiService10.vue';

const routes = [
  { path: '/', component: MainPage },
  { path: '/apiService1', component: ApiService1 },
  { path: '/apiService2', component: ApiService2 },
  { path: '/apiService3', component: ApiService3 },
  { path: '/apiService4', component: ApiService4 },
  { path: '/apiService5', component: ApiService5 },
  { path: '/apiService6', component: ApiService6 },
  { path: '/apiService7', component: ApiService7 },
  { path: '/apiService8', component: ApiService8 },
  { path: '/apiService9', component: ApiService9 },
  { path: '/apiService10', component: ApiService10 },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;