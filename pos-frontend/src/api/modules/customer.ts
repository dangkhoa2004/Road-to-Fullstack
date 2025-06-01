import { get, post, put, deleteApi } from '../api'
import type { AxiosError } from 'axios'

// --- Interface Customer ---
export interface Customer {
    id: number
    name: string
    phone: string
    email: string
    address: string
}

// --- Get All Customers ---
export const getAllCustomers = async (): Promise<Customer[]> => {
    try {
        return await get<Customer[]>('/customer/list')
    } catch (error: unknown) {
        const axiosError = error as AxiosError
        console.error('Fetch customers error:', axiosError.message)
        throw error
    }
}

// --- Get Customer By ID ---
export const getCustomerById = async (id: number): Promise<Customer> => {
    try {
        return await get<Customer>(`/customer/${id}`)
    } catch (error: unknown) {
        const axiosError = error as AxiosError
        console.error(`Fetch customer detail for ID ${id} error:`, axiosError.message)
        throw error
    }
}

// --- Get Customer By Email ---
export const getCustomerByEmail = async (email: string): Promise<Customer> => {
    try {
        return await get<Customer>(`/customer/by-email?email=${email}`)
    } catch (error: unknown) {
        const axiosError = error as AxiosError
        console.error(`Fetch customer by email ${email} error:`, axiosError.message)
        throw error
    }
}

// --- Get Customer By Phone ---
export const getCustomerByPhone = async (phone: string): Promise<Customer> => {
    try {
        return await get<Customer>(`/customer/by-phone?phone=${phone}`)
    } catch (error: unknown) {
        const axiosError = error as AxiosError
        console.error(`Fetch customer by phone ${phone} error:`, axiosError.message)
        throw error
    }
}

// --- Add Customer ---
export const addCustomer = async (customerData: Partial<Customer>): Promise<Customer> => {
    try {
        return await post<Customer>('/customer', customerData)
    } catch (error: unknown) {
        const axiosError = error as AxiosError
        console.error('Add customer error:', axiosError.message)
        throw error
    }
}

// --- Update Customer ---
export const updateCustomer = async (id: number, customerData: Partial<Customer>): Promise<Customer> => {
    try {
        return await put<Customer>(`/customer/${id}`, customerData)
    } catch (error: unknown) {
        const axiosError = error as AxiosError
        console.error(`Update customer ${id} error:`, axiosError.message)
        throw error
    }
}

// --- Delete Customer ---
export const deleteCustomer = async (id: number): Promise<void> => {
    try {
        await deleteApi<void>(`/customer/${id}`)
    } catch (error: unknown) {
        const axiosError = error as AxiosError
        console.error(`Delete customer ${id} error:`, axiosError.message)
        throw error
    }
}
