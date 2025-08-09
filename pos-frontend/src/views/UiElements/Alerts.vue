<template>
  <AdminLayout>
    <PageBreadcrumb :pageTitle="currentPageTitle"/>
    <div className="space-y-5 sm:space-y-6">
      <ComponentCard title="Success Alert">
        <Alert :showLink="true" linkHref="/"
               linkText="Tìm hiểu thêm"
               message="Hãy thận trọng khi thực hiện hành động này." title="Tin nhắn thành công" variant="success"/>
        <Alert :showLink="false" message="Hãy thận trọng khi thực hiện hành động này."
               title="Tin nhắn thành công"
               variant="success"/>
      </ComponentCard>
      <ComponentCard title="Warning Alert">
        <Alert :showLink="true" linkHref="/"
               linkText="Tìm hiểu thêm"
               message="Hãy thận trọng khi thực hiện hành động này." title="Warning  Message" variant="warning"/>
        <Alert :showLink="false" message="Hãy thận trọng khi thực hiện hành động này."
               title="Warning Message Message"
               variant="warning"/>
      </ComponentCard>
      <ComponentCard title="Error Alert">
        <Alert :showLink="true" linkHref="/"
               linkText="Tìm hiểu thêm"
               message="Hãy thận trọng khi thực hiện hành động này." title="Error  Message" variant="error"/>
        <Alert :showLink="false" message="Hãy thận trọng khi thực hiện hành động này."
               title="Error Message Message"
               variant="error"/>
      </ComponentCard>
      <ComponentCard title="Info Alert">
        <Alert :showLink="true" linkHref="/"
               linkText="Tìm hiểu thêm" message="Hãy thận trọng khi thực hiện hành động này."
               title="Info  Message" variant="info"/>
        <Alert :showLink="false" message="Hãy thận trọng khi thực hiện hành động này."
               title="Info Message Message"
               variant="info"/>
      </ComponentCard>
    </div>
  </AdminLayout>
</template>

<script setup>
import {ref} from 'vue'
import PageBreadcrumb from '@/components/common/PageBreadcrumb.vue'
import AdminLayout from '@/components/layout/AdminLayout.vue'
import ComponentCard from '@/components/common/ComponentCard.vue'
import Alert from '@/components/ui/Alert.vue'

const currentPageTitle = ref('Cảnh báo')
</script>

<style></style>
