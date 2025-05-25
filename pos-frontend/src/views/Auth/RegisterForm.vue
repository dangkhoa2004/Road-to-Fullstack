<template>
    <form @submit.prevent="handleRegister"
        class="max-w-3xl mx-auto p-8 bg-white rounded-lg shadow-xl
               dark:bg-gray-800 dark:border dark:border-gray-700">
        <h2 class="text-3xl font-extrabold text-gray-900 mb-8 text-center
                   dark:text-white">Đăng Ký Tài Khoản Mới</h2>

        <div class="grid gap-6 mb-6 md:grid-cols-2">
            <div>
                <label for="full_name" class="block mb-2 text-sm font-medium text-gray-900
                                              dark:text-gray-300">Tên đầy đủ</label>
                <input type="text" id="full_name" v-model="fullName"
                    class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg
                           focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5
                           dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400
                           dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                    placeholder="Nguyễn Văn A" required autocomplete="off" />
            </div>
            <div>
                <label for="username" class="block mb-2 text-sm font-medium text-gray-900
                                              dark:text-gray-300">Tên người dùng</label>
                <input type="text" id="username" v-model="username"
                    class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg
                           focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5
                           dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400
                           dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                    placeholder="nguyenvana" required autocomplete="off" />
            </div>
        </div>

        <div class="grid gap-6 mb-6 md:grid-cols-2">
            <div>
                <label for="email" class="block mb-2 text-sm font-medium text-gray-900
                                              dark:text-gray-300">Địa chỉ Email</label>
                <input type="email" id="email" v-model="email"
                    class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg
                           focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5
                           dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400
                           dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                    placeholder="john.doe@example.com" required autocomplete="off" />
            </div>
            <div>
                <label for="phone" class="block mb-2 text-sm font-medium text-gray-900
                                              dark:text-gray-300">Số điện thoại</label>
                <input type="tel" id="phone" v-model="phone"
                    class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg
                           focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5
                           dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400
                           dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                    placeholder="0912345678" pattern="[0-9]{10}" title="Vui lòng nhập 10 chữ số" required
                    autocomplete="off" />
            </div>
        </div>

        <div class="grid gap-6 mb-6 md:grid-cols-2">
            <div>
                <label for="password" class="block mb-2 text-sm font-medium text-gray-900
                                              dark:text-gray-300">Mật khẩu</label>
                <input type="password" id="password" v-model="password"
                    class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg
                           focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5
                           dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400
                           dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                    placeholder="•••••••••" required autocomplete="new-password" />
            </div>
            <div>
                <label for="confirm_password" class="block mb-2 text-sm font-medium text-gray-900
                                              dark:text-gray-300">Xác nhận mật khẩu</label>
                <input type="password" id="confirm_password" v-model="confirmPassword"
                    class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg
                           focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5
                           dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400
                           dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                    placeholder="•••••••••" required autocomplete="new-password" />
            </div>
        </div>

        <div class="flex items-start mb-6">
            <div class="flex items-center h-5">
                <input id="terms" type="checkbox" v-model="agreeTerms"
                    class="w-4 h-4 border border-gray-300 rounded-sm bg-gray-50
                           focus:ring-3 focus:ring-blue-300
                           dark:bg-gray-700 dark:border-gray-600 dark:focus:ring-blue-600 dark:ring-offset-gray-800"
                    required />
            </div>
            <label for="terms" class="ms-2 text-sm font-medium text-gray-900
                                      dark:text-gray-300">
                Tôi đồng ý với <a href="#" class="text-blue-600 hover:underline
                                                 dark:text-blue-500">điều khoản và điều kiện</a>.
            </label>
        </div>

        <div v-if="authError" class="mb-4 text-red-500 text-sm text-center
                                     dark:text-red-400">
            {{ authError }}
        </div>

        <button type="submit"
            class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300
                   font-medium rounded-lg text-sm w-full px-5 py-2.5 text-center
                   dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
            Đăng ký tài khoản mới
        </button>
    </form>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
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
                console.log('Đăng ký thành công!');
                // Bạn có thể thêm logic chuyển hướng ở đây, ví dụ:
                // this.$router.push('/registration-success');
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