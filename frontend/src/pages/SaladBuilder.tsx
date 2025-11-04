import { useState } from 'react';
import { useQuery, useMutation } from '@tanstack/react-query';
import { foodApi, saladApi } from '../services/api';
import type { SaladIngredient, SaladResponse } from '../types/api';
import { Plus, Trash2, ChefHat } from 'lucide-react';

export default function SaladBuilder() {
  const [ingredients, setIngredients] = useState<SaladIngredient[]>([]);
  const [saladResult, setSaladResult] = useState<SaladResponse | null>(null);

  // Fetch all foods
  const { data: foods, isLoading: foodsLoading } = useQuery({
    queryKey: ['foods'],
    queryFn: async () => {
      const response = await foodApi.getAll();
      return response.data;
    },
  });

  // Build salad mutation
  const buildSaladMutation = useMutation({
    mutationFn: async (data: { ingredients: SaladIngredient[] }) => {
      const response = await saladApi.build(data);
      return response.data;
    },
    onSuccess: (data) => {
      setSaladResult(data);
    },
  });

  const addIngredient = () => {
    if (foods && foods.length > 0) {
      setIngredients([...ingredients, { foodExtid: foods[0].extid, quantity: 1 }]);
    }
  };

  const removeIngredient = (index: number) => {
    setIngredients(ingredients.filter((_, i) => i !== index));
  };

  const updateIngredient = (index: number, field: keyof SaladIngredient, value: string | number) => {
    const updated = [...ingredients];
    updated[index] = { ...updated[index], [field]: value };
    setIngredients(updated);
  };

  const handleBuildSalad = () => {
    if (ingredients.length > 0) {
      buildSaladMutation.mutate({ ingredients });
    }
  };

  if (foodsLoading) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="text-gray-500">Loading foods...</div>
      </div>
    );
  }

  return (
    <div className="px-4 py-6 sm:px-0">
      <div className="flex items-center justify-between mb-6">
        <h1 className="text-3xl font-bold text-gray-900 flex items-center">
          <ChefHat className="h-8 w-8 mr-3 text-green-600" />
          Salad Builder
        </h1>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {/* Ingredient Selection */}
        <div className="bg-white shadow rounded-lg p-6">
          <div className="flex items-center justify-between mb-4">
            <h2 className="text-lg font-medium text-gray-900">Ingredients</h2>
            <button
              onClick={addIngredient}
              disabled={!foods || foods.length === 0}
              className="inline-flex items-center px-3 py-2 border border-transparent text-sm leading-4 font-medium rounded-md text-white bg-green-600 hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500 disabled:opacity-50 disabled:cursor-not-allowed"
            >
              <Plus className="h-4 w-4 mr-1" />
              Add Ingredient
            </button>
          </div>

          {ingredients.length === 0 ? (
            <div className="text-center py-12 text-gray-500">
              <p>No ingredients added yet.</p>
              <p className="text-sm mt-2">Click "Add Ingredient" to start building your salad!</p>
            </div>
          ) : (
            <div className="space-y-3">
              {ingredients.map((ingredient, index) => (
                <div key={index} className="flex items-center space-x-3">
                  <select
                    value={ingredient.foodExtid}
                    onChange={(e) => updateIngredient(index, 'foodExtid', e.target.value)}
                    className="flex-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-green-500 focus:ring-green-500 sm:text-sm"
                  >
                    {foods?.map((food) => (
                      <option key={food.extid} value={food.extid}>
                        {food.name}
                      </option>
                    ))}
                  </select>
                  <input
                    type="number"
                    value={ingredient.quantity}
                    onChange={(e) => updateIngredient(index, 'quantity', parseFloat(e.target.value) || 0)}
                    min="0.1"
                    step="0.1"
                    className="block w-24 rounded-md border-gray-300 shadow-sm focus:border-green-500 focus:ring-green-500 sm:text-sm"
                    placeholder="Qty"
                  />
                  <button
                    onClick={() => removeIngredient(index)}
                    className="p-2 text-red-600 hover:text-red-800"
                  >
                    <Trash2 className="h-4 w-4" />
                  </button>
                </div>
              ))}
            </div>
          )}

          <div className="mt-6">
            <button
              onClick={handleBuildSalad}
              disabled={ingredients.length === 0 || buildSaladMutation.isPending}
              className="w-full inline-flex justify-center items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md text-white bg-green-600 hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500 disabled:opacity-50 disabled:cursor-not-allowed"
            >
              {buildSaladMutation.isPending ? 'Building...' : 'Build Salad'}
            </button>
          </div>
        </div>

        {/* Results */}
        <div className="bg-white shadow rounded-lg p-6">
          <h2 className="text-lg font-medium text-gray-900 mb-4">Nutritional Summary</h2>

          {!saladResult ? (
            <div className="text-center py-12 text-gray-500">
              <p>Add ingredients and build your salad to see nutritional information.</p>
            </div>
          ) : (
            <div className="space-y-6">
              {/* Total Nutrition */}
              <div>
                <h3 className="text-sm font-medium text-gray-700 mb-3">Total Nutrition</h3>
                <div className="grid grid-cols-2 gap-4">
                  <div className="bg-blue-50 rounded-lg p-3">
                    <div className="text-sm text-gray-600">Carbohydrates</div>
                    <div className="text-2xl font-bold text-blue-600">
                      {saladResult.totalNutrition.carbohydrate}g
                    </div>
                  </div>
                  <div className="bg-yellow-50 rounded-lg p-3">
                    <div className="text-sm text-gray-600">Fat</div>
                    <div className="text-2xl font-bold text-yellow-600">
                      {saladResult.totalNutrition.fat}g
                    </div>
                  </div>
                  <div className="bg-green-50 rounded-lg p-3">
                    <div className="text-sm text-gray-600">Protein</div>
                    <div className="text-2xl font-bold text-green-600">
                      {saladResult.totalNutrition.protein}g
                    </div>
                  </div>
                  <div className="bg-pink-50 rounded-lg p-3">
                    <div className="text-sm text-gray-600">Sugar</div>
                    <div className="text-2xl font-bold text-pink-600">
                      {saladResult.totalNutrition.sugar}g
                    </div>
                  </div>
                </div>
              </div>

              {/* Average Flavor */}
              <div>
                <h3 className="text-sm font-medium text-gray-700 mb-3">Average Flavor Profile</h3>
                <div className="space-y-2">
                  <div>
                    <div className="flex items-center justify-between text-sm mb-1">
                      <span className="text-gray-600">Crunch</span>
                      <span className="font-medium">{saladResult.averageFlavor.crunch}/5</span>
                    </div>
                    <div className="w-full bg-gray-200 rounded-full h-2">
                      <div
                        className="bg-orange-500 h-2 rounded-full"
                        style={{ width: `${(saladResult.averageFlavor.crunch / 5) * 100}%` }}
                      />
                    </div>
                  </div>
                  <div>
                    <div className="flex items-center justify-between text-sm mb-1">
                      <span className="text-gray-600">Punch</span>
                      <span className="font-medium">{saladResult.averageFlavor.punch}/5</span>
                    </div>
                    <div className="w-full bg-gray-200 rounded-full h-2">
                      <div
                        className="bg-red-500 h-2 rounded-full"
                        style={{ width: `${(saladResult.averageFlavor.punch / 5) * 100}%` }}
                      />
                    </div>
                  </div>
                  <div>
                    <div className="flex items-center justify-between text-sm mb-1">
                      <span className="text-gray-600">Sweet</span>
                      <span className="font-medium">{saladResult.averageFlavor.sweet}/5</span>
                    </div>
                    <div className="w-full bg-gray-200 rounded-full h-2">
                      <div
                        className="bg-pink-500 h-2 rounded-full"
                        style={{ width: `${(saladResult.averageFlavor.sweet / 5) * 100}%` }}
                      />
                    </div>
                  </div>
                  <div>
                    <div className="flex items-center justify-between text-sm mb-1">
                      <span className="text-gray-600">Savory</span>
                      <span className="font-medium">{saladResult.averageFlavor.savory}/5</span>
                    </div>
                    <div className="w-full bg-gray-200 rounded-full h-2">
                      <div
                        className="bg-amber-600 h-2 rounded-full"
                        style={{ width: `${(saladResult.averageFlavor.savory / 5) * 100}%` }}
                      />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
