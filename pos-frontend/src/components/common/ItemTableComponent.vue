<template>
    <div class="overflow-hidden rounded-lg border border-gray-200 shadow-md m-5">
        <table class="w-full border-collapse bg-white text-left text-sm text-gray-500">
            <thead class="bg-gray-50">
                <tr>
                    <th v-for="(header, index) in headers" :key="index" scope="col"
                        class="px-6 py-4 font-medium text-gray-900">
                        {{ header.text }}
                    </th>
                    <th scope="col" class="px-6 py-4 font-medium text-gray-900"></th>
                </tr>
            </thead>
            <tbody class="divide-y divide-gray-100 border-t border-gray-100">
                <tr v-if="items.length === 0 && !error">
                    <td :colspan="headers.length + 1" class="px-6 py-4 text-center text-gray-500">
                        {{ loadingMessage }}
                    </td>
                </tr>
                <tr v-else-if="error">
                    <td :colspan="headers.length + 1" class="px-6 py-4 text-center text-red-500">
                        {{ error }}
                    </td>
                </tr>
                <tr v-else v-for="item in items" :key="item.id" class="hover:bg-gray-50">
                    <td v-for="(header, index) in headers" :key="index" :class="[
                        'px-6 py-4',
                        { 'font-normal text-gray-900 flex gap-3': header.value === 'name' },
                    ]">
                        <div v-if="header.value === 'name'" class="flex items-center">
                            <div class="relative h-10 w-10">
                                <img class="h-full w-full rounded-full object-cover object-center"
                                    :src="item.imagePath || 'https://i.ibb.co/3mWK7C3P/logo.png'" :alt="item.name" />
                                <span
                                    :class="['absolute right-0 bottom-0 h-2 w-2 rounded-full ring ring-white', item.isActive ? 'bg-green-400' : 'bg-red-400']"></span>
                            </div>
                            <div class="text-sm ml-3">
                                <div class="font-medium text-gray-700">{{ item.name }}</div>
                                <div class="text-gray-400">ID: {{ item.id }}</div>
                            </div>
                        </div>

                        <span v-else-if="header.value === 'isActive'"
                            :class="['inline-flex items-center gap-1 rounded-full px-2 py-1 text-xs font-semibold', item.isActive ? 'bg-green-50 text-green-600' : 'bg-red-50 text-red-600']">
                            <span
                                :class="['h-1.5 w-1.5 rounded-full', item.isActive ? 'bg-green-600' : 'bg-red-600']"></span>
                            {{ item.isActive ? 'Active' : 'Inactive' }}
                        </span>

                        <span v-else-if="header.value === 'price'">
                            {{ item.price ? item.price.toLocaleString('en-US', { style: 'currency', currency: 'USD' }) :
                            'N/A' }}
                        </span>

                        <span v-else-if="header.value === 'createdAt' || header.value === 'updatedAt'">
                            {{ item[header.value] ? new Date(item[header.value]).toLocaleDateString('en-US') : 'N/A' }}
                        </span>

                        <span v-else>
                            {{ item[header.value] || 'N/A' }}
                        </span>
                    </td>

                    <td class="px-6 py-4">
                        <div class="flex justify-end gap-4">
                            <a x-data="{ tooltip: 'Delete' }" href="#" @click.prevent="$emit('delete-item', item.id)">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                    stroke-width="1.5" stroke="currentColor" class="h-6 w-6" x-tooltip="tooltip">
                                    <path stroke-linecap="round" stroke-linejoin="round"
                                        d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0" />
                                </svg>
                            </a>
                            <a x-data="{ tooltip: 'Edit' }" href="#"
                                @click.prevent="$emit('view-item-detail', item.id)">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                    stroke-width="1.5" stroke="currentColor" class="h-6 w-6" x-tooltip="tooltip">
                                    <path stroke-linecap="round" stroke-linejoin="round"
                                        d="M16.862 4.487l1.687-1.688a1.875 1.875 0 112.652 2.652L6.832 19.82a4.5 4.5 0 01-1.897 1.13l-2.685.8.8-2.685a4.5 4.5 0 011.13-1.897L16.863 4.487zm0 0L19.5 7.125" />
                                </svg>
                            </a>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
export default {
    name: 'ItemTableComponent',
    props: {
        headers: {
            type: Array,
            required: true,
        },
        items: {
            type: Array,
            required: true,
        },
        error: {
            type: String,
            default: '',
        },
        loadingMessage: {
            type: String,
            default: 'Loading items...',
        },
    },
};
</script>