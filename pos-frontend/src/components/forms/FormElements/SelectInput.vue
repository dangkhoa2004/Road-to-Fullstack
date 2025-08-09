<template>
  <div class="space-y-6">
    <!-- Single Select Input -->
    <div>
      <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">
        Select Input
      </label>
      <div class="relative z-20 bg-transparent">
        <select v-model="singleSelect"
                :class="{ 'text-gray-800 dark:text-white/90': singleSelect }"
                class="dark:bg-dark-900 h-11 w-full appearance-none rounded-lg border border-gray-300 bg-transparent bg-none px-4 py-2.5 pr-11 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800">
          <option disabled value="">Select Option</option>
          <option class="text-gray-700 dark:bg-gray-900 dark:text-gray-400" value="marketing">
            Marketing
          </option>
          <option class="text-gray-700 dark:bg-gray-900 dark:text-gray-400" value="template">
            Template
          </option>
          <option class="text-gray-700 dark:bg-gray-900 dark:text-gray-400" value="development">
            Development
          </option>
        </select>
        <span
          class="absolute z-30 text-gray-700 -translate-y-1/2 pointer-events-none right-4 top-1/2 dark:text-gray-400">
          <svg class="stroke-current" fill="none" height="20" viewBox="0 0 20 20" width="20"
               xmlns="http://www.w3.org/2000/svg">
            <path d="M4.79175 7.396L10.0001 12.6043L15.2084 7.396" stroke="" stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="1.5"/>
          </svg>
        </span>
      </div>
    </div>

    <!-- Multiple Select Input -->
    <div>
      <MultipleSelect v-model="selectedItems" :options="optionss" class="w-full"/>
    </div>
  </div>
</template>

<script lang="ts" setup>
import {ref, computed} from 'vue'
import MultipleSelect from './MultipleSelect.vue'

const optionss = [
  {value: 'apple', label: 'Apple'},
  {value: 'banana', label: 'Banana'},
  {value: 'cherry', label: 'Cherry'},
  {value: 'date', label: 'Date'},
  {value: 'elderberry', label: 'Elderberry'},
  {value: 'graphs', label: 'Graphs'},
]

const selectedItems = ref([])

const singleSelect = ref('')

const options = ref([
  {text: 'Option 1', selected: false},
  {text: 'Option 2', selected: false},
  {text: 'Option 3', selected: false},
  {text: 'Option 4', selected: false},
])

const selected = computed(() => options.value.filter((option) => option.selected))
</script>
