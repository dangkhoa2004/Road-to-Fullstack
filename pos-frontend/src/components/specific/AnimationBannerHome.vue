<template>
    <div id="controls-carousel" class="relative w-full overflow-hidden group" @mouseenter="handleMouseEnter"
        @mouseleave="handleMouseLeave">
        <div class="relative h-56 md:h-96 rounded-lg">
            <div v-for="(image, index) in images" :key="image.id"
                class="absolute inset-0 transition-all duration-700 ease-in-out transform" :class="{
                    'translate-x-0 opacity-100': index === currentIndex,
                    'translate-x-full opacity-0': index === (currentIndex + 1) % images.length,
                    '-translate-x-full opacity-0': index === (currentIndex - 1 + images.length) % images.length,
                }">
                <img :src="image.src" :alt="image.alt"
                    class="absolute block w-full h-full object-cover top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2">
            </div>
        </div>

        <button type="button" @click="prevSlide"
            class="absolute top-0 start-0 z-30 flex items-center justify-center h-full px-4 cursor-pointer focus:outline-none opacity-0 group-hover:opacity-100 transition-opacity duration-300">
            <span
                class="inline-flex items-center justify-center w-10 h-10 rounded-full bg-white/30 dark:bg-gray-800/30 hover:bg-white/50 dark:hover:bg-gray-800/60 focus:ring-4 focus:ring-white dark:focus:ring-gray-800/70">
                <svg class="w-4 h-4 text-white dark:text-gray-800 rtl:rotate-180" aria-hidden="true"
                    xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 6 10">
                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                        d="M5 1 1 5l4 4" />
                </svg>
                <span class="sr-only">Previous</span>
            </span>
        </button>
        <button type="button" @click="nextSlide"
            class="absolute top-0 end-0 z-30 flex items-center justify-center h-full px-4 cursor-pointer focus:outline-none opacity-0 group-hover:opacity-100 transition-opacity duration-300">
            <span
                class="inline-flex items-center justify-center w-10 h-10 rounded-full bg-white/30 dark:bg-gray-800/30 hover:bg-white/50 dark:hover:bg-gray-800/60 focus:ring-4 focus:ring-white dark:focus:ring-gray-800/70">
                <svg class="w-4 h-4 text-white dark:text-gray-800 rtl:rotate-180" aria-hidden="true"
                    xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 6 10">
                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                        d="m1 9 4-4-4-4" />
                </svg>
                <span class="sr-only">Next</span>
            </span>
        </button>

        <div class="absolute bottom-4 left-1/2 -translate-x-1/2 z-30 flex space-x-2">
            <button v-for="(image, index) in images" :key="image.id" @click="currentIndex = index"
                class="cursor-pointer w-3 h-3 rounded-full bg-white/50 dark:bg-gray-800/50"
                :class="{ '!bg-white dark:!bg-gray-800': index === currentIndex }"></button>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
const images = ref([
    { id: 1, src: ('./src/assets/images/1.jpg'), alt: 'Image 1' },
    { id: 2, src: ('./src/assets/images/2.jpg'), alt: 'Image 2' },
    { id: 3, src: ('./src/assets/images/3.jpg'), alt: 'Image 3' },
    { id: 4, src: ('./src/assets/images/4.jpg'), alt: 'Image 4' },
]);

const currentIndex = ref(0);
let intervalId = null;

const nextSlide = () => {
    currentIndex.value = (currentIndex.value + 1) % images.value.length;
};

const prevSlide = () => {
    currentIndex.value = (currentIndex.value - 1 + images.value.length) % images.value.length;
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