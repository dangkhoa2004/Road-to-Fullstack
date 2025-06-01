<template>
    <AdminLayout>
        <PageBreadcrumb :pageTitle="currentPageTitle" />
        <div class="flex flex-col gap-6 lg:flex-row lg:gap-6">
            <!-- Danh sách món ăn -->
            <div class="flex-1 order-2 lg:order-1">
                <div class="flex">
                    <SearchBar v-model="searchQuery" class="flex-1 mb-4" />
                    <div class="flex flex-wrap gap-2 mb-4">
                        <button v-for="category in categories" :key="category" @click="activeCategory = category"
                            class="px-3 py-2 rounded text-sm font-medium transition-colors" :class="activeCategory === category
                                ? 'bg-brand-50 text-brand-500'
                                : 'text-gray-700 dark:text-gray-300 hover:bg-gray-100 dark:hover:bg-gray-800'">
                            {{ category }}
                        </button>
                    </div>
                </div>
                <div
                    class="p-2 overflow-y-auto max-h-[650px] rounded-xl border-gray-200 dark:border-gray-800 custom-scrollbar">
                    <ProductTableGrid :products="filteredProducts" @select="addToOrder" />
                </div>
            </div>

            <!-- Thông tin đơn hàng -->
            <div
                class="order-1 lg:order-2 w-full lg:w-1/3 bg-white dark:bg-gray-900 rounded-xl p-4 flex flex-col gap-4 shadow relative">
                <BuyReservationTabs />
                <div class="flex gap-2">
                    <div v-for="tab in tabs" :key="tab.id"
                        class="flex items-center bg-brand-500 text-gray-800 rounded overflow-hidden relative"
                        :class="activeTabId === tab.id ? 'bg-brand-500 text-white' : 'bg-gray-200 text-gray-800'">
                        <!-- Nút chọn tab -->
                        <button @click="activeTabId = tab.id" class="px-3 py-1 text-sm font-medium flex-1 text-left">
                            {{ tab.name }}
                        </button>
                        <!-- Icon xoá tab -->
                        <button @click.stop="removeTab(tab.id)"
                            class="px-1 flex items-center justify-center text-xs hover:text-red-600 transition">
                            <svg xmlns="http://www.w3.org/2000/svg" class="w-3 h-3" fill="none" viewBox="0 0 24 24"
                                stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M6 18L18 6M6 6l12 12" />
                            </svg>
                        </button>
                    </div>
                    <button @click="addNewTab" class="px-3 py-1 rounded bg-green-500 text-white">+</button>
                </div>


                <div class="flex-1 overflow-y-auto max-h-[450px] flex flex-col gap-4 pr-1 custom-scrollbar">
                    <CustomerInformation v-model="activeTab.customer" />
                    <CartDetailListing :orders="activeTab.orders" @increase="increaseQuantity"
                        @decrease="decreaseQuantity" @clear="clearOrders" />
                </div>

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

                <div class="sticky bottom-0 bg-white dark:bg-gray-900 flex gap-2">
                    <Button class-name="flex-6">Tạo hóa đơn</Button>
                    <Button class-name="flex-2">Thanh Toán</Button>
                    <button @click="showActionModal = true"
                        class="inline-flex items-center justify-center font-medium gap-2 rounded-lg transition w-10 aspect-square bg-gray-300 dark:bg-gray-700 text-black dark:text-gray-200">
                        <svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4" fill="currentColor" viewBox="0 0 24 24">
                            <path
                                d="M5 12a2 2 0 114 0 2 2 0 01-4 0zm5 0a2 2 0 114 0 2 2 0 01-4 0zm5 0a2 2 0 114 0 2 2 0 01-4 0z" />
                        </svg>
                    </button>
                </div>
            </div>
        </div>
        <InvoiceActionsModal v-if="showActionModal" :invoice="activeTab" @close="showActionModal = false"
            @print="printInvoice" @split="splitInvoice" @change-status="changeInvoiceStatus" @cancel="cancelInvoice"
            @save-draft="saveDraft" />
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
import InvoiceActionsModal from './components/InvoiceActionsModal.vue'

