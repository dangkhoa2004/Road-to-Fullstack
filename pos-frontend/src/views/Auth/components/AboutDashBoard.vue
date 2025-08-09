<template>
  <div class="relative items-center hidden w-full h-full lg:w-1/2 bg-black lg:grid">
    <div class="flex flex-col items-center justify-center z-1 p-6 gap-6">
      <common-grid-shape/>
      <div
        class="flex flex-col items-center max-w-sm text-center text-white dark:text-white/90 gap-4">
        <h2 class="flex items-center gap-2 text-2xl font-bold text-brand-400 tracking-wide">
          <svg class="w-7 h-7" fill="none" stroke="currentColor" stroke-width="2"
               viewBox="0 0 24 24">
            <path d="M12 4v16m8-8H4" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
          Hành Trình Fullstack
        </h2>

        <p class="text-sm text-gray-400 leading-relaxed">
          Đây là <span class="font-semibold text-brand-400">một tài liệu sống</span>,
          lưu lại toàn bộ quá trình học và xây dựng ứng dụng – từ kiến thức cơ bản
          đến một hệ thống hoàn chỉnh.
        </p>

        <div class="flex flex-col items-start gap-2 w-full mt-2 text-sm text-gray-300">
          <div class="flex items-center gap-2">
            <svg class="w-5 h-5 text-brand-400" fill="none" stroke="currentColor" stroke-width="2"
                 viewBox="0 0 24 24">
              <path d="M5 13l4 4L19 7" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
            <span>Áp dụng thực tế</span>
          </div>

          <div class="flex items-center gap-2">
            <svg class="w-5 h-5 text-brand-400" fill="none" stroke="currentColor" stroke-width="2"
                 viewBox="0 0 24 24">
              <path d="M12 8c-1.1 0-2 .9-2 2 0 .35.09.68.24.97C9.11 11.54 8 13.2 8 15h8c0-1.8-1.11-3.46-2.24-4.03.15-.29.24-.62.24-.97 0-1.1-.9-2-2-2z" stroke-linecap="round"
                    stroke-linejoin="round"/>
            </svg>
            <span>Tư duy giải quyết vấn đề</span>
          </div>

          <div class="flex items-center gap-2">
            <svg class="w-5 h-5 text-brand-400" fill="none" stroke="currentColor" stroke-width="2"
                 viewBox="0 0 24 24">
              <path d="M12 11c0 .83-.34 1.58-.88 2.12a3 3 0 01-4.24 0A3 3 0 015 11V5a2 2 0 012-2h6a2 2 0 012 2v6z" stroke-linecap="round"
                    stroke-linejoin="round"/>
              <path d="M17 17h-2v2h2v-2z" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
            <span>Kỹ năng toàn diện</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import CommonGridShape from '@/components/common/CommonGridShape.vue';

export default {
  components: {
    CommonGridShape,
  },
};
</script>
