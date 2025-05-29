// src/store/modules/auth.js
import { login } from '../../api/auth';
import router from '../../router';
import * as types from '../types';

const state = {
    token: localStorage.getItem('jwtToken') || null,
    user: JSON.parse(localStorage.getItem('user')) || null,
    authError: null,
    isLoggedIn: !!localStorage.getItem('jwtToken')
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
        state.authError = null;
        localStorage.removeItem('jwtToken');
        localStorage.removeItem('user');
    },
    [types.SET_AUTH_ERROR](state, error) {
        state.authError = error;
    }
};

const actions = {
    async [types.LOGIN]({ commit }, credentials) {
        try {
            const response = await login(credentials);
            const { token, user } = response.data;
            commit(types.SET_AUTH_TOKEN, token);
            commit(types.SET_AUTH_USER, user);
            commit(types.SET_AUTH_ERROR, null);
            router.push('/');
        } catch (error) {
            let errorMessage = 'Đăng nhập thất bại. Vui lòng kiểm tra lại thông tin.';
            if (error.response && error.response.data) {
                if (error.response.data.string) {
                    errorMessage = error.response.data.string;
                }
                else if (error.response.data.message) {
                    errorMessage = error.response.data.message;
                }
            }

            commit(types.SET_AUTH_ERROR, errorMessage);
            throw error;
        }
    },

    [types.LOGOUT]({ commit }) {
        commit(types.CLEAR_AUTH_DATA);
        router.push('/login');
    }
};


export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
};