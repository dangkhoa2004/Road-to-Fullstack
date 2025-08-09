<template>
  <div class="space-y-6">
    <div class="flex flex-wrap items-center gap-8">
      <!-- Default Checkbox -->
      <div>
        <label class="flex items-center text-sm font-medium text-gray-700 cursor-pointer select-none dark:text-gray-400"
               for="checkboxLabelOne">
          <div class="relative">
            <input id="checkboxLabelOne" v-model="checkboxOne" class="sr-only" type="checkbox"/>
            <div :class="checkboxOne
                ? 'border-brand-500 bg-brand-500'
                : 'bg-transparent border-gray-300 dark:border-gray-700'
              "
                 class="mr-3 flex h-5 w-5 items-center justify-center rounded-md border-[1.25px] hover:border-brand-500 dark:hover:border-brand-500">
              <span :class="checkboxOne ? '' : 'opacity-0'">
                <svg fill="none" height="14" viewBox="0 0 14 14" width="14"
                     xmlns="http://www.w3.org/2000/svg">
                  <path d="M11.6666 3.5L5.24992 9.91667L2.33325 7" stroke="white"
                        stroke-linecap="round"
                        stroke-linejoin="round" stroke-width="1.94437"/>
                </svg>
              </span>
            </div>
          </div>
          Default
        </label>
      </div>

      <!-- Checked Checkbox -->
      <div>
        <label class="flex items-center text-sm font-medium text-gray-700 cursor-pointer select-none dark:text-gray-400"
               for="checkboxLabelTwo">
          <div class="relative">
            <input id="checkboxLabelTwo" v-model="checkboxTwo" class="sr-only" type="checkbox"/>
            <div :class="checkboxTwo
                ? 'border-brand-500 bg-brand-500'
                : 'bg-transparent border-gray-300 dark:border-gray-700'
              "
                 class="mr-3 flex h-5 w-5 items-center justify-center rounded-md border-[1.25px] hover:border-brand-500 dark:hover:border-brand-500">
              <span :class="checkboxTwo ? '' : 'opacity-0'">
                <svg fill="none" height="14" viewBox="0 0 14 14" width="14"
                     xmlns="http://www.w3.org/2000/svg">
                  <path d="M11.6666 3.5L5.24992 9.91667L2.33325 7" stroke="white"
                        stroke-linecap="round"
                        stroke-linejoin="round" stroke-width="1.94437"/>
                </svg>
              </span>
            </div>
          </div>
          Checked
        </label>
      </div>

      <!-- Disabled Checkbox -->
      <div>
        <label class="flex items-center text-sm font-medium text-gray-300 cursor-pointer select-none dark:text-gray-700"
               for="checkboxLabelThree">
          <div class="relative">
            <input id="checkboxLabelThree" v-model="checkboxThree" class="sr-only peer"
                   disabled type="checkbox"/>
            <div :class="checkboxThree
                ? 'bg-transparent border-gray-200 dark:border-gray-800'
                : 'border-brand-500 bg-brand-500'
              " class="mr-3 flex h-5 w-5 items-center justify-center rounded-md border-[1.25px]">
              <span :class="checkboxThree ? '' : 'opacity-0'">
                <svg class="stroke-gray-200 dark:stroke-gray-800" fill="none" height="14"
                     viewBox="0 0 14 14" width="14"
                     xmlns="http://www.w3.org/2000/svg">
                  <path d="M11.6666 3.5L5.24992 9.91667L2.33325 7" stroke="" stroke-linecap="round"
                        stroke-linejoin="round" stroke-width="2.33333"/>
                </svg>
              </span>
            </div>
          </div>
          Disabled
        </label>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import {ref} from 'vue'

const checkboxOne = ref(false)
const checkboxTwo = ref(true)
const checkboxThree = ref(true)
</script>
