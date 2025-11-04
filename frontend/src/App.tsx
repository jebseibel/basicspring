import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import Layout from './components/Layout';
import Dashboard from './pages/Dashboard';
import SaladBuilder from './pages/SaladBuilder';
import Foods from './pages/Foods';

// Create a client
const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      refetchOnWindowFocus: false,
      retry: 1,
    },
  },
});

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <Router>
        <Routes>
          <Route path="/" element={<Layout />}>
            <Route index element={<Dashboard />} />
            <Route path="salad-builder" element={<SaladBuilder />} />
            <Route path="foods" element={<Foods />} />
            <Route path="nutrition" element={<div className="p-6">Nutrition page coming soon...</div>} />
            <Route path="flavors" element={<div className="p-6">Flavors page coming soon...</div>} />
            <Route path="profiles" element={<div className="p-6">Profiles page coming soon...</div>} />
            <Route path="companies" element={<div className="p-6">Companies page coming soon...</div>} />
          </Route>
        </Routes>
      </Router>
    </QueryClientProvider>
  );
}

export default App;
