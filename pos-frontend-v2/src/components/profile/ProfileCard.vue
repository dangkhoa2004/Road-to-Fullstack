<template>
  <div>
    <div class="p-5 mb-6 border border-gray-200 rounded-2xl dark:border-gray-800 lg:p-6">
      <div class="flex flex-col gap-5 xl:flex-row xl:items-center xl:justify-between">
        <div class="flex flex-col items-center w-full gap-6 xl:flex-row">
          <div class="w-20 h-20 overflow-hidden border border-gray-200 rounded-full dark:border-gray-800">
            <img src="/images/user/owner.jpg" alt="user" />
          </div>
          <div class="order-3 xl:order-2">
            <h4 class="mb-2 text-lg font-semibold text-center text-gray-800 dark:text-white/90 xl:text-left">
              {{ user.name }}
            </h4>
            <div class="flex flex-col items-center gap-1 text-center xl:flex-row xl:gap-3 xl:text-left">
              <p class="text-sm text-gray-500 dark:text-gray-400">Team Manager</p>
              <div class="hidden h-3.5 w-px bg-gray-300 dark:bg-gray-700 xl:block"></div>
              <p class="text-sm text-gray-500 dark:text-gray-400">Arizona, United States</p>
            </div>
          </div>
          <div class="flex items-center order-2 gap-2 grow xl:order-3 xl:justify-end">
            <a href="https://www.facebook.com/PimjoHQ" target="_blank" rel="noopener" class="social-button">
              <FacebookSvg />
            </a>
            <a href="https://x.com/PimjoHQ" target="_blank" rel="noopener" class="social-button">
              <XSvg />
            </a>
            <a href="https://www.linkedin.com/company/pimjo/" target="_blank" rel="noopener" class="social-button">
              <LinkedinSvg />
            </a>
            <a href="https://www.instagram.com/PimjoHQ" target="_blank" rel="noopener" class="social-button">
              <InstagramSvg />
            </a>
          </div>
        </div>
        <button @click="isProfileInfoModal = true" class="edit-button">
          <EditbuttonSvg />
          Edit
        </button>
      </div>
    </div>

    <Modal v-if="isProfileInfoModal" @close="isProfileInfoModal = false">
      <template #body>
        <div
          class="no-scrollbar relative w-full max-w-[700px] overflow-y-auto rounded-3xl bg-white p-4 dark:bg-gray-900 lg:p-11">
          <button @click="isProfileInfoModal = false"
            class="transition-color absolute right-5 top-5 z-999 flex h-11 w-11 items-center justify-center rounded-full bg-gray-100 text-gray-400 hover:bg-gray-200 hover:text-gray-600 dark:bg-gray-700 dark:bg-white/[0.05] dark:text-gray-400 dark:hover:bg-white/[0.07] dark:hover:text-gray-300">
            <ClosebuttonSvg />
          </button>
          <div class="px-2 pr-14">
            <h4 class="mb-2 text-2xl font-semibold text-gray-800 dark:text-white/90">Edit Personal Information</h4>
            <p class="mb-6 text-sm text-gray-500 dark:text-gray-400 lg:mb-7">Update your details to keep your profile
              up-to-date.</p>
          </div>
          <form class="flex flex-col">
            <div class="custom-scrollbar h-[458px] overflow-y-auto p-2">
              <div>
                <h5 class="mb-5 text-lg font-medium text-gray-800 dark:text-white/90 lg:mb-6">Social Links</h5>
                <div class="grid grid-cols-1 gap-x-6 gap-y-5 lg:grid-cols-2">
                  <div>
                    <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">Facebook</label>
                    <input type="text" v-model="profile.facebook"
                      class="dark:bg-dark-900 h-11 w-full appearance-none rounded-lg border border-gray-300 bg-transparent bg-none px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800" />
                  </div>
                  <div>
                    <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">X.com</label>
                    <input type="text" v-model="profile.xcom"
                      class="dark:bg-dark-900 h-11 w-full appearance-none rounded-lg border border-gray-300 bg-transparent bg-none px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800" />
                  </div>
                  <div>
                    <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">Linkedin</label>
                    <input type="text" v-model="profile.linkedin"
                      class="dark:bg-dark-900 h-11 w-full appearance-none rounded-lg border border-gray-300 bg-transparent bg-none px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800" />
                  </div>
                  <div>
                    <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">Instagram</label>
                    <input type="text" v-model="profile.instagram"
                      class="dark:bg-dark-900 h-11 w-full appearance-none rounded-lg border border-gray-300 bg-transparent bg-none px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800" />
                  </div>
                </div>
              </div>
              <div class="mt-7">
                <h5 class="mb-5 text-lg font-medium text-gray-800 dark:text-white/90 lg:mb-6">Personal Information</h5>
                <div class="grid grid-cols-1 gap-x-6 gap-y-5 lg:grid-cols-2">
                  <div class="col-span-2 lg:col-span-1">
                    <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">First Name</label>
                    <input type="text" v-model="profile.firstName"
                      class="dark:bg-dark-900 h-11 w-full rounded-lg border border-gray-300 bg-transparent bg-none px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800" />
                  </div>
                  <div class="col-span-2 lg:col-span-1">
                    <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">Last Name</label>
                    <input type="text" v-model="profile.lastName"
                      class="dark:bg-dark-900 h-11 w-full appearance-none rounded-lg border border-gray-300 bg-transparent bg-none px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800" />
                  </div>
                  <div class="col-span-2 lg:col-span-1">
                    <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">Email
                      Address</label>
                    <input type="text" v-model="profile.email"
                      class="dark:bg-dark-900 h-11 w-full appearance-none rounded-lg border border-gray-300 bg-transparent bg-none px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800" />
                  </div>
                  <div class="col-span-2 lg:col-span-1">
                    <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">Phone</label>
                    <input type="text" v-model="profile.phone"
                      class="dark:bg-dark-900 h-11 w-full appearance-none rounded-lg border border-gray-300 bg-transparent bg-none px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800" />
                  </div>
                  <div class="col-span-2">
                    <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">Bio</label>
                    <input type="text" v-model="profile.bio"
                      class="dark:bg-dark-900 h-11 w-full appearance-none rounded-lg border border-gray-300 bg-transparent bg-none px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800" />
                  </div>
                </div>
              </div>
            </div>
            <div class="flex items-center gap-3 px-2 mt-6 lg:justify-end">
              <button @click="isProfileInfoModal = false" type="button"
                class="flex w-full justify-center rounded-lg border border-gray-300 bg-white px-4 py-2.5 text-sm font-medium text-gray-700 hover:bg-gray-50 dark:border-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:hover:bg-white/[0.03] sm:w-auto">Close</button>
              <button @click="saveProfile" type="button"
                class="flex w-full justify-center rounded-lg bg-brand-500 px-4 py-2.5 text-sm font-medium text-white hover:bg-brand-600 sm:w-auto">Save
                Changes</button>
            </div>
          </form>
        </div>
      </template>
    </Modal>
  </div>
