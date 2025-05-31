<template>
  <AdminLayout>
    <PageBreadcrumb :pageTitle="currentPageTitle" />

    <div class="space-y-5 sm:space-y-6">
      <!-- Bảng Hàng Nhập Kho -->
      <ComponentCard title="Hàng Nhập Kho">
        <BasicTableOne :columns="stockColumns" :rows="stockIns || []" modalHeaderTitle="Chi tiết hàng nhập kho"
          modalHeaderDescription="Xem và chỉnh sửa thông tin hàng nhập kho tại đây.">
          <template #cell-quantity="{ row }">
            <span>{{ row.quantity }}</span>
          </template>
        </BasicTableOne>
      </ComponentCard>

      <!-- Bảng Hàng Xuất Kho -->
      <ComponentCard title="Hàng Xuất Kho">
        <BasicTableOne :columns="stockColumns" :rows="stockOuts || []" modalHeaderTitle="Chi tiết hàng xuất kho"
          modalHeaderDescription="Xem và chỉnh sửa thông tin hàng xuất kho tại đây.">
          <template #cell-quantity="{ row }">
            <span>{{ row.quantity }}</span>
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
    BasicTableOne,
  },
  data() {
    return {
      currentPageTitle: 'Bảng hàng tồn kho',
      stockColumns: [
        { key: 'id', label: 'ID' },
        { key: 'productId', label: 'Mã Sản Phẩm' },
        { key: 'productName', label: 'Tên Sản Phẩm' },
        { key: 'quantity', label: 'Số Lượng' },
        { key: 'note', label: 'Ghi Chú' },
        { key: 'employeeName', label: 'Nhân Viên' },
      ],
    }
  },
  computed: {
    ...mapGetters('stock', {
      stockIns: types.GET_STOCK_INS,
      stockOuts: types.GET_STOCK_OUTS,
      stockError: types.GET_STOCK_ERROR,
    }),
  },
  methods: {
    ...mapActions('stock', {
      fetchStockIns: types.FETCH_STOCK_INS,
      fetchStockOuts: types.FETCH_STOCK_OUTS,
    }),
    async loadStocks(): Promise<void> {
      try {
        await this.fetchStockIns()
        await this.fetchStockOuts()
      } catch (error) {
        console.error('Không thể tải danh sách hàng tồn kho:', error)
      }
    },
  },
  created() {
    this.loadStocks()
  },
})
</script>
