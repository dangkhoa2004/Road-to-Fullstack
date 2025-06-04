<template>
    <FullScreenLayout>
        <div class="relative p-6 bg-white z-1 dark:bg-gray-900 sm:p-0">
            <div class="relative flex flex-col justify-center w-full h-screen lg:flex-row dark:bg-gray-900">
                <div class="flex flex-col flex-1 w-full lg:w-1/2">
                    <div class="flex flex-col justify-center flex-1 w-full max-w-md mx-auto">
                        <div>
                            <div class="mb-5 sm:mb-8">
                                <h1
                                    class="mb-2 font-semibold text-gray-800 text-title-sm dark:text-white/90 sm:text-title-md">
                                    Quên mật khẩu
                                </h1>
                                <p class="text-sm text-gray-500 dark:text-gray-400">
                                    Nhập email để nhận mã xác nhận đặt lại mật khẩu.
                                </p>
                            </div>
                            <form @submit.prevent="handleSubmit">
                                <div class="space-y-5">
                                    <div>
                                        <label for="email"
                                            class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">
                                            Email<span class="text-error-500">*</span>
                                        </label>
                                        <input v-model="email" type="email" id="email" name="email"
                                            placeholder="Nhập email" :disabled="emailSent"
                                            class="dark:bg-dark-900 h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800" />
                                    </div>

                                    <!-- Các trường nhập xuất hiện khi đã gửi email thành công -->
                                    <div v-if="emailSent">
                                        <label for="otp"
                                            class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">
                                            Mã xác nhận<span class="text-error-500">*</span>
                                        </label>
                                        <input v-model="otp" type="text" id="otp" name="otp"
                                            placeholder="Nhập mã xác nhận"
                                            class="dark:bg-dark-900 h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800" />

                                        <label for="newPassword"
                                            class="mt-4 mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">
                                            Mật khẩu mới<span class="text-error-500">*</span>
                                        </label>
                                        <input v-model="newPassword" type="password" id="newPassword" name="newPassword"
                                            placeholder="Nhập mật khẩu mới"
                                            class="dark:bg-dark-900 h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800" />

                                        <label for="confirmPassword"
                                            class="mt-4 mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">
                                            Xác nhận mật khẩu<span class="text-error-500">*</span>
                                        </label>
                                        <input v-model="confirmPassword" type="password" id="confirmPassword"
                                            name="confirmPassword" placeholder="Xác nhận mật khẩu mới"
                                            class="dark:bg-dark-900 h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800" />
                                    </div>

                                    <div>
                                        <button type="submit"
                                            class="flex items-center justify-center w-full px-4 py-3 text-sm font-medium text-white transition rounded-lg bg-brand-500 shadow-theme-xs hover:bg-brand-600">
                                            {{ emailSent ? 'Xác nhận thay đổi mật khẩu' : 'Gửi mã xác nhận' }}
                                        </button>
                                    </div>
                                </div>
                            </form>

                            <div class="mt-5 text-center sm:text-start">
                                <router-link to="/dang-nhap"
                                    class="text-sm text-brand-500 hover:text-brand-600 dark:text-brand-400">
                                    Quay lại đăng nhập
                                </router-link>
                            </div>
                        </div>
                    </div>
                </div>
                <AboutDashBoard />
            </div>
        </div>
    </FullScreenLayout>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useStore } from 'vuex'
import FullScreenLayout from '@/components/layout/FullScreenLayout.vue'
import AboutDashBoard from './components/AboutDashBoard.vue'
import * as types from '@/store/types'
import { resetPassword as resetPasswordApi } from '@/api/modules/auth'

const store = useStore()
const email = ref('')
const otp = ref('')
const newPassword = ref('')
const confirmPassword = ref('')
const emailSent = ref(false)

const authError = computed(() => store.getters['auth/' + types.GET_AUTH_ERROR])

const resetPasswordAction = (payload: { email: string }) => {
    return store.dispatch('auth/' + types.RESET_PASSWORD, payload)
}

const handleSubmit = async () => {
    store.commit('auth/' + types.SET_AUTH_ERROR, null)
    if (!emailSent.value) {
        if (!email.value) {
            store.commit('auth/' + types.SET_AUTH_ERROR, 'Vui lòng nhập email.')
            return
        }
        try {
            await resetPasswordAction({ email: email.value })
            emailSent.value = true
        } catch (error) {
            console.error('Send reset email failed:', error)
        }
    } else {
        if (!otp.value || !newPassword.value || !confirmPassword.value) {
            store.commit('auth/' + types.SET_AUTH_ERROR, 'Vui lòng nhập đầy đủ thông tin.')
            return
        }
        if (newPassword.value !== confirmPassword.value) {
            store.commit('auth/' + types.SET_AUTH_ERROR, 'Mật khẩu và xác nhận không khớp.')
            return
        }
        try {
            await resetPasswordApi(otp.value, {
                newPassword: newPassword.value,
                confirmPassword: confirmPassword.value
            })
            location.href = '/dang-nhap'
        } catch (error) {
            console.error('Reset password failed:', error)
            store.commit('auth/' + types.SET_AUTH_ERROR, 'Không thể thay đổi mật khẩu. Vui lòng thử lại.')
        }
    }
}
</script>
