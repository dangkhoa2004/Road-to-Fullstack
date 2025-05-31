import { getAllStockIns, getAllStockOuts } from '@/api/modules/stocks'
import type { Stock } from '@/api/modules/stocks'
import type { ActionContext } from 'vuex'

export interface StockState {
  stockIns: Stock[]
  stockOuts: Stock[]
  stockLoading: boolean
  stockError: string | null
}

const state: StockState = {
  stockIns: [],
  stockOuts: [],
  stockLoading: false,
  stockError: null,
}

const getters = {
  GET_STOCK_INS: (state: StockState) => state.stockIns,
  GET_STOCK_OUTS: (state: StockState) => state.stockOuts,
  GET_STOCK_LOADING: (state: StockState) => state.stockLoading,
  GET_STOCK_ERROR: (state: StockState) => state.stockError,
}

const mutations = {
  SET_STOCK_INS(state: StockState, stockIns: Stock[]) {
    state.stockIns = stockIns
    state.stockError = null
  },
  SET_STOCK_OUTS(state: StockState, stockOuts: Stock[]) {
    state.stockOuts = stockOuts
    state.stockError = null
  },
  SET_STOCK_LOADING(state: StockState, isLoading: boolean) {
    state.stockLoading = isLoading
  },
  SET_STOCK_ERROR(state: StockState, error: string | null) {
    state.stockError = error
  },
  CLEAR_STOCK_DATA(state: StockState) {
    state.stockIns = []
    state.stockOuts = []
  },
}

const actions = {
  async FETCH_STOCK_INS({ commit }: ActionContext<StockState, unknown>) {
    commit('SET_STOCK_LOADING', true)
    commit('SET_STOCK_ERROR', null)
    try {
      const data = await getAllStockIns()
      commit('SET_STOCK_INS', data)
      return data
    } catch (error: any) {
      console.error('Error fetching stock ins:', error)
      const errorMessage =
        error.response?.data?.message || 'Failed to fetch stock ins. Please try again.'
      commit('SET_STOCK_ERROR', errorMessage)
      commit('CLEAR_STOCK_DATA') // Clear data only when error happens
      throw error
    } finally {
      commit('SET_STOCK_LOADING', false)
    }
  },

  async FETCH_STOCK_OUTS({ commit }: ActionContext<StockState, unknown>) {
    commit('SET_STOCK_LOADING', true)
    commit('SET_STOCK_ERROR', null)
    try {
      const data = await getAllStockOuts()
      commit('SET_STOCK_OUTS', data)
      return data
    } catch (error: any) {
      console.error('Error fetching stock outs:', error)
      const errorMessage =
        error.response?.data?.message || 'Failed to fetch stock outs. Please try again.'
      commit('SET_STOCK_ERROR', errorMessage)
      commit('CLEAR_STOCK_DATA') // Clear data only when error happens
      throw error
    } finally {
      commit('SET_STOCK_LOADING', false)
    }
  },
}

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions,
}
