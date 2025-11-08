import axios from 'axios';

// Backend API base URL - değiştirilecek endpoint'ler için tek nokta
const API_BASE_URL = 'http://localhost:8080';

// Axios instance oluştur
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Auth Service
export const authService = {
  // Kullanıcı kaydı - Gereksinim: /api/auth/register
  register: async (userData) => {
    try {
      const response = await api.post('/api/auth/register', userData);
      return { success: true, data: response.data };
    } catch (error) {
      return {
        success: false,
        error: error.response?.data?.message || 'Registration failed',
      };
    }
  },

  // Kullanıcı girişi - Gereksinim: /api/auth/login
  login: async (credentials) => {
    try {
      const response = await api.post('/api/auth/login', credentials);
      return { success: true, data: response.data };
    } catch (error) {
      return {
        success: false,
        error: error.response?.data?.message || 'Login failed. Invalid credentials.',
      };
    }
  },
};

// Task Service (vize için sadece skeleton, final'de implement edilecek)
export const taskService = {
  getAllTasks: async () => {
    try {
      const response = await api.get('/api/tasks');
      return { success: true, data: response.data };
    } catch (error) {
      return {
        success: false,
        error: error.response?.data?.message || 'Failed to fetch tasks',
      };
    }
  },

  createTask: async (taskData) => {
    try {
      const response = await api.post('/api/tasks', taskData);
      return { success: true, data: response.data };
    } catch (error) {
      return {
        success: false,
        error: error.response?.data?.message || 'Failed to create task',
      };
    }
  },
};

export default api;
