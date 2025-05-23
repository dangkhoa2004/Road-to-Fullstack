import axios from './index';

export const getAllProduct = async () => {
    try {
        const response = await axios.get('/product/list');
        console.log(response)
        return response.data;
    } catch (error) {
        console.error('Fetch products error:', error.response ? error.response.data : error.message);
        throw error;
    }
};
export const getProductById = async (productId) => {
    try {
        const response = await axios.get(`/product/${productId}`);
        console.log(response);
        return response.data;
    } catch (error) {
        console.error(`Fetch product detail for ID ${productId} error:`, error.response ? error.response.data : error.message);
        throw error;
    }
};