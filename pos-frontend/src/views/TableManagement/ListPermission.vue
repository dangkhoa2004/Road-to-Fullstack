<template>
  <AdminLayout>
    <PageBreadcrumb :pageTitle="currentPageTitle"/>

    <div class="space-y-5 sm:space-y-6">
      <!-- Bảng Danh Sách Quyền -->
      <ComponentCard title="Danh Sách Quyền Hệ Thống">
        <BasicTableOne :columns="permissionColumns" :rows="permissions || []"
                       modalHeaderDescription="Xem chi tiết thông tin quyền ở đây."
                       modalHeaderTitle="Chi tiết quyền">
          <!-- Ví dụ: cột mô tả có thể custom thêm style -->
          <template #cell-description="{ row }">
            <span class="text-gray-600">{{ row.description || 'Không có mô tả' }}</span>
          </template>
        </BasicTableOne>
      </ComponentCard>
    </div>
  </AdminLayout>
</template>

<script lang="ts">
import {defineComponent} from 'vue'
import {mapActions, mapGetters} from 'vuex'
import * as types from '@/store/types.ts'
import PageBreadcrumb from '@/components/common/PageBreadcrumb.vue'
import AdminLayout from '@/components/layout/AdminLayout.vue'
import ComponentCard from '@/components/common/ComponentCard.vue'
import BasicTableOne from '@/components/tables/basic-tables/MyTable.vue'

export default defineComponent({
  components: {
    PageBreadcrumb,
    AdminLayout,
    ComponentCard,
    BasicTableOne,
  },
  data() {
    return {
      currentPageTitle: 'Danh Sách Quyền Hệ Thống',
      permissionColumns: [
        {key: 'id', label: 'ID'},
        {key: 'name', label: 'Tên Quyền'},
        {key: 'description', label: 'Mô Tả'},
      ],
    }
  },
  computed: {
    ...mapGetters('permission', {
      permissions: types.GET_PERMISSIONS,
      permissionError: types.GET_PERMISSION_ERROR,
    }),
  },
  methods: {
    ...mapActions('permission', {
      fetchPermissions: types.FETCH_PERMISSIONS,
    }),
    async loadPermissions(): Promise<void> {
      try {
        await this.fetchPermissions()
      } catch (error) {
        console.error('Không thể tải danh sách quyền:', error)
      }
    },
  },
  mounted() {
    this.loadPermissions()
  },
})
</script>
