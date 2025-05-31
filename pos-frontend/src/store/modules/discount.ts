import { getAllDiscount, getDiscountById } from '@/api/modules/discount'
import type { Discount } from '@/api/modules/discount'
import type { ActionContext } from 'vuex'

// --- State ---
export interface DiscountState {
    discounts: Discount[]
    discount: Discount | null
    discountLoading: boolean
    discountError: string | null
}

const state: DiscountState = {
    discounts: [],
    discount: null,
    discountLoading: false,
    discountError: null,
}

// --- Getters ---
const getters = {
    GET_DISCOUNTS: (state: DiscountState) => state.discounts,
    GET_DISCOUNT: (state: DiscountState) => state.discount,
    GET_DISCOUNT_LOADING: (state: DiscountState) => state.discountLoading,
    GET_DISCOUNT_ERROR: (state: DiscountState) => state.discountError,
}

// --- Mutations ---
const mutations = {
    SET_DISCOUNTS(state: DiscountState, discounts: Discount[]) {
        state.discounts = discounts
        state.discountError = null
    },
    SET_DISCOUNT(state: DiscountState, discount: Discount | null) {
        state.discount = discount
        state.discountError = null
    },
    SET_DISCOUNT_LOADING(state: DiscountState, isLoading: boolean) {
        state.discountLoading = isLoading
    },
    SET_DISCOUNT_ERROR(state: DiscountState, error: string) {
        state.discountError = error
        state.discounts = []
        state.discount = null
    },
}

// --- Actions ---
const actions = {
    async FETCH_DISCOUNTS({ commit }: ActionContext<DiscountState, unknown>) {
        try {
            const data = await getAllDiscount()
            commit('SET_DISCOUNTS', data)
            return data
        } catch (error: any) {
            console.error('Error fetching discounts via service:', error)
            const errorMessage =
                error.response?.data?.message || 'Failed to fetch discounts. Please try again.'
            commit('SET_DISCOUNT_ERROR', errorMessage)
            throw error
        }
    },
    async FETCH_DISCOUNT_BY_ID(
        { commit }: ActionContext<DiscountState, unknown>,
        discountId: number
    ) {
        commit('SET_DISCOUNT_LOADING', true)
        commit('SET_DISCOUNT_ERROR', null)
        try {
            const data = await getDiscountById(discountId)
            commit('SET_DISCOUNT', data)
            return data
        } catch (error: any) {
            console.error(`Error fetching discount by ID (${discountId}) via service:`, error)
            const errorMessage =
                error.response?.data?.message ||
                `Failed to fetch discount with ID ${discountId}. Please try again.`
            commit('SET_DISCOUNT_ERROR', errorMessage)
            commit('SET_DISCOUNT', null)
            throw error
        } finally {
            commit('SET_DISCOUNT_LOADING', false)
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
