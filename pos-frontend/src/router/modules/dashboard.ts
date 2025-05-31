import type { RouteRecordRaw } from 'vue-router'

const dashboardRoutes: RouteRecordRaw[] = [
  {
    path: '/',
    name: 'Bảng điều khiển',
    component: () => import('../../views/Ecommerce.vue'),
    meta: { title: 'Bảng điều khiển thương mại' },
  },
  {
    path: '/products',
    name: 'Danh sách sản phẩm',
    component: () => import('../../views/TableManagement/ListProduct.vue'),
    meta: { title: 'Danh sách sản phẩm' },
  },
  {
    path: '/categorys',
    name: 'Danh sách danh mục',
    component: () => import('../../views/TableManagement/ListCategory.vue'),
    meta: { title: 'Danh sách danh mục' },
  },
  {
    path: '/discounts',
    name: 'Danh sách giảm giá',
    component: () => import('../../views/TableManagement/ListDiscount.vue'),
    meta: { title: 'Danh sách giảm giá' },
  },
  {
    path: '/invoices',
    name: 'Danh sách hóa đơn',
    component: () => import('../../views/TableManagement/ListInvoice.vue'),
    meta: { title: 'Danh sách hóa đơn' },
  },
]

export default dashboardRoutes
