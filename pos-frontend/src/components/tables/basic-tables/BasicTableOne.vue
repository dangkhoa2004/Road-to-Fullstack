<template>
  <div class="overflow-hidden rounded-xl border border-gray-200 bg-white dark:border-gray-800 dark:bg-white/[0.03]">
    <div class="max-w-full overflow-x-auto custom-scrollbar">
      <table class="min-w-full">
        <thead>
          <tr class="border-b border-gray-200 dark:border-gray-700">
            <th v-for="(column, index) in columns" :key="index" class="px-5 py-3 text-left sm:px-6 dark:text-white"
              :class="column.headerClass">
              {{ column.label }}
            </th>
          </tr>
        </thead>
        <tbody class="divide-y divide-gray-200 dark:divide-gray-700 dark:text-white">
          <tr v-for="(row, rowIndex) in paginatedRows" :key="rowIndex"
            class="border-t border-gray-100 dark:border-gray-800">
            <td v-for="(column, colIndex) in columns" :key="colIndex" class="px-5 py-4 sm:px-6">
              <slot :name="`cell-${column.key}`" :row="row" :column="column">
                <span class="text-gray-700 dark:text-gray-300">{{ row[column.key] }}</span>
              </slot>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Nút điều hướng phân trang đẹp hơn và có icon -->
    <div v-if="totalPages > 1" class="flex justify-end mt-4 space-x-2 p-2">
      <button @click="prevPage" :disabled="currentPage === 1"
        class="flex items-center px-3 py-1 border rounded-xl shadow-sm text-gray-700 dark:text-gray-300 hover:bg-gray-100 dark:hover:bg-gray-700 disabled:opacity-50 disabled:cursor-not-allowed transition">
        <!-- Icon mũi tên trái -->
        <svg class="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
        </svg>
        Trước
      </button>

      <span class="flex items-center px-2 py-1 text-gray-700 dark:text-gray-300">
        Trang {{ currentPage }} / {{ totalPages }}
      </span>

      <button @click="nextPage" :disabled="currentPage === totalPages"
        class="flex items-center px-3 py-1 border rounded-xl shadow-sm text-gray-700 dark:text-gray-300 hover:bg-gray-100 dark:hover:bg-gray-700 disabled:opacity-50 disabled:cursor-not-allowed transition">
        Sau
        <!-- Icon mũi tên phải -->
        <svg class="w-4 h-4 ml-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
        </svg>
      </button>
    </div>
  </div>
</template>

<script setup>
import { computed, ref } from "vue";

const props = defineProps({
  columns: {
    type: Array,
    required: true,
  },
  rows: {
    type: Array,
    required: true,
  },
});

// Phân trang
const rowsPerPage = 6;
const currentPage = ref(1);

const totalPages = computed(() => {
  return Math.ceil(props.rows.length / rowsPerPage);
});

const paginatedRows = computed(() => {
  const start = (currentPage.value - 1) * rowsPerPage;
  const end = start + rowsPerPage;
  return props.rows.slice(start, end);
});

function prevPage() {
  if (currentPage.value > 1) {
    currentPage.value--;
  }
}

function nextPage() {
  if (currentPage.value < totalPages.value) {
    currentPage.value++;
  }
}
</script>
