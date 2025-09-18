# Dockerfile pour le frontend React R-DGTT Portail
FROM node:18-alpine as build

# Métadonnées
LABEL maintainer="R-DGTT Development Team"
LABEL description="Frontend React pour R-DGTT Portail"
LABEL version="1.0.0"

# Variables d'environnement
ENV NODE_ENV=production
ENV REACT_APP_API_URL=https://api.dgtt-portail.com
ENV REACT_APP_KIBANA_URL=https://kibana.dgtt-portail.com

# Créer l'utilisateur non-root
RUN addgroup -g 1001 -S nodejs && \
    adduser -S react -u 1001

# Définir le répertoire de travail
WORKDIR /app

# Copier les fichiers de dépendances
COPY package*.json ./

# Installer les dépendances
RUN npm ci --only=production && npm cache clean --force

# Copier le code source
COPY . .

# Changer les permissions
RUN chown -R react:nodejs /app
USER react

# Construire l'application
RUN npm run build

# Stage de production avec Nginx
FROM nginx:alpine

# Métadonnées
LABEL maintainer="R-DGTT Development Team"
LABEL description="Frontend React pour R-DGTT Portail - Production"
LABEL version="1.0.0"

# Installer les dépendances système
RUN apk add --no-cache curl

# Copier la configuration Nginx
COPY nginx.conf /etc/nginx/nginx.conf

# Copier les fichiers construits
COPY --from=build /app/build /usr/share/nginx/html

# Créer les répertoires nécessaires
RUN mkdir -p /var/log/nginx /var/cache/nginx /var/run/nginx

# Changer les permissions
RUN chown -R nginx:nginx /usr/share/nginx/html /var/log/nginx /var/cache/nginx /var/run/nginx

# Exposer le port
EXPOSE 3000

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=30s --retries=3 \
    CMD curl -f http://localhost:3000/ || exit 1

# Démarrer Nginx
CMD ["nginx", "-g", "daemon off;"]
