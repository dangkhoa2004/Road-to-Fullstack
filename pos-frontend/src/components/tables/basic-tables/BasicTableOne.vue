<template>
  <div class="overflow-hidden rounded-xl border border-gray-200 bg-white dark:border-gray-800 dark:bg-white/[0.03]">
    <!-- Table -->
    <div class="max-w-full overflow-x-auto custom-scrollbar">
      <table class="min-w-full">
        <thead>
          <tr class="border-b border-gray-200 dark:border-gray-700">
            <th v-for="(column, index) in columns" :key="index" class="px-5 py-3 text-left sm:px-6 dark:text-white"
              :class="column.headerClass">
              {{ column.label }}
            </th>
            <th class="px-5 py-3 text-left sm:px-6 dark:text-white">Thao tác</th>
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
            <td class="px-5 py-4 sm:px-6">
              <div class="flex space-x-2">
                <span @click="showModal(row)"
                  class="inline-block px-2 py-1 rounded text-xs font-semibold cursor-pointer bg-green-100 text-green-800 hover:bg-green-200 dark:bg-green-200/10 dark:text-green-400 transition">
                  Xem/Sửa
                </span>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Pagination -->
    <div v-if="totalPages > 1" class="flex justify-end mt-4 space-x-2 p-2">
      <button @click="prevPage" :disabled="currentPage === 1"
        class="flex items-center px-3 py-1 border rounded-xl shadow-sm text-gray-700 dark:text-gray-300 hover:bg-gray-100 dark:hover:bg-gray-700 disabled:opacity-50 disabled:cursor-not-allowed transition">
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
        <svg class="w-4 h-4 ml-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
        </svg>
      </button>
    </div>

    <!-- Modal -->
    <Modal v-if="popupDetail" @close="popupDetail = false" :headerTitle="modalHeaderTitle"
      :headerDescription="modalHeaderDescription">
      <template #body>
        <div class="no-scrollbar relative w-full max-h-[700px] overflow-y-auto rounded-xl bg-white dark:bg-gray-900">

          <form class="flex flex-col space-y-4 p-2">
            <div v-for="column in columns" :key="column.key" v-if="selectedRow">
              <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">
                {{ column.label }}
              </label>

              <!-- Đặc biệt với isActive (boolean) -->
              <template v-if="column.key === 'isActive'">
                <select v-model="selectedRow[column.key]"
                  class="dark:bg-dark-900 h-11 w-full appearance-none rounded-lg border border-gray-300 bg-transparent bg-none px-4 py-2.5 pr-11 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800">
                  <option :value="true" class="text-gray-700 dark:bg-gray-900 dark:text-gray-400">
                    Active
                  </option>
                  <option :value="false" class="text-gray-700 dark:bg-gray-900 dark:text-gray-400">
                    Inactive
                  </option>
                </select>
              </template>

              <!-- Đặc biệt với role.name (nested) -->
              <template v-else-if="column.key === 'role.name'">
                <input :value="selectedRow.role?.name || ''"
                  @input="updateNestedField('role', 'name', $event.target.value)"
                  class="dark:bg-dark-900 h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800" />
              </template>

              <!-- Đặc biệt với permissions (array) -->
              <template v-else-if="column.key === 'permissions'">
                <div class="flex flex-wrap items-center gap-8">
                  <div v-for="(permission, index) in allPermissions" :key="index" class="flex items-center space-x-2">
                    <label :for="`permission-${index}`"
                      class="flex items-center text-sm font-medium text-gray-700 cursor-pointer select-none dark:text-gray-400">
                      <div class="relative">
                        <input type="checkbox" :id="`permission-${index}`" :value="permission"
                          v-model="selectedRow.permissions" class="sr-only" />
                        <div :class="selectedRow.permissions.includes(permission)
                          ? 'border-brand-500 bg-brand-500'
                          : 'bg-transparent border-gray-300 dark:border-gray-700'"
                          class="mr-3 flex h-5 w-5 items-center justify-center rounded-md border-[1.25px] hover:border-brand-500 dark:hover:border-brand-500">
                          <span :class="selectedRow.permissions.includes(permission) ? '' : 'opacity-0'">
                            <svg width="14" height="14" viewBox="0 0 14 14" fill="none"
                              xmlns="http://www.w3.org/2000/svg">
                              <path d="M11.6666 3.5L5.24992 9.91667L2.33325 7" stroke="white" stroke-width="1.94437"
                                stroke-linecap="round" stroke-linejoin="round" />
                            </svg>
                          </span>
                        </div>
                      </div>
                      {{ permission }}
                    </label>
                  </div>
                </div>
              </template>

              <!-- Đặc biệt với imagePath (link + preview) -->
              <template v-else-if="column.key === 'imagePath'">
                <!-- Ảnh Preview -->
                <img :src="selectedRow.imagePath" alt="Hình ảnh"
                  class="w-20 h-20 object-cover rounded border border-gray-300 dark:border-gray-700 mb-2" />

                <!-- Input Link Ảnh -->
                <input type="text" v-model="selectedRow[column.key]"
                  class="dark:bg-dark-900 h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800"
                  placeholder="Nhập đường dẫn ảnh" />
              </template>

              <!-- Các field bình thường -->
              <template v-else>
                <input type="text" v-model="selectedRow[column.key]"
                  class="dark:bg-dark-900 h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800" />
              </template>
            </div>
            <button @click="saveProfile" type="button"
              class="bg-green-500 text-white rounded px-4 py-2 self-end">Lưu</button>
          </form>
        </div>
      </template>
    </Modal>
  </div>
</template>
<script setup>
import { computed, ref } from "vue";
import Modal from '../../profile/Modal.vue';

const props = defineProps({
  columns: Array,
  rows: Array,
  modalHeaderTitle: String,
  modalHeaderDescription: String
});

const allPermissions = [
  'view_audit_logs',
  'edit_products',
  'view_employees',
  'delete_employees',
  'edit_employees',
  'view_invoices',
  'create_invoices',
  'view_products'
];

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

function updateNestedField(objectKey, nestedKey, value) {
  if (selectedRow.value[objectKey]) {
    selectedRow.value[objectKey][nestedKey] = value;
  } else {
    selectedRow.value[objectKey] = { [nestedKey]: value };
  }
}

function updateArrayField(key, value) {
  selectedRow.value[key] = value.split(',').map(item => item.trim());
}

function saveProfile() {
  console.log("Đã chỉnh sửa:", selectedRow.value);
  popupDetail.value = false;
}
</script>
