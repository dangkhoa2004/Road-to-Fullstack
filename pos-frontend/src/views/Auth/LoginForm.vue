<template>
  <section class="bg-gray-50 dark:bg-gray-900">
    <div class="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
      <a href="#" class="flex items-center mb-6 text-2xl font-semibold text-gray-900 dark:text-white">
        <img class="w-8 h-8 mr-2" src="https://flowbite.s3.amazonaws.com/blocks/marketing-ui/logo.svg" alt="logo">
        Flowbite
      </a>
      <div
        class="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 dark:bg-gray-800 dark:border-gray-700">
        <div class="p-6 space-y-4 md:space-y-6 sm:p-8">
          <h1 class="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white">
            Sign in to your account
          </h1>
          <form class="space-y-4 md:space-y-6" @submit.prevent="handleLogin">
            <div>
              <label for="username" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Your
                username</label>
              <input type="text" name="username" id="username" v-model="username"
                class="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                placeholder="john.doe" required="" aria-label="Tên đăng nhập">
            </div>
            <div>
              <label for="password"
                class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Password</label>
              <input type="password" name="password" id="password" placeholder="••••••••" v-model="password"
                class="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                required="" aria-label="Mật khẩu">
            </div>
            <div class="flex items-center justify-between">
              <div class="flex items-start">
                <div class="flex items-center h-5">
                  <input id="remember" aria-describedby="remember" type="checkbox"
                    class="w-4 h-4 border border-gray-300 rounded bg-gray-50 focus:ring-3 focus:ring-blue-300 dark:bg-gray-700 dark:border-gray-600 dark:focus:ring-blue-600 dark:ring-offset-gray-800">
                </div>
                <div class="ml-3 text-sm">
                  <label for="remember" class="text-gray-500 dark:text-gray-300">Remember me</label>
                </div>
              </div>
              <a href="#" class="text-sm font-medium text-blue-600 hover:underline dark:text-blue-500">Forgot
                password?</a>
            </div>
            <button type="submit"
              class="w-full text-white bg-blue-600 hover:bg-blue-700 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
              Sign in
            </button>
            <p v-if="authError" class="text-red-500 text-center text-sm">{{ authError }}</p>
            <p class="text-sm font-light text-gray-500 dark:text-gray-400">
              Don’t have an account yet? <a href="#"
                class="font-medium text-blue-600 hover:underline dark:text-blue-500">Sign up</a>
            </p>
          </form>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
import * as types from '@/store/types'; // Đảm bảo đường dẫn này đúng

export default {
  name: 'LoginCard',
  data() {
    return {
      username: '',
      password: '',
      // errorMessage: '', // Không cần trực tiếp quản lý ở đây nữa, dùng Vuex
      // successMessage: '', // Không cần trực tiếp quản lý ở đây nữa, Vuex sẽ điều hướng
    };
  },
  computed: {
    ...mapGetters('auth', { // Ánh xạ getter từ module 'auth'
      authError: types.GET_AUTH_ERROR
    })
  },
  methods: {
    ...mapActions('auth', { // Ánh xạ action từ module 'auth'
      loginAction: types.LOGIN
    }),
    async handleLogin() {
      // Không cần reset errorMessage và successMessage ở đây nữa, Vuex action sẽ lo
      // this.errorMessage = '';
      // this.successMessage = '';

      if (!this.username || !this.password) {
        // Bạn có thể xử lý lỗi cục bộ này nếu muốn, hoặc để Vuex action xử lý
        // Hiện tại, action của chúng ta chưa kiểm tra rỗng, nên để ở đây là tốt
        this.$store.commit(`auth/${types.SET_AUTH_ERROR}`, 'Vui lòng nhập cả tên người dùng và mật khẩu.');
        return;
      }

      try {
        // Gọi action Vuex thay vì gọi axios trực tiếp
        // Action `loginAction` của Vuex đã nhận `credentials` là đối tượng
        await this.loginAction({
          username: this.username,
          password: this.password,
        });

        // Sau khi action `loginAction` thành công, nó sẽ tự động điều hướng
        // nên không cần code điều hướng ở đây.
        // Các thông báo thành công cũng được quản lý bởi quá trình điều hướng
        // (ví dụ: hiển thị tin nhắn chào mừng trên trang dashboard)

      } catch (error) {
        // Lỗi sẽ được bắt và commit vào state.auth.authError bởi Vuex action
        // và sẽ tự động hiển thị qua computed property `authError`
        console.error("Login form submission failed in component:", error);
        // Không cần `this.errorMessage = ...` ở đây nữa
      }
    },
  },
};
</script>