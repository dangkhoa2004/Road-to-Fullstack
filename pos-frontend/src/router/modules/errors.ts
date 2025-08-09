import type {RouteRecordRaw} from 'vue-router'

const errorsRoutes: RouteRecordRaw[] = [
  {
    path: '/error-404',
    name: 'Lỗi 404',
    component: () => import('../../views/Errors/FourZeroFour.vue'),
    meta: {title: 'Lỗi 404'},
  },
]

export default errorsRoutes
