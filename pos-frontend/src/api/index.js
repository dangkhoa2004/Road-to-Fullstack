import axios from 'axios';
import router from '../router';

axios.defaults.baseURL = 'http://192.168.1.2:8080/api';

axios.interceptors.request.use(
    config => {
        const token = localStorage.getItem('jwtToken');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);

axios.interceptors.response.use(
    response => response,
    error => {
        if (error.response && error.response.status === 401 && error.config.url !== '/auth/login') {
            localStorage.removeItem('jwtToken');
            localStorage.removeItem('user');
            router.push('/login');
        }
        return Promise.reject(error);
    }
);

export default axios;