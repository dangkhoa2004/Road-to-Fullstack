// api.ts
import axios from './index';
import type { AxiosRequestConfig, AxiosResponse } from 'axios';

// Interface cho response data chuẩn
interface ApiResponse<T = any> {
  status: string;
  data: T;
  string?: string;
}

// Hàm chuẩn hoá response
export const handleResponse = <T>(response: AxiosResponse<ApiResponse<T>>): T => {
  const { status, data, string } = response.data;
  if (status === "200" && data !== undefined) {
    return data;
  } else {
    throw new Error(string || "Unknown API response");
  }
};

// GET
export const get = async <T>(
  url: string,
  config: AxiosRequestConfig = {}
): Promise<T> => {
  const response = await axios.get<ApiResponse<T>>(url, config);
  return handleResponse<T>(response);
};

// POST
export const post = async <T>(
  url: string,
  body: any,
  config: AxiosRequestConfig = {}
): Promise<T> => {
  const response = await axios.post<ApiResponse<T>>(url, body, config);
  return handleResponse<T>(response);
};

// PUT
export const put = async <T>(
  url: string,
  body: any,
  config: AxiosRequestConfig = {}
): Promise<T> => {
  const response = await axios.put<ApiResponse<T>>(url, body, config);
  return handleResponse<T>(response);
};

// DELETE
export const deleteApi = async <T>(
  url: string,
  config: AxiosRequestConfig = {}
): Promise<T> => {
  const response = await axios.delete<ApiResponse<T>>(url, config);
  return handleResponse<T>(response);
};
