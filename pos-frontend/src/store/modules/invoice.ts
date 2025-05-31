import { getAllInvoices, getInvoiceById, addInvoice, updateInvoice, deleteInvoice } from '@/api/invoice'
import type { Invoice } from '@/api/invoice'
import type { ActionContext } from 'vuex'

export interface InvoiceState {
  invoices: Invoice[]
  invoice: Invoice | null
  invoiceLoading: boolean
  invoiceError: string | null
}

const state: InvoiceState = {
  invoices: [],
  invoice: null,
  invoiceLoading: false,
  invoiceError: null,
}

const getters = {
  GET_INVOICES: (state: InvoiceState) => state.invoices,
  GET_INVOICE: (state: InvoiceState) => state.invoice,
  GET_INVOICE_LOADING: (state: InvoiceState) => state.invoiceLoading,
  GET_INVOICE_ERROR: (state: InvoiceState) => state.invoiceError,
}

const mutations = {
  SET_INVOICES(state: InvoiceState, invoices: Invoice[]) {
    state.invoices = invoices
    state.invoiceError = null
  },
  SET_INVOICE(state: InvoiceState, invoice: Invoice | null) {
    state.invoice = invoice
    state.invoiceError = null
  },
  SET_INVOICE_LOADING(state: InvoiceState, isLoading: boolean) {
    state.invoiceLoading = isLoading
  },
  SET_INVOICE_ERROR(state: InvoiceState, error: string) {
    state.invoiceError = error
    state.invoices = []
    state.invoice = null
  },
}

const actions = {
  async FETCH_INVOICES({ commit }: ActionContext<InvoiceState, unknown>) {
    try {
      const data = await getAllInvoices()
      commit('SET_INVOICES', data)
      return data
    } catch (error: any) {
      console.error('Error fetching invoices:', error)
      const errorMessage =
        error.response?.data?.message || 'Failed to fetch invoices. Please try again.'
      commit('SET_INVOICE_ERROR', errorMessage)
      throw error
    }
  },

  async FETCH_INVOICE_BY_ID({ commit }: ActionContext<InvoiceState, unknown>, invoiceId: number) {
    commit('SET_INVOICE_LOADING', true)
    commit('SET_INVOICE_ERROR', null)
    try {
      const data = await getInvoiceById(invoiceId)
      commit('SET_INVOICE', data)
      return data
    } catch (error: any) {
      console.error(`Error fetching invoice by ID (${invoiceId}):`, error)
      const errorMessage =
        error.response?.data?.message ||
        `Failed to fetch invoice with ID ${invoiceId}. Please try again.`
      commit('SET_INVOICE_ERROR', errorMessage)
      commit('SET_INVOICE', null)
      throw error
    } finally {
      commit('SET_INVOICE_LOADING', false)
    }
  },

  async ADD_INVOICE(_: ActionContext<InvoiceState, unknown>, invoiceData: Partial<Invoice>) {
    try {
      return await addInvoice(invoiceData)
    } catch (error: any) {
      console.error('Error adding invoice:', error)
      throw error
    }
  },

  async UPDATE_INVOICE(
    _: ActionContext<InvoiceState, unknown>,
    { invoiceId, invoiceData }: { invoiceId: number; invoiceData: Partial<Invoice> }
  ) {
    try {
      return await updateInvoice(invoiceId, invoiceData)
    } catch (error: any) {
      console.error(`Error updating invoice with ID ${invoiceId}:`, error)
      throw error
    }
  },

  async DELETE_INVOICE(_: ActionContext<InvoiceState, unknown>, invoiceId: number) {
    try {
      await deleteInvoice(invoiceId)
    } catch (error: any) {
      console.error(`Error deleting invoice with ID ${invoiceId}:`, error)
      throw error
    }
  },
}

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions,
}
