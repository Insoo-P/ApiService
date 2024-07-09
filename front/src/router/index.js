import { createRouter, createWebHistory } from 'vue-router';
import MainPage from '../views/MainPage.vue';
import ApiService1 from '@/components/apiService/ApiService1.vue';
import ApiService2 from '../components/apiService/ApiService2.vue';
import ApiService3 from '../components/apiService/ApiService3.vue';
import ApiService4 from '../components/apiService/ApiService4.vue';

const routes = [
  { path: '/', component: MainPage },
  { path: '/apiService1', component: ApiService1 },
  { path: '/apiService2', component: ApiService2 },
  { path: '/apiService3', component: ApiService3 },
  { path: '/apiService4', component: ApiService4 },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;