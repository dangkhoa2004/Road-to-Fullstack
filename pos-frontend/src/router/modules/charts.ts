import type { RouteRecordRaw } from 'vue-router'

const chartsRoutes: RouteRecordRaw[] = [
  {
    path: '/line-chart',
    name: 'Biểu đồ đường',
    component: () => import('../../views/Chart/LineChart/LineChart.vue'),
    meta: { title: 'Biểu đồ đường' },
  },
  {
    path: '/bar-chart',
    name: 'Biểu đồ cột',
    component: () => import('../../views/Chart/BarChart/BarChart.vue'),
    meta: { title: 'Biểu đồ cột' },
  },
]

export default chartsRoutes
