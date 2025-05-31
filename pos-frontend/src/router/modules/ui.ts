import type { RouteRecordRaw } from 'vue-router'

const uiRoutes: RouteRecordRaw[] = [
  {
    path: '/alerts',
    name: 'Cảnh báo',
    component: () => import('../../views/UiElements/Alerts.vue'),
    meta: { title: 'Cảnh báo' },
  },
  {
    path: '/avatars',
    name: 'Ảnh đại diện',
    component: () => import('../../views/UiElements/Avatars.vue'),
    meta: { title: 'Ảnh đại diện' },
  },
  {
    path: '/badge',
    name: 'Huy hiệu',
    component: () => import('../../views/UiElements/Badges.vue'),
    meta: { title: 'Huy hiệu' },
  },
  {
    path: '/buttons',
    name: 'Nút bấm',
    component: () => import('../../views/UiElements/Buttons.vue'),
    meta: { title: 'Nút bấm' },
  },
  {
    path: '/images',
    name: 'Hình ảnh',
    component: () => import('../../views/UiElements/Images.vue'),
    meta: { title: 'Hình ảnh' },
  },
  {
    path: '/videos',
    name: 'Video',
    component: () => import('../../views/UiElements/Videos.vue'),
    meta: { title: 'Video' },
  },
]

export default uiRoutes
