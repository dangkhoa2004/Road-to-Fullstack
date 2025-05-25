import {createRouter, createWebHistory} from 'vue-router';
import HomeView from '../views/Home/HomeView.vue';
import Dashboard from '../views/Dashboard/DashboardView.vue';
import {authGuard} from './guard';
import productRoutes from './modules/product';
import userRoutes from './modules/user';

const routes = [
    {
        path: '/',
        name: 'Home',
        component: HomeView,
    },
    {
        path: '/dashboard',
        name: 'Dashboard',
        component: Dashboard,
        meta: {requiresAuth: true}
    },
    ...productRoutes,
    ...userRoutes,
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

router.beforeEach(authGuard);
export default router;
