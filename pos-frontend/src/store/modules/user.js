// src/store/modules/user.js
import axios from '../../api'; // Import instance axios đã cấu hình
import * as types from '../types'; // Import các hằng số types

const state = {
    profile: null,
    isLoading: false,
    userError: null
};

const getters = {
    [types.GET_USER_PROFILE]: state => state.profile,
    [types.IS_USER_LOADING]: state => state.isLoading,
    [types.GET_USER_ERROR]: state => state.userError
};

const mutations = {
    [types.SET_USER_PROFILE](state, profile) {
        state.profile = profile;
        state.userError = null;
    },
    [types.CLEAR_USER_PROFILE](state) {
        state.profile = null;
        state.userError = null;
    },
    [types.SET_USER_LOADING](state, status) {
        state.isLoading = status;
    },
    [types.SET_USER_ERROR](state, error) {
        state.userError = error;
    }
};

const actions = {
    async [types.FETCH_USER_PROFILE]({commit}) {
        commit(types.SET_USER_LOADING, true);
        try {
            const response = await axios.get('/user/profile'); // Giả sử có endpoint này
            commit(types.SET_USER_PROFILE, response.data);
        } catch (error) {
            const errorMessage = error.response && error.response.data && error.response.data.message
                ? error.response.data.message
                : 'Không thể tải thông tin hồ sơ người dùng.';
            commit(types.SET_USER_ERROR, errorMessage);
            console.error('Fetch user profile error:', error);
        } finally {
            commit(types.SET_USER_LOADING, false);
        }
    },

    async [types.UPDATE_USER_PROFILE]({commit}, userData) {
        commit(types.SET_USER_LOADING, true);
        try {
            const response = await axios.put('/user/profile', userData); // Giả sử có endpoint này
            commit(types.SET_USER_PROFILE, response.data); // Cập nhật profile với dữ liệu mới từ server
            return true; // Trả về true nếu thành công
        } catch (error) {
            const errorMessage = error.response && error.response.data && error.response.data.message
                ? error.response.data.message
                : 'Không thể cập nhật thông tin hồ sơ người dùng.';
            commit(types.SET_USER_ERROR, errorMessage);
            console.error('Update user profile error:', error);
            throw error; // Ném lại lỗi để component xử lý
        } finally {
            commit(types.SET_USER_LOADING, false);
        }
    }
};

export default {
    namespaced: true, // Đảm bảo module này có namespace
    state,
    getters,
    mutations,
    actions
};