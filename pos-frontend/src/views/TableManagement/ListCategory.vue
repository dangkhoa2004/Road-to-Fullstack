<template>
  <AdminLayout>
    <PageBreadcrumb :pageTitle="currentPageTitle" />
    <div class="space-y-5 sm:space-y-6">
      <ComponentCard title="Bảng danh mục">
        <BasicTableOne :columns="categoryColumns" :rows="categories">
          <template #cell-description="{ row }">
            <span>{{ row.description }}</span>
          </template>
        </BasicTableOne>
      </ComponentCard>
    </div>
  </AdminLayout>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import { mapActions, mapGetters } from 'vuex'
import * as types from '@/store/types.ts'
import PageBreadcrumb from '@/components/common/PageBreadcrumb.vue'
import AdminLayout from '@/components/layout/AdminLayout.vue'
import ComponentCard from '@/components/common/ComponentCard.vue'
import BasicTableOne from '@/components/tables/basic-tables/BasicTableOne.vue'
import type { Category } from '@/api/category'

export default defineComponent({
  components: {
    PageBreadcrumb,
    AdminLayout,
    ComponentCard,
    BasicTableOne,
  },
  data() {
    return {
      currentPageTitle: 'Bảng danh mục',
      categoryColumns: [
        { key: 'id', label: 'ID' },
        { key: 'name', label: 'Tên danh mục' },
        { key: 'description', label: 'Mô tả' },
        // { key: 'created_at', label: 'Ngày tạo' },
        // { key: 'updated_at', label: 'Ngày cập nhật' },
        // { key: 'version', label: 'Phiên bản' },
      ],
    }
  },
  computed: {
    ...mapGetters('category', {
      categories: types.GET_CATEGORIES,
      categoryError: types.GET_CATEGORY_ERROR,
    }),
  },
  methods: {
    ...mapActions('category', {
      fetchCategories: types.FETCH_CATEGORIES,
    }),
    async loadCategories(): Promise<void> {
      try {
        await this.fetchCategories()
      } catch (error) {
        console.error('Không thể tải danh mục:', error)
      }
    },
  },
  created() {
    this.loadCategories()
  },
})
</script>
