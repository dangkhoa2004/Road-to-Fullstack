<template>
  <div class="login-container">
    <h2>Đăng nhập</h2>
    <form @submit.prevent="handleLogin">
      <div class="form-group">
        <label for="username">Tên đăng nhập:</label>
        <input
          type="text"
          id="username"
          v-model="username"
          required
          aria-label="Tên đăng nhập"
        />
      </div>

      <div class="form-group">
        <label for="password">Mật khẩu:</label>
        <input
          type="password"
          id="password"
          v-model="password"
          required
          aria-label="Mật khẩu"
        />
      </div>

      <button type="submit" class="login-button">Đăng nhập</button>

      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
      <p v-if="successMessage" class="success-message">{{ successMessage }}</p>
    </form>
  </div>
</template>

<script>
import axios from 'axios'; // Import axios

export default {
  name: 'LoginForm',
  data() {
    return {
      username: '',
      password: '',
      errorMessage: '',
      successMessage: '',
      backendLoginUrl: 'http://localhost:8080/api/auth/login' // URL API của bạn
    };
  },
  methods: {
    async handleLogin() {
      // Reset thông báo lỗi/thành công
      this.errorMessage = '';
      this.successMessage = '';

      if (!this.username || !this.password) {
        this.errorMessage = 'Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu.';
        return;
      }

      try {
        const response = await axios.post(this.backendLoginUrl, {
          username: this.username,
          password: this.password,
        }, {
          headers: {
            'Content-Type': 'application/json',
          },
        });

        // Xử lý khi đăng nhập thành công (HTTP Status 200 OK)
        if (response.status === 200) {
          const data = response.data;
          this.successMessage = data.message || 'Đăng nhập thành công!';
          console.log('Phản hồi từ backend:', data);

          // Lưu JWT token vào localStorage
          localStorage.setItem('jwtToken', data.token);

          // Lưu thông tin người dùng (ví dụ: employee object)
          // Đảm bảo rằng backend của bạn trả về một cấu trúc `employee` trong `AuthResponse`
          if (data.employee) {
            localStorage.setItem('user', JSON.stringify(data.employee));
          }

          // Chuyển hướng người dùng đến trang khác (ví dụ: trang dashboard)
          // Dùng Vue Router đã được cài đặt khi khởi tạo dự án
          this.$router.push('/dashboard'); // Thay '/dashboard' bằng route của trang chính
        } else {
          // Xử lý các mã trạng thái HTTP khác ngoài 200 OK nếu backend có trả về
          this.errorMessage = response.data.message || 'Đăng nhập thất bại với trạng thái: ' + response.status;
        }

      } catch (error) {
        // Xử lý lỗi từ API (ví dụ: 401 Unauthorized, 400 Bad Request, v.v.)
        if (error.response) {
          // Lỗi từ phản hồi của server (status code không phải 2xx)
          console.error('Lỗi phản hồi từ backend:', error.response.data);
          this.errorMessage = error.response.data.message || 'Tên đăng nhập hoặc mật khẩu không đúng.';
        } else if (error.request) {
          // Yêu cầu đã được gửi nhưng không nhận được phản hồi
          console.error('Không nhận được phản hồi từ backend:', error.request);
          this.errorMessage = 'Không thể kết nối đến máy chủ. Vui lòng thử lại sau.';
        } else {
          // Lỗi trong quá trình thiết lập yêu cầu
          console.error('Lỗi khi thiết lập yêu cầu:', error.message);
          this.errorMessage = 'Đã xảy ra lỗi không mong muốn. Vui lòng thử lại.';
        }
      }
    },
  },
};
</script>

<style scoped>
/* Basic styling for the login form */
.login-container {
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  background-color: #fff;
}

h2 {
  text-align: center;
  margin-bottom: 20px;
  color: #333;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
  color: #555;
}

.form-group input[type="text"],
.form-group input[type="password"] {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-sizing: border-box; /* Ensures padding doesn't increase width */
  font-size: 1rem;
}

.login-button {
  width: 100%;
  padding: 10px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1.1rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.login-button:hover {
  background-color: #0056b3;
}

.error-message {
  color: #dc3545;
  margin-top: 10px;
  text-align: center;
}

.success-message {
  color: #28a745;
  margin-top: 10px;
  text-align: center;
}
</style>