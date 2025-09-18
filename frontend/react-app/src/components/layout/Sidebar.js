import React from 'react';
import {
  Drawer,
  List,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Divider,
  Box,
  Typography,
} from '@mui/material';
import {
  Dashboard,
  School,
  People,
  Assignment,
  CarRental,
  Description,
  Settings,
  Assessment,
} from '@mui/icons-material';
import { useTranslation } from 'react-i18next';
import { useNavigate, useLocation } from 'react-router-dom';

const drawerWidth = 240;

const Sidebar = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const location = useLocation();

  const menuItems = [
    {
      text: t('dashboard'),
      icon: <Dashboard />,
      path: '/dashboard',
    },
    {
      text: t('autoEcoles'),
      icon: <School />,
      path: '/auto-ecoles',
    },
    {
      text: t('candidats'),
      icon: <People />,
      path: '/candidats',
    },
    {
      text: t('permis'),
      icon: <Assignment />,
      path: '/permis',
    },
    {
      text: t('cartesGrises'),
      icon: <CarRental />,
      path: '/cartes-grises',
    },
    {
      text: t('documents'),
      icon: <Description />,
      path: '/documents',
    },
    {
      text: t('reports'),
      icon: <Assessment />,
      path: '/reports',
    },
    {
      text: t('settings'),
      icon: <Settings />,
      path: '/settings',
    },
  ];

  const handleNavigation = (path) => {
    navigate(path);
  };

  return (
    <Drawer
      variant="permanent"
      sx={{
        width: drawerWidth,
        flexShrink: 0,
        '& .MuiDrawer-paper': {
          width: drawerWidth,
          boxSizing: 'border-box',
          mt: 8, // Account for AppBar height
        },
      }}
    >
      <Box sx={{ overflow: 'auto' }}>
        <List>
          {menuItems.map((item) => (
            <ListItem key={item.text} disablePadding>
              <ListItemButton
                selected={location.pathname === item.path}
                onClick={() => handleNavigation(item.path)}
                sx={{
                  '&.Mui-selected': {
                    backgroundColor: 'primary.main',
                    color: 'white',
                    '&:hover': {
                      backgroundColor: 'primary.dark',
                    },
                    '& .MuiListItemIcon-root': {
                      color: 'white',
                    },
                  },
                }}
              >
                <ListItemIcon>
                  {item.icon}
                </ListItemIcon>
                <ListItemText primary={item.text} />
              </ListItemButton>
            </ListItem>
          ))}
        </List>
        
        <Divider />
        
        <Box sx={{ p: 2 }}>
          <Typography variant="caption" color="text.secondary">
            R-DGTT Portail v1.0.0
          </Typography>
        </Box>
      </Box>
    </Drawer>
  );
};

export default Sidebar;
