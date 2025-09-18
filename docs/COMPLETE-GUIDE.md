# 🚀 R-DGTT Portail - Complete Guide

## 📋 What is R-DGTT Portail?

R-DGTT Portail is the digital platform for the **Ministère des Transports, de la Marine Marchande et de la Logistique du Gabon**. It manages:

- **Auto-Écoles** (Driving Schools) - Registration, inspection, authorization
- **Permis de Conduire** (Driving Licenses) - Application, exams, issuance
- **Usagers** (Users) - Citizens, auto-école admins, government staff
- **Partenaires Externes** (External Partners) - DGDI, Police, Insurance, etc.

## 🏗️ System Architecture

### 4 Core Services
1. **Usager Service** - User management & authentication
2. **Auto-École Service** - Auto-école management & candidate enrollment  
3. **Permis Service** - Driving license management & exams
4. **Autres Service** - External partners interoperability

### 4 Databases (PostgreSQL)
- **usager_db** - User management database
- **auto_ecole_db** - Auto-école management database
- **permis_db** - Driving license database
- **autres_db** - External partners database

### Technology Stack
- **Backend**: Spring Boot 3.2 + Spring Data JPA (ORM)
- **Frontend**: React.js + Material-UI
- **Database**: PostgreSQL (4 separate databases)
- **API Gateway**: Traefik
- **Service Discovery**: Consul
- **Logging**: ELK Stack (Elasticsearch, Logstash, Kibana)
- **Security**: OWASP (ModSecurity, Fail2Ban, Trivy)

## 📁 Project Structure

```
r-dgtt_v2/
├── docs/
│   └── COMPLETE-GUIDE.md     # 📚 This guide (only one!)
├── config/                   # 🔧 All configuration files
│   ├── traefik/              # API Gateway config
│   ├── consul/               # Service Discovery config
│   ├── logstash/             # Logging config
│   └── swagger/              # API documentation
├── databases/                # 🗄️ Database scripts
│   └── init/
│       ├── usager.sql
│       ├── auto-ecole.sql
│       ├── permis.sql
│       └── autres.sql
├── microservices/            # 🔧 4 Spring Boot services
│   ├── usager-service/       # User management
│   ├── auto-ecole-service/   # Auto-école management
│   ├── permis-service/       # Driving license management
│   └── autres-service/       # External partners
├── frontend/                 # ⚛️ React frontend
└── docker-compose.complete.yml # 🐳 Complete setup
```

## 🔄 Metier Workflow

### Auto-École Registration Workflow (Service Auto-École)

#### 1. Application Submission
**Auto-École Admin** submits application with required documents (to be uploaded digitally):
- **CAP** délivré par le ministère des transports
- **Correspondance au DC** (Manuscrit)
- **Permis Catégorie B** de plus de 5 ans
- **Pièces d'identité**
- **Copie Acte de naissance**
- **Demi carte photo**
- **Extrait casier judiciaire**

#### 2. Payment Processing
**Payment** of 100,000 FCFA via Airtel Money (simulated) to Treasury account:
- **Compte N° 4571-261 Code 78**
- **Quittance Trésor** generated (Template A to be created)
- **Official receipt model** (HTML format for real official documents)

#### 3. SEV Review
**Agent SEV (Service Examen et Validation - Bureau Général)**:
- Receives dossier on Dashboard
- Verifies and approves
- Schedules inspection on-site

#### 4. On-Site Inspection Checklist
**Inspection** with comprehensive checklist:

**Salle de cours:**
- Panneaux muraux
- Code Rousseau
- Moniteur certifié
- Matériel audio-visuel
- Projecteur
- Plaquette des panneaux
- Magnétique de panneaux
- Instructeur salle de cours
- Boîte à pharmacie

**Infrastructure:**
- Bureau du DG
- Bureau Secrétariat
- Salle d'attente
- Notation Hygiène
- 2 véhicules respectant la norme (3 pédales des 2 côtés, carte grise auto-école)
- Agrément
- Patente

**Rapport d'inspection** generated with QR Code, approved by SEV and sent to DC.

