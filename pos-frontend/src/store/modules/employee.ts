import {getAllEmployees, getEmployeeById} from '@/api/modules/employee'
import type {Employee} from '@/api/modules/employee'
import type {ActionContext} from 'vuex'

// --- State ---
export interface EmployeeState {
  employees: Employee[]
  employee: Employee | null
  employeeLoading: boolean
  employeeError: string | null
}

const state: EmployeeState = {
  employees: [],
  employee: null,
  employeeLoading: false,
  employeeError: null,
}

// --- Getters ---
const getters = {
  GET_EMPLOYEES: (state: EmployeeState) => state.employees,
  GET_EMPLOYEE: (state: EmployeeState) => state.employee,
  GET_EMPLOYEE_LOADING: (state: EmployeeState) => state.employeeLoading,
  GET_EMPLOYEE_ERROR: (state: EmployeeState) => state.employeeError,
}

// --- Mutations ---
const mutations = {
  SET_EMPLOYEES(state: EmployeeState, employees: Employee[]) {
    state.employees = employees
    state.employeeError = null
  },
  SET_EMPLOYEE(state: EmployeeState, employee: Employee | null) {
    state.employee = employee
    state.employeeError = null
  },
  SET_EMPLOYEE_LOADING(state: EmployeeState, isLoading: boolean) {
    state.employeeLoading = isLoading
  },
  SET_EMPLOYEE_ERROR(state: EmployeeState, error: string) {
    state.employeeError = error
    state.employees = []
    state.employee = null
  },
}

// --- Actions ---
const actions = {
  async FETCH_EMPLOYEES({commit}: ActionContext<EmployeeState, unknown>) {
    try {
      const data = await getAllEmployees()
      commit('SET_EMPLOYEES', data)
      return data
    } catch (error: any) {
      console.error('Error fetching employees:', error)
      const errorMessage =
        error.response?.data?.message || 'Failed to fetch employees. Please try again.'
      commit('SET_EMPLOYEE_ERROR', errorMessage)
      throw error
    }
  },

  async FETCH_EMPLOYEE_BY_ID(
    {commit}: ActionContext<EmployeeState, unknown>,
    employeeId: number
  ) {
    commit('SET_EMPLOYEE_LOADING', true)
    commit('SET_EMPLOYEE_ERROR', null)
    try {
      const data = await getEmployeeById(employeeId)
      commit('SET_EMPLOYEE', data)
      return data
    } catch (error: any) {
      console.error(`Error fetching employee by ID (${employeeId}):`, error)
      const errorMessage =
        error.response?.data?.message ||
        `Failed to fetch employee with ID ${employeeId}. Please try again.`
      commit('SET_EMPLOYEE_ERROR', errorMessage)
      commit('SET_EMPLOYEE', null)
      throw error
    } finally {
      commit('SET_EMPLOYEE_LOADING', false)
    }
  },
}

// --- Export ---
export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions,
}
