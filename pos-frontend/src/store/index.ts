import { createStore } from 'vuex'
import auth from './modules/auth'
import user from './modules/user'
import product from './modules/product'
import category from './modules/category'
import discount from './modules/discount'
import invoice from './modules/invoice'
import stock from './modules/stock'
import employee from './modules/employee.ts'

import type { AuthState } from './modules/auth'
import type { UserState } from './modules/user'
import type { ProductState } from './modules/product'
import type { CategoryState } from './modules/category'
import type { DiscountState } from './modules/discount'
import type { InvoiceState } from './modules/invoice'
import type { StockState } from './modules/stock'
import type { EmployeeState } from './modules/employee.ts'

export interface RootState {
  auth: AuthState
  user: UserState
  product: ProductState
  category: CategoryState
  discount: DiscountState
  invoice: InvoiceState
  stock: StockState
  employee: EmployeeState
}

const store = createStore<RootState>({
  modules: {
    auth,
    user,
    product,
    category,
    discount,
    invoice,
    stock,
    employee,
  },
})

export default store
