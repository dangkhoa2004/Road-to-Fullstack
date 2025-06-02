// src\store\modules\auth.ts
import { login, register, forgotPassword } from '@/api/modules/auth'; // Thêm register!
import router from '@/router';
import * as types from '../types';
import type { ActionContext } from 'vuex';

export interface AuthState {
  token: string | null;
  user: any;
  authError: string | null;
  isLoggedIn: boolean;
}

const state: AuthState = {
  token: localStorage.getItem('jwtToken') || null,
  user: JSON.parse(localStorage.getItem('user') || 'null'),
  authError: null,
  isLoggedIn: !!localStorage.getItem('jwtToken'),
};

const getters = {
  [types.IS_LOGGED_IN]: (state: AuthState) => !!state.token,
  [types.GET_AUTH_USER]: (state: AuthState) => state.user,
  [types.GET_AUTH_ERROR]: (state: AuthState) => state.authError,
  // 👇 MỚI: Kiểm tra role
  hasRole: (state: AuthState) => (roleName: string) => {
    return state.user?.role?.name === roleName
  },
  // 👇 MỚI: Kiểm tra permission
  hasPermission: (state: AuthState) => (permission: string) => {
    return state.user?.permissions?.includes(permission)
  },
}

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

const actions = {
  async [types.LOGIN](
    { commit }: ActionContext<AuthState, unknown>,
    credentials: Record<string, any>
  ) {
    try {
      const response = await login(credentials);
      const { token, user } = response;
      commit(types.SET_AUTH_TOKEN, token);
      commit(types.SET_AUTH_USER, user);
      commit(types.SET_AUTH_ERROR, null);
      const redirectPath = router.currentRoute.value.query.redirect as string;
      router.push(redirectPath || '/');
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
  async [types.REGISTER](
    { commit }: ActionContext<AuthState, unknown>,
    userData: Record<string, any>
  ) {
    try {
      const response = await register(userData);
      const { token, user } = response;
      commit(types.SET_AUTH_TOKEN, token);
      commit(types.SET_AUTH_USER, user);
      commit(types.SET_AUTH_ERROR, null);
      router.push('/dang-nhap');
    } catch (error: any) {
      let errorMessage = 'Đăng ký thất bại. Vui lòng thử lại.';
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

  async [types.RESET_PASSWORD](
    { commit }: ActionContext<AuthState, unknown>,
    payload: { email: string }
  ) {
    try {
      const response = await forgotPassword(payload);
      commit(types.SET_AUTH_ERROR, response.message);
    } catch (error: any) {
      let errorMessage = 'Gửi yêu cầu đặt lại mật khẩu thất bại.';
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

  [types.LOGOUT]({ commit }: ActionContext<AuthState, unknown>) {
    commit(types.CLEAR_AUTH_DATA);
    router.push('/dang-nhap');
  },
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions,
};