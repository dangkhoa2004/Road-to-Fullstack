<template>
  <AdminLayout>
    <PageBreadcrumb :pageTitle="currentPageTitle" />
    <div class="space-y-5 sm:space-y-6">
      <ComponentCard title="Bảng sản phẩm">
        <BasicTableOne :columns="productColumns" :rows="products" modalHeaderTitle="Chỉnh sửa sản phẩm"
          modalHeaderDescription="Cập nhật thông tin sản phẩm tại đây.">
          <template #cell-imagePath="{ row }">
            <img :src="row.imagePath" alt="Hình ảnh sản phẩm" class="w-12 h-12 object-cover rounded" />
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
      currentPageTitle: 'Bảng sản phẩm',
      productColumns: [
        { key: 'id', label: 'ID' },
        { key: 'barcode', label: 'Mã vạch' },
        { key: 'name', label: 'Tên sản phẩm' },
        { key: 'price', label: 'Giá' },
        { key: 'quantity', label: 'Số lượng' },
        { key: 'imagePath', label: 'Hình ảnh' },
        { key: 'categoryName', label: 'Danh mục' },
      ],
    }
  },
  computed: {
    ...mapGetters('product', {
      products: types.GET_PRODUCTS,
      productError: types.GET_PRODUCT_ERROR,
    }),
  },
  methods: {
    ...mapActions('product', {
      fetchProducts: types.FETCH_PRODUCTS,
    }),
    async loadProducts(): Promise<void> {
      try {
        await this.fetchProducts()
      } catch (error) {
        console.error('Không thể tải sản phẩm:', error)
      }
    },
  },
  created() {
    this.loadProducts()
  },
})
</script>
