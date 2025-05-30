import axios from './index';

export const login = async (credentials) => {
    try {
        const response = await axios.post('/auth/login', credentials);
        return response.data;
    } catch (error) {
        console.error('Login error:', error.response ? error.response.data : error.message);
        throw error;
    }
};

export const register = async (userData) => {
    try {
        const response = await axios.post('/auth/register', userData);
        return response.data;
    } catch (error) {
        console.error('Register error:', error.response ? error.response.data : error.message);
        throw error;
    }
};