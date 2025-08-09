<template>
  <AdminLayout>
    <PageBreadcrumb :pageTitle="currentPageTitle"/>
    <div className="space-y-5 sm:space-y-6">
      <ComponentCard title="Default Avatar">
        <div className="flex flex-col items-center justify-center gap-5 sm:flex-row">
          <Avatar v-for="size in sizes" :key="size" :size="size" :src="avatarSrc"/>
        </div>
      </ComponentCard>
      <ComponentCard title="Avatar with online indicator">
        <div className="flex flex-col items-center justify-center gap-5 sm:flex-row">
          <Avatar v-for="size in sizes" :key="size" :size="size" :src="avatarSrc" status="online"/>
        </div>
      </ComponentCard>
      <ComponentCard title="Avatar with Offline indicator">
        <div className="flex flex-col items-center justify-center gap-5 sm:flex-row">
          <Avatar v-for="size in sizes" :key="size" :size="size" :src="avatarSrc" status="offline"/>
        </div>
      </ComponentCard>
      <ComponentCard title="Avatar with busy indicator">
        <div className="flex flex-col items-center justify-center gap-5 sm:flex-row">
          <Avatar v-for="size in sizes" :key="size" :size="size" :src="avatarSrc" status="busy"/>
        </div>
      </ComponentCard>
    </div>
  </AdminLayout>
</template>

<script lang="ts" setup>
import {ref} from 'vue'
import PageBreadcrumb from '@/components/common/PageBreadcrumb.vue'
import AdminLayout from '@/components/layout/AdminLayout.vue'
import ComponentCard from '@/components/common/ComponentCard.vue'
import Avatar from '@/components/ui/Avatar.vue'

const currentPageTitle = ref('Avatars')

const avatarSrc = '/images/user/user-01.jpg'
const sizes = ['xsmall', 'small', 'medium', 'large', 'xlarge', 'xxlarge'] as const
</script>
