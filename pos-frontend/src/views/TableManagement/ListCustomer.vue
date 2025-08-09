<template>
  <AdminLayout>
    <PageBreadcrumb :pageTitle="currentPageTitle"/>
    <div class="space-y-5 sm:space-y-6">
      <ComponentCard title="Danh sách Khách Hàng">
        <BasicTableOne :columns="customerColumns" :rows="customers || []"
                       modalHeaderDescription="Cập nhật thông tin khách hàng ở đây."
                       modalHeaderTitle="Chỉnh sửa khách hàng">
          <!-- Có thể thêm các template slot tùy chỉnh cho cột ở đây nếu cần -->
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
    BasicTableOne
  },
  data() {
    return {
      currentPageTitle: 'Danh sách Khách Hàng',
      customerColumns: [
        {key: 'id', label: 'ID'},
        {key: 'name', label: 'Tên khách hàng'},
        {key: 'phone', label: 'Số điện thoại'},
        {key: 'email', label: 'Email'},
        {key: 'address', label: 'Địa chỉ'},
      ],
    }
  },
  computed: {
    ...mapGetters('customer', {
      customers: types.GET_CUSTOMERS,
      customerError: types.GET_CUSTOMER_ERROR,
    }),
  },
  methods: {
    ...mapActions('customer', {
      fetchCustomers: types.FETCH_CUSTOMERS,
    }),
    async loadCustomers(): Promise<void> {
      try {
        await this.fetchCustomers()
      } catch (error) {
        console.error('Không thể tải danh sách khách hàng:', error)
      }
    },
  },
  created() {
    this.loadCustomers()
  },
})
</script>