</template>

<script setup>
import { ref, reactive, watch, computed } from 'vue'
import { useStore } from 'vuex'
import Modal from './Modal.vue'
import LinkedinSvg from '@/assets/svg-icon/linkedin-svg.vue'
import InstagramSvg from '@/assets/svg-icon/instagram-svg.vue'
import FacebookSvg from '@/assets/svg-icon/facebook-svg.vue'
import XSvg from '@/assets/svg-icon/x-svg.vue'
import EditbuttonSvg from '@/assets/svg-icon/editbutton-svg.vue'
import ClosebuttonSvg from '@/assets/svg-icon/closebutton-svg.vue'

const store = useStore()
const isProfileInfoModal = ref(false)
const user = computed(() => store.getters['auth/getAuthUser'] || {
  name: 'Guest',
  email: 'guest@example.com',
  avatar: '/images/user/owner.jpg',
})
const profile = reactive({
  facebook: '',
  xcom: '',
  linkedin: '',
  instagram: '',
  firstName: '',
  lastName: '',
  email: '',
  phone: '',
  bio: '',
})
watch(isProfileInfoModal, (newVal) => {
  if (newVal && user.value) {
    Object.assign(profile, {
      firstName: user.value.name?.split(' ')[0] || '',
      lastName: user.value.name?.split(' ').slice(1).join(' ') || '',
      email: user.value.email || '',
      phone: user.value.phone || '',
      bio: user.value.bio || '',
      facebook: user.value.facebook || '',
      xcom: user.value.xcom || '',
      linkedin: user.value.linkedin || '',
      instagram: user.value.instagram || '',
    })
  }
})

const saveProfile = () => {
  console.log('Saving profile:', { ...profile })
  isProfileInfoModal.value = false
}
</script>
