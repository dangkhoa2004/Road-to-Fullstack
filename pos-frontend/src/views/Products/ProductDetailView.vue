<template>
    <div class="container mx-auto pl-5 pr-5">
        <div v-if="productLoading" class="flex justify-center items-center h-64">
            <p class="text-lg text-gray-600">Loading product details...</p>
        </div>
        <div v-else-if="productError" class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative"
            role="alert">
            <strong class="font-bold">Error!</strong>
            <span class="block sm:inline"> {{ productError }}</span>
        </div>
        <div v-else-if="product" class="bg-white shadow-lg rounded-lg overflow-hidden md:flex">
            <div class="md:w-1/2 p-4 flex justify-center items-center ">
                <img class="max-h-96 object-contain" :src="product.imagePath || 'No+Image'" :alt="product.name" />
            </div>
            <div class="md:w-1/2 p-6">
                <h1 class="text-3xl font-bold text-gray-800 mb-2">{{ product.name }}</h1>
                <p class="text-gray-600 text-sm mb-4">ID: {{ product.id }} | Mã vạch: {{ product.barcode || 'N/A' }}</p>

                <div class="mb-4">
                    <span
                        :class="['inline-flex items-center gap-1 rounded-full px-3 py-1 text-sm font-semibold', product.isActive ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700']">
                        <span
                            :class="['h-2 w-2 rounded-full', product.isActive ? 'bg-green-600' : 'bg-red-600']"></span>
                        {{ product.isActive ? 'Active' : 'Inactive' }}
                    </span>
                </div>

                <div class="mb-4">
                    <p class="text-gray-700 text-lg">
                        <span class="font-semibold">Giá:</span>
                        {{ product.price ? product.price.toLocaleString('en-US', { style: 'currency', currency: 'USD' })
                            : 'N/A' }}
                    </p>
                    <p class="text-gray-700 text-lg">
                        <span class="font-semibold">Số lượng:</span> {{ product.quantity || 0 }}
                    </p>
                    <p class="text-gray-700 text-lg">
                        <span class="font-semibold">Danh mục:</span> {{ product.categoryName || 'N/A' }}
                    </p>
                </div>

                <div class="text-sm text-gray-500 mb-4">
                    <p>Được tạo ra tại: {{ product.createdAt ? new Date(product.createdAt).toLocaleDateString('en-US') :
                        'N/A' }}</p>
                    <p>Đã cập nhật tại: {{ product.updatedAt ? new Date(product.updatedAt).toLocaleDateString('en-US') :
                        'N/A' }}</p>
                </div>

                <div class="flex gap-4 mt-6">
                    <button @click="editProduct(product.id)"
                        class="flex items-center px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                            stroke="currentColor" class="h-5 w-5 mr-2">
                            <path stroke-linecap="round" stroke-linejoin="round"
                                d="M16.862 4.487l1.687-1.688a1.875 1.875 0 112.652 2.652L6.832 19.82a4.5 4.5 0 01-1.897 1.13l-2.685.8.8-2.685a4.5 4.5 0 011.13-1.897L16.863 4.487zm0 0L19.5 7.125" />
                        </svg>
                        Chỉnh sửa sản phẩm
                    </button>
                    <button @click="goBack"
                        class="flex items-center px-4 py-2 bg-gray-300 text-gray-800 rounded-md hover:bg-gray-400 focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-offset-2">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                            stroke="currentColor" class="h-5 w-5 mr-2">
                            <path stroke-linecap="round" stroke-linejoin="round"
                                d="M9 15L3 9m0 0l6-6M3 9h12a6 6 0 010 12h-3" />
                        </svg>
                        Quay lại Danh sách Sản phẩm
                    </button>
                </div>
            </div>
        </div>
        <div v-else class="flex justify-center items-center h-64">
            <p class="text-lg text-gray-600">Product not found.</p>
        </div>
    </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
import * as types from '@/store/types';

export default {
    name: 'ProductDetailView',
    props: {
        id: {
            type: [String, Number],
            required: true,
        },
    },
    data() {
        return {
        };
    },
    computed: {
        ...mapGetters('product', {
            product: types.GET_PRODUCT,
            productLoading: types.GET_PRODUCT_LOADING,
            productError: types.GET_PRODUCT_ERROR,
        }),
    },
    async created() {
        await this.loadProductDetail();
    },
    watch: {
        id: 'loadProductDetail',
    },
    methods: {
        ...mapActions('product', {
            fetchProductById: types.FETCH_PRODUCT_BY_ID,
        }),
        async loadProductDetail() {
            try {
                await this.fetchProductById(this.id);
            } catch (err) {
                console.error("Failed to load product details in component:", err);
            }
        },
        editProduct(productId) {
            alert(`Navigate to edit product page for ID: ${productId}`);
            console.log('Edit product with ID:', productId);
        },
        goBack() {
            this.$router.push({ name: 'ProductsList' });
        }
    },
};
</script>
