<template>
  <AdminLayout>
    <PageBreadcrumb :pageTitle="currentPageTitle"/>
    <div class="space-y-5 sm:space-y-6">
      <ComponentCard title="Bảng Mã Giảm Giá">
        <BasicTableOne :columns="discountColumns" :rows="discounts || []"
                       modalHeaderDescription="Cập nhật chi tiết mã giảm giá tại đây."
                       modalHeaderTitle="Chỉnh sửa mã giảm giá">
          <template #cell-active="{ row }">
                        <span :class="row.active ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'"
                              class="inline-block px-2 py-1 rounded text-xs font-semibold">
                            {{ row.active ? 'Active' : 'Inactive' }}
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
import type {Discount} from '@/api/modules/discount'

export default defineComponent({
  components: {
    PageBreadcrumb,
    AdminLayout,
    ComponentCard,
    BasicTableOne,
  },
  data() {
    return {
      currentPageTitle: 'Bảng mã giảm giá',
      discountColumns: [
        {key: 'id', label: 'ID'},
        {key: 'code', label: 'Mã giảm giá'},
        {key: 'description', label: 'Mô tả'},
        {key: 'discountTypeId', label: 'Loại'},
        {key: 'value', label: 'Giá trị'},
        {key: 'startDate', label: 'Ngày bắt đầu'},
        {key: 'endDate', label: 'Ngày kết thúc'},
        {key: 'active', label: 'Trạng thái'},
        {key: 'minimumOrderAmount', label: 'Giá trị đơn tối thiểu'},
        {key: 'maximumDiscountAmount', label: 'Giảm tối đa'},
      ],
    }
  },
  computed: {
    ...mapGetters('discount', {
      discounts: types.GET_DISCOUNTS,
      discountError: types.GET_DISCOUNT_ERROR,
    }),
  },
  methods: {
    ...mapActions('discount', {
      fetchDiscounts: types.FETCH_DISCOUNTS,
    }),
    async loadDiscounts(): Promise<void> {
      try {
        await this.fetchDiscounts()
      } catch (error) {
        console.error('Không thể tải mã giảm giá:', error)
      }
    },
  },
  created() {
    this.loadDiscounts()
  },
})
</script>
