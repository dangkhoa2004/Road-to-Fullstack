// src/router/index.ts
import {createRouter, createWebHistory} from 'vue-router'
import type {RouteRecordRaw} from 'vue-router'
import {authGuard} from '@/composables/authGuard'

import authRoutes from './modules/auth'
import dashboardRoutes from './modules/dashboard'
import uiRoutes from './modules/ui'
import chartsRoutes from './modules/charts'
import othersRoutes from './modules/others'
import errorsRoutes from './modules/errors'
import pagesRoutes from './modules/pages'

const routes: RouteRecordRaw[] = [
  // Route mặc định redirect về 404 nếu không tìm thấy
  {
    path: '/:pathMatch(.*)*',
    redirect: '/error-404',
  },
  ...dashboardRoutes,
  ...authRoutes,
  ...uiRoutes,
  ...chartsRoutes,
  ...othersRoutes,
  ...errorsRoutes,
  ...pagesRoutes,
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  scrollBehavior(to, from, savedPosition) {
    return savedPosition || {left: 0, top: 0}
  },
  routes,
})

// Cập nhật tiêu đề động cho từng route
router.beforeEach((to, from, next) => {
  const defaultTitle = 'Ứng dụng của tôi'
  document.title = `${to.meta?.title || defaultTitle} | Pos - Application`
  next()
})

// Bảo vệ route (auth)
router.beforeEach(authGuard)

export default router
