import {get, post, put, deleteApi} from '../api'
import type {AxiosError} from 'axios'

// --- Interface Role ---
export interface Role {
  id: number
  name: string
}

// --- Interface Employee ---
export interface Employee {
  id: number
  name: string
  username: string
  phone: string
  email: string
  isActive: boolean
  role: Role
  permissions: string[]
}

// --- Get All Employees ---
export const getAllEmployees = async (): Promise<Employee[]> => {
  try {
    return await get<Employee[]>('/employees/list')
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    console.error('Fetch employees error:', axiosError.message)
    throw error
  }
}

// --- Get Employee By ID ---
export const getEmployeeById = async (id: number): Promise<Employee> => {
  try {
    return await get<Employee>(`/employees/${id}`)
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    console.error(`Fetch employee detail for ID ${id} error:`, axiosError.message)
    throw error
  }
}

// --- Get Employee By Email ---
export const getEmployeeByEmail = async (email: string): Promise<Employee> => {
  try {
    return await get<Employee>(`/employees/by-email?email=${email}`)
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    console.error(`Fetch employee by email ${email} error:`, axiosError.message)
    throw error
  }
}

// --- Get Employee By Username ---
export const getEmployeeByUsername = async (username: string): Promise<Employee> => {
  try {
    return await get<Employee>(`/employees/by-username?username=${username}`)
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    console.error(`Fetch employee by username ${username} error:`, axiosError.message)
    throw error
  }
}

// --- Add Employee ---
export const addEmployee = async (employeeData: Partial<Employee>): Promise<Employee> => {
  try {
    return await post<Employee>('/employees', employeeData)
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    console.error('Add employee error:', axiosError.message)
    throw error
  }
}

// --- Update Employee ---
export const updateEmployee = async (id: number, employeeData: Partial<Employee>): Promise<Employee> => {
  try {
    return await put<Employee>(`/employees/${id}`, employeeData)
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    console.error(`Update employee ${id} error:`, axiosError.message)
    throw error
  }
}

// --- Delete Employee ---
export const deleteEmployee = async (id: number): Promise<void> => {
  try {
    await deleteApi<void>(`/employees/${id}`)
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    console.error(`Delete employee ${id} error:`, axiosError.message)
    throw error
  }
}

// --- Change Employee Password ---
export const changeEmployeePassword = async (id: number, newPassword: string): Promise<void> => {
  try {
    await post<void>(`/employees/${id}/change-password?newPassword=${newPassword}`, {})
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    console.error(`Change password for employee ${id} error:`, axiosError.message)
    throw error
  }
}
