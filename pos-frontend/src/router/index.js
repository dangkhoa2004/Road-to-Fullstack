import router from './routes';
import {useLoadingStore} from '@/utils/helpers';

let loadingTimeout = null;

router.beforeEach((to, from, next) => {
    const loadingStore = useLoadingStore();

    loadingStore.setLoading(true);

    if (loadingTimeout) {
        clearTimeout(loadingTimeout);
        loadingTimeout = null;
    }

    next();
});

router.afterEach(() => {
    const loadingStore = useLoadingStore();

    loadingTimeout = setTimeout(() => {
        loadingStore.setLoading(false);
        loadingTimeout = null;
    }, 500);
});

export default router;
