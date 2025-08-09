<template>
  <div :class="['overflow-hidden rounded-lg', aspectRatioClass, className]">
    <iframe :src="`https://www.youtube.com/embed/${videoId}`" :title="title" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
            allowfullscreen
            class="w-full h-full"
            frameborder="0"></iframe>
  </div>
</template>

<script lang="ts" setup>
import {computed} from 'vue'

type AspectRatio = '16:9' | '4:3' | '21:9' | '1:1'

interface Props {
  videoId: string
  aspectRatio?: AspectRatio
  title?: string
  className?: string
}

const props = withDefaults(defineProps<Props>(), {
  aspectRatio: '16:9',
  title: 'YouTube video',
  className: '',
})

const aspectRatioClass = computed(() => {
  const aspectRatioClasses = {
    '16:9': 'aspect-video',
    '4:3': 'aspect-4/3',
    '21:9': 'aspect-21/9',
    '1:1': 'aspect-square',
  }
  return aspectRatioClasses[props.aspectRatio]
})
</script>
