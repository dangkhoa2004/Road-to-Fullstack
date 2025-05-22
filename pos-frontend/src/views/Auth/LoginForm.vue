<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-100">
    <div class="bg-white p-8 rounded-lg shadow-xl w-full max-w-md">
      <h2 class="text-3xl font-bold text-center mb-6 text-gray-800">Đăng nhập</h2>
      <form @submit.prevent="handleLogin">
        <div class="mb-4">
          <label for="username" class="block text-gray-700 text-sm font-semibold mb-2">Tên đăng nhập:</label>
          <input type="text" id="username" v-model="username" required aria-label="Tên đăng nhập"
            class="shadow-sm appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" />
        </div>

        <div class="mb-6">
          <label for="password" class="block text-gray-700 text-sm font-semibold mb-2">Mật khẩu:</label>
          <input type="password" id="password" v-model="password" required aria-label="Mật khẩu"
            class="shadow-sm appearance-none border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" />
        </div>

        <button type="submit"
          class="w-full bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50 transition duration-300 ease-in-out">
          Đăng nhập
        </button>

        <p v-if="errorMessage" class="text-red-500 text-center mt-4 text-sm">{{ errorMessage }}</p>
        <p v-if="successMessage" class="text-green-500 text-center mt-4 text-sm">{{ successMessage }}</p>
      </form>
    </div>
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