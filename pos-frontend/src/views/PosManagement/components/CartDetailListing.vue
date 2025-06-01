<template>
    <div>
        <div class="flex items-center justify-between mb-2">
            <h2 class="text-lg font-bold text-gray-800 dark:text-gray-100">
                Chi tiết đơn hàng
            </h2>
            <button class="text-xs text-red-500 cursor-pointer hover:underline" @click="$emit('clear')">
                Làm mới
            </button>
        </div>

        <div v-for="(order, index) in orders" :key="index" class="flex items-center justify-between py-1">
            <div>
                <p class="text-sm font-medium text-gray-800 dark:text-gray-200">
                    {{ order.name }}
                </p>
                <p class="text-xs text-gray-500 dark:text-gray-400">
                    ${{ order.price }}
                </p>
            </div>
            <div class="flex items-center gap-1">
                <button class="px-2 py-1 border rounded text-gray-700 dark:text-gray-300 dark:border-gray-600"
                    @click="$emit('decrease', index)">
                    -
                </button>
                <span class="w-6 text-center text-gray-800 dark:text-gray-200">
                    {{ order.quantity }}
                </span>
                <button class="px-2 py-1 border rounded text-gray-700 dark:text-gray-300 dark:border-gray-600"
                    @click="$emit('increase', index)">
                    +
                </button>
            </div>
        </div>
    </div>
</template>
<script setup lang="ts">
import { defineProps, defineEmits } from 'vue'

const props = defineProps<{
    orders: { name: string; price: number; quantity: number }[]
}>()

const emit = defineEmits(['increase', 'decrease', 'clear'])
</script>
