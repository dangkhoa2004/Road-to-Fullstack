// src/main.js
import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import store from './store'; // Nếu bạn dùng Vuex
import axios from 'axios'; // Import axios

// Cấu hình Axios interceptor để thêm Authorization header
axios.interceptors.request.use(
    config => {
        const token = localStorage.getItem('jwtToken'); // Lấy token từ localStorage
        if (token) {
            config.headers.Authorization = `Bearer ${token}`; // Thêm token vào header
        }
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);

// Xử lý lỗi 401 (Unauthorized) cho tất cả các request (tùy chọn)
axios.interceptors.response.use(
    response => response,
    error => {
        if (error.response && error.response.status === 401 && error.response.config.url !== 'http://localhost:8080/api/auth/login') {
            // Nếu là lỗi 401 (ngoại trừ request login)
            console.log("Token invalid or expired, redirecting to login.");
            localStorage.removeItem('jwtToken'); // Xóa token cũ
            localStorage.removeItem('user'); // Xóa thông tin user cũ
            router.push('/login'); // Chuyển hướng về trang đăng nhập
        }
        return Promise.reject(error);
    }
);

const app = createApp(App);
app.use(store); // Nếu bạn dùng Vuex
app.use(router);
app.mount('#app');