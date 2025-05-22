<template>
  <div class="dashboard-container">
    <h1>Chào mừng đến với Dashboard!</h1>
    <p v-if="user">Bạn đã đăng nhập với vai trò: <strong>{{ user.role ? user.role.name : 'N/A' }}</strong></p>
    <p>Đây là nội dung được bảo vệ. Chỉ người dùng đã đăng nhập mới có thể thấy.</p>

    <button @click="fetchProtectedData" class="data-button">Lấy dữ liệu bảo vệ</button>
    <pre v-if="protectedData" class="data-display">{{ protectedData }}</pre>
    <p v-if="fetchError" class="error-message">{{ fetchError }}</p>

    <button @click="logout" class="logout-button">Đăng xuất</button>
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
    const userJson = localStorage.getItem('user');
    if (userJson) {
      this.user = JSON.parse(userJson);
    }
  },
  methods: {
    async fetchProtectedData() {
      this.fetchError = null;
      this.protectedData = null;
      try {
        const response = await axios.get(this.backendProtectedUrl);
        this.protectedData = JSON.stringify(response.data, null, 2);
      } catch (error) {
        console.error("Lỗi khi lấy dữ liệu bảo vệ:", error.response || error);
        this.fetchError = "Không thể tải dữ liệu. Bạn có quyền truy cập không?";
        if (error.response && error.response.status === 401) {
          this.fetchError = "Phiên đăng nhập đã hết hạn hoặc không hợp lệ. Vui lòng đăng nhập lại.";
          // Nếu token hết hạn, có thể tự động đăng xuất
          this.logout();
        }
      }
    },
    logout() {
      localStorage.removeItem('jwtToken');
      localStorage.removeItem('user');
      this.$router.push('/login'); // Chuyển hướng về trang đăng nhập
    }
  }
};
</script>

<style scoped>
.dashboard-container {
  max-width: 800px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  background-color: #f9f9f9;
  text-align: center;
}

h1 {
  color: #333;
  margin-bottom: 15px;
}

p {
  color: #666;
  line-height: 1.6;
}

.data-button, .logout-button {
  padding: 10px 20px;
  margin: 10px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 1rem;
  transition: background-color 0.3s ease;
}

.data-button {
  background-color: #28a745;
  color: white;
}

.data-button:hover {
  background-color: #218838;
}

.logout-button {
  background-color: #dc3545;
  color: white;
}

.logout-button:hover {
  background-color: #c82333;
}

.data-display {
  background-color: #e9ecef;
  border: 1px solid #ced4da;
  padding: 15px;
  margin-top: 20px;
  border-radius: 5px;
  text-align: left;
  white-space: pre-wrap; /* Preserve whitespace and wrap long lines */
  word-break: break-all; /* Break words if too long */
}

.error-message {
  color: #dc3545;
  margin-top: 10px;
}
</style>