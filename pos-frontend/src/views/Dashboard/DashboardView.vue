<template>
  <div class="min-h-screen bg-gray-50 flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
    <div class="max-w-4xl w-full bg-white p-8 rounded-lg shadow-xl text-center">
      <h1 class="text-4xl font-extrabold text-gray-900 mb-6">Chào mừng đến với Dashboard!</h1>

      <p v-if="user && user.username" class="text-2xl text-gray-800 mb-4">
        Xin chào, <strong class="text-blue-600">{{ user.username }}</strong>!
      </p>

      <p v-if="user && user.role" class="text-lg text-gray-700 mb-4">
        Bạn đã đăng nhập với vai trò: <strong class="text-blue-600">{{
          user.role.name ? user.role.name : 'N/A'
        }}</strong>
      </p>

      <p class="text-md text-gray-600 mb-8">
        Đây là khu vực hiển thị các thông tin tổng quan và tiện ích dành cho người dùng đã đăng nhập.
      </p>

      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mt-8">
        <div class="bg-blue-100 p-6 rounded-lg shadow-md">
          <h3 class="text-xl font-semibold text-blue-800 mb-2">Tổng quan</h3>
          <p class="text-blue-700">Xem các số liệu thống kê chính.</p>
        </div>
        <div class="bg-purple-100 p-6 rounded-lg shadow-md">
          <h3 class="text-xl font-semibold text-purple-800 mb-2">Quản lý dự án</h3>
          <p class="text-purple-700">Theo dõi tiến độ các dự án của bạn.</p>
        </div>
        <div class="bg-yellow-100 p-6 rounded-lg shadow-md">
          <h3 class="text-xl font-semibold text-yellow-800 mb-2">Thông báo</h3>
          <p class="text-yellow-700">Xem các thông báo và cảnh báo mới nhất.</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {mapGetters} from 'vuex';
import * as types from '@/store/types';

export default {
  name: 'DashboardView',
  data() {
    return {};
  },
  computed: {
    ...mapGetters('auth', {
      user: types.GET_AUTH_USER
    }),
  },
  created() {
  }
};
</script>
