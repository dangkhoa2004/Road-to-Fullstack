<template>
  <aside :class="[
    'fixed mt-16 flex flex-col lg:mt-0 top-0 px-5 left-0 bg-white dark:bg-gray-900 dark:border-gray-800 text-gray-900 h-screen transition-all duration-300 ease-in-out z-99999 border-r border-gray-200',
    {
      'lg:w-[290px]': isExpanded || isMobileOpen,
      'lg:w-[90px]': !isExpanded,
      'translate-x-0 w-[290px]': isMobileOpen,
      '-translate-x-full': !isMobileOpen,
      'lg:translate-x-0': true,
    },
  ]">
    <!-- Logo + Tên app -->
    <div :class="['py-8 flex', isExpanded ? 'justify-start' : 'lg:justify-center']">
      <router-link to="/" class="flex items-center gap-2">
        <img v-if="isExpanded" class="dark:hidden rounded" src="/images/logo/logo.png" alt="Logo" width="40"
          height="40" />
        <img v-if="isExpanded" class="hidden dark:block rounded" src="/images/logo/logo.png" alt="Logo" width="40"
          height="40" />
        <img v-else src="/images/logo/logo.png" alt="Logo" width="32" height="32" class="rounded" />
        <h1 v-show="isExpanded && !isMobileOpen"
          class="text-lg font-semibold text-gray-800 dark:text-white tracking-wide">
          POS-APPLICATION
        </h1>
      </router-link>
    </div>

    <!-- Menu -->
    <div class="flex flex-col overflow-y-auto duration-300 ease-linear no-scrollbar">
      <nav class="mb-6">
        <div class="flex flex-col gap-4">
          <div v-for="(menuGroup, groupIndex) in menuGroups" :key="groupIndex">
            <h2 :class="[
              'mb-4 text-xs uppercase flex leading-[20px] text-gray-400',
              isExpanded ? 'justify-start' : 'lg:justify-center',
            ]">
              <template v-if="isExpanded || isMobileOpen">
                {{ menuGroup.title }}
              </template>
              <HorizontalDots v-else />
            </h2>

            <ul class="flex flex-col gap-4">
              <li v-for="(item, index) in menuGroup.items" :key="item.name">
                <!-- Có submenu -->
                <button v-if="item.subItems" @click="toggleSubmenu(groupIndex, index)" :class="[
                  'menu-item group w-full',
                  {
                    'menu-item-active': isSubmenuOpen(groupIndex, index),
                    'menu-item-inactive': !isSubmenuOpen(groupIndex, index),
                  },
                  isExpanded ? 'lg:justify-start' : 'lg:justify-center',
                ]">
                  <span :class="[
                    isSubmenuOpen(groupIndex, index)
                      ? 'menu-item-icon-active'
                      : 'menu-item-icon-inactive',
                  ]">
                    <component :is="item.icon" />
                  </span>
                  <span v-if="isExpanded || isMobileOpen" class="menu-item-text">
                    {{ item.name }}
                  </span>
                  <ChevronDownIcon v-if="isExpanded || isMobileOpen" :class="[
                    'ml-auto w-5 h-5 transition-transform duration-200',
                    {
                      'rotate-180 text-brand-500': isSubmenuOpen(groupIndex, index),
                    },
                  ]" />
                </button>

                <!-- Không có submenu -->
                <router-link v-else-if="item.path" :to="item.path" :class="[
                  'menu-item group',
                  {
                    'menu-item-active': isActive(item.path),
                    'menu-item-inactive': !isActive(item.path),
                  },
                ]">
                  <span :class="[
                    isActive(item.path)
                      ? 'menu-item-icon-active'
                      : 'menu-item-icon-inactive',
                  ]">
                    <component :is="item.icon" />
                  </span>
                  <span v-if="isExpanded || isMobileOpen" class="menu-item-text">
                    {{ item.name }}
                  </span>
                </router-link>

                <!-- Submenu -->
                <transition @enter="startTransition" @after-enter="endTransition" @before-leave="startTransition"
                  @after-leave="endTransition">
                  <div v-show="isSubmenuOpen(groupIndex, index) && (isExpanded || isMobileOpen)">
                    <ul class="mt-2 space-y-1 ml-9">
                      <li v-for="subItem in item.subItems" :key="subItem.name">
                        <router-link :to="subItem.path" :class="[
                          'menu-dropdown-item',
                          {
                            'menu-dropdown-item-active': isActive(subItem.path),
                            'menu-dropdown-item-inactive': !isActive(subItem.path),
                          },
                        ]">
                          {{ subItem.name }}
                          <span class="flex items-center gap-1 ml-auto">
                            <span v-if="subItem.new" class="menu-dropdown-badge" :class="{
                              'menu-dropdown-badge-active': isActive(subItem.path),
                              'menu-dropdown-badge-inactive': !isActive(subItem.path),
                            }">
                              Mới
                            </span>
                            <span v-if="subItem.pro" class="menu-dropdown-badge" :class="{
                              'menu-dropdown-badge-active': isActive(subItem.path),
                              'menu-dropdown-badge-inactive': !isActive(subItem.path),
                            }">
                              Pro
                            </span>
                          </span>
                        </router-link>
                      </li>
                    </ul>
                  </div>
                </transition>
              </li>
            </ul>
          </div>
        </div>
      </nav>
    </div>
  </aside>
