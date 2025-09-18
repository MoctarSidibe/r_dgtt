# R-DGTT Portail - Ministère des Transports du Gabon

## 📚 Complete Documentation

**👉 For detailed setup instructions, see [docs/COMPLETE-GUIDE.md](docs/COMPLETE-GUIDE.md)**

### 📁 Simplified Structure
- **docs/** - Only 1 file: COMPLETE-GUIDE.md
- **No scripts/** - Full manual setup as requested
- **No deployment/** - Manual deployment process
- **Clean & organized** - Easy to understand and follow

## 🚀 Quick Start (5 Minutes)

### Prerequisites
- Docker Desktop installed and running
- Git installed
- PostgreSQL (for manual database setup)

### Start Development
```bash
git clone https://github.com/votre-username/r-dgtt_v2.git
cd r-dgtt_v2

# 1. Setup databases manually (see complete guide)
# 2. Build and start everything
docker-compose -f docker-compose.complete.yml build
docker-compose -f docker-compose.complete.yml up -d
```

### Access
- **Frontend**: http://localhost:3000
- **API Documentation**: 
  - **Swagger UI**: http://localhost:8080/swagger
  - **ReDoc**: http://localhost:8080/redoc
- **Services**:
  - **Usager Service**: http://localhost:8081
  - **Auto-École Service**: http://localhost:8082
  - **Permis Service**: http://localhost:8083
  - **Autres Service**: http://localhost:8084
- **Infrastructure**:
  - **Traefik Dashboard**: http://localhost:8080
  - **Consul UI**: http://localhost:8500
  - **Kibana**: http://localhost:5601

## 🏗️ Architecture

### 4 Core Services
1. **Usager Service** - User management & authentication
2. **Auto-École Service** - Auto-école management & candidate enrollment
3. **Permis Service** - Driving license management & exams
4. **Autres Service** - External partners interoperability

### 4 Databases
- **usager_db** - User management database
- **auto_ecole_db** - Auto-école management database
- **permis_db** - Driving license database
- **autres_db** - External partners database

### Infrastructure
- **Traefik** - API Gateway and Load Balancer
- **Consul** - Service Discovery and Configuration
- **ELK Stack** - Elasticsearch, Logstash, Kibana
- **OWASP Security** - ModSecurity, Fail2Ban, Trivy
- **PostgreSQL** - 4 separate databases

## 📁 Project Structure

```
r-dgtt_v2/
├── config/                    # 🔧 All configuration files
│   ├── traefik/              # Traefik API Gateway
│   ├── consul/               # Consul Service Discovery
│   ├── logstash/             # Logstash configuration
│   ├── swagger/              # API documentation
│   ├── modsecurity/          # ModSecurity WAF
│   ├── fail2ban/             # Fail2Ban configuration
│   └── trivy/                # Trivy vulnerability scanner
├── databases/                 # 🗄️ Database initialization scripts
│   └── init/
│       ├── usager.sql
│       ├── auto-ecole.sql
│       ├── permis.sql
│       └── autres.sql
├── docs/                      # 📚 Documentation
│   ├── COMPLETE-GUIDE.md     # Complete guide
│   ├── DEVELOPMENT.md        # Development guide
│   └── deployment/           # Deployment guides
├── frontend/                  # ⚛️ React frontend
├── microservices/             # 🚀 4 Spring Boot services
│   ├── usager-service/
│   ├── auto-ecole-service/
│   ├── permis-service/
│   └── autres-service/
├── scripts/                   # 🔨 Deployment scripts
├── docker-compose.complete.yml # 🐳 Main Docker Compose file
└── README.md                  # 📖 This file
```

## 🔧 Common Commands

### Start Services
```bash
docker-compose -f docker-compose.complete.yml up -d
```

### Stop Services
```bash
docker-compose -f docker-compose.complete.yml down
```

### View Logs
```bash
docker-compose -f docker-compose.complete.yml logs -f
```

### Rebuild a Service
```bash
docker-compose -f docker-compose.complete.yml build usager-service
docker-compose -f docker-compose.complete.yml up -d usager-service
```

### Connect to Database
```bash
docker exec -it dgtt-postgres-usager psql -U dgtt_user -d usager_db
```

## 📖 API Documentation

- **Swagger UI**: http://localhost:8080/swagger - Interactive API documentation
- **ReDoc**: http://localhost:8080/redoc - Beautiful API documentation
- **OpenAPI Spec**: [config/swagger/swagger.json](config/swagger/swagger.json) - Complete API specification

## 🛡️ Security Features

- **JWT Authentication** - Secure token-based authentication
- **Role-based Access Control** - User permissions and roles
- **Audit Logging** - Complete audit trail for all actions
- **OWASP Security Stack** - ModSecurity, Fail2Ban, Trivy
- **Rate Limiting** - API protection against abuse
- **Security Headers** - HTTP security headers

## 🚀 Development

### Service Development
1. **Choose a service** - Start with one service
2. **Implement business logic** - Add features incrementally
3. **Test with Swagger** - Use Swagger UI for testing
4. **Add to other services** - Repeat for other services

### External Partners Integration
The **Autres Service** is ready for external partner integration:
- **DGDI** - Direction Générale de la Documentation et de l'Immigration
- **Police Nationale** - Sécurité Routière
- **Centre de Contrôle Technique**
- **Compagnie d'assurance**
- **Douanes**
- **Trésor Public**
- **Interpol**
- **Grand Public** - Portail Citoyen

## 📚 Documentation

- [Complete Guide](docs/COMPLETE-GUIDE.md) - Everything you need to know
- [Development Guide](docs/DEVELOPMENT.md) - Development workflow
- [Deployment Guide](docs/deployment/modern-deployment-guide.md) - Production deployment

## 🎯 Next Steps

1. **Test the setup** - Make sure everything works
2. **Start developing** - Focus on one service at a time
3. **Implement business logic** - Add features incrementally
4. **Add external partner APIs** - When you provide the requirements
5. **Scale when ready** - Add more services later

## 🆘 Troubleshooting

### Common Issues
1. **Port conflicts** - Make sure ports 80, 443, 8080, 8500, 5601, 9200 are free
2. **Memory issues** - Ensure you have at least 4GB RAM available
3. **Docker issues** - Make sure Docker Desktop is running
4. **Build errors** - Check Maven dependencies in pom.xml files

### Getting Help
- Check the logs: `docker-compose -f docker-compose.complete.yml logs -f`
- Check service status: `docker-compose -f docker-compose.complete.yml ps`
- Check individual service logs: `docker logs dgtt-usager-service`

---

**R-DGTT Portail - Clean, focused, and ready for development!** 🚀