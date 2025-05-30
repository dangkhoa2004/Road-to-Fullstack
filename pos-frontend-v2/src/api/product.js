// productApi.js
import { get, post, put, del } from './api';

// Lấy toàn bộ sản phẩm
export const getAllProduct = async () => {
    try {
        return await get('/product/list');
    } catch (error) {
        console.error('Fetch products error:', error.message);
        throw error;
    }
};

// Lấy chi tiết sản phẩm theo ID
export const getProductById = async (productId) => {
    try {
        return await get(`/product/${productId}`);
    } catch (error) {
        console.error(`Fetch product detail for ID ${productId} error:`, error.message);
        throw error;
    }
};

// Thêm sản phẩm mới
export const addProduct = async (productData) => {
    try {
        return await post('/product', productData);
    } catch (error) {
        console.error('Add product error:', error.message);
        throw error;
    }
};

// Sửa sản phẩm
export const updateProduct = async (productId, productData) => {
    try {
        return await put(`/product/${productId}`, productData);
    } catch (error) {
        console.error(`Update product ${productId} error:`, error.message);
        throw error;
    }
};

// Xoá sản phẩm
export const deleteProduct = async (productId) => {
    try {
        return await del(`/product/${productId}`);
    } catch (error) {
        console.error(`Delete product ${productId} error:`, error.message);
        throw error;
    }
};
