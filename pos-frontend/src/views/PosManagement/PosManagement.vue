<!-- pos-frontend\src\views\PosManagement\PosManagement.vue -->
<template>
    <AdminLayout>
        <PageBreadcrumb :pageTitle="currentPageTitle" />
        <div class="flex flex-col gap-6 lg:flex-row lg:gap-6">
            <!-- Danh sách món ăn -->
            <div class="flex-1 order-2 lg:order-1">
                <!-- Thanh tìm kiếm + bộ lọc -->
                <div class="flex flex-col sm:flex-row sm:items-center gap-3 mb-4">
                    <SearchBar v-model="searchQuery" class="flex-1" />
                    <div class="flex flex-wrap gap-2">
                        <button v-for="category in categories" :key="category" @click="activeCategory = category"
                            class="px-3 py-2 rounded text-sm font-medium transition-colors" :class="[
                                activeCategory === category
                                    ? 'bg-brand-50 text-brand-500'
                                    : 'text-gray-700 dark:text-gray-300 hover:bg-gray-100 dark:hover:bg-gray-800'
                            ]">
                            {{ category }}
                        </button>
                    </div>
                </div>

                <!-- ProductTableGrid -->
                <div
                    class="p-2 overflow-y-auto max-h-[650px] rounded-xl border-gray-200 dark:border-gray-800 custom-scrollbar">
                    <ProductTableGrid :products="filteredProducts" />
                </div>
            </div>

            <!-- Thông tin đơn hàng -->
            <div
                class="order-1 lg:order-2 w-full lg:w-1/3 bg-white dark:bg-gray-900 rounded-xl p-4 flex flex-col gap-4 shadow">
                <BuyReservationTabs />
                <!-- Thông tin khách hàng -->
                <CustomerInformation v-model="formData" />
                <!-- Chi tiết đơn hàng -->
                <CartDetailListing :orders="orders" @increase="increaseQuantity" @decrease="decreaseQuantity" />

                <!-- Tổng tiền -->
                <div class="border-t pt-2 space-y-1 text-sm border-gray-200 dark:border-gray-700">
                    <div class="flex justify-between">
                        <span class="text-gray-700 dark:text-gray-300">Tổng cộng</span>
                        <span class="text-gray-800 dark:text-gray-200">{{ subTotal.toLocaleString() }} VNĐ</span>
                    </div>
                    <div class="flex justify-between">
                        <span class="text-gray-700 dark:text-gray-300">Thuế (10%)</span>
                        <span class="text-gray-800 dark:text-gray-200">{{ tax.toLocaleString() }} VNĐ</span>
                    </div>
                    <div class="flex justify-between font-semibold text-base mt-1">
                        <span class="text-gray-800 dark:text-gray-100">Tổng</span>
                        <span class="text-gray-800 dark:text-gray-100">{{ total.toLocaleString() }} VNĐ</span>
                    </div>
                </div>

                <Button class-name="w-full mt-2">Thanh Toán</Button>
            </div>
        </div>
    </AdminLayout>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import { mapGetters, mapActions } from 'vuex'
import * as types from '@/store/types.ts'
import PageBreadcrumb from '@/components/common/PageBreadcrumb.vue'
import AdminLayout from '@/components/layout/AdminLayout.vue'
import SearchBar from '@/components/layout/header/SearchBar.vue'
import Button from '@/components/ui/Button.vue'
import BuyReservationTabs from './components/BuyReservationTabs.vue'
import ProductTableGrid from './components/ProductTableGrid.vue'
import CustomerInformation from './components/CustomerInformation.vue'
import CartDetailListing from './components/CartDetailListing.vue'

export default defineComponent({
    components: {
        SearchBar,
        PageBreadcrumb,
        AdminLayout,
        Button,
        BuyReservationTabs,
        ProductTableGrid,
        CustomerInformation,
        CartDetailListing
    },
    data() {
        return {
            formData: {
                input: '',
                description: '',
            },
            currentPageTitle: 'Điểm bán hàng',
            searchQuery: '',
            categories: ['All', 'Trà sữa', 'Đồ uống khác', 'Đồ ăn vặt'],
            activeCategory: 'All',
            orders: [
                { name: 'Cà phê đen', price: 25000, quantity: 1 },
                { name: 'Phở Bò', price: 10600, quantity: 1 },
            ],
        }
    },
    computed: {
        ...mapGetters('product', { allProducts: types.GET_PRODUCTS }),
        subTotal() {
            return this.orders.reduce((sum, order) => sum + order.price * order.quantity, 0)
        },
        tax() {
            return this.subTotal * 0.1
        },
        total() {
            return this.subTotal + this.tax
        },
        filteredProducts() {
            return this.allProducts.filter((item: any) => {
                const categoryMatch =
                    this.activeCategory === 'All' || item.category === this.activeCategory
                const searchMatch = item.name
                    .toLowerCase()
                    .includes(this.searchQuery.toLowerCase())

                return categoryMatch && searchMatch
            })
        },
    },
    methods: {
        ...mapActions('product', { fetchProducts: types.FETCH_PRODUCTS }),
        increaseQuantity(index: number) {
            this.orders[index].quantity++
        },
        decreaseQuantity(index: number) {
            if (this.orders[index].quantity > 1) {
                this.orders[index].quantity--
            }
        },
        async loadProducts() {
            try {
                await this.fetchProducts()
            } catch (e) {
                console.error('Không thể tải sản phẩm:', e)
            }
        },
    },
    created() {
        this.loadProducts()
    },
})

</script>
