import {getAllCategory, getCategoryById} from '@/api/modules/category'
import type {Category} from '@/api/modules/category'
import type {ActionContext} from 'vuex'

// --- State ---
export interface CategoryState {
  categories: Category[]
  category: Category | null
  categoryLoading: boolean
  categoryError: string | null
}

const state: CategoryState = {
  categories: [],
  category: null,
  categoryLoading: false,
  categoryError: null,
}

// --- Getters ---
const getters = {
  GET_CATEGORIES: (state: CategoryState) => state.categories,
  GET_CATEGORY: (state: CategoryState) => state.category,
  GET_CATEGORY_LOADING: (state: CategoryState) => state.categoryLoading,
  GET_CATEGORY_ERROR: (state: CategoryState) => state.categoryError,
}

// --- Mutations ---
const mutations = {
  SET_CATEGORIES(state: CategoryState, categories: Category[]) {
    state.categories = categories
    state.categoryError = null
  },
  SET_CATEGORY(state: CategoryState, category: Category | null) {
    state.category = category
    state.categoryError = null
  },
  SET_CATEGORY_LOADING(state: CategoryState, isLoading: boolean) {
    state.categoryLoading = isLoading
  },
  SET_CATEGORY_ERROR(state: CategoryState, error: string) {
    state.categoryError = error
    state.categories = []
    state.category = null
  },
}

// --- Actions ---
const actions = {
  async FETCH_CATEGORIES({commit}: ActionContext<CategoryState, unknown>) {
    try {
      const data = await getAllCategory()
      commit('SET_CATEGORIES', data)
      return data
    } catch (error: any) {
      console.error('Error fetching categories via service:', error)
      const errorMessage =
        error.response?.data?.message || 'Failed to fetch categories. Please try again.'
      commit('SET_CATEGORY_ERROR', errorMessage)
      throw error
    }
  },
  async FETCH_CATEGORY_BY_ID(
    {commit}: ActionContext<CategoryState, unknown>,
    categoryId: number
  ) {
    commit('SET_CATEGORY_LOADING', true)
    commit('SET_CATEGORY_ERROR', null)
    try {
      const data = await getCategoryById(categoryId)
      commit('SET_CATEGORY', data)
      return data
    } catch (error: any) {
      console.error(`Error fetching category by ID (${categoryId}) via service:`, error)
      const errorMessage =
        error.response?.data?.message ||
        `Failed to fetch category with ID ${categoryId}. Please try again.`
      commit('SET_CATEGORY_ERROR', errorMessage)
      commit('SET_CATEGORY', null)
      throw error
    } finally {
      commit('SET_CATEGORY_LOADING', false)
    }
  },
}

// --- Export ---
export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions,
}
