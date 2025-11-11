import { useQuery } from '@tanstack/react-query';
import { servingApi } from '../services/api';
import { Scale, ArrowUpDown, ArrowUp, ArrowDown } from 'lucide-react';
import { useState, useMemo } from 'react';
import type { Serving } from '../types/api';

type SortField = 'name' | 'cup' | 'quarter' | 'tablespoon' | 'teaspoon' | 'gram';
type SortDirection = 'asc' | 'desc' | null;

export default function Serving() {
    const [sortField, setSortField] = useState<SortField | null>(null);
    const [sortDirection, setSortDirection] = useState<SortDirection>(null);

    const { data: servings, isLoading, error } = useQuery({
        queryKey: ['servings'],
        queryFn: async () => {
            const response = await servingApi.getAll();
            return response.data;
        },
    });

    const sortedServings = useMemo(() => {
        if (!servings || !sortField || !sortDirection) return servings;

        return [...servings].sort((a, b) => {
            let aValue = a[sortField];
            let bValue = b[sortField];

            // Handle null/undefined values
            if (aValue == null) aValue = '';
            if (bValue == null) bValue = '';

            // Convert to lowercase for string comparison
            if (typeof aValue === 'string') aValue = aValue.toLowerCase();
            if (typeof bValue === 'string') bValue = bValue.toLowerCase();

            if (aValue < bValue) return sortDirection === 'asc' ? -1 : 1;
            if (aValue > bValue) return sortDirection === 'asc' ? 1 : -1;
            return 0;
        });
    }, [servings, sortField, sortDirection]);

    const handleSort = (field: SortField) => {
        if (sortField === field) {
            // Cycle through: asc -> desc -> null
            if (sortDirection === 'asc') {
                setSortDirection('desc');
            } else if (sortDirection === 'desc') {
                setSortDirection(null);
                setSortField(null);
            }
        } else {
            setSortField(field);
            setSortDirection('asc');
        }
    };

    const getSortIcon = (field: SortField) => {
        if (sortField !== field) {
            return <ArrowUpDown className="h-4 w-4 ml-1 text-gray-400" />;
        }
        if (sortDirection === 'asc') {
            return <ArrowUp className="h-4 w-4 ml-1 text-amber-600" />;
        }
        return <ArrowDown className="h-4 w-4 ml-1 text-amber-600" />;
    };

    if (isLoading) {
        return (
            <div className="flex items-center justify-center h-64">
                <div className="text-gray-500">Loading serving profiles...</div>
            </div>
        );
    }

    if (error) {
        return (
            <div className="flex items-center justify-center h-64">
                <div className="text-red-500">Error loading serving profiles. Make sure the backend is running.</div>
            </div>
        );
    }

    return (
        <div className="px-4 py-6 sm:px-0">
            <div className="flex items-center justify-between mb-6">
                <h1 className="text-3xl font-bold text-gray-900 flex items-center">
                    <Scale className="h-8 w-8 mr-3 text-amber-600" />
                    Serving Profiles
                </h1>
            </div>

            <div className="bg-white shadow overflow-hidden sm:rounded-lg">
                <table className="min-w-full divide-y divide-gray-200">
                    <thead className="bg-gray-50">
                    <tr>
                        <th
                            className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider cursor-pointer hover:bg-gray-100"
                            onClick={() => handleSort('name')}
                        >
                            <div className="flex items-center">
                                Name
                                {getSortIcon('name')}
                            </div>
                        </th>
                        <th
                            className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider cursor-pointer hover:bg-gray-100"
                            onClick={() => handleSort('cup')}
                        >
                            <div className="flex items-center">
                                Cup
                                {getSortIcon('cup')}
                            </div>
                        </th>
                        <th
                            className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider cursor-pointer hover:bg-gray-100"
                            onClick={() => handleSort('quarter')}
                        >
                            <div className="flex items-center">
                                Quarter
                                {getSortIcon('quarter')}
                            </div>
                        </th>
                        <th
                            className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider cursor-pointer hover:bg-gray-100"
                            onClick={() => handleSort('tablespoon')}
                        >
                            <div className="flex items-center">
                                Tablespoon
                                {getSortIcon('tablespoon')}
                            </div>
                        </th>
                        <th
                            className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider cursor-pointer hover:bg-gray-100"
                            onClick={() => handleSort('teaspoon')}
                        >
                            <div className="flex items-center">
                                Teaspoon
                                {getSortIcon('teaspoon')}
                            </div>
                        </th>
                        <th
                            className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider cursor-pointer hover:bg-gray-100"
                            onClick={() => handleSort('gram')}
                        >
                            <div className="flex items-center">
                                Gram
                                {getSortIcon('gram')}
                            </div>
                        </th>
                    </tr>
                    </thead>
                    <tbody className="bg-white divide-y divide-gray-200">
                    {sortedServings && sortedServings.length > 0 ? (
                        sortedServings.map((serving) => (
                            <tr key={serving.extid} className="hover:bg-gray-50">
                                <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                                    {serving.name}
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                                    {serving.cup}
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                                    {serving.quarter}
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                                    {serving.tablespoon}
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                                    {serving.teaspoon}
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                                    {serving.gram}
                                </td>
                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan={6} className="px-6 py-12 text-center text-gray-500">
                                No serving profiles found.
                            </td>
                        </tr>
                    )}
                    </tbody>
                </table>
            </div>
        </div>
    );
}