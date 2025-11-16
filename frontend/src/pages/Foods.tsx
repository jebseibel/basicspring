import { useQuery } from '@tanstack/react-query';
import { foodApi } from '../services/api';
import { Apple, ArrowUpDown, ArrowUp, ArrowDown } from 'lucide-react';
import { useState, useMemo } from 'react';

type SortField = 'name' | 'category' | 'subcategory' | 'description';
type SortDirection = 'asc' | 'desc' | null;

export default function Foods() {
  const [sortField, setSortField] = useState<SortField | null>(null);
  const [sortDirection, setSortDirection] = useState<SortDirection>(null);
  const [mixableFilter, setMixableFilter] = useState<'all' | 'mixable' | 'non-mixable'>('all');
  const [foundationFilter, setFoundationFilter] = useState<'all' | 'foundation'>('all');

  const { data: foods, isLoading, error } = useQuery({
    queryKey: ['foods'],
    queryFn: async () => {
      const response = await foodApi.getAll();
      return response.data;
    },
  });

  const filteredAndSortedFoods = useMemo(() => {
    if (!foods) return foods;

    // First apply filters
    let filtered = foods;

    // Apply mixable filter
    if (mixableFilter === 'mixable') {
      filtered = filtered.filter(food => food.mixable === true);
    } else if (mixableFilter === 'non-mixable') {
      filtered = filtered.filter(food => food.mixable !== true);
    }

    // Apply foundation filter
    if (foundationFilter === 'foundation') {
      filtered = filtered.filter(food => food.foundation === true);
    }

    // Then apply sorting
    if (!sortField || !sortDirection) return filtered;

    return [...filtered].sort((a, b) => {
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
  }, [foods, sortField, sortDirection, mixableFilter, foundationFilter]);

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
      return <ArrowUp className="h-4 w-4 ml-1 text-green-600" />;
    }
    return <ArrowDown className="h-4 w-4 ml-1 text-green-600" />;
  };

  if (isLoading) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="text-gray-500">Loading foods...</div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="text-red-500">Error loading foods. Make sure the backend is running.</div>
      </div>
    );
  }

  return (
    <div className="px-4 py-6 sm:px-0">
      <div className="flex items-center justify-between mb-6">
        <h1 className="text-3xl font-bold text-gray-900 flex items-center">
          <Apple className="h-8 w-8 mr-3 text-green-600" />
          Food Items
        </h1>
        <div className="flex items-center gap-4">
          <div className="flex items-center gap-2">
            <input
              type="checkbox"
              id="foundationFilter"
              checked={foundationFilter === 'foundation'}
              onChange={(e) => setFoundationFilter(e.target.checked ? 'foundation' : 'all')}
              className="w-4 h-4 text-green-600 bg-gray-100 border-gray-300 rounded focus:ring-green-500 focus:ring-2"
            />
            <label htmlFor="foundationFilter" className="text-sm font-medium text-gray-700 cursor-pointer">
              Foundation
            </label>
          </div>
          <div className="flex items-center gap-2">
            <input
              type="checkbox"
              id="mixableFilter"
              checked={mixableFilter === 'mixable'}
              onChange={(e) => setMixableFilter(e.target.checked ? 'mixable' : 'all')}
              className="w-4 h-4 text-green-600 bg-gray-100 border-gray-300 rounded focus:ring-green-500 focus:ring-2"
            />
            <label htmlFor="mixableFilter" className="text-sm font-medium text-gray-700 cursor-pointer">
              Mixable
            </label>
          </div>
        </div>
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
                onClick={() => handleSort('category')}
              >
                <div className="flex items-center">
                  Category
                  {getSortIcon('category')}
                </div>
              </th>
              <th
                className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider cursor-pointer hover:bg-gray-100"
                onClick={() => handleSort('subcategory')}
              >
                <div className="flex items-center">
                  Subcategory
                  {getSortIcon('subcategory')}
                </div>
              </th>
              <th
                className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider cursor-pointer hover:bg-gray-100"
                onClick={() => handleSort('description')}
              >
                <div className="flex items-center">
                  Description
                  {getSortIcon('description')}
                </div>
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Foundation
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Mixable
              </th>
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-200">
            {filteredAndSortedFoods && filteredAndSortedFoods.length > 0 ? (
              filteredAndSortedFoods.map((food) => (
                <tr key={food.extid} className="hover:bg-gray-50">
                  <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                    {food.name}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    {food.category || '-'}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    {food.subcategory || '-'}
                  </td>
                  <td className="px-6 py-4 text-sm text-gray-500">
                    {food.description || '-'}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    {food.foundation ? '✓' : '-'}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    {food.mixable ? '✓' : '-'}
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan={6} className="px-6 py-12 text-center text-gray-500">
                  No food items found.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}
