<template>
  <div id="app" class="font-sans antialiased text-center text-gray-700">
    <nav class="p-4 bg-gray-100 border-b border-gray-200">
      <router-link to="/" class="font-bold text-gray-700 no-underline mx-4 hover:underline">Trang chủ</router-link> |
      <router-link v-if="!isLoggedIn" to="/login" class="font-bold text-gray-700 no-underline mx-4 hover:underline">Đăng
        nhập</router-link>
      <span v-if="isLoggedIn" class="text-gray-500"> | <router-link to="/dashboard"
          class="font-bold text-gray-700 no-underline mx-4 hover:underline">Dashboard</router-link></span>
      <span v-if="isLoggedIn" class="text-gray-500"> | <a @click.prevent="logout" href="#"
          class="font-bold text-red-600 no-underline mx-4 cursor-pointer hover:underline">Đăng xuất</a></span>
    </nav>
    <router-view />
  </div>
</template>

<script>
import { computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';

export default {
  name: 'App',
  setup() {
    const router = useRouter();
    const route = useRoute(); // route is not used in the template, consider removing if not needed.

    const isLoggedIn = computed(() => {
      // For a more robust solution, consider moving JWT handling to a dedicated auth service
      // and checking token validity (e.g., expiry) instead of just existence.
      return localStorage.getItem('jwtToken') !== null;
    });

    const logout = () => {
      localStorage.removeItem('jwtToken');
      localStorage.removeItem('user'); // Assuming 'user' is also stored in localStorage
      router.push('/login');
    };

    return {
      isLoggedIn,
      logout,
      // If 'route' is not used in the template, you can remove it from here.
      // route,
    };
  }
};
</script>