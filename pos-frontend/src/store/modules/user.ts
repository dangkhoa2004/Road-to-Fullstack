import axios from '@/api';
import * as types from '../types';
import type {ActionContext} from 'vuex';

// --- User State ---
export interface UserState {
  profile: any;
  isLoading: boolean;
  userError: string | null;
}

const state: UserState = {
  profile: null,
  isLoading: false,
  userError: null,
};

// --- Getters ---
const getters = {
  [types.GET_USER_PROFILE]: (state: UserState) => state.profile,
  [types.IS_USER_LOADING]: (state: UserState) => state.isLoading,
  [types.GET_USER_ERROR]: (state: UserState) => state.userError,
};

// --- Mutations ---
const mutations = {
  [types.SET_USER_PROFILE](state: UserState, profile: any) {
    state.profile = profile;
    state.userError = null;
  },
  [types.CLEAR_USER_PROFILE](state: UserState) {
    state.profile = null;
    state.userError = null;
  },
  [types.SET_USER_LOADING](state: UserState, status: boolean) {
    state.isLoading = status;
  },
  [types.SET_USER_ERROR](state: UserState, error: string) {
    state.userError = error;
  },
};

// --- Actions ---
const actions = {
  async [types.FETCH_USER_PROFILE]({commit}: ActionContext<UserState, unknown>) {
    commit(types.SET_USER_LOADING, true);
    try {
      const response = await axios.get('/user/profile');
      commit(types.SET_USER_PROFILE, response.data);
    } catch (error: any) {
      const errorMessage =
        error.response?.data?.message || 'Không thể tải thông tin hồ sơ người dùng.';
      commit(types.SET_USER_ERROR, errorMessage);
      console.error('Fetch user profile error:', error);
    } finally {
      commit(types.SET_USER_LOADING, false);
    }
  },

  async [types.UPDATE_USER_PROFILE](
    {commit}: ActionContext<UserState, unknown>,
    userData: Record<string, any>
  ) {
    commit(types.SET_USER_LOADING, true);
    try {
      const response = await axios.put('/user/profile', userData);
      commit(types.SET_USER_PROFILE, response.data);
      return true;
    } catch (error: any) {
      const errorMessage =
        error.response?.data?.message || 'Không thể cập nhật thông tin hồ sơ người dùng.';
      commit(types.SET_USER_ERROR, errorMessage);
      console.error('Update user profile error:', error);
      throw error;
    } finally {
      commit(types.SET_USER_LOADING, false);
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
