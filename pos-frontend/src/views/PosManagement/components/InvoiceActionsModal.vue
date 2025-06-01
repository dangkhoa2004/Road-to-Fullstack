<template>
    <Modal @close="$emit('close')" headerTitle="Chức năng hóa đơn">
        <template #body>
            <div class="flex flex-col gap-3 p-4">
                <!-- Thông tin hóa đơn -->
                <div
                    class="p-2 rounded bg-gray-100 dark:bg-gray-800 text-sm text-gray-800 dark:text-gray-300 space-y-1">
                    <p><span class="font-semibold">Tên hóa đơn:</span> {{ invoice.name }}</p>
                    <p><span class="font-semibold">Khách hàng:</span> {{ invoice.customer.name || 'N/A' }}</p>
                    <p><span class="font-semibold">Số điện thoại:</span> {{ invoice.customer.phone || 'N/A' }}</p>
                    <p><span class="font-semibold">Ghi chú:</span> {{ invoice.customer.note || 'N/A' }}</p>
                    <p><span class="font-semibold">Số món:</span> {{ invoice.orders.length }}</p>

                    <!-- Danh sách sản phẩm -->
                    <div class="mt-2">
                        <p class="font-semibold">Danh sách sản phẩm:</p>
                        <ul class="list-disc ml-4">
                            <li v-for="(item, idx) in invoice.orders" :key="idx">
                                {{ item.name }} (x{{ item.quantity }})
                            </li>
                        </ul>
                    </div>

                    <!-- Tổng tiền -->
                    <p class="mt-1">
                        <span class="font-semibold">Tổng tiền:</span>
                        {{ calculateTotal(invoice.orders).toLocaleString('vi-VN') }} VNĐ
                    </p>
                </div>

                <!-- Các nút hành động: Grid 4 cột -->
                <div class="grid grid-cols-4 gap-2">
                    <button @click="$emit('print', invoice)"
                        class="flex flex-col items-center justify-center gap-1 px-2 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 transition text-xs text-center">
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24"
                            stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M16 8v8m0 0H8m8 0h4V8h-4V4H8v4H4v8h4v4h8v-4z" />
                        </svg>
                        In
                    </button>

                    <button @click="$emit('split', invoice)"
                        class="flex flex-col items-center justify-center gap-1 px-2 py-2 bg-yellow-500 text-white rounded hover:bg-yellow-600 transition text-xs text-center">
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24"
                            stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
                        </svg>
                        Chia
                    </button>

                    <button @click="$emit('change-status', invoice)"
                        class="flex flex-col items-center justify-center gap-1 px-2 py-2 bg-purple-500 text-white rounded hover:bg-purple-600 transition text-xs text-center">
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24"
                            stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
                        </svg>
                        Trạng thái
                    </button>

                    <button @click="$emit('cancel', invoice)"
                        class="flex flex-col items-center justify-center gap-1 px-2 py-2 bg-red-500 text-white rounded hover:bg-red-600 transition text-xs text-center">
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24"
                            stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M6 18L18 6M6 6l12 12" />
                        </svg>
                        Huỷ
                    </button>

                    <!-- Nút thứ 5 nếu có -->
                    <button @click="$emit('save-draft', invoice)"
                        class="flex flex-col items-center justify-center gap-1 px-2 py-2 bg-green-500 text-white rounded hover:bg-green-600 transition text-xs text-center col-span-4">
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24"
                            stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
                        </svg>
                        Lưu tạm
                    </button>
                </div>
            </div>
        </template>
    </Modal>
</template>

<script setup lang="ts">
import Modal from '@/components/profile/Modal.vue'
const props = defineProps<{
    invoice: { id: number; name: string; orders: { name: string; price: number; quantity: number }[]; customer: any;[key: string]: any }
}>()

/** Tính tổng tiền hóa đơn */
const calculateTotal = (orders: { price: number; quantity: number }[]) => {
    return orders.reduce((sum, item) => sum + item.price * item.quantity, 0)
}
</script>
