import { useQuery } from '@tanstack/react-query';
import { flavorApi } from '../services/api';
import { Sparkles } from 'lucide-react';

export default function Flavors() {
    const { data: flavors, isLoading, error } = useQuery({
        queryKey: ['flavors'],
        queryFn: async () => {
            const response = await flavorApi.getAll();
            return response.data;
        },
    });

    if (isLoading) {
        return (
            <div className="flex items-center justify-center h-64">
                <div className="text-gray-500">Loading flavor profiles...</div>
            </div>
        );
    }

    if (error) {
        return (
            <div className="flex items-center justify-center h-64">
                <div className="text-red-500">Error loading flavor profiles. Make sure the backend is running.</div>
            </div>
        );
    }

    return (
        <div className="px-4 py-6 sm:px-0">
            <div className="flex items-center justify-between mb-6">
                <h1 className="text-3xl font-bold text-gray-900 flex items-center">
                    <Sparkles className="h-8 w-8 mr-3 text-purple-600" />
                    Flavor Profiles
                </h1>
                <button className="inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md text-white bg-purple-600 hover:bg-purple-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-purple-500">
                    Add Flavor Profile
                </button>
            </div>

            <div className="bg-white shadow overflow-hidden sm:rounded-lg">
                <table className="min-w-full divide-y divide-gray-200">
                    <thead className="bg-gray-50">
                    <tr>
                        <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                            Name
                        </th>
                        <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                            Code
                        </th>
                        <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                            Category
                        </th>
                        <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                            Crunch
                        </th>
                        <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                            Punch
                        </th>
                        <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                            Sweet
                        </th>
                        <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                            Savory
                        </th>
                        <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                            How to Use
                        </th>
                        <th className="px-6 py-3 relative">
                            <span className="sr-only">Actions</span>
                        </th>
                    </tr>
                    </thead>
                    <tbody className="bg-white divide-y divide-gray-200">
                    {flavors && flavors.length > 0 ? (
                        flavors.map((flavor) => (
                            <tr key={flavor.extid} className="hover:bg-gray-50">
                                <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                                    {flavor.name}
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                                    {flavor.code}
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                                    {flavor.category || '-'}
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                                    {flavor.crunch}
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                                    {flavor.punch}
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                                    {flavor.sweet}
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                                    {flavor.savory}
                                </td>
                                <td className="px-6 py-4 text-sm text-gray-500">
                                    {flavor.howtouse || '-'}
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                                    <button className="text-purple-600 hover:text-purple-900 mr-4">
                                        Edit
                                    </button>
                                    <button className="text-red-600 hover:text-red-900">
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan={9} className="px-6 py-12 text-center text-gray-500">
                                No flavor profiles found. Add your first flavor profile to get started!
                            </td>
                        </tr>
                    )}
                    </tbody>
                </table>
            </div>
        </div>
    );
}