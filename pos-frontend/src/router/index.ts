import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'

import authRoutes from './modules/auth'
import dashboardRoutes from './modules/dashboard'
import uiRoutes from './modules/ui'
import chartsRoutes from './modules/charts'
import othersRoutes from './modules/others'
import errorsRoutes from './modules/errors'
import pagesRoutes from './modules/pages'

const routes: RouteRecordRaw[] = [
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
    return savedPosition || { left: 0, top: 0 }
  },
  routes,
})

router.beforeEach((to, from, next) => {
  const defaultTitle = 'Ứng dụng của tôi'
  document.title = `${to.meta?.title || defaultTitle} | Pos - Application`

  const token = localStorage.getItem('jwtToken')
  const user = localStorage.getItem('user')

  const publicRoutes = ['/dang-nhap', '/dang-ky', '/quen-mat-khau']

  if ((!token || !user) && !publicRoutes.includes(to.path)) {
    next('/dang-nhap')
  } else {
    next()
  }
})

export default router
