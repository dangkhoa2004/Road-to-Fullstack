<template>
  <CustomLoading v-if="loadingStore.isLoading" />
  <div id="app" class="antialiased text-gray-700 flex flex-col min-h-screen">

    <AppNavbar />

    <main class="flex-grow container mx-auto pl-4 pr-4">
      <router-view />
    </main>

    <AppFooter />
  </div>
</template>

<script>
import CustomLoading from "@/components/specific/CustomLoading.vue";
import { useLoadingStore } from "@/utils/helpers";
import AppNavbar from './components/layout/AppNavbar.vue';
import AppFooter from './components/layout/AppFooter.vue';

export default {
  name: 'App',
  components: {
    CustomLoading,
    AppNavbar,
    AppFooter,
  },
  data() {
    return {
      loadingStore: useLoadingStore(),
    };
  },
};
</script>

<style>
#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

main {
  flex-grow: 1;
}
</style>