<template>
  <AdminLayout>
    <PageBreadcrumb :pageTitle="currentPageTitle"/>
    <div className="grid grid-cols-1 gap-5 sm:gap-6 xl:grid-cols-2">
      <div className="space-y-5 sm:space-y-6">
        <ComponentCard title="Video Ratio 16:9">
          <YouTubeEmbed videoId="dQw4w9WgXcQ"/>
        </ComponentCard>
        <ComponentCard title="Video Ratio 4:3">
          <YouTubeEmbed aspectRatio="4:3" videoId="dQw4w9WgXcQ"/>
        </ComponentCard>
      </div>
      <div className="space-y-5 sm:space-y-6">
        <ComponentCard title="Video Ratio 21:9">
          <YouTubeEmbed aspectRatio="21:9" videoId="dQw4w9WgXcQ"/>
        </ComponentCard>
        <ComponentCard title="Video Ratio 1:1">
          <YouTubeEmbed aspectRatio="1:1" videoId="dQw4w9WgXcQ"/>
        </ComponentCard>
      </div>
    </div>
  </AdminLayout>
</template>

<script setup>
import {ref} from 'vue'
import PageBreadcrumb from '@/components/common/PageBreadcrumb.vue'
import AdminLayout from '@/components/layout/AdminLayout.vue'
import ComponentCard from '@/components/common/ComponentCard.vue'
import YouTubeEmbed from '@/components/ui/YouTubeEmbed.vue'

const currentPageTitle = ref('Videos')
</script>

<style></style>
