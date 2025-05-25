// src/store/modules/auth.js
import {login} from '../../api/auth'; // Import các hàm API
import router from '../../router'; // Import Vue Router
import * as types from '../types'; // Import các hằng số types

const state = {
    token: localStorage.getItem('jwtToken') || null,
    user: JSON.parse(localStorage.getItem('user')) || null, // Lưu thông tin user
    authError: null,
    isLoggedIn: !!localStorage.getItem('jwtToken') // Kiểm tra trạng thái đăng nhập ban đầu
};

const getters = {
    [types.IS_LOGGED_IN]: state => !!state.token,
    [types.GET_AUTH_USER]: state => state.user,
    [types.GET_AUTH_ERROR]: state => state.authError
};

const mutations = {
    [types.SET_AUTH_TOKEN](state, token) {
        state.token = token;
        localStorage.setItem('jwtToken', token);
    },
    [types.SET_AUTH_USER](state, user) {
        state.user = user;
        localStorage.setItem('user', JSON.stringify(user));
    },
    [types.CLEAR_AUTH_DATA](state) {
        state.token = null;
        state.user = null;
        state.authError = null; // Xóa lỗi khi logout
        localStorage.removeItem('jwtToken');
        localStorage.removeItem('user');
    },
    [types.SET_AUTH_ERROR](state, error) {
        state.authError = error;
    }
};

const actions = {
    async [types.LOGIN]({commit}, credentials) {
        try {
            const response = await login(credentials); // Gọi hàm login từ API service
            const {token, user} = response; // Giả sử response có token và user
            commit(types.SET_AUTH_TOKEN, token);
            commit(types.SET_AUTH_USER, user);
            commit(types.SET_AUTH_ERROR, null); // Xóa lỗi nếu đăng nhập thành công
            router.push('/'); // Điều hướng đến trang chủ
        } catch (error) {
            const errorMessage = error.response && error.response.data && error.response.data.message
                ? error.response.data.message
                : 'Đăng nhập thất bại. Vui lòng kiểm tra lại thông tin.';
            commit(types.SET_AUTH_ERROR, errorMessage);
            throw error; // Ném lại lỗi để component có thể xử lý
        }
    },

    [types.LOGOUT]({commit}) {
        commit(types.CLEAR_AUTH_DATA);
        // Có thể cần clear thêm state của module user nếu thông tin profile không tự clear khi logout
        commit(types.CLEAR_USER_PROFILE); // Gọi mutation từ module user
        router.push('/login');
    }
};

export default {
    namespaced: true, // Đảm bảo module này có namespace để tránh xung đột tên
    state,
    getters,
    mutations,
    actions
};