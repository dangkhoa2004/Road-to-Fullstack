<template>
  <header class="bg-white border-gray-200 dark:bg-gray-900 dark:border-gray-700">
    <div class="flex flex-wrap items-center justify-between mx-auto p-4">
      <router-link class="flex items-center space-x-3 rtl:space-x-reverse" to="/">
        <img alt="Logo" class="h-8" src="https://flowbite.com/docs/images/logo.svg"/>
        <span class="self-center text-2xl font-semibold whitespace-nowrap dark:text-white">Ứng dụng của tôi</span>
      </router-link>
      <button :aria-expanded="isMobileMenuOpen ? 'true' : 'false'" aria-controls="navbar-menu"
              class="inline-flex items-center p-2 w-10 h-10 justify-center text-sm text-gray-500 rounded-lg md:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 dark:text-gray-400 dark:hover:bg-gray-700 dark:focus:ring-gray-600"
              type="button" @click="toggleMobileMenu">
        <span class="sr-only">Mở menu chính</span>
        <svg aria-hidden="true" class="w-5 h-5" fill="none" viewBox="0 0 17 14" xmlns="http://www.w3.org/2000/svg">
          <path d="M1 1h15M1 7h15M1 13h15" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                stroke-width="2"/>
        </svg>
      </button>

      <div id="navbar-menu" :class="{ 'hidden': !isMobileMenuOpen }" class="w-full md:block md:w-auto">
        <ul
            class="flex flex-col font-medium p-4 md:p-0 mt-4 border border-gray-100 rounded-lg bg-gray-50 md:space-x-8 rtl:space-x-reverse md:flex-row md:mt-0 md:border-0 md:bg-white dark:bg-gray-800 md:dark:bg-gray-900 dark:border-gray-700">
          <li>
            <router-link active-class="text-blue-700 dark:text-blue-500 font-bold" aria-current="page"
                         class="block py-2 px-3 text-gray-900 rounded-sm hover:bg-gray-100 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-white md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent"
                         to="/" @click="closeMobileMenu">Trang chủ
            </router-link>
          </li>
          <li v-if="isLoggedIn">
            <router-link active-class="text-blue-700 dark:text-blue-500 font-bold" class="block py-2 px-3 text-gray-900 rounded-sm hover:bg-gray-100 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-white md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent"
                         to="/products"
                         @click="closeMobileMenu">Sản phẩm
            </router-link>
          </li>
          <li v-if="isLoggedIn">
            <router-link active-class="text-blue-700 dark:text-blue-500 font-bold" class="block py-2 px-3 text-gray-900 rounded-sm hover:bg-gray-100 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-white md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent"
                         to=""
                         @click="closeMobileMenu">Bảng giá
            </router-link>
          </li>
          <li v-if="isLoggedIn">
            <router-link active-class="text-blue-700 dark:text-blue-500 font-bold" class="block py-2 px-3 text-gray-900 rounded-sm hover:bg-gray-100 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-white md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent"
                         to=""
                         @click="closeMobileMenu">Liên hệ
            </router-link>
          </li>
          <li v-if="!isLoggedIn">
            <router-link active-class="text-blue-700 dark:text-blue-500 font-bold" class="block py-2 px-3 text-gray-900 rounded-sm hover:bg-gray-100 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-white md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent"
                         to="/login"
                         @click="closeMobileMenu">Đăng nhập
            </router-link>
          </li>
          <li v-if="isLoggedIn" class="relative">
            <button id="user-dropdown-button" class="flex items-center justify-between w-full py-2 px-3 text-gray-900 rounded-sm hover:bg-gray-100 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 md:w-auto dark:text-white md:dark:hover:text-blue-500 dark:focus:text-white dark:border-gray-700 dark:hover:bg-gray-700 md:dark:hover:bg-transparent"
                    @click="toggleUserDropdown">
              Tài khoản
              <svg aria-hidden="true" class="w-2.5 h-2.5 ms-2.5" fill="none" viewBox="0 0 10 6"
                   xmlns="http://www.w3.org/2000/svg">
                <path d="m1 1 4 4 4-4" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                      stroke-width="2"/>
              </svg>
            </button>
            <UserDropdown :is-open="isUserDropdownOpen" @logout="handleLogoutAndClose"
                          @close-all-menus="closeAllMenus"/>
          </li>
        </ul>
      </div>
    </div>
  </header>
  <div class="p-4">
    <BreadCrumb/>
  </div>
</template>

<script>
import {computed, onMounted, onUnmounted, ref} from 'vue';
import {useStore} from 'vuex';
import * as types from '@/store/types';
import BreadCrumb from '@/components/common/BreadCrumb.vue';
import UserDropdown from '@/components/common/UserDropdown.vue';

export default {
  name: 'AppNavbar',
  components: {
    BreadCrumb,
    UserDropdown,
  },
  setup() {
    const store = useStore();
    const isMobileMenuOpen = ref(false);
    const isUserDropdownOpen = ref(false);
    const isLoggedIn = computed(() => {
      return store.getters['auth/' + types.IS_LOGGED_IN];
    });
    const toggleMobileMenu = () => {
      isMobileMenuOpen.value = !isMobileMenuOpen.value;
      if (isMobileMenuOpen.value) {
        isUserDropdownOpen.value = false;
      }
    };
    const closeMobileMenu = () => {
      isMobileMenuOpen.value = false;
    };
    const toggleUserDropdown = () => {
      isUserDropdownOpen.value = !isUserDropdownOpen.value;
      if (isUserDropdownOpen.value) {
        isMobileMenuOpen.value = false;
      }
    };
    const closeUserDropdown = () => {
      isUserDropdownOpen.value = false;
    };
    const closeAllMenus = () => {
      closeMobileMenu();
      closeUserDropdown();
    };
    const handleLogout = () => {
      store.dispatch('auth/' + types.LOGOUT);
    };
    const handleLogoutAndClose = () => {
      handleLogout();
      closeAllMenus();
    };
    const handleClickOutside = (event) => {
      const userDropdownButton = document.getElementById('user-dropdown-button');
      const userDropdownMenu = document.getElementById('user-dropdown-menu');
      if (isUserDropdownOpen.value && userDropdownMenu && userDropdownButton &&
          !userDropdownMenu.contains(event.target) && !userDropdownButton.contains(event.target)) {
        closeUserDropdown();
      }
      const mobileMenuButton = document.querySelector('[aria-controls="navbar-menu"]');
      const mobileMenu = document.getElementById('navbar-menu');
      if (isMobileMenuOpen.value && mobileMenu && mobileMenuButton &&
          !mobileMenu.contains(event.target) && !mobileMenuButton.contains(event.target)) {
        closeMobileMenu();
      }
    };
    onMounted(() => {
      document.addEventListener('click', handleClickOutside);
    });
    onUnmounted(() => {
      document.removeEventListener('click', handleClickOutside);
    });
    return {
      isLoggedIn,
      isMobileMenuOpen,
      isUserDropdownOpen,
      toggleMobileMenu,
      closeMobileMenu,
      toggleUserDropdown,
      closeUserDropdown,
      closeAllMenus,
      handleLogoutAndClose,
    };
  }
};
</script>