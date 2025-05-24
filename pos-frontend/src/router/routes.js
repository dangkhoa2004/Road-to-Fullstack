import { createRouter, createWebHistory } from 'vue-router';
import LoginForm from '../views/Auth/LoginForm.vue';
import HomeView from '../views/Home/HomeView.vue';
import Dashboard from '../views/Dashboard/DashboardView.vue';
import { authGuard } from './guard';
import productRoutes from './modules/product';

const routes = [
    {
        path: '/',
        name: 'Home',
        component: HomeView,
    },
    {
        path: '/login',
        name: 'Login',
        component: LoginForm,
    },
    {
        path: '/dashboard',
        name: 'Dashboard',
        component: Dashboard,
        meta: { requiresAuth: true }
    },
    ...productRoutes,
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

router.beforeEach(authGuard);
export default router;
