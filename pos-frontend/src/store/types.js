// src/store/types.js

// --- Auth Mutations ---
export const SET_AUTH_TOKEN = 'SET_AUTH_TOKEN';
export const SET_AUTH_USER = 'SET_AUTH_USER';
export const CLEAR_AUTH_DATA = 'CLEAR_AUTH_DATA';
export const SET_AUTH_ERROR = 'SET_AUTH_ERROR';

// --- Auth Actions ---
export const LOGIN = 'LOGIN';
export const LOGOUT = 'LOGOUT';

// --- Auth Getters ---
export const IS_LOGGED_IN = 'IS_LOGGED_IN';
export const GET_AUTH_ERROR = 'GET_AUTH_ERROR';

// --- User Mutations ---
export const SET_USER_PROFILE = 'SET_USER_PROFILE';
export const CLEAR_USER_PROFILE = 'CLEAR_USER_PROFILE';
export const SET_USER_LOADING = 'SET_USER_LOADING';

// --- User Actions ---
export const FETCH_USER_PROFILE = 'FETCH_USER_PROFILE';
export const UPDATE_USER_PROFILE = 'UPDATE_USER_PROFILE';

// --- User Getters ---
export const GET_USER_PROFILE = 'GET_USER_PROFILE';
export const IS_USER_LOADING = 'IS_USER_LOADING';
export const GET_USER_ERROR = 'GET_USER_ERROR';

// --- Product Actions ---
export const FETCH_PRODUCTS = 'FETCH_PRODUCTS';
export const FETCH_PRODUCT_BY_ID = 'FETCH_PRODUCT_BY_ID'; // Added for single product fetch

// --- Product Mutations ---
export const SET_PRODUCTS = 'SET_PRODUCTS';
export const SET_PRODUCT = 'SET_PRODUCT'; // Added to store a single product
export const SET_PRODUCT_LOADING = 'SET_PRODUCT_LOADING'; // Added for loading state of a single product
export const SET_PRODUCT_ERROR = 'SET_PRODUCT_ERROR';

// --- Product Getters ---
export const GET_PRODUCTS = 'GET_PRODUCTS';
export const GET_PRODUCT = 'GET_PRODUCT'; // Added to retrieve a single product
export const GET_PRODUCT_LOADING = 'GET_PRODUCT_LOADING'; // Added for loading state of a single product
export const GET_PRODUCT_ERROR = 'GET_PRODUCT_ERROR';
