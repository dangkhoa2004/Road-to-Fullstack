import { createRouter, createWebHistory } from 'vue-router'; // Import các hàm cần thiết từ vue-router
import LoginForm from '../views/LoginForm.vue'; // Import component LoginForm

const routes = [
    {
        path: '/login', // Route cho trang đăng nhập
        name: 'Login',
        component: LoginForm,
    },
    {
        path: '/dashboard', // Route cho trang dashboard
        name: 'Dashboard',
        // Sử dụng lazy-loading cho DashboardView để tối ưu hiệu suất
        component: () => import(/* webpackChunkName: "dashboard" */ '../views/DashboardView.vue'),
        meta: { requiresAuth: true } // Đánh dấu route này cần xác thực
    },
    // Thêm các route khác của bạn ở đây
];

// Tạo instance của router
const router = createRouter({
    history: createWebHistory(), // Sử dụng history mode
    routes, // Gán các routes đã định nghĩa
});

// Navigation Guard: Kiểm tra xác thực trước khi chuyển hướng
router.beforeEach((to, from, next) => {
    const loggedIn = localStorage.getItem('jwtToken'); // Kiểm tra xem token có tồn tại không

    if (to.matched.some(record => record.meta.requiresAuth) && !loggedIn) {
        // Nếu route yêu cầu xác thực và người dùng chưa đăng nhập, chuyển hướng đến trang login
        console.log('Chưa đăng nhập, chuyển hướng đến /login');
        next('/login');
    } else if (to.path === '/login' && loggedIn) {
        // Nếu người dùng đã đăng nhập mà cố gắng vào trang login, chuyển hướng đến dashboard
        console.log('Đã đăng nhập, chuyển hướng đến /dashboard');
        next('/dashboard');
    } else {
        // Cho phép truy cập
        next();
    }
});

export default router; // Export router để có thể sử dụng trong main.js