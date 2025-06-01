<template>
    <div class="flex items-center justify-center">
        <div class="relative bg-gray-100 dark:bg-gray-800 rounded-full p-1 flex w-full">
            <!-- Tab: Buy -->
            <button @click="activeTab = 'Buy'" :class="[
                'flex-1 text-center text-sm font-medium py-2 rounded-full transition-all',
                activeTab === 'Buy'
                    ? 'bg-white dark:bg-gray-700 shadow text-black dark:text-white'
                    : 'text-gray-500 dark:text-gray-400 hover:text-black dark:hover:text-white'
            ]">
                Mang về
            </button>

            <!-- Tab: Reservation -->
            <button @click="activeTab = 'Reservation'" :class="[
                'flex-1 text-center text-sm font-medium py-2 rounded-full transition-all',
                activeTab === 'Reservation'
                    ? 'bg-white dark:bg-gray-700 shadow text-black dark:text-white'
                    : 'text-gray-500 dark:text-gray-400 hover:text-black dark:hover:text-white'
            ]">
                Đặt bàn
            </button>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const activeTab = ref<'Buy' | 'Reservation'>('Buy')
</script>
