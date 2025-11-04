import { Salad, Apple, Sparkles, Scale, Utensils } from 'lucide-react';
import { useQuery } from '@tanstack/react-query';
import { foodApi, nutritionApi, flavorApi, servingApi } from '../services/api';

export default function Dashboard() {
    // Fetch data for stats
    const { data: foods } = useQuery({
        queryKey: ['foods'],
        queryFn: async () => {
            const response = await foodApi.getAll();
            return response.data;
        },
    });

    const { data: nutritions } = useQuery({
        queryKey: ['nutritions'],
        queryFn: async () => {
            const response = await nutritionApi.getAll();
            return response.data;
        },
    });

    const { data: flavors } = useQuery({
        queryKey: ['flavors'],
        queryFn: async () => {
            const response = await flavorApi.getAll();
            return response.data;
        },
    });

    const { data: servings } = useQuery({
        queryKey: ['servings'],
        queryFn: async () => {
            const response = await servingApi.getAll();
            return response.data;
        },
    });

    return (
        <div className="px-4 py-6 sm:px-0">
            <h1 className="text-3xl font-bold text-gray-900 mb-8">
                Custom Profile Salad System
            </h1>

            {/* Stats Grid */}
            <div className="grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-5 mb-8">
                <div className="bg-white overflow-hidden shadow rounded-lg">
                    <div className="p-5">
                        <div className="flex items-center">
                            <div className="flex-shrink-0">
                                <Apple className="h-6 w-6 text-green-600" />
                            </div>
                            <div className="ml-5 w-0 flex-1">
                                <dl>
                                    <dt className="text-sm font-medium text-gray-500 truncate">
                                        Food Items
                                    </dt>
                                    <dd className="text-lg font-medium text-gray-900">
                                        {foods?.length || 0}
                                    </dd>
                                </dl>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="bg-white overflow-hidden shadow rounded-lg">
                    <div className="p-5">
                        <div className="flex items-center">
                            <div className="flex-shrink-0">
                                <Scale className="h-6 w-6 text-blue-600" />
                            </div>
                            <div className="ml-5 w-0 flex-1">
                                <dl>
                                    <dt className="text-sm font-medium text-gray-500 truncate">
                                        Nutrition Profiles
                                    </dt>
                                    <dd className="text-lg font-medium text-gray-900">
                                        {nutritions?.length || 0}
                                    </dd>
                                </dl>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="bg-white overflow-hidden shadow rounded-lg">
                    <div className="p-5">
                        <div className="flex items-center">
                            <div className="flex-shrink-0">
                                <Sparkles className="h-6 w-6 text-purple-600" />
                            </div>
                            <div className="ml-5 w-0 flex-1">
                                <dl>
                                    <dt className="text-sm font-medium text-gray-500 truncate">
                                        Flavor Profiles
                                    </dt>
                                    <dd className="text-lg font-medium text-gray-900">
                                        {flavors?.length || 0}
                                    </dd>
                                </dl>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="bg-white overflow-hidden shadow rounded-lg">
                    <div className="p-5">
                        <div className="flex items-center">
                            <div className="flex-shrink-0">
                                <Utensils className="h-6 w-6 text-amber-600" />
                            </div>
                            <div className="ml-5 w-0 flex-1">
                                <dl>
                                    <dt className="text-sm font-medium text-gray-500 truncate">
                                        Serving Profiles
                                    </dt>
                                    <dd className="text-lg font-medium text-gray-900">
                                        {servings?.length || 0}
                                    </dd>
                                </dl>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="bg-white overflow-hidden shadow rounded-lg">
                    <div className="p-5">
                        <div className="flex items-center">
                            <div className="flex-shrink-0">
                                <Salad className="h-6 w-6 text-orange-600" />
                            </div>
                            <div className="ml-5 w-0 flex-1">
                                <dl>
                                    <dt className="text-sm font-medium text-gray-500 truncate">
                                        Salads Built
                                    </dt>
                                    <dd className="text-lg font-medium text-gray-900">0</dd>
                                </dl>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            {/* Salad Actions */}
            <div className="bg-white shadow rounded-lg p-6 mb-8">
                <h2 className="text-lg font-medium text-gray-900 mb-4">Salad Actions</h2>
                <div className="grid grid-cols-1 gap-4 sm:grid-cols-2">
                    <a
                        href="/salad-builder"
                        className="relative rounded-lg border border-gray-300 bg-white px-6 py-5 shadow-sm flex items-center space-x-3 hover:border-gray-400 focus-within:ring-2 focus-within:ring-offset-2 focus-within:ring-orange-500"
                    >
                        <div className="flex-shrink-0">
                            <Salad className="h-10 w-10 text-orange-600" />
                        </div>
                        <div className="flex-1 min-w-0">
                            <span className="absolute inset-0" aria-hidden="true" />
                            <p className="text-sm font-medium text-gray-900">Build a Salad</p>
                            <p className="text-sm text-gray-500 truncate">
                                Create custom salad with nutrition analysis
                            </p>
                        </div>
                    </a>

                    <a
                        href="/salads"
                        className="relative rounded-lg border border-gray-300 bg-white px-6 py-5 shadow-sm flex items-center space-x-3 hover:border-gray-400 focus-within:ring-2 focus-within:ring-offset-2 focus-within:ring-orange-500"
                    >
                        <div className="flex-shrink-0">
                            <Salad className="h-10 w-10 text-orange-600" />
                        </div>
                        <div className="flex-1 min-w-0">
                            <span className="absolute inset-0" aria-hidden="true" />
                            <p className="text-sm font-medium text-gray-900">View Salads</p>
                            <p className="text-sm text-gray-500 truncate">
                                Browse your saved salad creations
                            </p>
                        </div>
                    </a>
                </div>
            </div>

            {/* Management Actions */}
            <div className="bg-white shadow rounded-lg p-6">
                <h2 className="text-lg font-medium text-gray-900 mb-4">Manage Data</h2>
                <div className="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-4">
                    <a
                        href="/foods"
                        className="relative rounded-lg border border-gray-300 bg-white px-6 py-5 shadow-sm flex items-center space-x-3 hover:border-gray-400 focus-within:ring-2 focus-within:ring-offset-2 focus-within:ring-green-500"
                    >
                        <div className="flex-shrink-0">
                            <Apple className="h-10 w-10 text-green-600" />
                        </div>
                        <div className="flex-1 min-w-0">
                            <span className="absolute inset-0" aria-hidden="true" />
                            <p className="text-sm font-medium text-gray-900">Manage Foods</p>
                            <p className="text-sm text-gray-500 truncate">
                                Add or edit food items
                            </p>
                        </div>
                    </a>

                    <a
                        href="/nutrition"
                        className="relative rounded-lg border border-gray-300 bg-white px-6 py-5 shadow-sm flex items-center space-x-3 hover:border-gray-400 focus-within:ring-2 focus-within:ring-offset-2 focus-within:ring-blue-500"
                    >
                        <div className="flex-shrink-0">
                            <Scale className="h-10 w-10 text-blue-600" />
                        </div>
                        <div className="flex-1 min-w-0">
                            <span className="absolute inset-0" aria-hidden="true" />
                            <p className="text-sm font-medium text-gray-900">Manage Nutrition</p>
                            <p className="text-sm text-gray-500 truncate">
                                View and manage nutrition profiles
                            </p>
                        </div>
                    </a>

                    <a
                        href="/flavors"
                        className="relative rounded-lg border border-gray-300 bg-white px-6 py-5 shadow-sm flex items-center space-x-3 hover:border-gray-400 focus-within:ring-2 focus-within:ring-offset-2 focus-within:ring-purple-500"
                    >
                        <div className="flex-shrink-0">
                            <Sparkles className="h-10 w-10 text-purple-600" />
                        </div>
                        <div className="flex-1 min-w-0">
                            <span className="absolute inset-0" aria-hidden="true" />
                            <p className="text-sm font-medium text-gray-900">Manage Flavors</p>
                            <p className="text-sm text-gray-500 truncate">
                                View and manage flavor profiles
                            </p>
                        </div>
                    </a>

                    <a
                        href="/serving"
                        className="relative rounded-lg border border-gray-300 bg-white px-6 py-5 shadow-sm flex items-center space-x-3 hover:border-gray-400 focus-within:ring-2 focus-within:ring-offset-2 focus-within:ring-amber-500"
                    >
                        <div className="flex-shrink-0">
                            <Utensils className="h-10 w-10 text-amber-600" />
                        </div>
                        <div className="flex-1 min-w-0">
                            <span className="absolute inset-0" aria-hidden="true" />
                            <p className="text-sm font-medium text-gray-900">Manage Servings</p>
                            <p className="text-sm text-gray-500 truncate">
                                View and manage serving profiles
                            </p>
                        </div>
                    </a>
                </div>
            </div>
        </div>
    );
}