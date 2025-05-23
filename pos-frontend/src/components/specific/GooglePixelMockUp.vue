<template>
  <div
    class="flex flex-col lg:flex-row items-center justify-center min-h-screen bg-gray-100 dark:bg-gray-900 p-4 space-y-8 lg:space-y-0 lg:space-x-8">

    <div
      class="relative border-gray-800 dark:border-gray-800 bg-gray-800 border-[14px] rounded-xl h-[600px] w-[300px] shadow-xl flex-shrink-0">
      <div class="w-[148px] h-[18px] bg-gray-800 top-0 rounded-b-[1rem] left-1/2 -translate-x-1/2 absolute"></div>
      <div class="h-[32px] w-[3px] bg-gray-800 absolute -start-[17px] top-[72px] rounded-s-lg"></div>
      <div class="h-[46px] w-[3px] bg-gray-800 absolute -start-[17px] top-[124px] rounded-s-lg"></div>
      <div class="h-[46px] w-[3px] bg-gray-800 absolute -start-[17px] top-[178px] rounded-s-lg"></div>
      <div class="h-[64px] w-[3px] bg-gray-800 absolute -end-[17px] top-[142px] rounded-e-lg"></div>

      <div
        class="rounded-xl overflow-hidden w-[272px] h-[572px] bg-white dark:bg-gray-800 flex items-center justify-center text-gray-400 dark:text-gray-500 text-sm">
        <img src="https://via.placeholder.com/272x572?text=Your+App+Mockup" alt="App Mockup"
          class="w-full h-full object-cover">
      </div>
    </div>

    <div class="flex-1 max-w-lg text-center lg:text-left p-4">
      <h1 class="text-4xl font-extrabold text-gray-900 dark:text-white mb-4">Chào mừng đến với Ứng dụng của tôi!</h1>
      <p class="text-lg text-gray-700 dark:text-gray-300 mb-6 leading-relaxed">
        Khám phá một trải nghiệm độc đáo với ứng dụng web tiên tiến của chúng tôi. Ứng dụng này được thiết kế để giúp
        bạn quản lý các hoạt động hàng ngày một cách hiệu quả, cung cấp cái nhìn trực quan về tiến độ và các sự kiện
        quan trọng.
      </p>
      <p class="text-md text-gray-600 dark:text-gray-400">
        Với giao diện thân thiện và các tính năng mạnh mẽ, bạn sẽ dễ dàng theo dõi mọi thứ từ công việc đến cuộc sống cá
        nhân. Hãy cùng chúng tôi nâng cao năng suất và tổ chức công việc của bạn!
      </p>
      <button
        class="mt-8 px-6 py-3 bg-blue-600 text-white font-semibold rounded-lg shadow-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-75 transition duration-300">
        Tìm hiểu thêm
      </button>
    </div>

    <div
      class="w-full lg:w-[400px] h-[600px] bg-white dark:bg-gray-800 rounded-xl shadow-xl overflow-hidden flex-shrink-0">
      <TimelineApplication />
    </div>

  </div>
</template>

<script>
import TimelineApplication from './TimelineApplication.vue';

export default {
  name: 'GooglePixelMockUp',
  components: {
    TimelineApplication
  },
  setup() {

  }
}
</script>