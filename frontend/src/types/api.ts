// Base types
export interface Food {
  extid: string;
  code: string;
  name: string;
  category?: string;
  subcategory?: string;
  description?: string;
  notes?: string;
  flavorExtid?: string;
  nutritionExtid?: string;
  servingExtid?: string;
}

export interface FoodRequest {
  code: string;
  name: string;
  category?: string;
  subcategory?: string;
  description?: string;
  notes?: string;
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
  usage?: string;
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
  usage?: string;
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

// Salad Builder Types
export interface SaladIngredient {
  foodExtid: string;
  quantity: number;
}

export interface SaladRequest {
  ingredients: SaladIngredient[];
}

export interface SaladIngredientDetail {
  food: Food;
  quantity: number;
  nutrition: Nutrition;
  flavor: Flavor;
}

export interface SaladResponse {
  ingredients: SaladIngredientDetail[];
  totalNutrition: {
    carbohydrate: number;
    fat: number;
    protein: number;
    sugar: number;
  };
  averageFlavor: {
    crunch: number;
    punch: number;
    sweet: number;
    savory: number;
  };
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
