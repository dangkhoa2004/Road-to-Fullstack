<template>
    <div class="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 gap-4 rounded-xl">
        <div v-for="(product, index) in products" :key="index"
            class="flex flex-col bg-white dark:bg-gray-800 rounded-xl overflow-hidden border border-gray-200 dark:border-gray-700 shadow hover:shadow-lg hover:border-brand-300 dark:hover:border-brand-500 transition transform hover:-translate-y-1 duration-300">
            <img :src="product.imagePath" :alt="product.name" class="w-full h-32 sm:h-36 md:h-40 object-cover" />
            <div class="p-3 flex-1 flex flex-col justify-between">
                <h3 class="text-sm font-semibold text-gray-800 dark:text-gray-200 truncate">
                    {{ product.name }}
                </h3>
                <p class="text-orange-500 font-bold mt-1">
                    {{ product.price.toLocaleString('vi-VN') }} VNĐ
                </p>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import type { PropType } from 'vue'

export default defineComponent({
    props: {
        products: {
            type: Array as PropType<
                { name: string; price: number; imagePath: string }[]
            >,
            required: true,
        },
    },
})
</script>