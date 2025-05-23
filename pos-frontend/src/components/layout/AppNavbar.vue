<template>
  <nav class="bg-white border-gray-200 dark:bg-gray-900 dark:border-gray-700">
    <div class="max-w-screen-xl flex flex-wrap items-center justify-between mx-auto p-4">
      <router-link to="/" class="flex items-center space-x-3 rtl:space-x-reverse">
        <img src="https://flowbite.com/docs/images/logo.svg" class="h-8" alt="Logo" />
        <span class="self-center text-2xl font-semibold whitespace-nowrap dark:text-white">Ứng dụng của tôi</span>
      </router-link>

      <button @click="toggleMobileMenu" type="button"
        class="inline-flex items-center p-2 w-10 h-10 justify-center text-sm text-gray-500 rounded-lg md:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 dark:text-gray-400 dark:hover:bg-gray-700 dark:focus:ring-gray-600"
        aria-controls="navbar-menu" :aria-expanded="isMobileMenuOpen ? 'true' : 'false'">
        <span class="sr-only">Open main menu</span>
        <svg class="w-5 h-5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 17 14">
          <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
            d="M1 1h15M1 7h15M1 13h15" />
        </svg>
      </button>

      <div :class="{ 'hidden': !isMobileMenuOpen }" class="w-full md:block md:w-auto" id="navbar-menu">
        <ul
          class="flex flex-col font-medium p-4 md:p-0 mt-4 border border-gray-100 rounded-lg bg-gray-50 md:space-x-8 rtl:space-x-reverse md:flex-row md:mt-0 md:border-0 md:bg-white dark:bg-gray-800 md:dark:bg-gray-900 dark:border-gray-700">
          <li>
            <router-link to="/" @click="closeMobileMenu"
              class="block py-2 px-3 text-gray-900 rounded-sm hover:bg-gray-100 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-white md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent"
              active-class="text-blue-700 dark:text-blue-500 font-bold" aria-current="page">Trang chủ</router-link>
          </li>



          <li v-if="isLoggedIn">
            <router-link to="/products" @click="closeMobileMenu"
              class="block py-2 px-3 text-gray-900 rounded-sm hover:bg-gray-100 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-white md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent"
              active-class="text-blue-700 dark:text-blue-500 font-bold">Sản phẩm</router-link>
          </li>
          <li v-if="isLoggedIn">
            <router-link to="" @click="closeMobileMenu"
              class="block py-2 px-3 text-gray-900 rounded-sm hover:bg-gray-100 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-white md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent"
              active-class="text-blue-700 dark:text-blue-500 font-bold">Bảng giá</router-link>
          </li>
          <li v-if="isLoggedIn">
            <router-link to="" @click="closeMobileMenu"
              class="block py-2 px-3 text-gray-900 rounded-sm hover:bg-gray-100 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-white md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent"
              active-class="text-blue-700 dark:text-blue-500 font-bold">Liên hệ</router-link>
          </li>
          <li v-if="!isLoggedIn">
            <router-link to="" @click="closeMobileMenu"
              class="block py-2 px-3 text-gray-900 rounded-sm hover:bg-gray-100 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-white md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent"
              active-class="text-blue-700 dark:text-blue-500 font-bold">Đăng nhập</router-link>
          </li>

          <li v-if="isLoggedIn" class="relative">
            <button @click="toggleUserDropdown"
              class="flex items-center justify-between w-full py-2 px-3 text-gray-900 rounded-sm hover:bg-gray-100 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 md:w-auto dark:text-white md:dark:hover:text-blue-500 dark:focus:text-white dark:border-gray-700 dark:hover:bg-gray-700 md:dark:hover:bg-transparent">
              Tài khoản
              <svg class="w-2.5 h-2.5 ms-2.5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                viewBox="0 0 10 6">
                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="m1 1 4 4 4-4" />
              </svg>
            </button>

            <div :class="{ 'hidden': !isUserDropdownOpen }"
              class="absolute z-10 font-normal bg-white divide-y divide-gray-100 rounded-lg shadow-sm w-44 dark:bg-gray-700 dark:divide-gray-600 md:origin-top-right md:right-0 md:mt-2">
              <ul class="py-2 text-sm text-gray-700 dark:text-gray-400">
                <li>
                  <router-link to="" @click="closeAllMenus"
                    class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                    active-class="text-blue-700 dark:text-blue-500 font-bold">Dashboard</router-link>
                </li>
                <li>
                  <router-link to="" @click="closeAllMenus"
                    class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                    active-class="text-blue-700 dark:text-blue-500 font-bold">Cài đặt hồ sơ</router-link>
                </li>
                <li>
                  <a href="#" @click.prevent="handleLogoutAndClose"
                    class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white">Đăng xuất</a>
                </li>
              </ul>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  <BreadCrumb />
</template>

<script>
import { ref, computed, onMounted, onUnmounted } from 'vue';
import { useStore } from 'vuex';
import * as types from '@/store/types';
import BreadCrumb from '@/components/common/BreadCrumb.vue';
export default {
  name: 'AppNavbar',
  components: {
    BreadCrumb
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
      if (isUserDropdownOpen.value) {
        const dropdown = document.getElementById('dropdownNavbar');
        const button = document.getElementById('dropdownNavbarLink');
        if (dropdown && !dropdown.contains(event.target) && button && !button.contains(event.target)) {
          closeUserDropdown();
        }
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