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
} from '../types/api';

// API Configuration
const API_BASE_URL = 'http://localhost:8080/api';

const apiClient = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

// API Endpoints
export const foodApi = {
    getAll: () => apiClient.get<Food[]>('/food'),
    getById: (extid: string) => apiClient.get<Food>(`/food/${extid}`),
    create: (food: FoodRequest) => apiClient.post<Food>('/food', food),
    update: (extid: string, food: FoodRequest) => apiClient.put<Food>(`/food/${extid}`, food),
    delete: (extid: string) => apiClient.delete(`/food/${extid}`),
};

export const nutritionApi = {
    getAll: () => apiClient.get<Nutrition[]>('/nutrition'),
    getById: (extid: string) => apiClient.get<Nutrition>(`/nutrition/${extid}`),
    create: (nutrition: NutritionRequest) => apiClient.post<Nutrition>('/nutrition', nutrition),
    update: (extid: string, nutrition: NutritionRequest) => apiClient.put<Nutrition>(`/nutrition/${extid}`, nutrition),
    delete: (extid: string) => apiClient.delete(`/nutrition/${extid}`),
};

export const flavorApi = {
    getAll: () => apiClient.get<Flavor[]>('/flavor'),
    getById: (extid: string) => apiClient.get<Flavor>(`/flavor/${extid}`),
    create: (flavor: FlavorRequest) => apiClient.post<Flavor>('/flavor', flavor),
    update: (extid: string, flavor: FlavorRequest) => apiClient.put<Flavor>(`/flavor/${extid}`, flavor),
    delete: (extid: string) => apiClient.delete(`/flavor/${extid}`),
};

export const servingApi = {
    getAll: () => apiClient.get<Serving[]>('/serving'),
    getById: (extid: string) => apiClient.get<Serving>(`/serving/${extid}`),
    create: (serving: ServingRequest) => apiClient.post<Serving>('/serving', serving),
    update: (extid: string, serving: ServingRequest) => apiClient.put<Serving>(`/serving/${extid}`, serving),
    delete: (extid: string) => apiClient.delete(`/serving/${extid}`),
};

export const profileApi = {
    getAll: () => apiClient.get<Profile[]>('/profile'),
    getById: (extid: string) => apiClient.get<Profile>(`/profile/${extid}`),
    create: (profile: ProfileRequest) => apiClient.post<Profile>('/profile', profile),
    update: (extid: string, profile: ProfileRequest) => apiClient.put<Profile>(`/profile/${extid}`, profile),
    delete: (extid: string) => apiClient.delete(`/profile/${extid}`),
};

export const companyApi = {
    getAll: () => apiClient.get<Company[]>('/company'),
    getById: (extid: string) => apiClient.get<Company>(`/company/${extid}`),
    create: (company: CompanyRequest) => apiClient.post<Company>('/company', company),
    update: (extid: string, company: CompanyRequest) => apiClient.put<Company>(`/company/${extid}`, company),
    delete: (extid: string) => apiClient.delete(`/company/${extid}`),
};

export const saladApi = {
    buildSalad: (request: SaladRequest) => apiClient.post<SaladResponse>('/salad/build', request),
};