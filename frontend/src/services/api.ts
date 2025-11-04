import axios from 'axios';
import type {
  Food,
  FoodRequest,
  Nutrition,
  NutritionRequest,
  Flavor,
  FlavorRequest,
  Serving,
  ServingRequest,
  Profile,
  ProfileRequest,
  Company,
  CompanyRequest,
  SaladRequest,
  SaladResponse,
  PageRequest,
  PageResponse,
} from '../types/api';

// Create axios instance with base configuration
const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

// Food API
export const foodApi = {
  getAll: () => api.get<Food[]>('/food'),
  getById: (extid: string) => api.get<Food>(`/food/${extid}`),
  create: (data: FoodRequest) => api.post<Food>('/food', data),
  update: (extid: string, data: FoodRequest) => api.put<Food>(`/food/${extid}`, data),
  delete: (extid: string) => api.delete(`/food/${extid}`),
};

// Nutrition API
export const nutritionApi = {
  getAll: () => api.get<Nutrition[]>('/nutrition'),
  getById: (extid: string) => api.get<Nutrition>(`/nutrition/${extid}`),
  create: (data: NutritionRequest) => api.post<Nutrition>('/nutrition', data),
  update: (extid: string, data: NutritionRequest) => api.put<Nutrition>(`/nutrition/${extid}`, data),
  delete: (extid: string) => api.delete(`/nutrition/${extid}`),
};

// Flavor API
export const flavorApi = {
  getAll: () => api.get<Flavor[]>('/flavor'),
  getById: (extid: string) => api.get<Flavor>(`/flavor/${extid}`),
  create: (data: FlavorRequest) => api.post<Flavor>('/flavor', data),
  update: (extid: string, data: FlavorRequest) => api.put<Flavor>(`/flavor/${extid}`, data),
  delete: (extid: string) => api.delete(`/flavor/${extid}`),
};

// Serving API
export const servingApi = {
  getAll: () => api.get<Serving[]>('/serving'),
  getById: (extid: string) => api.get<Serving>(`/serving/${extid}`),
  create: (data: ServingRequest) => api.post<Serving>('/serving', data),
  update: (extid: string, data: ServingRequest) => api.put<Serving>(`/serving/${extid}`, data),
  delete: (extid: string) => api.delete(`/serving/${extid}`),
};

// Profile API
export const profileApi = {
  getAll: (params?: PageRequest) => api.get<PageResponse<Profile>>('/profile', { params }),
  getById: (extid: string) => api.get<Profile>(`/profile/${extid}`),
  create: (data: ProfileRequest) => api.post<Profile>('/profile', data),
  update: (extid: string, data: ProfileRequest) => api.put<Profile>(`/profile/${extid}`, data),
  partialUpdate: (extid: string, data: Partial<ProfileRequest>) => api.patch<Profile>(`/profile/${extid}`, data),
  delete: (extid: string) => api.delete(`/profile/${extid}`),
};

// Company API
export const companyApi = {
  getAll: (params?: PageRequest) => api.get<PageResponse<Company>>('/company', { params }),
  getById: (extid: string) => api.get<Company>(`/company/${extid}`),
  create: (data: CompanyRequest) => api.post<Company>('/company', data),
  update: (extid: string, data: CompanyRequest) => api.put<Company>(`/company/${extid}`, data),
  partialUpdate: (extid: string, data: Partial<CompanyRequest>) => api.patch<Company>(`/company/${extid}`, data),
  delete: (extid: string) => api.delete(`/company/${extid}`),
};

// Salad Builder API
export const saladApi = {
  build: (data: SaladRequest) => api.post<SaladResponse>('/salad/build', data),
};

export default api;
