import { get, post, put, deleteApi } from './api';
import type { AxiosError } from 'axios';

// Định nghĩa đầy đủ Product
export interface Product {
  id: number;
  barcode: string;
  name: string;
  price: number;
  quantity: number;
  image_path: string;
  category_id: number;
  is_active: number;
  created_at: string;
  updated_at: string;
  version: number;
}

// Lấy toàn bộ sản phẩm
export const getAllProduct = async (): Promise<Product[]> => {
  try {
    return await get<Product[]>('/product/list');
  } catch (error: unknown) {
    const axiosError = error as AxiosError;
    console.error('Fetch products error:', axiosError.message);
    throw error;
  }
};

// Lấy chi tiết sản phẩm theo ID
export const getProductById = async (productId: number): Promise<Product> => {
  try {
    return await get<Product>(`/product/${productId}`);
  } catch (error: unknown) {
    const axiosError = error as AxiosError;
    console.error(`Fetch product detail for ID ${productId} error:`, axiosError.message);
    throw error;
  }
};

// Thêm sản phẩm mới
export const addProduct = async (productData: Partial<Product>): Promise<Product> => {
  try {
    return await post<Product>('/product', productData);
  } catch (error: unknown) {
    const axiosError = error as AxiosError;
    console.error('Add product error:', axiosError.message);
    throw error;
  }
};

// Sửa sản phẩm
export const updateProduct = async (
  productId: number,
  productData: Partial<Product>
): Promise<Product> => {
  try {
    return await put<Product>(`/product/${productId}`, productData);
  } catch (error: unknown) {
    const axiosError = error as AxiosError;
    console.error(`Update product ${productId} error:`, axiosError.message);
    throw error;
  }
};

// Xoá sản phẩm
export const deleteProduct = async (productId: number): Promise<void> => {
  try {
    await deleteApi<void>(`/product/${productId}`);
  } catch (error: unknown) {
    const axiosError = error as AxiosError;
    console.error(`Delete product ${productId} error:`, axiosError.message);
    throw error;
  }
};
