<template>
  <div class="min-h-screen bg-gray-50 flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
    <div class="max-w-4xl w-full bg-white p-8 rounded-lg shadow-xl text-center">
      <h1 class="text-4xl font-extrabold text-gray-900 mb-6">Chào mừng đến với Dashboard!</h1>
      <p v-if="user" class="text-lg text-gray-700 mb-4">
        Bạn đã đăng nhập với vai trò: <strong class="text-blue-600">{{ user.role ? user.role.name : 'N/A' }}</strong>
      </p>
      <p class="text-md text-gray-600 mb-8">Đây là nội dung được bảo vệ. Chỉ người dùng đã đăng nhập mới có thể thấy.</p>

      <div class="space-x-4 mb-8">
        <button
          @click="fetchProtectedData"
          class="inline-flex items-center px-6 py-3 border border-transparent text-base font-medium rounded-md shadow-sm text-white bg-green-600 hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500 transition duration-300 ease-in-out"
        >
          Lấy dữ liệu bảo vệ
        </button>
        <button
          @click="logout"
          class="inline-flex items-center px-6 py-3 border border-transparent text-base font-medium rounded-md shadow-sm text-white bg-red-600 hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500 transition duration-300 ease-in-out"
        >
          Đăng xuất
        </button>
      </div>

      <pre v-if="protectedData" class="mt-6 p-4 bg-gray-100 border border-gray-300 rounded-md text-left text-gray-800 text-sm overflow-auto max-h-96 whitespace-pre-wrap break-words">{{ protectedData }}</pre>
      <p v-if="fetchError" class="text-red-600 mt-4 text-sm font-medium">{{ fetchError }}</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'DashboardView',
  data() {
    return {
      user: null,
      protectedData: null,
      fetchError: null,
      backendProtectedUrl: 'http://localhost:8080/api/employees' // Ví dụ API cần xác thực
    };
  },
  mounted() {
    // Attempt to load user data from localStorage
    const userJson = localStorage.getItem('user');
    if (userJson) {
      try {
        this.user = JSON.parse(userJson);
      } catch (e) {
        console.error("Error parsing user data from localStorage:", e);
        localStorage.removeItem('user'); // Clear corrupted data
      }
    }

    // Add Axios interceptor to include JWT token in requests
    axios.interceptors.request.use(
      (config) => {
        const token = localStorage.getItem('jwtToken');
        if (token) {
          config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
      },
      (error) => {
        return Promise.reject(error);
      }
    );
  },
  methods: {
    async fetchProtectedData() {
      this.fetchError = null;
      this.protectedData = null;
      try {
        const response = await axios.get(this.backendProtectedUrl);
        // Pretty print JSON response for better readability
        this.protectedData = JSON.stringify(response.data, null, 2);
      } catch (error) {
        console.error("Lỗi khi lấy dữ liệu bảo vệ:", error.response || error);
        this.fetchError = "Không thể tải dữ liệu. Bạn có quyền truy cập không?";

        if (error.response) {
          if (error.response.status === 401 || error.response.status === 403) {
            // Unauthorized or Forbidden: token might be invalid or expired
            this.fetchError = "Phiên đăng nhập đã hết hạn hoặc không hợp lệ. Vui lòng đăng nhập lại.";
            // Automatically log out if token is invalid/expired
            this.logout();
          } else if (error.response.data && error.response.data.message) {
            this.fetchError = error.response.data.message;
          }
        } else if (error.request) {
          this.fetchError = "Không thể kết nối đến máy chủ. Vui lòng thử lại sau.";
        } else {
          this.fetchError = "Đã xảy ra lỗi không mong muốn. Vui lòng thử lại.";
        }
      }
    },
    logout() {
      localStorage.removeItem('jwtToken');
      localStorage.removeItem('user');
      // Clear user state
      this.user = null;
      this.protectedData = null;
      this.fetchError = null;
      this.$router.push('/login'); // Redirect to login page
    }
  }
};
</script>

<style scoped>
/* No custom styles needed here anymore, as Tailwind handles it */
</style>