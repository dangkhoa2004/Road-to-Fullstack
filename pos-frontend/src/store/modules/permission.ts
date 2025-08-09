import {
  getAllPermissions,
  getPermissionById,
  addPermission,
  updatePermission,
  deletePermission
} from '@/api/modules/permission'
import type {Permission} from '@/api/modules/permission'
import type {ActionContext} from 'vuex'

export interface PermissionState {
  permissions: Permission[]
  permission: Permission | null
  permissionLoading: boolean
  permissionError: string | null
}

const state: PermissionState = {
  permissions: [],
  permission: null,
  permissionLoading: false,
  permissionError: null
}

const getters = {
  GET_PERMISSIONS: (state: PermissionState) => state.permissions,
  GET_PERMISSION: (state: PermissionState) => state.permission,
  GET_PERMISSION_LOADING: (state: PermissionState) => state.permissionLoading,
  GET_PERMISSION_ERROR: (state: PermissionState) => state.permissionError
}

const mutations = {
  SET_PERMISSIONS(state: PermissionState, permissions: Permission[]) {
    state.permissions = permissions
    state.permissionError = null
  },
  SET_PERMISSION(state: PermissionState, permission: Permission | null) {
    state.permission = permission
    state.permissionError = null
  },
  SET_PERMISSION_LOADING(state: PermissionState, isLoading: boolean) {
    state.permissionLoading = isLoading
  },
  SET_PERMISSION_ERROR(state: PermissionState, error: string) {
    state.permissionError = error
    state.permissions = []
    state.permission = null
  }
}

const actions = {
  async FETCH_PERMISSIONS({commit}: ActionContext<PermissionState, unknown>) {
    try {
      const data = await getAllPermissions()
      commit('SET_PERMISSIONS', data)
      return data
    } catch (error: any) {
      console.error('Error fetching permissions:', error)
      const errorMessage =
        error.response?.data?.message || 'Failed to fetch permissions. Please try again.'
      commit('SET_PERMISSION_ERROR', errorMessage)
      throw error
    }
  },

  async FETCH_PERMISSION_BY_ID({commit}: ActionContext<PermissionState, unknown>, permissionId: number) {
    commit('SET_PERMISSION_LOADING', true)
    commit('SET_PERMISSION_ERROR', null)
    try {
      const data = await getPermissionById(permissionId)
      commit('SET_PERMISSION', data)
      return data
    } catch (error: any) {
      console.error(`Error fetching permission by ID (${permissionId}):`, error)
      const errorMessage =
        error.response?.data?.message ||
        `Failed to fetch permission with ID ${permissionId}. Please try again.`
      commit('SET_PERMISSION_ERROR', errorMessage)
      commit('SET_PERMISSION', null)
      throw error
    } finally {
      commit('SET_PERMISSION_LOADING', false)
    }
  },

  async ADD_PERMISSION(_: ActionContext<PermissionState, unknown>, permissionData: Partial<Permission>) {
    try {
      return await addPermission(permissionData)
    } catch (error: any) {
      console.error('Error adding permission:', error)
      throw error
    }
  },

  async UPDATE_PERMISSION(
    _: ActionContext<PermissionState, unknown>,
    {permissionId, permissionData}: { permissionId: number; permissionData: Partial<Permission> }
  ) {
    try {
      return await updatePermission(permissionId, permissionData)
    } catch (error: any) {
      console.error(`Error updating permission with ID ${permissionId}:`, error)
      throw error
    }
  },

  async DELETE_PERMISSION(_: ActionContext<PermissionState, unknown>, permissionId: number) {
    try {
      await deletePermission(permissionId)
    } catch (error: any) {
      console.error(`Error deleting permission with ID ${permissionId}:`, error)
      throw error
    }
  }
}

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
}
