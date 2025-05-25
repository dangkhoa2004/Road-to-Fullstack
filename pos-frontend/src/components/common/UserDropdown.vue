<template>
  <div id="user-dropdown-menu" :class="{ 'hidden': !isOpen }"
       class="absolute z-10 font-normal bg-white divide-y divide-gray-100 rounded-lg shadow-sm w-44 dark:bg-gray-700 dark:divide-gray-600 md:origin-top-right md:right-0 md:mt-2">
    <ul class="py-2 text-sm text-gray-700 dark:text-gray-400">
      <li>
        <router-link active-class="text-blue-700 dark:text-blue-500 font-bold" class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                     to=""
                     @click="closeAll">Dashboard
        </router-link>
      </li>
      <li>
        <router-link active-class="text-blue-700 dark:text-blue-500 font-bold" class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                     to=""
                     @click="closeAll">Cài đặt hồ sơ
        </router-link>
      </li>
      <li>
        <a class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white" href="#"
           @click.prevent="handleLogout">Đăng xuất</a>
      </li>
    </ul>
  </div>
</template>

<script>
import {defineComponent} from 'vue';

export default defineComponent({
  name: 'UserDropdown',
  props: {
    isOpen: {
      type: Boolean,
      required: true,
    },
  },
  emits: ['close-all-menus', 'logout'],
  setup(props, {emit}) {

    const closeAll = () => {
      emit('close-all-menus');
    };

    const handleLogout = () => {
      emit('logout');
    };

    return {
      closeAll,
      handleLogout,
    };
  },
});
</script>