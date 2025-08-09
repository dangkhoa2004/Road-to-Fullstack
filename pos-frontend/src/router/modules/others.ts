import type {RouteRecordRaw} from 'vue-router'

const othersRoutes: RouteRecordRaw[] = [
  {
    path: '/calendar',
    name: 'Lịch',
    component: () => import('../../views/Others/Calendar.vue'),
    meta: {title: 'Lịch'},
  },
  {
    path: '/profile',
    name: 'Hồ sơ',
    component: () => import('../../views/Others/UserProfile.vue'),
    meta: {title: 'Hồ sơ người dùng'},
  },
  {
    path: '/form-elements',
    name: 'Phần tử biểu mẫu',
    component: () => import('../../views/Forms/FormElements.vue'),
    meta: {title: 'Phần tử biểu mẫu'},
  },
  {
    path: '/basic-tables',
    name: 'Bảng cơ bản',
    component: () => import('../../views/Tables/BasicTables.vue'),
    meta: {title: 'Bảng cơ bản'},
  },
]

export default othersRoutes
