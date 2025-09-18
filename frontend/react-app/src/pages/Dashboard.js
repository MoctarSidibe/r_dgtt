import React from 'react';
import {
  Grid,
  Paper,
  Typography,
  Box,
  Card,
  CardContent,
  CardActions,
  Button,
  Chip,
  LinearProgress,
} from '@mui/material';
import {
  School,
  People,
  Assignment,
  TrendingUp,
  Warning,
  CheckCircle,
} from '@mui/icons-material';
import { useTranslation } from 'react-i18next';

const Dashboard = () => {
  const { t } = useTranslation();

  const stats = [
    {
      title: t('totalAutoEcoles'),
      value: '156',
      change: '+12%',
      icon: <School />,
      color: 'primary',
    },
    {
      title: t('totalCandidats'),
      value: '2,847',
      change: '+8%',
      icon: <People />,
      color: 'secondary',
    },
    {
      title: t('permisDelivres'),
      value: '1,234',
      change: '+15%',
      icon: <Assignment />,
      color: 'success',
    },
    {
      title: t('tauxReussite'),
      value: '87%',
      change: '+3%',
      icon: <TrendingUp />,
      color: 'info',
    },
  ];

  const recentActivities = [
    {
      id: 1,
      type: 'auto-ecole',
      message: 'Nouvelle auto-école "Excellence" enregistrée',
      time: 'Il y a 2 heures',
      status: 'success',
    },
    {
      id: 2,
      type: 'candidat',
      message: 'Candidat Amadou DIOUF a réussi son examen',
      time: 'Il y a 4 heures',
      status: 'success',
    },
    {
      id: 3,
      type: 'paiement',
      message: 'Paiement validé pour Auto-École Moderne',
      time: 'Il y a 6 heures',
      status: 'info',
    },
    {
      id: 4,
      type: 'inspection',
      message: 'Inspection programmée pour Centre Formation',
      time: 'Il y a 8 heures',
      status: 'warning',
    },
  ];

  const getStatusIcon = (status) => {
    switch (status) {
      case 'success':
        return <CheckCircle color="success" />;
      case 'warning':
        return <Warning color="warning" />;
      case 'info':
        return <Assignment color="info" />;
      default:
        return <Assignment />;
    }
  };

  return (
    <Box>
      <Typography variant="h4" gutterBottom>
        {t('dashboard')}
      </Typography>
      
      <Typography variant="body1" color="text.secondary" paragraph>
        {t('dashboardWelcome')}
      </Typography>

      {/* Statistics Cards */}
      <Grid container spacing={3} sx={{ mb: 3 }}>
        {stats.map((stat, index) => (
          <Grid item xs={12} sm={6} md={3} key={index}>
            <Card>
              <CardContent>
                <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                  <Box
                    sx={{
                      p: 1,
                      borderRadius: 1,
                      backgroundColor: `${stat.color}.light`,
                      color: `${stat.color}.contrastText`,
                      mr: 2,
                    }}
                  >
                    {stat.icon}
                  </Box>
                  <Box>
                    <Typography variant="h4" component="div">
                      {stat.value}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      {stat.title}
                    </Typography>
                  </Box>
                </Box>
                <Box sx={{ display: 'flex', alignItems: 'center' }}>
                  <Chip
                    label={stat.change}
                    size="small"
                    color="success"
                    sx={{ mr: 1 }}
                  />
                  <Typography variant="caption" color="text.secondary">
                    {t('vsLastMonth')}
                  </Typography>
                </Box>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>

      <Grid container spacing={3}>
        {/* Recent Activities */}
        <Grid item xs={12} md={8}>
          <Paper sx={{ p: 3 }}>
            <Typography variant="h6" gutterBottom>
              {t('recentActivities')}
            </Typography>
            <Box sx={{ mt: 2 }}>
              {recentActivities.map((activity) => (
                <Box
                  key={activity.id}
                  sx={{
                    display: 'flex',
                    alignItems: 'center',
                    py: 2,
                    borderBottom: '1px solid',
                    borderColor: 'divider',
                    '&:last-child': {
                      borderBottom: 'none',
                    },
                  }}
                >
                  <Box sx={{ mr: 2 }}>
                    {getStatusIcon(activity.status)}
                  </Box>
                  <Box sx={{ flexGrow: 1 }}>
                    <Typography variant="body1">
                      {activity.message}
                    </Typography>
                    <Typography variant="caption" color="text.secondary">
                      {activity.time}
                    </Typography>
                  </Box>
                </Box>
              ))}
            </Box>
          </Paper>
        </Grid>

        {/* Quick Actions */}
        <Grid item xs={12} md={4}>
          <Paper sx={{ p: 3 }}>
            <Typography variant="h6" gutterBottom>
              {t('quickActions')}
            </Typography>
            <Box sx={{ mt: 2 }}>
              <Button
                variant="contained"
                fullWidth
                sx={{ mb: 2 }}
                startIcon={<School />}
              >
                {t('newAutoEcole')}
              </Button>
              <Button
                variant="outlined"
                fullWidth
                sx={{ mb: 2 }}
                startIcon={<People />}
              >
                {t('newCandidat')}
              </Button>
              <Button
                variant="outlined"
                fullWidth
                sx={{ mb: 2 }}
                startIcon={<Assignment />}
              >
                {t('scheduleInspection')}
              </Button>
            </Box>
          </Paper>

          {/* System Status */}
          <Paper sx={{ p: 3, mt: 2 }}>
            <Typography variant="h6" gutterBottom>
              {t('systemStatus')}
            </Typography>
            <Box sx={{ mt: 2 }}>
              <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 1 }}>
                <Typography variant="body2">API Gateway</Typography>
                <Typography variant="body2" color="success.main">
                  {t('online')}
                </Typography>
              </Box>
              <LinearProgress variant="determinate" value={100} sx={{ mb: 2 }} />
              
              <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 1 }}>
                <Typography variant="body2">Base de données</Typography>
                <Typography variant="body2" color="success.main">
                  {t('online')}
                </Typography>
              </Box>
              <LinearProgress variant="determinate" value={100} sx={{ mb: 2 }} />
              
              <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 1 }}>
                <Typography variant="body2">Services externes</Typography>
                <Typography variant="body2" color="warning.main">
                  {t('partial')}
                </Typography>
              </Box>
              <LinearProgress variant="determinate" value={75} />
            </Box>
          </Paper>
        </Grid>
      </Grid>
    </Box>
  );
};

export default Dashboard;
