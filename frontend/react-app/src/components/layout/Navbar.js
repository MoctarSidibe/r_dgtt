import React, { useState } from 'react';
import {
  AppBar,
  Toolbar,
  Typography,
  IconButton,
  Menu,
  MenuItem,
  Avatar,
  Box,
  Badge,
  Tooltip,
} from '@mui/material';
import {
  AccountCircle,
  Notifications,
  Settings,
  Logout,
  Language,
} from '@mui/icons-material';
import { useTranslation } from 'react-i18next';

const Navbar = () => {
  const { t, i18n } = useTranslation();
  const [anchorEl, setAnchorEl] = useState(null);
  const [languageAnchor, setLanguageAnchor] = useState(null);

  const handleProfileMenuOpen = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleLanguageMenuOpen = (event) => {
    setLanguageAnchor(event.currentTarget);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
    setLanguageAnchor(null);
  };

  const handleLanguageChange = (language) => {
    i18n.changeLanguage(language);
    handleMenuClose();
  };

  const handleLogout = () => {
    // TODO: Implémenter la déconnexion
    console.log('Déconnexion');
    handleMenuClose();
  };

  return (
    <AppBar position="fixed" sx={{ zIndex: (theme) => theme.zIndex.drawer + 1 }}>
      <Toolbar>
        <Typography
          variant="h6"
          component="div"
          sx={{ flexGrow: 1, fontWeight: 'bold' }}
        >
          R-DGTT Portail
        </Typography>

        <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
          {/* Notifications */}
          <Tooltip title={t('notifications')}>
            <IconButton color="inherit">
              <Badge badgeContent={3} color="error">
                <Notifications />
              </Badge>
            </IconButton>
          </Tooltip>

          {/* Language Selector */}
          <Tooltip title={t('language')}>
            <IconButton
              color="inherit"
              onClick={handleLanguageMenuOpen}
            >
              <Language />
            </IconButton>
          </Tooltip>

          <Menu
            anchorEl={languageAnchor}
            open={Boolean(languageAnchor)}
            onClose={handleMenuClose}
          >
            <MenuItem onClick={() => handleLanguageChange('fr')}>
              🇫🇷 Français
            </MenuItem>
            <MenuItem onClick={() => handleLanguageChange('en')}>
              🇬🇧 English
            </MenuItem>
          </Menu>

          {/* Profile Menu */}
          <Tooltip title={t('profile')}>
            <IconButton
              color="inherit"
              onClick={handleProfileMenuOpen}
            >
              <Avatar sx={{ width: 32, height: 32 }}>
                <AccountCircle />
              </Avatar>
            </IconButton>
          </Tooltip>

          <Menu
            anchorEl={anchorEl}
            open={Boolean(anchorEl)}
            onClose={handleMenuClose}
            anchorOrigin={{
              vertical: 'bottom',
              horizontal: 'right',
            }}
            transformOrigin={{
              vertical: 'top',
              horizontal: 'right',
            }}
          >
            <MenuItem onClick={handleMenuClose}>
              <Settings sx={{ mr: 1 }} />
              {t('settings')}
            </MenuItem>
            <MenuItem onClick={handleLogout}>
              <Logout sx={{ mr: 1 }} />
              {t('logout')}
            </MenuItem>
          </Menu>
        </Box>
      </Toolbar>
    </AppBar>
  );
};

export default Navbar;
