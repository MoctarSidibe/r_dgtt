import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import { CssBaseline, Box } from '@mui/material';
import { Provider } from 'react-redux';
import { store } from './store/store';
import { useTranslation } from 'react-i18next';

// Components
import Navbar from './components/layout/Navbar';
import Sidebar from './components/layout/Sidebar';
import Footer from './components/layout/Footer';

// Pages
import Dashboard from './pages/Dashboard';
import AutoEcoleList from './pages/auto-ecole/AutoEcoleList';
import AutoEcoleCreate from './pages/auto-ecole/AutoEcoleCreate';
import AutoEcoleDetails from './pages/auto-ecole/AutoEcoleDetails';
import CandidatList from './pages/candidat/CandidatList';
import CandidatCreate from './pages/candidat/CandidatCreate';
import CandidatDetails from './pages/candidat/CandidatDetails';
import Login from './pages/auth/Login';
import Profile from './pages/auth/Profile';

// Theme
const theme = createTheme({
  palette: {
    primary: {
      main: '#1976d2',
      light: '#42a5f5',
      dark: '#1565c0',
    },
    secondary: {
      main: '#dc004e',
    },
    background: {
      default: '#f5f5f5',
    },
  },
  typography: {
    fontFamily: '"Roboto", "Helvetica", "Arial", sans-serif',
    h1: {
      fontSize: '2.5rem',
      fontWeight: 600,
    },
    h2: {
      fontSize: '2rem',
      fontWeight: 500,
    },
    h3: {
      fontSize: '1.75rem',
      fontWeight: 500,
    },
  },
  components: {
    MuiButton: {
      styleOverrides: {
        root: {
          textTransform: 'none',
          borderRadius: 8,
        },
      },
    },
    MuiCard: {
      styleOverrides: {
        root: {
          borderRadius: 12,
          boxShadow: '0 2px 8px rgba(0,0,0,0.1)',
        },
      },
    },
  },
});

function AppContent() {
  const { i18n } = useTranslation();

  return (
    <Router>
      <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
        <Navbar />
        <Box sx={{ display: 'flex', flex: 1 }}>
          <Sidebar />
          <Box component="main" sx={{ flexGrow: 1, p: 3, mt: 8 }}>
            <Routes>
              {/* Dashboard */}
              <Route path="/" element={<Dashboard />} />
              <Route path="/dashboard" element={<Dashboard />} />
              
              {/* Auto-École Routes */}
              <Route path="/auto-ecoles" element={<AutoEcoleList />} />
              <Route path="/auto-ecoles/nouveau" element={<AutoEcoleCreate />} />
              <Route path="/auto-ecoles/:id" element={<AutoEcoleDetails />} />
              
              {/* Candidat Routes */}
              <Route path="/candidats" element={<CandidatList />} />
              <Route path="/candidats/nouveau" element={<CandidatCreate />} />
              <Route path="/candidats/:id" element={<CandidatDetails />} />
              
              {/* Auth Routes */}
              <Route path="/login" element={<Login />} />
              <Route path="/profile" element={<Profile />} />
            </Routes>
          </Box>
        </Box>
        <Footer />
      </Box>
    </Router>
  );
}

function App() {
  return (
    <Provider store={store}>
      <ThemeProvider theme={theme}>
        <CssBaseline />
        <AppContent />
      </ThemeProvider>
    </Provider>
  );
}

export default App;
