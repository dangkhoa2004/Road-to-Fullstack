import { get, post, put, deleteApi } from '../api'
import type { AxiosError } from 'axios'

export interface InvoiceItem {
  id: number
  productId: number
  productName: string
  quantity: number
  unitPrice: number
  itemTotal: number
}

export interface Invoice {
  id: number
  customerId: number
  employeeId: number
  tableId: number
  discountId: number
  subTotal: number
  discountAmount: number
  taxAmount: number
  totalAmount: number
  status: string
  note: string
  items: InvoiceItem[]
}

export const getAllInvoices = async (): Promise<Invoice[]> => {
  try {
    return await get<Invoice[]>('/invoice/list')
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    console.error('Fetch invoices error:', axiosError.message)
    throw error
  }
}

export const getInvoiceById = async (invoiceId: number): Promise<Invoice> => {
  try {
    return await get<Invoice>(`/invoice/${invoiceId}`)
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    console.error(`Fetch invoice detail for ID ${invoiceId} error:`, axiosError.message)
    throw error
  }
}

export const addInvoice = async (invoiceData: Partial<Invoice>): Promise<Invoice> => {
  try {
    return await post<Invoice>('/invoice', invoiceData)
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    console.error('Add invoice error:', axiosError.message)
    throw error
  }
}

export const updateInvoice = async (
  invoiceId: number,
  invoiceData: Partial<Invoice>
): Promise<Invoice> => {
  try {
    return await put<Invoice>(`/invoice/${invoiceId}`, invoiceData)
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    console.error(`Update invoice ${invoiceId} error:`, axiosError.message)
    throw error
  }
}

export const deleteInvoice = async (invoiceId: number): Promise<void> => {
  try {
    await deleteApi<void>(`/invoice/${invoiceId}`)
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    console.error(`Delete invoice ${invoiceId} error:`, axiosError.message)
    throw error
  }
}
