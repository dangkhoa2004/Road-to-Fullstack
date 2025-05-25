<template>
  <div class="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
    <a class="flex items-center mb-6 text-2xl font-semibold text-gray-900 dark:text-white" href="#">
      <img alt="logo" class="w-8 h-8 mr-2" src="https://flowbite.s3.amazonaws.com/blocks/marketing-ui/logo.svg">
      RTFS
    </a>
    <div
        class="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 dark:bg-gray-800 dark:border-gray-700">
      <div class="p-6 space-y-4 md:space-y-6 sm:p-8">
        <h1 class="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white">
          Đăng nhập vào tài khoản của bạn
        </h1>
        <form class="space-y-4 md:space-y-6" @submit.prevent="handleLogin">
          <div>
            <label class="block mb-2 text-sm font-medium text-gray-900 dark:text-white" for="username">Tài khoản</label>
            <input id="username" v-model="username" aria-label="Tên đăng nhập" class="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                   name="username"
                   placeholder="john.doe" required="" type="text">
          </div>
          <div>
            <label class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                   for="password">Mật khẩu</label>
            <input id="password" v-model="password" aria-label="Mật khẩu" class="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" name="password"
                   placeholder="••••••••"
                   required="" type="password">
          </div>
          <div class="flex items-center justify-between">
            <div class="flex items-start">
              <div class="flex items-center h-5">
                <input id="remember" aria-describedby="remember" class="w-4 h-4 border border-gray-300 rounded bg-gray-50 focus:ring-3 focus:ring-blue-300 dark:bg-gray-700 dark:border-gray-600 dark:focus:ring-blue-600 dark:ring-offset-gray-800"
                       type="checkbox">
              </div>
              <div class="ml-3 text-sm">
                <label class="text-gray-500 dark:text-gray-300" for="remember">Ghi nhớ tôi</label>
              </div>
            </div>
            <a class="text-sm font-medium text-blue-600 hover:underline dark:text-blue-500" href="#">Quên mật khẩu?</a>
          </div>
          <button class="w-full text-white bg-blue-600 hover:bg-blue-700 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                  type="submit">
            Đăng nhập
          </button>
          <p v-if="authError" class="text-red-500 text-center text-sm">{{ authError }}</p>
          <p class="text-sm font-light text-gray-500 dark:text-gray-400">
            Bạn chưa có tài khoản? <a class="font-medium text-blue-600 hover:underline dark:text-blue-500"
                                      href="/register">Đăng ký</a>
          </p>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import {mapActions, mapGetters} from 'vuex';
import * as types from '@/store/types';

export default {
  name: 'LoginCard',
  data() {
    return {
      username: '',
      password: '',
    };
  },
  computed: {
    ...mapGetters('auth', {
      authError: types.GET_AUTH_ERROR
    })
  },
  methods: {
    ...mapActions('auth', {
      loginAction: types.LOGIN
    }),
    async handleLogin() {
      if (!this.username || !this.password) {
        this.$store.commit(`auth/${types.SET_AUTH_ERROR}`, 'Vui lòng nhập cả tên người dùng và mật khẩu.');
        return;
      }
      try {
        await this.loginAction({
          username: this.username,
          password: this.password,
        });
      } catch (error) {
        console.error("Login form submission failed in component:", error);
      }
    },
  },
};
</script>