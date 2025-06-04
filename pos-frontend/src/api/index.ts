import axios from 'axios';
import type { AxiosError, AxiosResponse, InternalAxiosRequestConfig } from 'axios';
import router from '../router';

const hostname = window.location.hostname;
const apiPort = 8080;

axios.defaults.baseURL = `http://${hostname}:${apiPort}/api`;

// Interceptor request
axios.interceptors.request.use(
  (config: InternalAxiosRequestConfig): InternalAxiosRequestConfig => {
    const token = localStorage.getItem('jwtToken');
    if (token && config.headers) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error: AxiosError): Promise<never> => {
    return Promise.reject(error);
  }
);

// Interceptor response
axios.interceptors.response.use(
  (response: AxiosResponse): AxiosResponse => response,
  (error: AxiosError): Promise<never> => {
    if (
      error.response &&
      error.response.status === 401 &&
      error.config?.url !== '/auth/dang-nhap'
    ) {
      localStorage.removeItem('jwtToken');
      localStorage.removeItem('user');
      router.push('/dang-nhap');
    }
    return Promise.reject(error);
  }
);

export default axios;