#### 5. DC Approval
**DC (Directeur du Centre)**:
- Reviews and approves
- Digital signature
- Generates **Permis d'exploitation** (6 months renewable)
- Full report approval (Conformément à l'arrêté 590/MTAC/MM/OC/DT et l'ordonnance 27/79)
- Sends to SAF department

#### 6. SAF Final Approval
**SAF (Service Administratif et Financier - Bureau Général)**:
- Receives dossier
- Reviews everything and payments (Quittance Trésor)
- Official receipt model (HTML format for real official documents)
- **Status Approved** (Conformément à l'arrêté 0005/MTMM/SG/DGTT/DCRCT)
- Status approved to SEV department
- Dossier transmitted
- **Status Approved** on Auto-École Admin interface
- **Auto-École can now work**

#### 7. Candidate Enrollment
**Auto-École** can now enroll candidates for formation process.

**Formulaire d'enrôlement:**
- Date, Heure: Auto
- N° Dossier: Generated automatically for full tracking
- Numéro de License: Generated automatically for full tracking

**Candidat Information:**
- nomFamille, nomJeuneFille, prenom
- dateNaissance, lieuNaissance, Nationalité
- Catégories Permis: A1, A, B, C, D, E, F
- Numéro de téléphone validé
- Photo
- Commentaire

**Documents numériques à téléverser:**

**Pour les Gabonais:**
- Copie légalisée de l'acte de naissance
- Copie de carte d'identité nationale ou passeport ou récépissé
- Demi carte photo (4 demi carte photos au dossier physique)
- 1 timbre fiscal
- Certificat médical
- Extrait casier judiciaire datant de moins de 3 mois

**Pour les Étrangers:**
- Copie de la carte de séjour en cours de validité
- Copie de la carte consulaire
- Copie acte de naissance
- Certificat de résidence
- Demi carte photo (4 demi carte photos au dossier physique)
- 1 timbre fiscal
- Certificat médical

**SMS Notification:** Candidate receives SMS with dossier details and Airtel Money payment link. When paid, candidate status changes, Quittance Trésor generated and assigned to dossier.

#### 8. Formation Process
**Auto-École Admin** can create Examinator role and assign candidates.

**Each Examinator** (with interface) processes candidate formation:

**3 Passages au Code:**
- Scheduled by Examinator
- Candidate receives SMS with passage details and day
- Notation: Oui/Non
- 3 Non = Suspended, 3 Oui = Pass to Créneau

**3 Passages au Créneau:**
- Scheduled by Examinator
- Candidate receives SMS with passage details and day
- Notation: Oui/Non
- 3 Non = Suspended, 3 Oui = Pass to Pratique

**3 Passages à la Pratique en Ville:**
- Scheduled by Examinator
- Candidate receives SMS with passage details and day
- Notation: Oui/Non
- 3 Non = Suspended, 3 Oui = **Admis** (Status Admis, pré-enrôlé pour l'examen)

**SMS Notification:** Candidate receives SMS with admission details and is sent to SEV department (Bureau Général et Bureau 102).

### Service Permis Workflow

#### 1. SEV Bureau 102 Review
**SEV (Bureau 102)**:
- Receives documents on dashboard
- Reviews everything
- If OK, dossier approved and sent to SEV (Bureau 108) and SAF (Bureau Général for final approval)

#### 2. SEV Bureau Général Scheduling
**SEV (Bureau Général)**:
- Reviews dossier
- Schedules exam date (Only Mercredi et Samedi)
- Candidate receives SMS with exam date and details

#### 3. Exam Management
**Exam Management Metier** - To be implemented later with more data on Exam Metier

#### 4. Attestation d'Admission
**Attestation d'admission** generated and sent to DC for digital signature and approval

#### 5. SEV Bureau Général Final Review
**SEV (Bureau Général)**:
- Receives full dossier and attestation d'admission
- Full review and approval
- **Procès-verbaux** generated
- Sent to STIAS department

#### 6. STIAS Review
**STIAS**:
- Reviews everything with all data
- Card production with Cardpressor

#### 7. DGTT Final Approval
**DGTT (Directeur Général des Transports Terrestres)**:
- Reviews everything, all details, parcours, documents, etc.
- Digital signature
- Full dossier approved
- Sent to Production department

#### 8. Production Department
**Production Department**:
- Card printing through Cardpressor
- Production checklist to ensure everything is complete

### Important Notes

#### Permis System
- **When candidate passes exam**: Receives **permis provisoire**
- **Six months later**: **Permis rose** printed with Cardpressor

#### User Management
- **Each Department Admin** can create users for their Bureau
- **Each role** has its own interface with relevant data
- **Notifications** for beautiful, smooth process
- **Strong full Audit log** for all actions

#### QR Code System
- **Online deployment**: QR codes will display document links
- **HTML format documents**: Conform to law for real official digital documents
- **Document access**: Secure access to official documents

#### Archive System
- **Physical documents reception**: System for managing physical document archives
- **Digital backup**: All documents stored digitally with QR code access

### External Partners Integration

- **DGDI** - Identity verification
- **Police Nationale** - Traffic violations
- **Centre de Contrôle Technique** - Vehicle inspection
- **Compagnie d'assurance** - Insurance verification
- **Trésor Public** - Payment processing
- **Interpol** - International verification

## 🗄️ Database Setup (Manual)

### Step 1: Install PostgreSQL on Windows

1. **Download PostgreSQL** from https://www.postgresql.org/download/windows/
2. **Run installer** and follow setup wizard
3. **Set password** for postgres user (remember this!)
4. **Note the port** (default: 5432)
5. **Add PostgreSQL to PATH** (important for psql command)

### Step 2: Add PostgreSQL to Windows PATH

1. **Open System Properties** → Advanced → Environment Variables
2. **Edit PATH** variable
3. **Add PostgreSQL bin folder**: `C:\Program Files\PostgreSQL\17\bin`
4. **Restart Command Prompt** or PowerShell

### Step 3: Test psql Command

```cmd
# Open Command Prompt and test
psql --version
```

If still not working, use full path:
```cmd
"C:\Program Files\PostgreSQL\17\bin\psql.exe" --version
```

### Step 4: Create Databases

```cmd
# Connect to PostgreSQL
psql -U postgres

# Or use full path if needed
"C:\Program Files\PostgreSQL\17\bin\psql.exe" -U postgres
```

In PostgreSQL prompt:
```sql
-- Create 4 databases
CREATE DATABASE usager_db;
CREATE DATABASE auto_ecole_db;
CREATE DATABASE permis_db;
CREATE DATABASE autres_db;

-- Exit
\q
```

### Step 5: Run Database Scripts

```cmd
# Run each script for its database
psql -U postgres -d usager_db -f databases\init\usager.sql
psql -U postgres -d auto_ecole_db -f databases\init\auto-ecole.sql
psql -U postgres -d permis_db -f databases\init\permis.sql
psql -U postgres -d autres_db -f databases\init\autres.sql
```

### Step 6: Verify Setup

```cmd
# Connect to each database and check tables
psql -U postgres -d usager_db
\dt
\q

psql -U postgres -d auto_ecole_db
\dt
\q

psql -U postgres -d permis_db
\dt
\q

psql -U postgres -d autres_db
\dt
\q
```

## 🔧 ORM Configuration

### Spring Data JPA (ORM)

Each microservice uses **Spring Data JPA** as the ORM:

- **Entity Classes** - Map to database tables
- **Repository Interfaces** - Data access layer
- **JPA Annotations** - @Entity, @Table, @Column, etc.
- **Automatic Queries** - findBy methods
- **Custom Queries** - @Query annotations

### Database Connections

Each service connects to its own database:

```yaml
# application.yml example
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/usager_db
    username: postgres
    password: your_password
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: true
```

## 🐳 Docker Setup

### Step 1: Environment Setup

```bash
# Copy environment template
copy env.example .env

# Edit .env file with your database passwords
notepad .env
```

### Step 2: Build and Start

```bash
# Build all services
docker-compose -f docker-compose.complete.yml build

# Start everything
docker-compose -f docker-compose.complete.yml up -d

# Check status
docker-compose -f docker-compose.complete.yml ps
```

### Step 3: Verify Services

```bash
# Check logs
docker-compose -f docker-compose.complete.yml logs -f

# Check specific service
docker-compose -f docker-compose.complete.yml logs -f usager-service
```

## 🌐 Access URLs

- **Frontend**: http://localhost:3000
- **API Gateway**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger
- **Consul UI**: http://localhost:8500
- **Kibana**: http://localhost:5601

### Service Endpoints
- **Usager Service**: http://localhost:8080/api/usager
- **Auto-École Service**: http://localhost:8080/api/auto-ecole
- **Permis Service**: http://localhost:8080/api/permis
- **Autres Service**: http://localhost:8080/api/autres

## 🚀 GitHub Setup

### Step 1: Create GitHub Repository

1. Go to https://github.com
2. Click "New repository"
3. Name: `r-dgtt-portail`
4. Description: `R-DGTT Portail - Ministère des Transports Gabon`
5. Set to Private
6. Don't initialize with README
7. Click "Create repository"

### Step 2: Connect and Push

```bash
# Initialize git (if not done)
git init

# Add all files
git add .

# First commit
git commit -m "Initial commit: R-DGTT Portail"

# Add remote
git remote add origin https://github.com/YOUR_USERNAME/r-dgtt-portail.git

# Push to GitHub
git push -u origin main
```

### Step 3: Daily Workflow

```bash
# Pull latest changes
git pull origin main

# Make your changes
# Add changes
git add .

# Commit changes
git commit -m "Your commit message"

# Push changes
git push origin main
```

## 🔒 Security Features

### OWASP Security Stack
- **ModSecurity WAF** - Web Application Firewall
- **Fail2Ban** - Intrusion prevention
- **Trivy** - Vulnerability scanning
- **JWT Authentication** - Secure token-based auth
- **RBAC** - Role-based access control
- **Audit Logging** - Complete audit trail

### User Roles
- **ADMIN** - System administrator
- **DC** - Directeur du Centre
- **SEV** - Service Examen et Validation
- **SAF** - Service Administratif et Financier
- **STIAS** - Service Technique, Informatique, des Archives et de la Statistiques
- **DGTT** - Directeur Général des Transports Terrestres
- **AUTO_ECOLE_ADMIN** - Auto-école administrator

## 🛠️ Development

### Backend Development

```bash
# Navigate to service
cd microservices/usager-service

# Build with Maven
mvn clean compile

# Run tests
mvn test

# Run application
mvn spring-boot:run
```

### Frontend Development

```bash
# Navigate to frontend
cd frontend/react-app

# Install dependencies
npm install

# Start development server
npm start
```

## 🚨 Troubleshooting

### Common Issues

#### psql Command Not Found
```cmd
# Use full path
"C:\Program Files\PostgreSQL\17\bin\psql.exe" -U postgres

# Or add to PATH (see Step 2 above)
```

#### Database Connection Issues
```bash
# Check if PostgreSQL is running
net start postgresql-x64-17

# Check logs
docker-compose -f docker-compose.complete.yml logs postgres-usager
```

#### Service Not Starting
```bash
# Check service logs
docker-compose -f docker-compose.complete.yml logs usager-service

# Check if port is available
netstat -an | findstr :8080
```

#### Memory Issues
```bash
# Check Docker memory usage
docker stats

# Clean up
docker system prune -a
```

### Reset Everything
```bash
# Stop all services
docker-compose -f docker-compose.complete.yml down

# Remove everything
docker-compose -f docker-compose.complete.yml down -v
docker system prune -a

# Start fresh
docker-compose -f docker-compose.complete.yml up -d
```

## 📊 Monitoring

### Check Service Health
```bash
# All services
docker-compose -f docker-compose.complete.yml ps

# Service logs
docker-compose -f docker-compose.complete.yml logs -f

# Resource usage
docker stats
```

### Database Monitoring
```sql
-- Connect to database
psql -U postgres -d usager_db

-- Check tables
\dt

-- Check data
SELECT COUNT(*) FROM usagers;

-- Exit
\q
```

## 🎯 Next Steps

1. **Setup databases** - Follow database setup section
2. **Start services** - Use Docker setup
3. **Test APIs** - Use Swagger UI
4. **Develop features** - Add business logic
5. **Deploy to production** - When ready

## 📞 Support

If you have issues:
1. Check this guide first
2. Check the logs: `docker-compose logs -f`
3. Check troubleshooting section
4. Contact the development team

---

**Ready to build the R-DGTT Portail! 🚀**
