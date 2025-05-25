import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import './assets/styles/global.css';
import './api/index';
import { createPinia } from 'pinia';
import AnimateOnScroll from './utils/v-animate-on-scroll';

const app = createApp(App);
const pinia = createPinia();

app.use(pinia);
app.use(store);
app.use(router);
app.directive('animate-on-scroll', AnimateOnScroll);

app.mount('#app');