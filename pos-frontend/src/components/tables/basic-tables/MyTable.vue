<template>
    <div class="overflow-hidden bg-white dark:border-gray-800 dark:bg-white/[0.03]">
        <div class="max-h-[750px] overflow-y-auto custom-scrollbar">
            <table class="min-w-full table-auto text-sm text-gray-700 dark:text-gray-300">
                <thead class="sticky top-0 bg-white dark:bg-gray-800">
                    <tr class="border-b border-gray-200 dark:border-gray-700">
                        <th v-for="(column, index) in columns" :key="index"
                            class="px-4 py-3 text-left sm:px-6 whitespace-nowrap dark:text-white"
                            :class="column.headerClass">
                            {{ column.label }}
                        </th>
                        <th class="px-4 py-3 text-left sm:px-6 whitespace-nowrap dark:text-white">Thao tác</th>
                    </tr>
                </thead>
                <tbody class="divide-y divide-gray-200 dark:divide-gray-700">
                    <tr v-for="(row, rowIndex) in paginatedRows" :key="rowIndex"
                        class="border-t border-gray-100 dark:border-gray-800 hover:bg-gray-50 dark:hover:bg-gray-700/30 transition">
                        <td v-for="(column, colIndex) in columns" :key="colIndex"
                            class="px-4 py-3 sm:px-6 whitespace-nowrap">
                            <slot :name="`cell-${column.key}`" :row="row" :column="column">
                                <span class="text-gray-700 dark:text-gray-300">{{ row[column.key] }}</span>
                            </slot>
                        </td>
                        <td class="px-4 py-3 sm:px-6 whitespace-nowrap">
                            <div class="flex space-x-2">
                                <span @click="showModal(row)"
                                    class="inline-block px-2 py-1 rounded text-xs font-semibold cursor-pointer bg-green-100 text-black hover:bg-green-200 dark:bg-green-200/10 dark:text-green-400 transition">
                                    Xem / Sửa
                                </span>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <!-- Pagination -->
        <Pagination :current-page="currentPage" :total-pages="totalPages" @prev="prevPage" @next="nextPage" />

        <!-- Modal -->
        <EditModal v-if="popupDetail" :columns="columns" :row="selectedRow" :header-title="modalHeaderTitle"
            :header-description="modalHeaderDescription" @close="popupDetail = false" @save="saveProfile" />
    </div>
</template>
<script setup>
import { computed, ref } from "vue";
import Pagination from "./Pagination.vue";
import EditModal from "./EditModal.vue";

const props = defineProps({
    columns: Array,
    rows: Array,
    modalHeaderTitle: String,
    modalHeaderDescription: String
});

const rowsPerPage = 6;
const currentPage = ref(1);
const popupDetail = ref(false);
const selectedRow = ref(null);

const totalPages = computed(() => Math.ceil(props.rows.length / rowsPerPage));
const paginatedRows = computed(() => {
    const start = (currentPage.value - 1) * rowsPerPage;
    const end = start + rowsPerPage;
    return props.rows.slice(start, end);
});

function prevPage() {
    if (currentPage.value > 1) currentPage.value--;
}
function nextPage() {
    if (currentPage.value < totalPages.value) currentPage.value++;
}

function showModal(row) {
    selectedRow.value = JSON.parse(JSON.stringify(row));
    popupDetail.value = true;
}

function saveProfile(editedRow) {
    console.log("Đã chỉnh sửa:", editedRow);
    popupDetail.value = false;
}
</script>
