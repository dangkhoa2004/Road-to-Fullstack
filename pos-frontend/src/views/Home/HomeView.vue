<template>
  <AnimationBannerHome />
  <GalleryFillter />
  <CTACard />
  <GooglePixelMockUp />
</template>

<script>
import CTACard from "./components/CTACard.vue"
import AnimationBannerHome from "./components/AnimationBannerHome.vue"
import GooglePixelMockUp from "./components/GooglePixelMockUp.vue"
import GalleryFillter from "./components/GalleryFillter.vue"

export default {
  name: 'AppFooter',
  components: {
    CTACard,
    AnimationBannerHome,
    GooglePixelMockUp,
    GalleryFillter
  },
  data() {
    return {
      currentYear: new Date().getFullYear(),
    };
  },
};
</script>