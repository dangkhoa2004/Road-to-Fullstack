<template>
  <div ref="dropdownRef" class="relative">
    <button class="flex items-center text-gray-700 dark:text-gray-400"
            @click.prevent="toggleDropdown">
      <span class="mr-3 overflow-hidden rounded-full h-11 w-11">
        <img alt="User" src="/images/user/owner.jpg"/>
      </span>

      <span class="block mr-1 font-medium text-theme-sm">{{ user.name }}</span>

      <ChevronDownIcon :class="{ 'rotate-180': dropdownOpen }"/>
    </button>

    <div v-if="dropdownOpen"
         class="absolute right-0 mt-[17px] flex w-[260px] flex-col rounded-2xl border border-gray-200 bg-white p-3 shadow-theme-lg dark:border-gray-800 dark:bg-gray-dark">
      <div>
        <span class="block font-medium text-gray-700 text-theme-sm dark:text-gray-400">
          {{ user.name }}
        </span>
        <span class="mt-0.5 block text-theme-xs text-gray-500 dark:text-gray-400">
          {{ user.email }}
        </span>
      </div>

      <ul class="flex flex-col gap-1 pt-4 pb-3 border-b border-gray-200 dark:border-gray-800">
        <li v-for="item in menuItems" :key="item.href">
          <router-link :to="item.href"
                       class="flex items-center gap-3 px-3 py-2 font-medium text-gray-700 rounded-lg group text-theme-sm hover:bg-gray-100 hover:text-gray-700 dark:text-gray-400 dark:hover:bg-white/5 dark:hover:text-gray-300">
            <component :is="item.icon"
                       class="text-gray-500 group-hover:text-gray-700 dark:group-hover:text-gray-300"/>
            {{ item.text }}
          </router-link>
        </li>
      </ul>
      <button class="flex items-center gap-3 px-3 py-2 mt-3 font-medium text-gray-700 rounded-lg group text-theme-sm hover:bg-gray-100 hover:text-gray-700 dark:text-gray-400 dark:hover:bg-white/5 dark:hover:text-gray-300"
              @click="signOut">
        <LogoutIcon class="text-gray-500 group-hover:text-gray-700 dark:group-hover:text-gray-300"/>
        Đăng xuất
      </button>

    </div>
  </div>
</template>
<script lang="ts" setup>
import {computed, ref, onMounted, onUnmounted} from 'vue'
import {useStore} from 'vuex'
import {useRouter} from 'vue-router'
import {UserCircleIcon, ChevronDownIcon, LogoutIcon, SettingsIcon, InfoCircleIcon} from '@/icons'
import {LOGOUT} from '@/store/types'

const store = useStore()
const router = useRouter()

const dropdownOpen = ref(false)
const dropdownRef = ref<HTMLElement | null>(null)

const toggleDropdown = () => {
  dropdownOpen.value = !dropdownOpen.value
}
const closeDropdown = () => {
  dropdownOpen.value = false
}

const user = computed(() => store.getters['auth/getAuthUser'] || {
  name: 'Guest',
  email: 'guest@example.com',
  avatar: '/images/user/owner.jpg',
})

const menuItems = [
  {href: '/profile', icon: UserCircleIcon, text: 'Sửa thông tin'},
  {href: '/chat', icon: SettingsIcon, text: 'Cài đặt tài khoản'},
  {href: '/profile', icon: InfoCircleIcon, text: 'Hỗ trợ'},
]

const signOut = () => {
  store.dispatch(`auth/${LOGOUT}`)
  closeDropdown()
  router.push('/dang-nhap')
}

const handleClickOutside = (event: MouseEvent) => {
  if (dropdownRef.value && !dropdownRef.value.contains(event.target as Node)) {
    closeDropdown()
  }
}

onMounted(() => {
  document.addEventListener('click', handleClickOutside)
})

onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside)
})
</script>