export default defineComponent({
    components: {
        SearchBar,
        PageBreadcrumb,
        AdminLayout,
        Button,
        BuyReservationTabs,
        ProductTableGrid,
        CustomerInformation,
        CartDetailListing,
        InvoiceActionsModal,
    },
    data() {
        return {
            currentPageTitle: 'Điểm bán hàng',
            searchQuery: '',
            categories: ['All', 'Trà sữa', 'Đồ uống khác', 'Đồ ăn vặt'],
            activeCategory: 'All',
            tabs: [
                {
                    id: 1,
                    name: 'Hóa đơn 1',
                    orders: [] as { name: string; price: number; quantity: number }[], // 👉 fix: rõ ràng kiểu
                    customer: { name: '', phone: '', note: '' },
                },
            ],
            activeTabId: 1,
            showActionModal: false,
        }
    },
    computed: {
        ...mapGetters('product', { allProducts: types.GET_PRODUCTS }),
        activeTab() { return this.tabs.find(tab => tab.id === this.activeTabId)! },
        subTotal() { return this.activeTab.orders.reduce((sum, order) => sum + order.price * order.quantity, 0) },
        tax() { return this.subTotal * 0.1 },
        total() { return this.subTotal + this.tax },
        filteredProducts() {
            return this.allProducts.filter((item: any) => {
                const categoryMatch = this.activeCategory === 'All' || item.category === this.activeCategory
                const searchMatch = item.name.toLowerCase().includes(this.searchQuery.toLowerCase())
                return categoryMatch && searchMatch
            })
        },
    },
    methods: {
        ...mapActions('product', { fetchProducts: types.FETCH_PRODUCTS }),

        increaseQuantity(index: number) {
            this.activeTab.orders[index].quantity++
        },
        decreaseQuantity(index: number) {
            if (this.activeTab.orders[index].quantity > 1)
                this.activeTab.orders[index].quantity--
            else this.activeTab.orders.splice(index, 1)
        },
        addToOrder(product: { name: string; price: number }) {
            const index = this.activeTab.orders.findIndex(order => order.name === product.name)
            if (index !== -1) this.activeTab.orders[index].quantity++
            else this.activeTab.orders.push({ name: product.name, price: product.price, quantity: 1 })
        },
        addNewTab() {
            if (this.tabs.length >= 4) return alert('Chỉ có thể tạo tối đa 4 hóa đơn!')
            const newId = this.tabs[this.tabs.length - 1].id + 1
            this.tabs.push({ id: newId, name: `Hóa đơn ${newId}`, orders: [], customer: { name: '', phone: '', note: '' } })
            this.activeTabId = newId
        },
        clearOrders() { this.activeTab.orders = [] },

        async loadProducts() { await this.fetchProducts() },

        // Các hàm mới nhận `invoice` làm đối số
        printInvoice(invoice: any) {
            console.log('In hóa đơn:', invoice)
            alert(`In hóa đơn: ${invoice.name}`)
        },
        splitInvoice(invoice: any) {
            console.log('Chia hóa đơn:', invoice)
            alert(`Chia hóa đơn: ${invoice.name}`)
        },
        changeInvoiceStatus(invoice: any) {
            console.log('Đổi trạng thái hóa đơn:', invoice)
            alert(`Đổi trạng thái hóa đơn: ${invoice.name}`)
        },
        cancelInvoice(invoice: any) {
            const confirmDelete = confirm(`Bạn có chắc chắn muốn huỷ hóa đơn "${invoice.name}" không?`)
            if (confirmDelete) this.clearOrders()
        },
        saveDraft(invoice: any) {
            console.log('Lưu tạm hóa đơn:', invoice)
            alert(`Lưu tạm hóa đơn: ${invoice.name}`)
        },
        removeTab(id: number) {
            if (this.tabs.length === 1) return alert('Không thể xoá hết toàn bộ hoá đơn!')
            this.tabs = this.tabs.filter(tab => tab.id !== id)
            if (this.activeTabId === id && this.tabs.length) {
                this.activeTabId = this.tabs[0].id
            }
        },
    },
    created() { this.loadProducts() },
})
</script>
