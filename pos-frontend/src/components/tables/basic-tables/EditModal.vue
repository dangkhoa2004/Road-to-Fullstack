<template>
  <Modal :headerDescription="headerDescription" :headerTitle="headerTitle" @close="$emit('close')">
    <template #body>
      <div
        class="no-scrollbar relative w-full max-h-[700px] overflow-y-auto bg-white dark:bg-gray-900">
        <form class="flex flex-col space-y-4">
          <div v-for="column in columns" v-if="row" :key="column.key">
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

            <template v-else-if="column.key === 'id'">
              <input v-model="row[column.key]" class="h-11 w-full rounded-lg border border-gray-300 bg-gray-100 px-4 py-2.5 text-sm text-gray-800 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 focus:border-brand-300 focus:ring-brand-500/10" disabled
                     type="text"/>
            </template>

            <template v-else-if="column.key === 'role.name'">
              <select v-model="row.role.name"
                      class="h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 focus:border-brand-300 focus:ring-brand-500/10">
                <option value="Quản trị viên">Quản trị viên</option>
                <option value="Nhân viên">Nhân viên</option>
              </select>
            </template>

            <template v-else-if="column.key === 'permissions' && Array.isArray(row.permissions)">
              <div class="flex flex-wrap items-center gap-8">
                <div v-for="(permission, index) in allPermissions" :key="index"
                     class="flex items-center space-x-2">
                  <label :for="`permission-${index}`"
                         class="flex items-center text-sm font-medium text-gray-700 cursor-pointer select-none dark:text-gray-400">
                    <div class="relative">
                      <input :id="`permission-${index}`" v-model="row.permissions" :value="permission"
                             class="sr-only"
                             type="checkbox"/>
                      <div :class="row.permissions.includes(permission)
                        ? 'border-brand-500 bg-brand-500'
                        : 'bg-transparent border-gray-300 dark:border-gray-700'"
                           class="mr-3 flex h-5 w-5 items-center justify-center rounded-md border-[1.25px] hover:border-brand-500">
                        <span :class="row.permissions.includes(permission) ? '' : 'opacity-0'">
                          <svg fill="none" height="14" viewBox="0 0 14 14" width="14"
                               xmlns="http://www.w3.org/2000/svg">
                            <path d="M11.6666 3.5L5.24992 9.91667L2.33325 7" stroke="white"
                                  stroke-linecap="round"
                                  stroke-linejoin="round" stroke-width="1.94437"/>
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
                   class="w-20 h-20 object-cover rounded border border-gray-300 dark:border-gray-700 mb-2"/>
              <input v-model="row[column.key]" class="h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 focus:border-brand-300 focus:ring-brand-500/10"
                     placeholder="Nhập đường dẫn ảnh"
                     type="text"/>
            </template>

            <template v-else>
              <input v-model="row[column.key]" class="h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 focus:border-brand-300 focus:ring-brand-500/10"
                     type="text"/>
            </template>
          </div>
        </form>
      </div>
    </template>
  </Modal>
</template>

<script setup>
import {ref} from "vue";
import Modal from "../../profile/Modal.vue";

const props = defineProps({
  columns: Array,
  row: Object,
  headerTitle: String,
  headerDescription: String
});

const allPermissions = [
  'view_products',
  'edit_products',
  'delete_products',
  'view_invoices',
  'create_invoices',
  'delete_invoices',
  'view_employees',
  'edit_employees',
  'delete_employees',
  'view_audit_logs',
  'export_data',
  'import_data',
  'view_customers',
  'edit_customers',
  'delete_customers',
  'view_suppliers',
  'edit_suppliers',
  'delete_suppliers',
  'view_reports',
  'generate_reports',
  'view_settings',
  'edit_settings',
  'manage_roles',
  'assign_roles',
  'view_notifications',
  'manage_notifications',
  'view_dashboard',
  'access_admin_panel',
  'reset_passwords',
  'approve_requests',
  'manage_integrations'
];


function updateNestedField(objectKey, nestedKey, value) {
  if (props.row[objectKey]) {
    props.row[objectKey][nestedKey] = value;
  } else {
    props.row[objectKey] = {[nestedKey]: value};
  }
}
</script>
