import { createStore } from 'vuex';
import auth from './modules/auth';
import user from './modules/user';
import product from './modules/product';
import type { AuthState } from './modules/auth';
import type { UserState } from './modules/user';
import type { ProductState } from './modules/product';

// --- Định nghĩa RootState ---
export interface RootState {
  auth: AuthState;
  user: UserState;
  product: ProductState;
}

const store = createStore<RootState>({
  modules: {
    auth,
    user,
    product,
  },
});

export default store;
