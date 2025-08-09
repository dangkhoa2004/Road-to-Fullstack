import {getAllCustomers, getCustomerById} from '@/api/modules/customer'
import type {Customer} from '@/api/modules/customer'
import type {ActionContext} from 'vuex'

// --- State ---
export interface CustomerState {
  customers: Customer[]
  customer: Customer | null
  customerLoading: boolean
  customerError: string | null
}

const state: CustomerState = {
  customers: [],
  customer: null,
  customerLoading: false,
  customerError: null,
}

// --- Getters ---
const getters = {
  GET_CUSTOMERS: (state: CustomerState) => state.customers,
  GET_CUSTOMER: (state: CustomerState) => state.customer,
  GET_CUSTOMER_LOADING: (state: CustomerState) => state.customerLoading,
  GET_CUSTOMER_ERROR: (state: CustomerState) => state.customerError,
}

// --- Mutations ---
const mutations = {
  SET_CUSTOMERS(state: CustomerState, customers: Customer[]) {
    state.customers = customers
    state.customerError = null
  },
  SET_CUSTOMER(state: CustomerState, customer: Customer | null) {
    state.customer = customer
    state.customerError = null
  },
  SET_CUSTOMER_LOADING(state: CustomerState, isLoading: boolean) {
    state.customerLoading = isLoading
  },
  SET_CUSTOMER_ERROR(state: CustomerState, error: string) {
    state.customerError = error
    state.customers = []
    state.customer = null
  },
}

// --- Actions ---
const actions = {
  async FETCH_CUSTOMERS({commit}: ActionContext<CustomerState, unknown>) {
    try {
      const data = await getAllCustomers()
      commit('SET_CUSTOMERS', data)
      return data
    } catch (error: any) {
      console.error('Error fetching customers:', error)
      const errorMessage =
        error.response?.data?.message || 'Failed to fetch customers. Please try again.'
      commit('SET_CUSTOMER_ERROR', errorMessage)
      throw error
    }
  },

  async FETCH_CUSTOMER_BY_ID(
    {commit}: ActionContext<CustomerState, unknown>,
    customerId: number
  ) {
    commit('SET_CUSTOMER_LOADING', true)
    commit('SET_CUSTOMER_ERROR', null)
    try {
      const data = await getCustomerById(customerId)
      commit('SET_CUSTOMER', data)
      return data
    } catch (error: any) {
      console.error(`Error fetching customer by ID (${customerId}):`, error)
      const errorMessage =
        error.response?.data?.message ||
        `Failed to fetch customer with ID ${customerId}. Please try again.`
      commit('SET_CUSTOMER_ERROR', errorMessage)
      commit('SET_CUSTOMER', null)
      throw error
    } finally {
      commit('SET_CUSTOMER_LOADING', false)
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
