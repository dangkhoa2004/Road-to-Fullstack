<template>
  <div class="space-y-4">
    <div>
      <label class="block mb-1 text-sm font-medium text-gray-700 dark:text-gray-400">
        Tên khách hàng
      </label>
      <input :value="modelValue.name" class="w-full h-11 rounded-lg border border-gray-300 bg-transparent px-4 py-2 text-sm text-gray-800 shadow-theme-xs focus:border-brand-300 focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30" placeholder="Nhập tên khách hàng..."
             type="text"
             @input="onInput('name', $event)"/>
    </div>

    <div>
      <label class="block mb-1 text-sm font-medium text-gray-700 dark:text-gray-400">
        Số điện thoại
      </label>
      <input :value="modelValue.phone" class="w-full h-11 rounded-lg border border-gray-300 bg-transparent px-4 py-2 text-sm text-gray-800 shadow-theme-xs focus:border-brand-300 focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30" placeholder="Nhập số điện thoại..."
             type="text"
             @input="onInput('phone', $event)"/>
    </div>

    <div>
      <label class="block mb-1 text-sm font-medium text-gray-700 dark:text-gray-400">
        Ghi chú
      </label>
      <textarea :value="modelValue.note" class="w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2 text-sm text-gray-800 shadow-theme-xs focus:border-brand-300 focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30" placeholder="Viết chú thích....."
                rows="4"
                @input="onInput('note', $event)"></textarea>
    </div>
  </div>
</template>

<script lang="ts" setup>
import {defineProps, defineEmits} from 'vue'

const props = defineProps<{
  modelValue: { name: string; phone: string; note: string }
}>()

const emit = defineEmits<{
  (e: 'update:modelValue', value: { name: string; phone: string; note: string }): void
}>()

const onInput = (field: 'name' | 'phone' | 'note', e: Event) => {
  const target = e.target as HTMLInputElement | HTMLTextAreaElement | null
  if (!target) return
  emit('update:modelValue', {...props.modelValue, [field]: target.value})
}
</script>
