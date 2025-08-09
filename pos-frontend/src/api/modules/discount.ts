import {get, post, put, deleteApi} from '../api'
import type {AxiosError} from 'axios'

// --- Interface Discount ---
export interface Discount {
  id: number
  code: string
  description: string
  discount_type_id: number
  value: number
  start_date: string
  end_date: string
  active: boolean
  minimum_order_amount: number
  maximum_discount_amount: number
  created_at: string
  updated_at: string
  version: number
}

// --- Get All Discounts ---
export const getAllDiscount = async (): Promise<Discount[]> => {
  try {
    return await get<Discount[]>('/discount/list')
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    console.error('Fetch discounts error:', axiosError.message)
    throw error
  }
}

// --- Get Discount By ID ---
export const getDiscountById = async (discountId: number): Promise<Discount> => {
  try {
    return await get<Discount>(`/discount/${discountId}`)
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    console.error(`Fetch discount detail for ID ${discountId} error:`, axiosError.message)
    throw error
  }
}

// --- Add Discount ---
export const addDiscount = async (discountData: Partial<Discount>): Promise<Discount> => {
  try {
    return await post<Discount>('/discount', discountData)
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    console.error('Add discount error:', axiosError.message)
    throw error
  }
}

// --- Update Discount ---
export const updateDiscount = async (
  discountId: number,
  discountData: Partial<Discount>
): Promise<Discount> => {
  try {
    return await put<Discount>(`/discount/${discountId}`, discountData)
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    console.error(`Update discount ${discountId} error:`, axiosError.message)
    throw error
  }
}

// --- Delete Discount ---
export const deleteDiscount = async (discountId: number): Promise<void> => {
  try {
    await deleteApi<void>(`/discount/${discountId}`)
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    console.error(`Delete discount ${discountId} error:`, axiosError.message)
    throw error
  }
}
