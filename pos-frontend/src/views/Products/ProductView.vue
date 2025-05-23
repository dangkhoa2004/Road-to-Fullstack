<template>
    <ItemTableComponent :headers="productHeaders" :items="products" :error="productError"
        loading-message="Loading products..." @delete-item="deleteProduct" @view-item-detail="viewProductDetail" />
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
import * as types from '@/store/types';
import ItemTableComponent from '@/components/common/ItemTableComponent.vue'; // Import the new component

export default {
    name: 'ProductView',
    components: {
        ItemTableComponent,
    },
    data() {
        return {
            productHeaders: [
                { text: 'Name', value: 'name' },
                { text: 'Barcode', value: 'barcode' },
                { text: 'State', value: 'isActive' },
                { text: 'Category', value: 'categoryName' },
                { text: 'Price', value: 'price' },
                { text: 'Quantity', value: 'quantity' },
                { text: 'Created At', value: 'createdAt' },
                { text: 'Updated At', value: 'updatedAt' },
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
            console.log('Deleting product with ID:', id);
            alert(`Delete product with ID: ${id}`);
        },
        viewProductDetail(id) {
            console.log('Navigating to product detail for ID:', id);
            this.$router.push({ name: 'ProductDetail', params: { id: id } });
        },
    },
    created() {
        this.loadProducts();
    },
};
</script>