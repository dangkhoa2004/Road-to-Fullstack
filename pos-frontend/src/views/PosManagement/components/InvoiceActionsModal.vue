<template>
  <Modal headerTitle="Chức năng hóa đơn" @close="$emit('close')">
    <template #body>
      <div class="flex flex-col gap-3 p-4">
        <!-- Thông tin hóa đơn -->
        <div
          class="p-2 rounded bg-gray-100 dark:bg-gray-800 text-sm text-gray-800 dark:text-gray-300 space-y-1">
          <p><span class="font-semibold">Tên hóa đơn:</span> {{ invoice.name }}</p>
          <p><span class="font-semibold">Khách hàng:</span> {{ invoice.customer.name || 'N/A' }}</p>
          <p><span class="font-semibold">Số điện thoại:</span> {{ invoice.customer.phone || 'N/A' }}
          </p>
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
          <button class="flex flex-col items-center justify-center gap-1 px-2 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 transition text-xs text-center"
                  @click="$emit('print', invoice)">
            <svg class="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"
                 xmlns="http://www.w3.org/2000/svg">
              <path d="M16 8v8m0 0H8m8 0h4V8h-4V4H8v4H4v8h4v4h8v-4z" stroke-linecap="round" stroke-linejoin="round"
                    stroke-width="2"/>
            </svg>
            In
          </button>

          <button class="flex flex-col items-center justify-center gap-1 px-2 py-2 bg-yellow-500 text-white rounded hover:bg-yellow-600 transition text-xs text-center"
                  @click="$emit('split', invoice)">
            <svg class="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"
                 xmlns="http://www.w3.org/2000/svg">
              <path d="M9 5l7 7-7 7" stroke-linecap="round" stroke-linejoin="round"
                    stroke-width="2"/>
            </svg>
            Chia
          </button>

          <button class="flex flex-col items-center justify-center gap-1 px-2 py-2 bg-purple-500 text-white rounded hover:bg-purple-600 transition text-xs text-center"
                  @click="$emit('change-status', invoice)">
            <svg class="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"
                 xmlns="http://www.w3.org/2000/svg">
              <path d="M5 13l4 4L19 7" stroke-linecap="round" stroke-linejoin="round"
                    stroke-width="2"/>
            </svg>
            Trạng thái
          </button>

          <button class="flex flex-col items-center justify-center gap-1 px-2 py-2 bg-red-500 text-white rounded hover:bg-red-600 transition text-xs text-center"
                  @click="$emit('cancel', invoice)">
            <svg class="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"
                 xmlns="http://www.w3.org/2000/svg">
              <path d="M6 18L18 6M6 6l12 12" stroke-linecap="round" stroke-linejoin="round"
                    stroke-width="2"/>
            </svg>
            Huỷ
          </button>

          <!-- Nút thứ 5 nếu có -->
          <button class="flex flex-col items-center justify-center gap-1 px-2 py-2 bg-green-500 text-white rounded hover:bg-green-600 transition text-xs text-center col-span-4"
                  @click="$emit('save-draft', invoice)">
            <svg class="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"
                 xmlns="http://www.w3.org/2000/svg">
              <path d="M12 4v16m8-8H4" stroke-linecap="round" stroke-linejoin="round"
                    stroke-width="2"/>
            </svg>
            Lưu tạm
          </button>
        </div>
      </div>
    </template>
  </Modal>
</template>

<script lang="ts" setup>
import Modal from '@/components/profile/Modal.vue'

const props = defineProps<{
  invoice: {
    id: number;
    name: string;
    orders: { name: string; price: number; quantity: number }[];
    customer: any;
    [key: string]: any
  }
}>()

/** Tính tổng tiền hóa đơn */
const calculateTotal = (orders: { price: number; quantity: number }[]) => {
  return orders.reduce((sum, item) => sum + item.price * item.quantity, 0)
}
</script>
