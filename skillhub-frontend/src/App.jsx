import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import Navbar         from './components/Navbar';
import ProtectedRoute from './components/ProtectedRoute';

import Home               from './pages/Home';
import Formations         from './pages/Formations';
import FormationDetail    from './pages/FormationDetail';
import DashboardApprenant from './pages/DashboardApprenant';
import DashboardFormateur from './pages/DashboardFormateur';
import SuiviFormation     from './pages/SuiviFormation';

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Navbar />
        <Routes>
          <Route path="/"              element={<Home />} />
          <Route path="/formations"    element={<Formations />} />
          <Route path="/formation/:id" element={<FormationDetail />} />

          <Route path="/dashboard/apprenant" element={
            <ProtectedRoute role="apprenant">
              <DashboardApprenant />
            </ProtectedRoute>
          } />
          <Route path="/apprendre/:id" element={
            <ProtectedRoute role="apprenant">
              <SuiviFormation />
            </ProtectedRoute>
          } />

          <Route path="/dashboard/formateur" element={
            <ProtectedRoute role="formateur">
              <DashboardFormateur />
            </ProtectedRoute>
          } />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}