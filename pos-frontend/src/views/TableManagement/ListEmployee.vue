<template>
  <AdminLayout>
    <PageBreadcrumb :pageTitle="currentPageTitle" />
    <div class="space-y-5 sm:space-y-6">
      <ComponentCard title="Danh sách Nhân Viên">
        <BasicTableOne
          :columns="employeeColumns"
          :rows="employees || []"
          modalHeaderTitle="Chỉnh sửa nhân viên"
          modalHeaderDescription="Cập nhật thông tin nhân viên ở đây."
        >
          <template #cell-isActive="{ row }">
            <span class="inline-block px-2 py-1 rounded text-xs font-semibold"
              :class="row.isActive ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'">
              {{ row.isActive ? 'Active' : 'Inactive' }}
            </span>
          </template>
          <template #cell-permissions="{ row }">
            <ul class="list-disc pl-4 text-xs text-gray-600">
              <li v-for="perm in row.permissions" :key="perm">{{ perm }}</li>
            </ul>
          </template>
          <template #cell-role.name="{ row }">
            <span class="text-[16px] text-gray-700 dark:text-gray-300">{{ row.role.name }}</span>
          </template>
        </BasicTableOne>
      </ComponentCard>
    </div>
  </AdminLayout>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import { mapActions, mapGetters } from 'vuex'
import * as types from '@/store/types.ts'
import PageBreadcrumb from '@/components/common/PageBreadcrumb.vue'
import AdminLayout from '@/components/layout/AdminLayout.vue'
import ComponentCard from '@/components/common/ComponentCard.vue'
import BasicTableOne from '@/components/tables/basic-tables/BasicTableOne.vue'

export default defineComponent({
  components: {
    PageBreadcrumb,
    AdminLayout,
    ComponentCard,
    BasicTableOne
  },
  data() {
    return {
      currentPageTitle: 'Danh sách Nhân Viên',
      employeeColumns: [
        { key: 'id', label: 'ID' },
        { key: 'name', label: 'Tên nhân viên' },
        { key: 'username', label: 'Username' },
        { key: 'phone', label: 'Số điện thoại' },
        { key: 'email', label: 'Email' },
        { key: 'isActive', label: 'Trạng thái' },
        { key: 'role.name', label: 'Vai trò' },
        { key: 'permissions', label: 'Quyền' },
      ],
    }
  },
  computed: {
    ...mapGetters('employee', {
      employees: types.GET_EMPLOYEES,
      employeeError: types.GET_EMPLOYEE_ERROR,
    }),
  },
  methods: {
    ...mapActions('employee', {
      fetchEmployees: types.FETCH_EMPLOYEES,
    }),
    async loadEmployees(): Promise<void> {
      try {
        await this.fetchEmployees()
      } catch (error) {
        console.error('Không thể tải danh sách nhân viên:', error)
      }
    },
  },
  created() {
    this.loadEmployees()
  },
})
</script>
