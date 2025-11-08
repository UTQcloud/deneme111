import axios from 'axios';

// Backend API base URL - değiştirilecek endpoint'ler için tek nokta
const API_BASE_URL = 'http://localhost:8080';
const AUTH_TOKEN_KEY = 'authToken';

const encodeBasicToken = (mail, password) => {
  const rawToken = `${mail}:${password}`;

  if (typeof window !== 'undefined' && typeof window.btoa === 'function') {
    return window.btoa(rawToken);
  }

  if (typeof Buffer !== 'undefined') {
    // Node.js fallback (ör. testler sırasında)
    return Buffer.from(rawToken).toString('base64');
  }

  throw new Error('Base64 encoding is not supported in this environment');
};

// Axios instance oluştur
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

const applyAuthHeader = (token) => {
  if (token) {
    api.defaults.headers.common['Authorization'] = `Basic ${token}`;
  } else {
    delete api.defaults.headers.common['Authorization'];
  }
};

const loadPersistedToken = () => {
  if (typeof window === 'undefined') {
    return;
  }

  const storedToken = window.localStorage.getItem(AUTH_TOKEN_KEY);
  if (storedToken) {
    applyAuthHeader(storedToken);
  }
};

loadPersistedToken();

export const storeAuthToken = (token) => {
  if (typeof window !== 'undefined') {
    if (token) {
      window.localStorage.setItem(AUTH_TOKEN_KEY, token);
    } else {
      window.localStorage.removeItem(AUTH_TOKEN_KEY);
    }
  }
  applyAuthHeader(token);
};

export const clearAuthToken = () => {
  storeAuthToken(null);
};

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
      const token = encodeBasicToken(credentials.mail, credentials.password);
      storeAuthToken(token);
      return { success: true, data: response.data, token };
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
      const normalizedTime = taskData.dueTime
        ? (taskData.dueTime.length === 5 ? `${taskData.dueTime}:00` : taskData.dueTime)
        : null;

      const payload = {
        ...taskData,
        dueTime: normalizedTime,
      };
      const response = await api.post('/api/tasks', payload);
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
