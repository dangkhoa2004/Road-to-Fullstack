// api.js
import axios from './index';

// Hàm chuẩn hoá response
export const handleResponse = (response) => {
    const { status, data, string } = response.data;
    if (status === "200" && data !== undefined) {
        return data;
    } else {
        throw new Error(string || "Unknown API response");
    }
};

// GET
export const get = async (url, config = {}) => {
    const response = await axios.get(url, config);
    return handleResponse(response);
};

// POST
export const post = async (url, body, config = {}) => {
    const response = await axios.post(url, body, config);
    return handleResponse(response);
};

// PUT
export const put = async (url, body, config = {}) => {
    const response = await axios.put(url, body, config);
    return handleResponse(response);
};

// DELETE
export const del = async (url, config = {}) => {
    const response = await axios.delete(url, config);
    return handleResponse(response);
};
