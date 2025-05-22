<template>
  <div id="app">
    <nav>
      <router-link to="/">Trang chủ</router-link> |
      <router-link v-if="!isLoggedIn" to="/login">Đăng nhập</router-link>
      <span v-if="isLoggedIn"> | <router-link to="/dashboard">Dashboard</router-link></span>
      <span v-if="isLoggedIn"> | <a @click="logout" href="#">Đăng xuất</a></span>
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
    const route = useRoute();

    const isLoggedIn = computed(() => {
      return localStorage.getItem('jwtToken') !== null;
    });

    const logout = () => {
      localStorage.removeItem('jwtToken');
      localStorage.removeItem('user');
      router.push('/login');
    };

    return {
      isLoggedIn,
      logout,
      route // Export route if you need to use it in the template
    };
  }
};
</script>

<style>
/* Global styles for the app */
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

nav {
  padding: 30px;
  background-color: #f8f9fa;
  border-bottom: 1px solid #e0e0e0;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
  text-decoration: none;
  margin: 0 15px;
}

nav a.router-link-exact-active {
  color: #42b983;
}

nav a:hover {
  text-decoration: underline;
}

nav span {
  color: #777;
}

nav a[href="#"] { /* Style for logout link */
  cursor: pointer;
  color: #dc3545;
}
</style>