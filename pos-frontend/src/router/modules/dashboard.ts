import type { RouteRecordRaw } from 'vue-router'

const dashboardRoutes: RouteRecordRaw[] = [
  {
    path: '/',
    name: 'Bảng điều khiển',
    component: () => import('../../views/Ecommerce.vue'),
    meta: { title: 'Bảng điều khiển thương mại' },
  },
  {
    path: '/pos',
    name: 'POS',
    component: () => import('../../views/PosManagement/PosManagement.vue'),
    meta: { title: 'POS' },
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
  {
    path: '/stocks',
    name: 'Danh sách nhập / xuất kho',
    component: () => import('../../views/TableManagement/ListStocks.vue'),
    meta: { title: 'Danh sách nhập / xuất kho' },
  },
  {
    path: '/permissions',
    name: 'Danh sách phân quyền',
    component: () => import('../../views/TableManagement/ListEmployee.vue'),
    meta: { title: 'Danh sách phân quyền' },
  },
]

export default dashboardRoutes
