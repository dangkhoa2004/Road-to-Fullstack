import axios from '../index';
import type { AxiosResponse, AxiosError } from 'axios';

// --- Interface User ---
interface User {
  id: number;
  name: string;
  username: string;
  role: {
    id: number;
    name: string;
  };
  phone: string;
  email: string;
  isActive: boolean;
}

// --- Interface Auth API Response ---
interface AuthApiResponse {
  status: string;
  string: string;
  data: {
    token: string;
    message: string;
    user: User;
  };
}

// --- Interface Auth Response (gọn) ---
export interface AuthResponse {
  token: string;
  user: User;
}

// --- Interface Response của forgot/reset password (theo backend) ---
export interface ResetPasswordResponse {
  message: string;
  success: boolean;
}

// --- Hàm login ---
export const login = async (credentials: Record<string, any>): Promise<AuthResponse> => {
  try {
    const response: AxiosResponse<AuthApiResponse> = await axios.post('/auth/login', credentials);
    const { token, user } = response.data.data;
    return { token, user };
  } catch (error: unknown) {
    const axiosError = error as AxiosError;
    console.error(
      'Login error:',
      axiosError.response ? axiosError.response.data : axiosError.message
    );
    throw error;
  }
};

// --- Hàm register ---
export const register = async (userData: Record<string, any>): Promise<AuthResponse> => {
  try {
    const response: AxiosResponse<AuthApiResponse> = await axios.post('/auth/register', userData);
    const { token, user } = response.data.data;
    return { token, user };
  } catch (error: unknown) {
    const axiosError = error as AxiosError;
    console.error(
      'Register error:',
      axiosError.response ? axiosError.response.data : axiosError.message
    );
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
    console.error(
      'Forgot password error:',
      axiosError.response ? axiosError.response.data : axiosError.message
    );
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
    }> = await axios.post(
      `/auth/reset-password?token=${encodeURIComponent(token)}`,
      payload
    );

    return response.data.data;
  } catch (error: unknown) {
    const axiosError = error as AxiosError;
    console.error(
      'Reset password error:',
      axiosError.response ? axiosError.response.data : axiosError.message
    );
    throw error;
  }
};
