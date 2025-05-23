<template>
  <AnimationBannerHome />
  <GalleryFillter />
  <CTACard />
  <GooglePixelMockUp />
</template>

<script>
import CTACard from "@/components/specific/CTACard.vue"
import AnimationBannerHome from "@/components/specific/AnimationBannerHome.vue"
import GooglePixelMockUp from "@/components/specific/GooglePixelMockUp.vue"
import GalleryFillter from "@/components/specific/GalleryFillter.vue"

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