<template>
  <AdminLayout>
    <PageBreadcrumb :pageTitle="currentPageTitle"/>
    <div class="space-y-5 sm:space-y-6">
      <ComponentCard title="Bảng Hóa Đơn">
        <BasicTableOne :columns="invoiceColumns" :rows="invoices || []"
                       modalHeaderDescription="Xem và chỉnh sửa thông tin hóa đơn tại đây."
                       modalHeaderTitle="Chi tiết hóa đơn">
          <template #cell-status="{ row }">
            <span :class="row.status === 'paid' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'"
                  class="inline-block px-2 py-1 rounded text-xs font-semibold">
              {{ row.status === 'paid' ? 'Đã Thanh Toán' : 'Chưa Thanh Toán' }}
            </span>
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
import type {Invoice} from '@/api/modules/invoice'

export default defineComponent({
  components: {
    PageBreadcrumb,
    AdminLayout,
    ComponentCard,
    BasicTableOne,
  },
  data() {
    return {
      currentPageTitle: 'Bảng Hóa Đơn',
      invoiceColumns: [
        {key: 'id', label: 'ID'},
        {key: 'customerId', label: 'Mã Khách Hàng'},
        {key: 'employeeId', label: 'Mã Nhân Viên'},
        {key: 'tableId', label: 'Mã Bàn'},
        {key: 'discountId', label: 'Mã Giảm Giá'},
        {key: 'subTotal', label: 'Tạm Tính'},
        {key: 'discountAmount', label: 'Giảm Giá'},
        {key: 'taxAmount', label: 'Thuế'},
        {key: 'totalAmount', label: 'Tổng Tiền'},
        {key: 'status', label: 'Trạng Thái'},
        {key: 'note', label: 'Ghi Chú'},
      ],
    }
  },
  computed: {
    ...mapGetters('invoice', {
      invoices: types.GET_INVOICES,
      invoiceError: types.GET_INVOICE_ERROR,
    }),
  },
  methods: {
    ...mapActions('invoice', {
      fetchInvoices: types.FETCH_INVOICES,
    }),
    async loadInvoices(): Promise<void> {
      try {
        await this.fetchInvoices()
      } catch (error) {
        console.error('Không thể tải danh sách hóa đơn:', error)
      }
    },
  },
  created() {
    this.loadInvoices()
  },
})
</script>
