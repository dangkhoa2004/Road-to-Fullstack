import { getAllProduct, getProductById } from '@/api/modules/product';
import type { Product } from '@/api/modules/product';
import type { ActionContext } from 'vuex';

// --- State ---
export interface ProductState {
    products: Product[];
    product: Product | null;
    productLoading: boolean;
    productError: string | null;
}

const state: ProductState = {
    products: [],
    product: null,
    productLoading: false,
    productError: null,
};

// --- Getters ---
const getters = {
    GET_PRODUCTS: (state: ProductState) => state.products,
    GET_PRODUCT: (state: ProductState) => state.product,
    GET_PRODUCT_LOADING: (state: ProductState) => state.productLoading,
    GET_PRODUCT_ERROR: (state: ProductState) => state.productError,
};

// --- Mutations ---
const mutations = {
    SET_PRODUCTS(state: ProductState, products: Product[]) {
        state.products = products;
        state.productError = null;
    },
    SET_PRODUCT(state: ProductState, product: Product | null) {
        state.product = product;
        state.productError = null;
    },
    SET_PRODUCT_LOADING(state: ProductState, isLoading: boolean) {
        state.productLoading = isLoading;
    },
    SET_PRODUCT_ERROR(state: ProductState, error: string) {
        state.productError = error;
        state.products = [];
        state.product = null;
    },
};

// --- Actions ---
const actions = {
    async FETCH_PRODUCTS({ commit }: ActionContext<ProductState, unknown>) {
        try {
            const data = await getAllProduct();
            commit('SET_PRODUCTS', data);
            return data;
        } catch (error: any) {
            console.error('Error fetching products via service:', error);
            const errorMessage =
                error.response?.data?.message || 'Failed to fetch products. Please try again.';
            commit('SET_PRODUCT_ERROR', errorMessage);
            throw error;
        }
    },
    async FETCH_PRODUCT_BY_ID(
        { commit }: ActionContext<ProductState, unknown>,
        productId: number
    ) {
        commit('SET_PRODUCT_LOADING', true);
        commit('SET_PRODUCT_ERROR', null);
        try {
            const data = await getProductById(productId);
            commit('SET_PRODUCT', data);
            return data;
        } catch (error: any) {
            console.error(`Error fetching product by ID (${productId}) via service:`, error);
            const errorMessage =
                error.response?.data?.message ||
                `Failed to fetch product with ID ${productId}. Please try again.`;
            commit('SET_PRODUCT_ERROR', errorMessage);
            commit('SET_PRODUCT', null);
            throw error;
        } finally {
            commit('SET_PRODUCT_LOADING', false);
        }
    },
};

// --- Export ---
export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions,
};
