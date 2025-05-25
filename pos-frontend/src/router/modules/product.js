import Products from '../../views/Products/ProductView.vue';
import ProductDetail from '../../views/Products/ProductDetailView.vue';

const productRoutes = [
    {
        path: '/products',
        name: 'ProductsList',
        component: Products,
        meta: {requiresAuth: true},
    },
    {
        path: '/products/:id',
        name: 'ProductDetail',
        component: ProductDetail,
        props: true,
        meta: {requiresAuth: true},
    },
    // ... bạn có thể thêm các route khác cho sản phẩm ở đây nếu cần, ví dụ /products/add
    // {
    //     path: '/products/add',
    //     name: 'AddProduct',
    //     component: AddProductView,
    //     meta: { requiresAuth: true }
    // }
];

export default productRoutes;
