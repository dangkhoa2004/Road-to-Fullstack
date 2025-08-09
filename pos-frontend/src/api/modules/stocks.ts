import {get} from '../api'
import type {AxiosError} from 'axios'

export interface Stock {
  id: number
  productId: number
  productName: string
  quantity: number
  note: string
  employeeId: number | null
  employeeName: string | null
}

// Lấy danh sách phiếu xuất kho
export const getAllStockOuts = async (): Promise<Stock[]> => {
  try {
    return await get<Stock[]>('/stock/out/list')
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    console.error('Fetch stock-out error:', axiosError.message)
    throw error
  }
}

// Lấy danh sách phiếu nhập kho
export const getAllStockIns = async (): Promise<Stock[]> => {
  try {
    return await get<Stock[]>('/stock/in/list')
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    console.error('Fetch stock-in error:', axiosError.message)
    throw error
  }
}
