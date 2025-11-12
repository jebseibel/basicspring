// Base types
export interface Food {
    extid: string;
    code: string;
    name: string;
    category?: string;
    subcategory?: string;
    description?: string;
    notes?: string;
    foundation?: boolean;
    mixable?: boolean;
    flavor?: Flavor;
    nutrition?: Nutrition;
    serving?: Serving;
}

export interface FoodRequest {
    code: string;
    name: string;
    category?: string;
    subcategory?: string;
    description?: string;
    notes?: string;
    foundation?: boolean;
    mixable?: boolean;
    flavorExtid?: string;
    nutritionExtid?: string;
    servingExtid?: string;
}

export interface Nutrition {
    extid: string;
    code: string;
    name: string;
    category?: string;
    subcategory?: string;
    description?: string;
    notes?: string;
    carbohydrate: number;
    fat: number;
    protein: number;
    sugar: number;
}

export interface NutritionRequest {
    code: string;
    name: string;
    category?: string;
    subcategory?: string;
    description?: string;
    notes?: string;
    carbohydrate: number;
    fat: number;
    protein: number;
    sugar: number;
}

export interface Flavor {
    extid: string;
    code: string;
    name: string;
    category?: string;
    subcategory?: string;
    description?: string;
    notes?: string;
    howtouse?: string;
    crunch: number;
    punch: number;
    sweet: number;
    savory: number;
}

export interface FlavorRequest {
    code: string;
    name: string;
    category?: string;
    subcategory?: string;
    description?: string;
    notes?: string;
    howtouse?: string;
    crunch: number;
    punch: number;
    sweet: number;
    savory: number;
}

export interface Serving {
    extid: string;
    code: string;
    name: string;
    category?: string;
    subcategory?: string;
    description?: string;
    notes?: string;
    cup: number;
    quarter: number;
    tablespoon: number;
    teaspoon: number;
    gram: number;
}

export interface ServingRequest {
    code: string;
    name: string;
    category?: string;
    subcategory?: string;
    description?: string;
    notes?: string;
    cup: number;
    quarter: number;
    tablespoon: number;
    teaspoon: number;
    gram: number;
}

export interface Profile {
    extid: string;
    nickname: string;
    fullname: string;
}

export interface ProfileRequest {
    nickname: string;
    fullname: string;
}

export interface Company {
    extid: string;
    code: string;
    name: string;
    description?: string;
}

export interface CompanyRequest {
    code: string;
    name: string;
    description?: string;
}

// Salad Types
export interface Salad {
    extid: string;
    name: string;
    description?: string;
    userExtid: string;
    createdAt: string;
    updatedAt: string;
}

export interface SaladRequest {
    name: string;
    description?: string;
}

// Mixture Types
export interface Mixture {
    extid: string;
    name: string;
    description?: string;
    userExtid: string;
    createdAt: string;
    updatedAt: string;
}

export interface MixtureRequest {
    name: string;
    description?: string;
}

// Authentication
export interface User {
    username: string;
    email?: string;
    role: string;
}

export interface LoginRequest {
    username: string;
    password: string;
}

export interface RegisterRequest {
    username: string;
    password: string;
    email?: string;
}

export interface AuthResponse {
    token: string;
    username: string;
    email?: string;
    role: string;
}

// Pagination
export interface PageRequest {
    page?: number;
    size?: number;
    sort?: string;
    active?: boolean;
}

export interface PageResponse<T> {
    content: T[];
    totalElements: number;
    totalPages: number;
    size: number;
    number: number;
}