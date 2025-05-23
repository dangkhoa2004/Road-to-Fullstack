// guard.js (nằm trong thư mục src/router/)

/**
 * Hàm này chứa logic Navigation Guard để kiểm tra xác thực.
 * Nó sẽ được gọi bởi router.beforeEach.
 * @param {Object} to - Đối tượng route mà người dùng đang cố gắng truy cập.
 * @param {Object} from - Đối tượng route mà người dùng đang rời đi.
 * @param {Function} next - Hàm để chuyển hướng hoặc cho phép truy cập.
 */
export function authGuard(to, from, next) {
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
}
