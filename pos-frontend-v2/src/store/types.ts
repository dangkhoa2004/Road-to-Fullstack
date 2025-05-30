// --- Auth Mutations ---
export const SET_AUTH_TOKEN = 'SET_AUTH_TOKEN' as const;
export const SET_AUTH_USER = 'SET_AUTH_USER' as const;
export const CLEAR_AUTH_DATA = 'CLEAR_AUTH_DATA' as const;
export const SET_AUTH_ERROR = 'SET_AUTH_ERROR' as const;

// --- Auth Actions ---
export const LOGIN = 'LOGIN' as const;
export const LOGOUT = 'LOGOUT' as const;
export const GET_AUTH_USER = 'getAuthUser' as const;
export const REGISTER = 'register' as const;
export const SET_USER_ERROR = 'setUserError' as const;

// --- Auth Getters ---
export const IS_LOGGED_IN = 'IS_LOGGED_IN' as const;
export const GET_AUTH_ERROR = 'GET_AUTH_ERROR' as const;

// --- User Mutations ---
export const SET_USER_PROFILE = 'SET_USER_PROFILE' as const;
export const CLEAR_USER_PROFILE = 'CLEAR_USER_PROFILE' as const;
export const SET_USER_LOADING = 'SET_USER_LOADING' as const;

// --- User Actions ---
export const FETCH_USER_PROFILE = 'FETCH_USER_PROFILE' as const;
export const UPDATE_USER_PROFILE = 'UPDATE_USER_PROFILE' as const;

// --- User Getters ---
export const GET_USER_PROFILE = 'GET_USER_PROFILE' as const;
export const IS_USER_LOADING = 'IS_USER_LOADING' as const;
export const GET_USER_ERROR = 'GET_USER_ERROR' as const;

// --- Product Actions ---
export const FETCH_PRODUCTS = 'FETCH_PRODUCTS' as const;
export const FETCH_PRODUCT_BY_ID = 'FETCH_PRODUCT_BY_ID' as const;

// --- Product Mutations ---
export const SET_PRODUCTS = 'SET_PRODUCTS' as const;
export const SET_PRODUCT = 'SET_PRODUCT' as const;
export const SET_PRODUCT_LOADING = 'SET_PRODUCT_LOADING' as const;
export const SET_PRODUCT_ERROR = 'SET_PRODUCT_ERROR' as const;

// --- Product Getters ---
export const GET_PRODUCTS = 'GET_PRODUCTS' as const;
export const GET_PRODUCT = 'GET_PRODUCT' as const;
export const GET_PRODUCT_LOADING = 'GET_PRODUCT_LOADING' as const;
export const GET_PRODUCT_ERROR = 'GET_PRODUCT_ERROR' as const;
