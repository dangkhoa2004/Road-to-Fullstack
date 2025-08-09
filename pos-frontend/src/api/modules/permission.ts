import {get, post, put, deleteApi} from '../api'
import type {AxiosError} from 'axios'

// --- Interface Permission ---
export interface Permission {
  id: number
  name: string
  description: string
}

// --- Lấy toàn bộ permissions ---
export const getAllPermissions = async (): Promise<Permission[]> => {
  try {
    return await get<Permission[]>('/permission/list')
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    console.error('Fetch permissions error:', axiosError.message)
    throw error
  }
}

// --- Lấy permission theo ID ---
export const getPermissionById = async (permissionId: number): Promise<Permission> => {
  try {
    return await get<Permission>(`/permission/${permissionId}`)
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    console.error(`Fetch permission detail for ID ${permissionId} error:`, axiosError.message)
    throw error
  }
}

// --- Thêm mới permission ---
export const addPermission = async (
  permissionData: Partial<Permission>
): Promise<Permission> => {
  try {
    return await post<Permission>('/permission/create', permissionData)
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    console.error('Add permission error:', axiosError.message)
    throw error
  }
}

// --- Cập nhật permission ---
export const updatePermission = async (
  permissionId: number,
  permissionData: Partial<Permission>
): Promise<Permission> => {
  try {
    return await put<Permission>(`/permission/${permissionId}`, permissionData)
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    console.error(`Update permission ${permissionId} error:`, axiosError.message)
    throw error
  }
}

// --- Xóa permission ---
export const deletePermission = async (permissionId: number): Promise<void> => {
  try {
    await deleteApi<void>(`/permission/${permissionId}`)
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    console.error(`Delete permission ${permissionId} error:`, axiosError.message)
    throw error
  }
}
