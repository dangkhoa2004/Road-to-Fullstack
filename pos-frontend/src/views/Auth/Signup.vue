<template>
  <FullScreenLayout>
    <div class="relative p-6 bg-white z-1 dark:bg-gray-900 sm:p-0">
      <div
        class="relative flex flex-col justify-center w-full h-screen lg:flex-row dark:bg-gray-900">
        <div class="flex flex-col flex-1 w-full lg:w-1/2">
          <div class="flex flex-col justify-center flex-1 w-full max-w-md mx-auto">
            <div>
              <div class="mb-5 sm:mb-8">
                <h1
                  class="mb-2 font-semibold text-gray-800 text-title-sm dark:text-white/90 sm:text-title-md">
                  Đăng ký
                </h1>
                <p class="text-sm text-gray-500 dark:text-gray-400">
                  Điền thông tin để tạo tài khoản!
                </p>
              </div>
              <form @submit.prevent="handleSubmit">
                <div class="space-y-5">
                  <div>
                    <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400"
                           for="name">
                      Họ và tên<span class="text-error-500">*</span>
                    </label>
                    <input id="name" v-model="name" class="dark:bg-dark-900 h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800" name="name"
                           placeholder="Nhập họ và tên"
                           type="text"/>
                  </div>

                  <div>
                    <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400"
                           for="username">
                      Tên đăng nhập<span class="text-error-500">*</span>
                    </label>
                    <input id="username" v-model="username" class="dark:bg-dark-900 h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800" name="username"
                           placeholder="Nhập tên đăng nhập"
                           type="text"/>
                  </div>

                  <div>
                    <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400"
                           for="email">
                      Email<span class="text-error-500">*</span>
                    </label>
                    <input id="email" v-model="email" class="dark:bg-dark-900 h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800" name="email"
                           placeholder="Nhập email"
                           type="email"/>
                  </div>

                  <div>
                    <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400"
                           for="phone">
                      Số điện thoại<span class="text-error-500">*</span>
                    </label>
                    <input id="phone" v-model="phone" class="dark:bg-dark-900 h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800" name="phone"
                           placeholder="Nhập số điện thoại"
                           type="tel"/>
                  </div>

                  <div>
                    <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400"
                           for="password">
                      Mật khẩu<span class="text-error-500">*</span>
                    </label>
                    <input id="password" v-model="password" class="dark:bg-dark-900 h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800" name="password"
                           placeholder="Nhập mật khẩu"
                           type="password"/>
                  </div>

                  <div>
                    <button class="flex items-center justify-center w-full px-4 py-3 text-sm font-medium text-white transition rounded-lg bg-brand-500 shadow-theme-xs hover:bg-brand-600"
                            type="submit">
                      Đăng ký
                    </button>
                  </div>
                </div>
              </form>

              <div class="mt-5 text-center sm:text-start">
                <router-link class="text-sm text-brand-500 hover:text-brand-600 dark:text-brand-400"
                             to="/dang-nhap">
                  Đã có tài khoản? Đăng nhập
                </router-link>
              </div>
            </div>
          </div>
        </div>
        <AboutDashBoard/>
      </div>
    </div>
  </FullScreenLayout>
</template>

<script lang="ts" setup>
import {ref, computed} from 'vue'
import {useStore} from 'vuex'
import FullScreenLayout from '@/components/layout/FullScreenLayout.vue'
import AboutDashBoard from './components/AboutDashBoard.vue'
import * as types from '@/store/types'

const store = useStore()
const name = ref('')
const username = ref('')
const email = ref('')
const phone = ref('')
const password = ref('')

const authError = computed(() => store.getters['auth/' + types.GET_AUTH_ERROR])

const registerAction = (payload: {
  name: string;
  username: string;
  email: string;
  phone: string;
  password: string
}) => {
  return store.dispatch('auth/' + types.REGISTER, payload)
}

const handleSubmit = async () => {
  store.commit('auth/' + types.SET_AUTH_ERROR, null)
  if (!name.value || !username.value || !email.value || !phone.value || !password.value) {
    store.commit('auth/' + types.SET_AUTH_ERROR, 'Vui lòng nhập đầy đủ thông tin.')
    return
  }
  try {
    await registerAction({
      name: name.value,
      username: username.value,
      email: email.value,
      phone: phone.value,
      password: password.value
    })
  } catch (error) {
    console.error('Register form submission failed:', error)
  }
}
</script>
