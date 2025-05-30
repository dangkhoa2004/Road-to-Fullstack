import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  scrollBehavior(to, from, savedPosition) {
    return savedPosition || { left: 0, top: 0 }
  },
  routes: [
    {
      path: '/',
      name: 'Bảng điều khiển',
      component: () => import('../views/Ecommerce.vue'),
      meta: {
        title: 'Bảng điều khiển thương mại',
      },
    },
    {
      path: '/calendar',
      name: 'Lịch',
      component: () => import('../views/Others/Calendar.vue'),
      meta: {
        title: 'Lịch',
      },
    },
    {
      path: '/profile',
      name: 'Hồ sơ',
      component: () => import('../views/Others/UserProfile.vue'),
      meta: {
        title: 'Hồ sơ người dùng',
      },
    },
    {
      path: '/form-elements',
      name: 'Phần tử biểu mẫu',
      component: () => import('../views/Forms/FormElements.vue'),
      meta: {
        title: 'Phần tử biểu mẫu',
      },
    },
    {
      path: '/products',
      name: 'Danh sách sản phẩm',
      component: () => import('../views/Products/ListProduct.vue'),
      meta: {
        title: 'Danh sách sản phẩm',
      },
    },
    {
      path: '/basic-tables',
      name: 'Bảng cơ bản',
      component: () => import('../views/Tables/BasicTables.vue'),
      meta: {
        title: 'Bảng cơ bản',
      },
    },
    {
      path: '/line-chart',
      name: 'Biểu đồ đường',
      component: () => import('../views/Chart/LineChart/LineChart.vue'),
    },
    {
      path: '/bar-chart',
      name: 'Biểu đồ cột',
      component: () => import('../views/Chart/BarChart/BarChart.vue'),
    },
    {
      path: '/alerts',
      name: 'Cảnh báo',
      component: () => import('../views/UiElements/Alerts.vue'),
      meta: {
        title: 'Cảnh báo',
      },
    },
    {
      path: '/avatars',
      name: 'Ảnh đại diện',
      component: () => import('../views/UiElements/Avatars.vue'),
      meta: {
        title: 'Ảnh đại diện',
      },
    },
    {
      path: '/badge',
      name: 'Huy hiệu',
      component: () => import('../views/UiElements/Badges.vue'),
      meta: {
        title: 'Huy hiệu',
      },
    },
    {
      path: '/buttons',
      name: 'Nút bấm',
      component: () => import('../views/UiElements/Buttons.vue'),
      meta: {
        title: 'Nút bấm',
      },
    },
    {
      path: '/images',
      name: 'Hình ảnh',
      component: () => import('../views/UiElements/Images.vue'),
      meta: {
        title: 'Hình ảnh',
      },
    },
    {
      path: '/videos',
      name: 'Video',
      component: () => import('../views/UiElements/Videos.vue'),
      meta: {
        title: 'Video',
      },
    },
    {
      path: '/blank',
      name: 'Trang trắng',
      component: () => import('../views/Pages/BlankPage.vue'),
      meta: {
        title: 'Trang trắng',
      },
    },
    {
      path: '/error-404',
      name: 'Lỗi 404',
      component: () => import('../views/Errors/FourZeroFour.vue'),
      meta: {
        title: 'Lỗi 404',
      },
    },
    {
      path: '/signin',
      name: 'Đăng nhập',
      component: () => import('../views/Auth/Signin.vue'),
      meta: {
        title: 'Đăng nhập',
      },
    },
    {
      path: '/signup',
      name: 'Đăng ký',
      component: () => import('../views/Auth/Signup.vue'),
      meta: {
        title: 'Đăng ký',
      },
    },
  ],
})

export default router

router.beforeEach((to, from, next) => {
  document.title = `Vue.js ${to.meta.title} | TailAdmin - Mẫu bảng điều khiển Vue.js + Tailwind CSS`
  next()
})
