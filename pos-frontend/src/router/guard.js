// router/guard.js
export function authGuard(to, from, next) {
    const loggedIn = localStorage.getItem('jwtToken');

    if (to.matched.some(record => record.meta.requiresAuth) && !loggedIn) {
        // console.log('Chưa đăng nhập, chuyển hướng đến /login');
        next('/login');
    } else if (to.path === '/login' && loggedIn) {
        // console.log('Đã đăng nhập, chuyển hướng đến /dashboard');
        next('/dashboard');
    } else {
        next();
    }
}
