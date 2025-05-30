// store/modules/product.js
import {getAllProduct, getProductById} from '@/api/product';

export const FETCH_PRODUCTS = 'FETCH_PRODUCTS';
export const FETCH_PRODUCT_BY_ID = 'FETCH_PRODUCT_BY_ID';

export const SET_PRODUCTS = 'SET_PRODUCTS';
export const SET_PRODUCT = 'SET_PRODUCT';
export const SET_PRODUCT_LOADING = 'SET_PRODUCT_LOADING';
export const SET_PRODUCT_ERROR = 'SET_PRODUCT_ERROR';

export const GET_PRODUCTS = 'GET_PRODUCTS';
export const GET_PRODUCT = 'GET_PRODUCT';
export const GET_PRODUCT_LOADING = 'GET_PRODUCT_LOADING';
export const GET_PRODUCT_ERROR = 'GET_PRODUCT_ERROR';

const state = {
    products: [],
    product: null,
    productLoading: false,
    productError: null,
};

const getters = {
    [GET_PRODUCTS]: (state) => state.products,
    [GET_PRODUCT]: (state) => state.product,
    [GET_PRODUCT_LOADING]: (state) => state.productLoading,
    [GET_PRODUCT_ERROR]: (state) => state.productError,
};

const mutations = {
    [SET_PRODUCTS]: (state, products) => {
        state.products = products;
        state.productError = null;
    },
    [SET_PRODUCT]: (state, product) => {
        state.product = product;
        state.productError = null;
    },
    [SET_PRODUCT_LOADING]: (state, isLoading) => {
        state.productLoading = isLoading;
    },
    [SET_PRODUCT_ERROR]: (state, error) => {
        state.productError = error;
        state.products = [];
        state.product = null;
    },
};

const actions = {
    async [FETCH_PRODUCTS]({commit}) {
        try {
            const data = await getAllProduct();
            commit(SET_PRODUCTS, data);
            return data;
        } catch (error) {
            console.error('Error fetching products via service:', error);
            const errorMessage = error.response && error.response.data && error.response.data.message
                ? error.response.data.message
                : 'Failed to fetch products. Please try again.';
            commit(SET_PRODUCT_ERROR, errorMessage);
            throw error;
        }
    },
    async [FETCH_PRODUCT_BY_ID]({commit}, productId) {
        commit(SET_PRODUCT_LOADING, true);
        commit(SET_PRODUCT_ERROR, null);
        try {
            const data = await getProductById(productId);
            commit(SET_PRODUCT, data);
            return data;
        } catch (error) {
            console.error(`Error fetching product by ID (${productId}) via service:`, error);
            const errorMessage = error.response && error.response.data && error.response.data.message
                ? error.response.data.message
                : `Failed to fetch product with ID ${productId}. Please try again.`;
            commit(SET_PRODUCT_ERROR, errorMessage);
            commit(SET_PRODUCT, null);
            throw error;
        } finally {
            commit(SET_PRODUCT_LOADING, false);
        }
    },
};

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions,
};
