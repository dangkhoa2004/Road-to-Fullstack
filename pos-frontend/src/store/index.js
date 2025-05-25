import {createStore} from 'vuex';
import auth from './modules/auth';
import user from './modules/user';
import product from './modules/product';

export default createStore({
    modules: {
        auth,
        user,
        product
    },
    state: {},
    mutations: {},
    actions: {}
});
