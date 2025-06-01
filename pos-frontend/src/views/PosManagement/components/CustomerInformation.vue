<template>
    <div class="space-y-4">
        <div>
            <label class="block mb-1 text-sm font-medium text-gray-700 dark:text-gray-400">
                Tên khách hàng
            </label>
            <input type="text" :value="modelValue.name" @input="onInput('name', $event)"
                placeholder="Nhập tên khách hàng..."
                class="w-full h-11 rounded-lg border border-gray-300 bg-transparent px-4 py-2 text-sm text-gray-800 shadow-theme-xs focus:border-brand-300 focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30" />
        </div>

        <div>
            <label class="block mb-1 text-sm font-medium text-gray-700 dark:text-gray-400">
                Số điện thoại
            </label>
            <input type="text" :value="modelValue.phone" @input="onInput('phone', $event)"
                placeholder="Nhập số điện thoại..."
                class="w-full h-11 rounded-lg border border-gray-300 bg-transparent px-4 py-2 text-sm text-gray-800 shadow-theme-xs focus:border-brand-300 focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30" />
        </div>

        <div>
            <label class="block mb-1 text-sm font-medium text-gray-700 dark:text-gray-400">
                Ghi chú
            </label>
            <textarea rows="4" :value="modelValue.note" @input="onInput('note', $event)"
                placeholder="Viết chú thích....."
                class="w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2 text-sm text-gray-800 shadow-theme-xs focus:border-brand-300 focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30"></textarea>
        </div>
    </div>
</template>

<script setup lang="ts">
import { defineProps, defineEmits } from 'vue'

const props = defineProps<{
    modelValue: { name: string; phone: string; note: string }
}>()

const emit = defineEmits<{
    (e: 'update:modelValue', value: { name: string; phone: string; note: string }): void
}>()

const onInput = (field: 'name' | 'phone' | 'note', e: Event) => {
    const target = e.target as HTMLInputElement | HTMLTextAreaElement | null
    if (!target) return
    emit('update:modelValue', { ...props.modelValue, [field]: target.value })
}
</script>
