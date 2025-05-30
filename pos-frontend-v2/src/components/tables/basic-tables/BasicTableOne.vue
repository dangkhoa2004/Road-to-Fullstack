<template>
  <div
    class="overflow-hidden rounded-xl border border-gray-200 bg-white dark:border-gray-800 dark:bg-white/[0.03]"
  >
    <div class="max-w-full overflow-x-auto custom-scrollbar">
      <table class="min-w-full">
        <thead>
          <tr class="border-b border-gray-200 dark:border-gray-700">
            <th
              v-for="(column, index) in columns"
              :key="index"
              class="px-5 py-3 text-left sm:px-6"
              :class="column.headerClass"
            >
              {{ column.label }}
            </th>
          </tr>
        </thead>
        <tbody class="divide-y divide-gray-200 dark:divide-gray-700">
          <tr
            v-for="(row, rowIndex) in rows"
            :key="rowIndex"
            class="border-t border-gray-100 dark:border-gray-800"
          >
            <td
              v-for="(column, colIndex) in columns"
              :key="colIndex"
              class="px-5 py-4 sm:px-6"
            >
              <!-- Sử dụng slot động để tuỳ chỉnh nếu cần -->
              <slot
                :name="`cell-${column.key}`"
                :row="row"
                :column="column"
              >
                <!-- Mặc định: in ra giá trị -->
                <span class="text-gray-700 dark:text-gray-300">{{ row[column.key] }}</span>
              </slot>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  columns: {
    type: Array,
    required: true,
  },
  rows: {
    type: Array,
    required: true,
  },
})
</script>
