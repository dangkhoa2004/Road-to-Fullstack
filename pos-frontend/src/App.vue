<template>
  <div id="app" class="font-sans antialiased text-gray-700 flex flex-col min-h-screen">

    <AppNavbar />

    <main class="flex-grow container mx-auto p-4">
      <router-view />
    </main>

    <AppFooter />
  </div>
</template>

<script>
import AppNavbar from './components/layout/AppNavbar.vue';
import AppFooter from './components/layout/AppFooter.vue';

export default {
  name: 'App',
  components: {
    AppNavbar,
    AppFooter,
  },
};
</script>

<style>
/* CSS toàn cục có thể ở đây */
/* Ví dụ: để đảm bảo footer luôn ở cuối trang */
#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}
main {
  flex-grow: 1; /* Đảm bảo nội dung chính chiếm không gian còn lại */
}
</style>