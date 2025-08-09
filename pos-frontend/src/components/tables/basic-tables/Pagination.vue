<template>
  <div v-if="totalPages > 1" class="flex justify-end mt-4 space-x-2 p-2">
    <button :disabled="currentPage === 1" class="border border-gray-400 dark:border-gray-700 w-16 inline-block px-2 py-1 rounded text-xs font-semibold cursor-pointer text-black dark:text-white hover:bg-gray-50 dark:hover:bg-gray-800 transition disabled:opacity-50 disabled:cursor-not-allowed"
            @click="$emit('prev')">
      Trước
    </button>
    <span class="flex items-center px-2 py-1 text-gray-700 dark:text-gray-300">
      Trang {{ currentPage }} / {{ totalPages }}
    </span>
    <button :disabled="currentPage === totalPages" class="border border-gray-400 dark:border-gray-700 w-16 inline-block px-2 py-1 rounded text-xs font-semibold cursor-pointer text-black dark:text-white hover:bg-gray-50 dark:hover:bg-gray-800 transition disabled:opacity-50 disabled:cursor-not-allowed"
            @click="$emit('next')">
      Sau
    </button>
  </div>
</template>

<script setup>
defineProps({
  currentPage: Number,
  totalPages: Number
});
</script>
