import axios from '../index';
import type { AxiosResponse, AxiosError } from 'axios';

// --- Interface User ---
export interface User {
  id: number;
  name: string;
  username: string;
  phone: string;
  email: string;
  isActive: boolean;
  role: {
    id: number;
    name: string;
  };
  permissions: string[];
}

// --- Interface Auth API Response ---
interface AuthApiResponse {
  message: string;
  code: string;
  data: {
    token: string;
    message: string;
    employeeId: number;
  };
}

export interface AuthResponse {
  token: string;
  employeeId: number;
}

export interface ResetPasswordResponse {
  message: string;
  success: boolean;
}

// --- Hàm login ---
export const login = async (credentials: Record<string, any>): Promise<AuthResponse> => {
  try {
    const response: AxiosResponse<AuthApiResponse> = await axios.post('/auth/login', credentials);
    const { token, employeeId } = response.data.data;
    return { token, employeeId };
  } catch (error: unknown) {
    const axiosError = error as AxiosError;
    console.error('Login error:', axiosError.response?.data || axiosError.message);
    throw error;
  }
};

// --- Hàm register ---
export const register = async (userData: Record<string, any>): Promise<AuthResponse> => {
  try {
    const response: AxiosResponse<AuthApiResponse> = await axios.post('/auth/register', userData);
    const { token, employeeId } = response.data.data;
    return { token, employeeId };
  } catch (error: unknown) {
    const axiosError = error as AxiosError;
    console.error('Register error:', axiosError.response?.data || axiosError.message);
    throw error;
  }
};

// --- Hàm forgot password ---
export const forgotPassword = async (payload: { email: string }): Promise<ResetPasswordResponse> => {
  try {
    const response: AxiosResponse<{
      message: string;
      code: string;
      data: ResetPasswordResponse;
    }> = await axios.post('/auth/forgot-password', payload);
    return response.data.data;
  } catch (error: unknown) {
    const axiosError = error as AxiosError;
    console.error('Forgot password error:', axiosError.response?.data || axiosError.message);
    throw error;
  }
};

// --- Hàm reset password ---
export const resetPassword = async (
  token: string,
  payload: { newPassword: string; confirmPassword: string }
): Promise<ResetPasswordResponse> => {
  try {
    const response: AxiosResponse<{
      message: string;
      code: string;
      data: ResetPasswordResponse;
    }> = await axios.post(`/auth/reset-password?token=${encodeURIComponent(token)}`, payload);
    return response.data.data;
  } catch (error: unknown) {
    const axiosError = error as AxiosError;
    console.error('Reset password error:', axiosError.response?.data || axiosError.message);
    throw error;
  }
};

// --- Hàm lấy thông tin nhân viên theo ID ---
export const getEmployeeById = async (id: number): Promise<User> => {
  try {
    const response: AxiosResponse<{
      status: string;
      string: string;
      data: User;
    }> = await axios.get(`/employees/${id}`);

    return response.data.data;
  } catch (error: unknown) {
    const axiosError = error as AxiosError;
    console.error('Get employee by ID error:', axiosError.response?.data || axiosError.message);
    throw error;
  }
};
