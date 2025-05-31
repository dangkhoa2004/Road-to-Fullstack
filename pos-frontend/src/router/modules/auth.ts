import type { RouteRecordRaw } from 'vue-router'

const authRoutes: RouteRecordRaw[] = [
  {
    path: '/dang-nhap',
    name: 'Đăng nhập',
    component: () => import('../../views/Auth/Signin.vue'),
    meta: { title: 'Đăng nhập' },
  },
  {
    path: '/dang-ky',
    name: 'Đăng ký',
    component: () => import('../../views/Auth/Signup.vue'),
    meta: { title: 'Đăng ký' },
  },
  {
    path: '/quen-mat-khau',
    name: 'Quên mật khẩu',
    component: () => import('../../views/Auth/ForgotPassword.vue'),
    meta: { title: 'Quên mật khẩu' },
  },
]

export default authRoutes
