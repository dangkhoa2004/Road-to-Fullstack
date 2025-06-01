<template>
  <Modal @close="$emit('close')" :headerTitle="headerTitle" :headerDescription="headerDescription">
    <template #body>
      <div class="no-scrollbar relative w-full max-h-[700px] overflow-y-auto rounded-xl bg-white dark:bg-gray-900">
        <form class="flex flex-col space-y-4 p-2">
          <div v-for="column in columns" :key="column.key" v-if="row">
            <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">
              {{ column.label }}
            </label>

            <!-- Xử lý đặc biệt -->
            <template v-if="column.key === 'isActive'">
              <select v-model="row[column.key]"
                class="h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 focus:border-brand-300 focus:ring-brand-500/10">
                <option :value="true">Active</option>
                <option :value="false">Inactive</option>
              </select>
            </template>

            <template v-else-if="column.key === 'role.name'">
              <input :value="row.role?.name || ''"
                @input="updateNestedField('role', 'name', $event.target.value)"
                class="h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 focus:border-brand-300 focus:ring-brand-500/10" />
            </template>

            <template v-else-if="column.key === 'permissions'">
              <div class="flex flex-wrap items-center gap-8">
                <div v-for="(permission, index) in allPermissions" :key="index" class="flex items-center space-x-2">
                  <label :for="`permission-${index}`"
                    class="flex items-center text-sm font-medium text-gray-700 cursor-pointer select-none dark:text-gray-400">
                    <div class="relative">
                      <input type="checkbox" :id="`permission-${index}`" :value="permission"
                        v-model="row.permissions" class="sr-only" />
                      <div :class="row.permissions.includes(permission)
                        ? 'border-brand-500 bg-brand-500'
                        : 'bg-transparent border-gray-300 dark:border-gray-700'"
                        class="mr-3 flex h-5 w-5 items-center justify-center rounded-md border-[1.25px] hover:border-brand-500">
                        <span :class="row.permissions.includes(permission) ? '' : 'opacity-0'">
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

            <template v-else-if="column.key === 'imagePath'">
              <img :src="row.imagePath" alt="Hình ảnh"
                class="w-20 h-20 object-cover rounded border border-gray-300 dark:border-gray-700 mb-2" />
              <input type="text" v-model="row[column.key]"
                class="h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 focus:border-brand-300 focus:ring-brand-500/10"
                placeholder="Nhập đường dẫn ảnh" />
            </template>

            <template v-else>
              <input type="text" v-model="row[column.key]"
                class="h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 focus:border-brand-300 focus:ring-brand-500/10" />
            </template>
          </div>
          <button @click.prevent="$emit('save', row)" type="button"
            class="bg-green-500 text-white rounded px-4 py-2 self-end">Lưu</button>
        </form>
      </div>
    </template>
  </Modal>
</template>

<script setup>
import { ref } from "vue";
import Modal from "../../profile/Modal.vue";

const props = defineProps({
  columns: Array,
  row: Object,
  headerTitle: String,
  headerDescription: String
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

function updateNestedField(objectKey, nestedKey, value) {
  if (props.row[objectKey]) {
    props.row[objectKey][nestedKey] = value;
  } else {
    props.row[objectKey] = { [nestedKey]: value };
  }
}
</script>
