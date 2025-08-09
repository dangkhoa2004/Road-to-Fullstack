<template>
  <AdminLayout>
    <PageBreadcrumb :pageTitle="currentPageTitle"/>
    <div class="space-y-5 sm:space-y-6">
      <ComponentCard title="Xem code mới">
        <div class="flex flex-col gap-2">
          <button v-if="isAdmin" class="bg-green-500 text-white px-3 py-1 rounded">
            Admin Only Action
          </button>
          <button v-if="canEditProducts" class="bg-blue-500 text-white px-3 py-1 rounded">
            Chỉnh sửa sản phẩm
          </button>
        </div>

        <div class="mt-4 text-sm text-gray-700">
          <h4 class="font-semibold">Thông tin người dùng:</h4>
          <p><strong>Tên:</strong> {{ user.name }}</p>
          <p><strong>Email:</strong> {{ user.email }}</p>
          <p><strong>Số điện thoại:</strong> {{ user.phone }}</p>
          <p><strong>Role:</strong> {{ user.role?.name }}</p>

          <div class="mt-2">
            <strong>Permissions:</strong>
            <ul class="list-disc list-inside ml-4">
              <li v-for="(perm, index) in user.permissions" :key="index">{{ perm }}</li>
            </ul>
          </div>
        </div>
      </ComponentCard>
    </div>
  </AdminLayout>
</template>

<script lang="ts" setup>
import {useStore} from 'vuex'
import {computed, ref} from 'vue'
import PageBreadcrumb from '@/components/common/PageBreadcrumb.vue'
import AdminLayout from '@/components/layout/AdminLayout.vue'
import ComponentCard from '@/components/common/ComponentCard.vue'

const store = useStore()
const currentPageTitle = ref('Xem code mới')

const isAdmin = computed(() =>
  store.getters['auth/hasRole']('Quản trị viên')
)
const canEditProducts = computed(() =>
  store.getters['auth/hasPermission']('edit_products')
)
const user = computed(() => store.state.auth.user)
</script>
