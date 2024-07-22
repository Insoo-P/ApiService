import axios from 'axios';

const axiosInstance = axios.create({
    baseURL: process.env.VUE_APP_BASE_URL,
});

export default {
    install(app) {
        app.config.globalProperties.$axios = axiosInstance;
        app.provide('axios', axiosInstance);
    }
}