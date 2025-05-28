<template>
  <div>
    <AnimationBannerHome />
    <GalleryFillter />
    <CTACard />
    <GooglePixelMockUp />
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