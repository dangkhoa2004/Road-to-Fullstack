import type {RouteRecordRaw} from 'vue-router'

const pagesRoutes: RouteRecordRaw[] = [
  {
    path: '/blank',
    name: 'Trang trắng',
    component: () => import('../../views/Pages/BlankPage.vue'),
    meta: {title: 'Trang trắng'},
  },
]

export default pagesRoutes
