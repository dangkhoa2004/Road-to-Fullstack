// src/store/modules/auth.ts
import { login } from '@/api/auth';
import router from '@/router';
import * as types from '../types';
import type { ActionContext } from 'vuex';

// --- Auth State ---
export interface AuthState {
  token: string | null;
  user: any; // Thay bằng User interface nếu có
  authError: string | null;
  isLoggedIn: boolean;
}

const state: AuthState = {
  token: localStorage.getItem('jwtToken') || null,
  user: JSON.parse(localStorage.getItem('user') || 'null'),
  authError: null,
  isLoggedIn: !!localStorage.getItem('jwtToken'),
};

// --- Getters ---
const getters = {
  [types.IS_LOGGED_IN]: (state: AuthState) => !!state.token,
  [types.GET_AUTH_USER]: (state: AuthState) => state.user,
  [types.GET_AUTH_ERROR]: (state: AuthState) => state.authError,
};

// --- Mutations ---
const mutations = {
  [types.SET_AUTH_TOKEN](state: AuthState, token: string) {
    state.token = token;
    localStorage.setItem('jwtToken', token);
  },
  [types.SET_AUTH_USER](state: AuthState, user: any) {
    state.user = user;
    localStorage.setItem('user', JSON.stringify(user));
  },
  [types.CLEAR_AUTH_DATA](state: AuthState) {
    state.token = null;
    state.user = null;
    state.authError = null;
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('user');
  },
  [types.SET_AUTH_ERROR](state: AuthState, error: string) {
    state.authError = error;
  },
};

// --- Actions ---
const actions = {
  async [types.LOGIN](
    { commit }: ActionContext<AuthState, unknown>,
    credentials: Record<string, any>
  ) {
    try {
      const response = await login(credentials);

      // response là { token, user } rồi
      const { token, user } = response;

      commit(types.SET_AUTH_TOKEN, token);
      commit(types.SET_AUTH_USER, user);
      commit(types.SET_AUTH_ERROR, null);
      router.push('/');
    } catch (error: any) {
      let errorMessage = 'Đăng nhập thất bại. Vui lòng kiểm tra lại thông tin.';
      if (error.response && error.response.data) {
        if (error.response.data.string) {
          errorMessage = error.response.data.string;
        } else if (error.response.data.message) {
          errorMessage = error.response.data.message;
        }
      }
      commit(types.SET_AUTH_ERROR, errorMessage);
      throw error;
    }
  },
};

// --- Export ---
export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions,
};
