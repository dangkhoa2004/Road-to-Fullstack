<template>
    <div class="relative items-center hidden w-full h-full lg:w-1/2 bg-brand-950 dark:bg-white/5 lg:grid">
        <div class="flex items-center justify-center z-1">
            <common-grid-shape />
            <div class="flex flex-col items-center max-w-xs">
                <router-link to="/" class="block mb-4">
                    <img width="{231}" height="{48}" src="/images/logo/auth-logo.svg" alt="Logo" />
                </router-link>
                <p class="text-center text-gray-400 dark:text-white/60">
                    Free and Open-Source Tailwind CSS Admin Dashboard Template
                </p>
            </div>
        </div>
    </div>
</template>
<script lang="ts">
import CommonGridShape from '@/components/common/CommonGridShape.vue';

export default {
  components: {
    CommonGridShape,
  },
};
</script>
