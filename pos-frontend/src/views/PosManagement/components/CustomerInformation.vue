<template>
    <div class="space-y-4">
        <div>
            <label class="block mb-1 text-sm font-medium text-gray-700 dark:text-gray-400">
                Thông tin khách hàng
            </label>
            <input type="text" :value="modelValue.input" @input="onInput('input', $event)"
                class="w-full h-11 rounded-lg border border-gray-300 bg-transparent px-4 py-2 text-sm text-gray-800 shadow-theme-xs focus:border-brand-300 focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30" />
        </div>

        <div>
            <label class="block mb-1 text-sm font-medium text-gray-700 dark:text-gray-400">
                Ghi chú
            </label>
            <textarea rows="4" :value="modelValue.description" @input="onInput('description', $event)"
                placeholder="Viết chú thích....."
                class="w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2 text-sm text-gray-800 shadow-theme-xs focus:border-brand-300 focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30"></textarea>
        </div>
    </div>
</template>

<script setup lang="ts">
import { defineProps, defineEmits } from 'vue'

const props = defineProps<{
    modelValue: { input: string; description: string }
}>()

const emit = defineEmits<{
    (e: 'update:modelValue', value: { input: string; description: string }): void
}>()

const onInput = (
    field: 'input' | 'description',
    e: Event
) => {
    const target = e.target as HTMLInputElement | HTMLTextAreaElement | null
    if (!target) return
    emit('update:modelValue', { ...props.modelValue, [field]: target.value })
}
</script>
