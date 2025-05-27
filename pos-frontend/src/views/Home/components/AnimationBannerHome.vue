<template>
  <div id="controls-carousel" class="relative w-full overflow-hidden group" @mouseenter="handleMouseEnter"
       @mouseleave="handleMouseLeave">
    <div class="relative h-56 md:h-96 rounded-lg">
      <div v-for="(imagePath, index) in imagePaths" :key="index"
           :class="{
                    'translate-x-0 opacity-100': index === currentIndex,
                    'translate-x-full opacity-0': index === (currentIndex + 1) % imagePaths.length,
                    '-translate-x-full opacity-0': index === (currentIndex - 1 + imagePaths.length) % imagePaths.length,
                }" class="absolute inset-0 transition-all duration-700 ease-in-out transform">
        <img :alt="'Image ' + (index + 1)" :src="imagePath"
             class="absolute block w-full h-full object-cover top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2">
      </div>
    </div>
    <button class="absolute top-0 start-0 z-30 flex items-center justify-center h-full px-4 cursor-pointer focus:outline-none opacity-0 group-hover:opacity-100 transition-opacity duration-300" type="button"
            @click="prevSlide">
            <span
                class="inline-flex items-center justify-center w-10 h-10 rounded-full bg-black/30 dark:bg-white hover:bg-black dark:hover:bg-gray-200 focus:ring-4 focus:ring-black dark:focus:ring-gray-800/70">
                <svg aria-hidden="true" class="w-4 h-4 text-white dark:text-black rtl:rotate-180"
                     fill="none" viewBox="0 0 6 10" xmlns="http://www.w3.org/2000/svg">
                    <path d="M5 1 1 5l4 4" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                          stroke-width="2"/>
                </svg>
                <span class="sr-only">Previous</span>
            </span>
    </button>
    <button class="absolute top-0 end-0 z-30 flex items-center justify-center h-full px-4 cursor-pointer focus:outline-none opacity-0 group-hover:opacity-100 transition-opacity duration-300" type="button"
            @click="nextSlide">
            <span
                class="inline-flex items-center justify-center w-10 h-10 rounded-full bg-black/30 dark:bg-white hover:bg-black dark:hover:bg-gray-200 focus:ring-4 focus:ring-black dark:focus:ring-gray-800/70">
                <svg aria-hidden="true" class="w-4 h-4 text-white dark:text-black rtl:rotate-180"
                     fill="none" viewBox="0 0 6 10" xmlns="http://www.w3.org/2000/svg">
                    <path d="m1 9 4-4-4-4" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                          stroke-width="2"/>
                </svg>
                <span class="sr-only">Next</span>
            </span>
    </button>

    <div class="absolute bottom-4 left-1/2 -translate-x-1/2 z-30 flex space-x-2">
      <button v-for="(imagePath, index) in imagePaths" :key="index" :class="{ '!bg-white dark:!bg-gray-800': index === currentIndex }"
              class="cursor-pointer w-3 h-3 rounded-full bg-white/50 dark:bg-white"
              @click="currentIndex = index"></button>
    </div>
  </div>
</template>

<script setup>
import { onMounted, onUnmounted, ref } from 'vue';

const imagePaths = [
  new URL('@/assets/images/bg-main-1.jpg', import.meta.url).href,
  new URL('@/assets/images/bg-main-2.jpg', import.meta.url).href,
  new URL('@/assets/images/bg-main-3.jpg', import.meta.url).href,
  new URL('@/assets/images/bg-main-4.jpg', import.meta.url).href,
];

const currentIndex = ref(0);
let intervalId = null;

const nextSlide = () => {
  currentIndex.value = (currentIndex.value + 1) % imagePaths.length;
};

const prevSlide = () => {
  currentIndex.value = (currentIndex.value - 1 + imagePaths.length) % imagePaths.length;
};

const startAutoplay = () => {
  intervalId = setInterval(nextSlide, 3000);
};

const stopAutoplay = () => {
  if (intervalId) {
    clearInterval(intervalId);
    intervalId = null;
  }
};

onMounted(() => {
  startAutoplay();
});

onUnmounted(() => {
  stopAutoplay();
});

const handleMouseEnter = () => {
  stopAutoplay();
};

const handleMouseLeave = () => {
  startAutoplay();
};
</script>