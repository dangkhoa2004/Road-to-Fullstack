import LoginForm from '../../views/Auth/LoginForm.vue';
import RegisterForm from '../../views/Auth/RegisterForm.vue';

const userRoutes = [
    {
        path: '/login',
        name: 'LoginForm',
        component: LoginForm,
        meta: { requiresAuth: false },
    },
    {
        path: '/register',
        name: 'RegisterForm',
        component: RegisterForm,
        meta: { requiresAuth: false },
    },
];

export default userRoutes;
