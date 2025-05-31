import axios from '../index';
import type { AxiosResponse, AxiosError } from 'axios';

// Interface cho user (tuỳ chỉnh thêm nếu muốn rõ ràng hơn)
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

// Interface cho response API trả về
interface AuthApiResponse {
  status: string;
  string: string;
  data: {
    token: string;
    message: string;
    user: User;
  };
}

// Interface cho dữ liệu trả về của hàm login/register (gọn gàng hơn)
export interface AuthResponse {
  token: string;
  user: User;
}

export const login = async (credentials: Record<string, any>): Promise<AuthResponse> => {
  try {
    const response: AxiosResponse<AuthApiResponse> = await axios.post('/auth/login', credentials);
    // Dữ liệu thực tế nằm trong response.data.data
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
