<template>
  <AdminLayout>
    <PageBreadcrumb :pageTitle="currentPageTitle" />
    <ComponentCard title="Checkboxes">
      <CheckboxInput />
    </ComponentCard>
  </AdminLayout>
</template>

<script setup>
import { ref } from 'vue'
import PageBreadcrumb from '@/components/common/PageBreadcrumb.vue'
import AdminLayout from '@/components/layout/AdminLayout.vue'
import ComponentCard from '@/components/common/ComponentCard.vue'
import CheckboxInput from '@/components/forms/FormElements/CheckboxInput.vue'

const currentPageTitle = ref('Cài đặt phân quyền')
</script>
