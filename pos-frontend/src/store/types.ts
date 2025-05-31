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

// --- Category Actions ---
export const FETCH_CATEGORIES = 'FETCH_CATEGORIES' as const;
export const FETCH_CATEGORY_BY_ID = 'FETCH_CATEGORY_BY_ID' as const;

// --- Category Mutations ---
export const SET_CATEGORIES = 'SET_CATEGORIES' as const;
export const SET_CATEGORY = 'SET_CATEGORY' as const;
export const SET_CATEGORY_LOADING = 'SET_CATEGORY_LOADING' as const;
export const SET_CATEGORY_ERROR = 'SET_CATEGORY_ERROR' as const;

// --- Category Getters ---
export const GET_CATEGORIES = 'GET_CATEGORIES' as const;
export const GET_CATEGORY = 'GET_CATEGORY' as const;
export const GET_CATEGORY_LOADING = 'GET_CATEGORY_LOADING' as const;
export const GET_CATEGORY_ERROR = 'GET_CATEGORY_ERROR' as const;

// --- Discount Actions ---
export const FETCH_DISCOUNTS = 'FETCH_DISCOUNTS' as const;
export const FETCH_DISCOUNT_BY_ID = 'FETCH_DISCOUNT_BY_ID' as const;

// --- Discount Mutations ---
export const SET_DISCOUNTS = 'SET_DISCOUNTS' as const;
export const SET_DISCOUNT = 'SET_DISCOUNT' as const;
export const SET_DISCOUNT_LOADING = 'SET_DISCOUNT_LOADING' as const;
export const SET_DISCOUNT_ERROR = 'SET_DISCOUNT_ERROR' as const;

// --- Discount Getters ---
export const GET_DISCOUNTS = 'GET_DISCOUNTS' as const;
export const GET_DISCOUNT = 'GET_DISCOUNT' as const;
export const GET_DISCOUNT_LOADING = 'GET_DISCOUNT_LOADING' as const;
export const GET_DISCOUNT_ERROR = 'GET_DISCOUNT_ERROR' as const;

// --- Invoice Types ---
export const FETCH_INVOICES = 'FETCH_INVOICES'
export const GET_INVOICES = 'GET_INVOICES'
export const GET_INVOICE = 'GET_INVOICE'
export const SET_INVOICES = 'SET_INVOICES'
export const SET_INVOICE = 'SET_INVOICE'
export const SET_INVOICE_LOADING = 'SET_INVOICE_LOADING'
export const SET_INVOICE_ERROR = 'SET_INVOICE_ERROR'
export const GET_INVOICE_LOADING = 'GET_INVOICE_LOADING'
export const GET_INVOICE_ERROR = 'GET_INVOICE_ERROR'
