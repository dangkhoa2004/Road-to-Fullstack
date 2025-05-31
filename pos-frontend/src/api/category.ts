import { get, post, put, deleteApi } from './api'
import type { AxiosError } from 'axios'

// Định nghĩa đầy đủ Category
export interface Category {
    id: number
    name: string
    description: string
    created_at: string
    updated_at: string
    version: number
}

// Lấy toàn bộ danh mục
export const getAllCategory = async (): Promise<Category[]> => {
    try {
        return await get<Category[]>('/category/list')
    } catch (error: unknown) {
        const axiosError = error as AxiosError
        console.error('Fetch categories error:', axiosError.message)
        throw error
    }
}

// Lấy chi tiết danh mục theo ID
export const getCategoryById = async (categoryId: number): Promise<Category> => {
    try {
        return await get<Category>(`/category/${categoryId}`)
    } catch (error: unknown) {
        const axiosError = error as AxiosError
        console.error(`Fetch category detail for ID ${categoryId} error:`, axiosError.message)
        throw error
    }
}

// Thêm danh mục mới
export const addCategory = async (categoryData: Partial<Category>): Promise<Category> => {
    try {
        return await post<Category>('/category', categoryData)
    } catch (error: unknown) {
        const axiosError = error as AxiosError
        console.error('Add category error:', axiosError.message)
        throw error
    }
}

// Sửa danh mục
export const updateCategory = async (
    categoryId: number,
    categoryData: Partial<Category>
): Promise<Category> => {
    try {
        return await put<Category>(`/category/${categoryId}`, categoryData)
    } catch (error: unknown) {
        const axiosError = error as AxiosError
        console.error(`Update category ${categoryId} error:`, axiosError.message)
        throw error
    }
}

// Xoá danh mục
export const deleteCategory = async (categoryId: number): Promise<void> => {
    try {
        await deleteApi<void>(`/category/${categoryId}`)
    } catch (error: unknown) {
        const axiosError = error as AxiosError
        console.error(`Delete category ${categoryId} error:`, axiosError.message)
        throw error
    }
}
