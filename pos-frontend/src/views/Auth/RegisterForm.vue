<template>
  <form class="max-w-3xl mx-auto p-8 bg-white rounded-lg shadow-xl
               dark:bg-gray-800 dark:border dark:border-gray-700"
        @submit.prevent="handleRegister">
    <h2 class="text-3xl font-extrabold text-gray-900 mb-8 text-center
                   dark:text-white">Đăng Ký Tài Khoản Mới</h2>

    <div class="grid gap-6 mb-6 md:grid-cols-2">
      <div>
        <label class="block mb-2 text-sm font-medium text-gray-900
                                              dark:text-gray-300" for="full_name">Tên đầy đủ</label>
        <input id="full_name" v-model="fullName" autocomplete="off"
               class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg
                           focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5
                           dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400
                           dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
               placeholder="Nguyễn Văn A" required type="text"/>
      </div>
      <div>
        <label class="block mb-2 text-sm font-medium text-gray-900
                                              dark:text-gray-300" for="username">Tên người dùng</label>
        <input id="username" v-model="username" autocomplete="off"
               class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg
                           focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5
                           dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400
                           dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
               placeholder="nguyenvana" required type="text"/>
      </div>
    </div>

    <div class="grid gap-6 mb-6 md:grid-cols-2">
      <div>
        <label class="block mb-2 text-sm font-medium text-gray-900
                                              dark:text-gray-300" for="email">Địa chỉ Email</label>
        <input id="email" v-model="email" autocomplete="off"
               class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg
                           focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5
                           dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400
                           dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
               placeholder="john.doe@example.com" required type="email"/>
      </div>
      <div>
        <label class="block mb-2 text-sm font-medium text-gray-900
                                              dark:text-gray-300" for="phone">Số điện thoại</label>
        <input id="phone" v-model="phone" autocomplete="off"
               class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg
                           focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5
                           dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400
                           dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
               pattern="[0-9]{10}" placeholder="0912345678" required title="Vui lòng nhập 10 chữ số"
               type="tel"/>
      </div>
    </div>

    <div class="grid gap-6 mb-6 md:grid-cols-2">
      <div>
        <label class="block mb-2 text-sm font-medium text-gray-900
                                              dark:text-gray-300" for="password">Mật khẩu</label>
        <input id="password" v-model="password" autocomplete="new-password"
               class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg
                           focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5
                           dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400
                           dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
               placeholder="•••••••••" required type="password"/>
      </div>
      <div>
        <label class="block mb-2 text-sm font-medium text-gray-900
                                              dark:text-gray-300" for="confirm_password">Xác nhận mật khẩu</label>
        <input id="confirm_password" v-model="confirmPassword" autocomplete="new-password"
               class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg
                           focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5
                           dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400
                           dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
               placeholder="•••••••••" required type="password"/>
      </div>
    </div>

    <div class="flex items-start mb-6">
      <div class="flex items-center h-5">
        <input id="terms" v-model="agreeTerms" class="w-4 h-4 border border-gray-300 rounded-sm bg-gray-50
                           focus:ring-3 focus:ring-blue-300
                           dark:bg-gray-700 dark:border-gray-600 dark:focus:ring-blue-600 dark:ring-offset-gray-800"
               required
               type="checkbox"/>
      </div>
      <label class="ms-2 text-sm font-medium text-gray-900
                                      dark:text-gray-300" for="terms">
        Tôi đồng ý với <a class="text-blue-600 hover:underline
                                                 dark:text-blue-500" href="#">điều khoản và điều kiện</a>.
      </label>
    </div>

    <div v-if="authError" class="mb-4 text-red-500 text-sm text-center
                                     dark:text-red-400">
      {{ authError }}
    </div>

    <button class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300
                   font-medium rounded-lg text-sm w-full px-5 py-2.5 text-center
                   dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
            type="submit">
      Đăng ký tài khoản mới
    </button>
  </form>
</template>

<script>
import {mapActions, mapGetters} from 'vuex';
import * as types from '@/store/types';

export default {
  name: 'RegisterForm',
  data() {
    return {
      fullName: '',
      username: '',
      email: '',
      phone: '',
      password: '',
      confirmPassword: '',
      agreeTerms: false,
    };
  },
  computed: {
    ...mapGetters('auth', {
      authError: types.GET_AUTH_ERROR
    })
  },
  methods: {
    ...mapActions('auth', {
      registerAction: types.REGISTER
    }),
    async handleRegister() {
      this.$store.commit(`auth/${types.SET_AUTH_ERROR}`, null);

      if (!this.fullName || !this.username || !this.email || !this.phone || !this.password || !this.confirmPassword || !this.agreeTerms) {
        this.$store.commit(`auth/${types.SET_AUTH_ERROR}`, 'Vui lòng điền đầy đủ tất cả các trường và đồng ý với điều khoản.');
        return;
      }

      if (this.password !== this.confirmPassword) {
        this.$store.commit(`auth/${types.SET_AUTH_ERROR}`, 'Mật khẩu và xác nhận mật khẩu không khớp.');
        return;
      }

      try {
        await this.registerAction({
          name: this.fullName,
          username: this.username,
          password: this.password,
          email: this.email,
          phone: this.phone,
        });
      } catch (error) {
        console.error("Đăng ký thất bại:", error);
        const errorMessage = error.response && error.response.data && error.response.data.message
            ? error.response.data.message
            : 'Đăng ký thất bại. Vui lòng thử lại.';
        this.$store.commit(`auth/${types.SET_AUTH_ERROR}`, errorMessage);
      }
    },
  },
};
</script>

<style scoped></style>