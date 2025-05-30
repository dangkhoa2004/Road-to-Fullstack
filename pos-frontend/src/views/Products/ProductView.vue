<template>
  <AdminLayout>
    <PageBreadcrumb :pageTitle="currentPageTitle" />
    <div class="space-y-5 sm:space-y-6">
      <ComponentCard title="Product Table">
        <BasicTableOne :columns="productColumns" :rows="products">
          <!-- Tuỳ chỉnh render riêng cho cột image_path -->
          <template #cell-image_path="{ row }">
            <img :src="row.image_path" alt="product image" class="w-12 h-12 object-cover rounded" />
          </template>
        </BasicTableOne>
      </ComponentCard>
    </div>
  </AdminLayout>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
import * as types from '@/store/types';
import PageBreadcrumb from "@/components/common/PageBreadcrumb.vue";
import AdminLayout from "@/components/layout/AdminLayout.vue";
import ComponentCard from "@/components/common/ComponentCard.vue";
import BasicTableOne from "@/components/tables/basic-tables/BasicTableOne.vue";

export default {
  components: {
    PageBreadcrumb,
    AdminLayout,
    ComponentCard,
    BasicTableOne,
  },
  data() {
    return {
      currentPageTitle: "Basic Tables",
      productColumns: [
        { key: "id", label: "ID" },
        { key: "barcode", label: "Barcode" },
        { key: "name", label: "Name" },
        { key: "price", label: "Price" },
        { key: "quantity", label: "Quantity" },
        { key: "image_path", label: "Image" },
        { key: "category_id", label: "Category ID" },
        { key: "is_active", label: "Is Active" },
        { key: "created_at", label: "Created At" },
        { key: "updated_at", label: "Updated At" },
        { key: "version", label: "Version" },
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
        console.error("Failed to load products in component:", error);
      }
    },
    deleteProduct(id) {
      alert(`Delete product with ID: ${id}`);
    },
    viewProductDetail(id) {
      this.$router.push({ name: 'ProductDetail', params: { id: id } });
    },
  },
  created() {
    this.loadProducts();
  },
};
</script>