</template>

<script setup>
import { ref, computed } from "vue";
import { useRoute } from "vue-router";
import {
  SettingsIcon,
  GridIcon,
  CalenderIcon,
  UserCircleIcon,
  ChevronDownIcon,
  HorizontalDots,
  PageIcon,
  TableIcon,
  ListIcon,
  PieChartIcon,
} from "../../icons";
import BoxCubeIcon from "@/icons/BoxCubeIcon.vue";
import { useSidebar } from "@/composables/useSidebar";

const route = useRoute();
const { isExpanded, isMobileOpen, openSubmenu } = useSidebar();

const menuGroups = [
  {
    title: "Chính",
    items: [
      {
        icon: GridIcon,
        name: "Trang chủ",
        subItems: [
          { name: "Thương mại điện tử", path: "/", pro: false },
          { name: "Điểm bán hàng", path: "/pos", pro: false },
        ],
      },
      {
        icon: CalenderIcon,
        name: "Lịch",
        path: "/calendar",
      },
      {
        icon: UserCircleIcon,
        name: "Hồ sơ người dùng",
        path: "/profile",
      },
      {
        name: "Biểu mẫu",
        icon: ListIcon,
        subItems: [
          { name: "Phần tử biểu mẫu", path: "/form-elements", pro: false },

        ],
      },
      {
        name: "Bảng",
        icon: TableIcon,
        subItems: [
          { name: "Sản phẩm", path: "/products", pro: false },
          { name: "Danh mục", path: "/categorys", pro: false },
          { name: "Giảm giá", path: "/discounts", pro: false },
          { name: "Hóa đơn", path: "/invoices", pro: false },
          { name: "Nhập / Xuất kho", path: "/stocks", pro: false },
        ]
      },
      {
        name: "Các trang",
        icon: PageIcon,
        subItems: [
          { name: "Trang trắng", path: "/blank", pro: false },
          { name: "Trang lỗi 404", path: "/error-404", pro: false },
        ],
      },
    ],
  },
  {
    title: "Khác",
    items: [
      {
        icon: SettingsIcon,
        name: "Hệ thống",
        subItems: [
          { name: "Khách hàng", path: "/customers", pro: false },
          { name: "Phân quyền", path: "/permissions", pro: false },
          { name: "Code mới", path: "/developer", pro: false },
        ],
      },
      {
        icon: PieChartIcon,
        name: "Biểu đồ",
        subItems: [
          { name: "Biểu đồ đường", path: "/line-chart", pro: false },
          { name: "Biểu đồ thanh", path: "/bar-chart", pro: false },
        ],
      },
      {
        icon: BoxCubeIcon,
        name: "Thành phần UI",
        subItems: [
          { name: "Cảnh báo", path: "/alerts", pro: false },
          { name: "Ảnh đại diện", path: "/avatars", pro: false },
          { name: "Huy hiệu", path: "/badge", pro: false },
          { name: "Nút bấm", path: "/buttons", pro: false },
          { name: "Hình ảnh", path: "/images", pro: false },
          { name: "Videos", path: "/videos", pro: false },
        ],
      }
    ],
  },
];

const isActive = (path) => route.path === path;

const toggleSubmenu = (groupIndex, itemIndex) => {
  const key = `${groupIndex}-${itemIndex}`;
  openSubmenu.value = openSubmenu.value === key ? null : key;
};

const isAnySubmenuRouteActive = computed(() => {
  return menuGroups.some((group) =>
    group.items.some(
      (item) =>
        item.subItems &&
        item.subItems.some((subItem) => isActive(subItem.path))
    )
  );
});

const isSubmenuOpen = (groupIndex, itemIndex) => {
  const key = `${groupIndex}-${itemIndex}`;
  return (
    openSubmenu.value === key ||
    (isAnySubmenuRouteActive.value &&
      menuGroups[groupIndex].items[itemIndex].subItems?.some((subItem) =>
        isActive(subItem.path)
      ))
  );
};

const startTransition = (el) => {
  el.style.height = "auto";
  const height = el.scrollHeight;
  el.style.height = "0px";
  el.offsetHeight;
  el.style.height = height + "px";
};

const endTransition = (el) => {
  el.style.height = "";
};
</script>
