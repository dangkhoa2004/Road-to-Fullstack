<template>
  <div class="file-uploader">
    <form :id="dropzoneId" ref="dropzoneForm" :action="uploadUrl"
          class="border-gray-300 border-dashed dropzone rounded-xl bg-gray-50 p-7 hover:border-brand-500 dark:border-gray-700 dark:bg-gray-900 dark:hover:border-brand-500 lg:p-10">
      <div class="dz-message m-0!">
        <div class="mb-[22px] flex justify-center">
          <div
            class="flex h-[68px] w-[68px] items-center justify-center rounded-full bg-gray-200 text-gray-700 dark:bg-gray-800 dark:text-gray-400">
            <EditbuttonSvg/>
          </div>
        </div>

        <h4 class="mb-3 font-semibold text-gray-800 text-theme-xl dark:text-white/90">
          Drag & Drop File Here
        </h4>
        <span
          class="mx-auto mb-5 block w-full max-w-[290px] text-sm text-gray-700 dark:text-gray-400">
          Drag and drop your PNG, JPG, WebP, SVG images here or browse
        </span>

        <span class="font-medium underline cursor-pointer text-theme-sm text-brand-500">
          Browse File
        </span>
      </div>
    </form>
  </div>
</template>

<script setup>
import {ref, onMounted, onBeforeUnmount} from 'vue'
import Dropzone from 'dropzone'
import 'dropzone/dist/dropzone.css'
import EditbuttonSvg from '@/assets/svg-icon/upload-svg.vue'

const props = defineProps({
  uploadUrl: {
    type: String,
    default: '/upload',
  },
})

const dropzoneForm = ref(null)
const dropzoneId = `dropzone-${Math.random().toString(36).substr(2, 9)}`
let dropzoneInstance = null

onMounted(() => {
  Dropzone.autoDiscover = false

  dropzoneInstance = new Dropzone(`#${dropzoneId}`, {
    url: props.uploadUrl,
    thumbnailWidth: 150,
    maxFilesize: 0.5,
    acceptedFiles: 'image/jpeg,image/png,image/gif,image/webp,image/svg+xml',
    headers: {'My-Awesome-Header': 'header value'},
    dictDefaultMessage: '',
    init: function () {
      this.on('addedfile', (file) => {
        console.log('A file has been added', file)
      })
      this.on('success', (file, response) => {
        console.log('File successfully uploaded', file, response)
      })
      this.on('error', (file, error) => {
        console.error('An error occurred during upload', file, error)
      })
    },
  })
})

onBeforeUnmount(() => {
  if (dropzoneInstance) {
    dropzoneInstance.destroy()
  }
})
</script>

<style>
.dropzone {
  border: 1px dashed #d0d5dd;
  transition: all 0.3s ease;
}

.dropzone:hover {
  border-color: #465fff;
}

.dropzone .dz-preview {
  margin: 10px;
}

.dropzone .dz-preview .dz-image {
  border-radius: 8px;
}

.dropzone .dz-preview .dz-details {
  padding: 1em;
}

.dropzone .dz-preview .dz-progress {
  height: 10px;
}

.dropzone .dz-preview .dz-progress .dz-upload {
  background: #4f46e5;
}

.dark .dropzone {
  background-color: #111827;
  border-color: #374151;
}

.dark .dropzone:hover {
  border-color: #6366f1;
}
</style>
