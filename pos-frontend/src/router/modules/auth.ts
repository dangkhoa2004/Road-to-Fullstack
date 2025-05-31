import type { RouteRecordRaw } from 'vue-router'

const authRoutes: RouteRecordRaw[] = [
  {
    path: '/signin',
    name: 'Đăng nhập',
    component: () => import('../../views/Auth/Signin.vue'),
    meta: { title: 'Đăng nhập' },
  },
  {
    path: '/signup',
    name: 'Đăng ký',
    component: () => import('../../views/Auth/Signup.vue'),
    meta: { title: 'Đăng ký' },
  },
]

export default authRoutes
