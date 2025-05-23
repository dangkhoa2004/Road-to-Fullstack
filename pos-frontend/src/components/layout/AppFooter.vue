<template>
  <div class=" flex flex-col bg-gray-100">
    <!-- <CTACard /> -->
    <footer class="bg-gray-800 text-white p-6 text-center">
      <div class="container mx-auto">
        <p>&copy; {{ currentYear }} Ứng dụng của tôi. Mọi quyền được bảo lưu.</p>
        <p class="text-sm mt-1">Được phát triển bởi LẬP TRÌNH</p>
      </div>
    </footer>
  </div>
</template>
<script>
import CTACard from "@/components/specific/CTACard.vue"

export default {
  name: 'AppFooter',
  components: {
    CTACard
  },
  data() {
    return {
      currentYear: new Date().getFullYear(),
    };
  },
};
</script>