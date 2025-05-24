<template>
    <div class="flex items-center justify-center py-4 md:py-8 flex-wrap">
        <button type="button"
            class="text-blue-700 hover:text-white border border-blue-600 bg-white hover:bg-blue-700 focus:ring-2 focus:outline-none focus:ring-blue-300 rounded-full text-base font-medium px-5 py-2.5 text-center me-3 mb-3 dark:border-blue-500 dark:text-blue-500 dark:hover:text-white dark:hover:bg-blue-500 dark:bg-white">Tất cả danh mục</button>
        <button type="button"
            class="text-gray-900 border border-white hover:border-gray-200 dark:border-gray-900 dark:bg-white dark:hover:border-gray-700 bg-white focus:ring-2 focus:outline-none focus:ring-gray-300 rounded-full text-base font-medium px-5 py-2.5 text-center me-3 mb-3 dark:text-black dark:focus:ring-gray-800">Trà sữa truyền thống</button>
        <button type="button"
            class="text-gray-900 border border-white hover:border-gray-200 dark:border-gray-900 dark:bg-white dark:hover:border-gray-700 bg-white focus:ring-2 focus:outline-none focus:ring-gray-300 rounded-full text-base font-medium px-5 py-2.5 text-center me-3 mb-3 dark:text-black dark:focus:ring-gray-800">Cà phê - sinh tố - nước trái cây</button>
        <button type="button"
            class="text-gray-900 border border-white hover:border-gray-200 dark:border-gray-900 dark:bg-white dark:hover:border-gray-700 bg-white focus:ring-2 focus:outline-none focus:ring-gray-300 rounded-full text-base font-medium px-5 py-2.5 text-center me-3 mb-3 dark:text-black dark:focus:ring-gray-800">Các món ăn nhẹ - bánh - snack</button>
    </div>
    <div class="grid grid-cols-2 md:grid-cols-3 gap-4">
        <div>
            <img class="h-auto max-w-full rounded-lg"
                src="@/assets/images/tstt.jpg" alt="">
        </div>
        <div>
            <img class="h-auto max-w-full rounded-lg"
                src="@/assets/images/caphe.jpg" alt="">
        </div>
        <div>
            <img class="h-auto max-w-full rounded-lg"
                src="@/assets/images/tstx.jpg" alt="">
        </div>
        <div>
            <img class="h-auto max-w-full rounded-lg"
                src="@/assets/images/snack.jpg" alt="">
        </div>
        <div>
            <img class="h-auto max-w-full rounded-lg"
                src="@/assets/images/cake.webp" alt="">
        </div>
        <div>
            <img class="h-auto max-w-full rounded-lg"
                src="@/assets/images/tsdn.jpg" alt="">
        </div>
    </div>

</template>