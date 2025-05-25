<template>
  <div>
    <AnimationBannerHome />
    <GalleryFillter v-animate-on-scroll class="animate-item" />
    <CTACard v-animate-on-scroll class="animate-item" />
    <GooglePixelMockUp v-animate-on-scroll class="animate-item" />
  </div>
</template>

<script>
import CTACard from "./components/CTACard.vue"
import AnimationBannerHome from "./components/AnimationBannerHome.vue"
import GooglePixelMockUp from "./components/GooglePixelMockUp.vue"
import GalleryFillter from "./components/GalleryFillter.vue"

export default {
  name: 'HomeView',
  components: {
    CTACard,
    AnimationBannerHome,
    GooglePixelMockUp,
    GalleryFillter
  }
};
</script>
<style scoped>
.animate-item {
  opacity: 0;
  transform: translateY(20px);
  transition: opacity 1.5s ease-out, transform 1.5s ease-out;
}

.fade-in {
  opacity: 1;
  transform: translateY(0);
}
</style>