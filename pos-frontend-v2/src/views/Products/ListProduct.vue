<template>
  <AdminLayout>
    <PageBreadcrumb :pageTitle="currentPageTitle" />
    <div class="space-y-5 sm:space-y-6">
      <ComponentCard title="Product Table">
        <BasicTableOne :columns="productColumns" :rows="products">
          <template #cell-imagePath="{ row }">
            <img :src="row.imagePath" alt="product image" class="w-12 h-12 object-cover rounded" />
          </template>
        </BasicTableOne>
      </ComponentCard>
    </div>
  </AdminLayout>
</template>

<script lang="ts">
import { mapActions, mapGetters } from 'vuex';
import * as types from '@/store/types.ts';
import PageBreadcrumb from '@/components/common/PageBreadcrumb.vue';
import AdminLayout from '@/components/layout/AdminLayout.vue';
import ComponentCard from '@/components/common/ComponentCard.vue';
import BasicTableOne from '@/components/tables/basic-tables/BasicTableOne.vue';
import type { Product } from '@/api/product';

export default {
  components: {
    PageBreadcrumb,
    AdminLayout,
    ComponentCard,
    BasicTableOne,
  },
  data() {
    return {
      currentPageTitle: 'Basic Tables',
      productColumns: [
        { key: 'id', label: 'ID' },
        { key: 'barcode', label: 'Barcode' },
        { key: 'name', label: 'Name' },
        { key: 'price', label: 'Price' },
        { key: 'quantity', label: 'Quantity' },
        { key: 'imagePath', label: 'Image' },
        { key: 'categoryName', label: 'Category ID' },
        { key: 'createdAt', label: 'Created At' },
        { key: 'updatedAt', label: 'Updated At' }
      ],
    };
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
    async loadProducts() {
      try {
        await this.fetchProducts();
      } catch (error) {
        console.error('Failed to load products in component:', error);
      }
    },

  },
  created() {
    this.loadProducts();
  },
};
</script>
