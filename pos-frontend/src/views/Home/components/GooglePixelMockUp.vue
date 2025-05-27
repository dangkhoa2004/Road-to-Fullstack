<template>
  <div
      class="flex flex-col lg:flex-row items-center justify-center min-h-screen p-4 space-y-8 lg:space-y-0 lg:space-x-8">
    <div
        class="relative border-gray-800 dark:border-gray-800 bg-gray-800 border-[14px] rounded-xl h-[600px] w-[300px] shadow-xl flex-shrink-0">
      <div class="w-[148px] h-[18px] bg-gray-800 top-0 rounded-b-[1rem] left-1/2 -translate-x-1/2 absolute"></div>
      <div class="h-[32px] w-[3px] bg-gray-800 absolute -start-[17px] top-[72px] rounded-s-lg"></div>
      <div class="h-[46px] w-[3px] bg-gray-800 absolute -start-[17px] top-[124px] rounded-s-lg"></div>
      <div class="h-[46px] w-[3px] bg-gray-800 absolute -start-[17px] top-[178px] rounded-s-lg"></div>
      <div class="h-[64px] w-[3px] bg-gray-800 absolute -end-[17px] top-[142px] rounded-e-lg"></div>

      <div
          class="rounded-xl overflow-hidden w-[272px] h-[572px] bg-white flex items-center justify-center text-gray-400 dark:text-gray-500 text-sm">
        <img alt="App Mockup" class="w-full h-full object-cover"
             src="">
      </div>
    </div>

    <div class="flex-1 max-w-lg text-center lg:text-left p-4">
      <h1 class="text-4xl font-extrabold text-gray-900 dark:text-black mb-4">Chào mừng đến với Ứng dụng của tôi!</h1>
      <div class="flex items-center p-4">
        <svg aria-hidden="true" class="w-4 h-4 text-yellow-300 me-1" fill="currentColor"
             viewBox="0 0 22 20" xmlns="http://www.w3.org/2000/svg">
          <path
              d="M20.924 7.625a1.523 1.523 0 0 0-1.238-1.044l-5.051-.734-2.259-4.577a1.534 1.534 0 0 0-2.752 0L7.365 5.847l-5.051.734A1.535 1.535 0 0 0 1.463 9.2l3.656 3.563-.863 5.031a1.532 1.532 0 0 0 2.226 1.616L11 17.033l4.518 2.375a1.534 1.534 0 0 0 2.226-1.617l-.863-5.03L20.537 9.2a1.523 1.523 0 0 0 .387-1.575Z"/>
        </svg>
        <p class="ms-2 text-sm font-bold text-gray-900 dark:text-black">4.95</p>
        <span class="w-1 h-1 mx-1.5 bg-gray-500 rounded-full dark:bg-gray-400"></span>
        <a class="text-sm font-medium text-gray-900 underline hover:no-underline dark:text-black" href="#">73
          đánh giá</a>
      </div>
      <p class="text-lg text-gray-700 dark:text-black mb-6 leading-relaxed">
        Khám phá một trải nghiệm độc đáo với ứng dụng web tiên tiến của chúng tôi. Ứng dụng này được thiết kế để giúp
        bạn quản lý các hoạt động hàng ngày một cách hiệu quả, cung cấp cái nhìn trực quan về tiến độ và các sự kiện
        quan trọng.
      </p>
      <p class="text-md text-gray-600 dark:text-black">
        Với giao diện thân thiện và các tính năng mạnh mẽ, bạn sẽ dễ dàng theo dõi mọi thứ từ công việc đến cuộc sống cá
        nhân. Hãy cùng chúng tôi nâng cao năng suất và tổ chức công việc của bạn!
      </p>
      <button
          class="mt-8 px-6 py-3 bg-blue-600 text-white font-semibold rounded-lg shadow-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-75 transition duration-300">
        Tìm hiểu thêm
      </button>
    </div>

    <div
        class="w-full lg:w-[400px] bg-white dark:bg-gray-800 rounded-xl shadow-xl overflow-hidden flex-shrink-0">
      <TimelineApplication/>
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