-- Database: usager
-- Description: Base de données pour la gestion des usagers du système R-DGTT

-- Création de la base de données
CREATE DATABASE usager;

-- Connexion à la base de données
\c usager;

-- Extension pour UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Table des usagers
CREATE TABLE usagers (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    telephone VARCHAR(20),
    role VARCHAR(50) NOT NULL,
    statut VARCHAR(50) NOT NULL DEFAULT 'ACTIF',
    derniere_connexion TIMESTAMP,
    nombre_tentatives_connexion INTEGER DEFAULT 0,
    date_verrouillage TIMESTAMP,
    mot_de_passe_expire BOOLEAN DEFAULT FALSE,
    date_expiration_mot_de_passe TIMESTAMP,
    deuxieme_facteur_active BOOLEAN DEFAULT FALSE,
    cle_2fa VARCHAR(255),
    avatar_url VARCHAR(500),
    langue_preferee VARCHAR(10) DEFAULT 'fr',
    timezone VARCHAR(50) DEFAULT 'Africa/Libreville',
    notes TEXT,
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table des permissions
CREATE TABLE permissions (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(100) UNIQUE NOT NULL,
    description VARCHAR(255) NOT NULL,
    categorie VARCHAR(50) NOT NULL,
    est_active BOOLEAN DEFAULT TRUE,
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table de liaison usager-permissions
CREATE TABLE usager_permissions (
    usager_id BIGINT REFERENCES usagers(id) ON DELETE CASCADE,
    permission_id BIGINT REFERENCES permissions(id) ON DELETE CASCADE,
    PRIMARY KEY (usager_id, permission_id)
);

-- Table des sessions de connexion
CREATE TABLE sessions_connexion (
    id BIGSERIAL PRIMARY KEY,
    usager_id BIGINT REFERENCES usagers(id) ON DELETE CASCADE,
    token VARCHAR(500) NOT NULL,
    adresse_ip VARCHAR(45),
    user_agent TEXT,
    date_connexion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_deconnexion TIMESTAMP,
    est_active BOOLEAN DEFAULT TRUE
);

-- Table des logs d'audit
CREATE TABLE audit_logs (
    id BIGSERIAL PRIMARY KEY,
    entite VARCHAR(100) NOT NULL,
    entite_id BIGINT,
    action VARCHAR(50) NOT NULL,
    utilisateur VARCHAR(255) NOT NULL,
    role_utilisateur VARCHAR(100),
    adresse_ip VARCHAR(45),
    user_agent TEXT,
    donnees_avant TEXT,
    donnees_apres TEXT,
    message TEXT,
    niveau_securite VARCHAR(50) DEFAULT 'INFO',
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Index pour les performances
CREATE INDEX idx_usagers_username ON usagers(username);
CREATE INDEX idx_usagers_email ON usagers(email);
CREATE INDEX idx_usagers_role ON usagers(role);
CREATE INDEX idx_usagers_statut ON usagers(statut);
CREATE INDEX idx_sessions_usager_id ON sessions_connexion(usager_id);
CREATE INDEX idx_sessions_token ON sessions_connexion(token);
CREATE INDEX idx_audit_logs_entite ON audit_logs(entite);
CREATE INDEX idx_audit_logs_utilisateur ON audit_logs(utilisateur);
CREATE INDEX idx_audit_logs_date ON audit_logs(date_creation);

-- Insertion des permissions de base
INSERT INTO permissions (nom, description, categorie) VALUES
('READ', 'Permission de lecture', 'LECTURE'),
('WRITE', 'Permission d''écriture', 'ECRITURE'),
('DELETE', 'Permission de suppression', 'SUPPRESSION'),
('ADMIN', 'Permission d''administration', 'ADMINISTRATION'),
('AUDIT', 'Permission d''audit', 'AUDIT'),
('REPORTS', 'Permission de rapports', 'RAPPORTS'),
('NOTIFICATIONS', 'Permission de notifications', 'NOTIFICATIONS');

-- Insertion d'un utilisateur admin par défaut
INSERT INTO usagers (username, email, password, nom, prenom, role, statut) VALUES
('admin', 'admin@dgtt-portail.com', '$2a$10$8K1p/a0dL3x4H8K1p/a0dL3x4H8K1p/a0dL3x4H8K1p/a0dL3x4H8K1p/a0dL3x4H', 'Admin', 'Système', 'ADMIN', 'ACTIF');

-- Attribution des permissions à l'admin
INSERT INTO usager_permissions (usager_id, permission_id) 
SELECT u.id, p.id 
FROM usagers u, permissions p 
WHERE u.username = 'admin';

-- Fonction pour mettre à jour la date de modification
CREATE OR REPLACE FUNCTION update_modified_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.date_modification = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Triggers pour mettre à jour automatiquement la date de modification
CREATE TRIGGER update_usagers_modification_time BEFORE UPDATE ON usagers FOR EACH ROW EXECUTE FUNCTION update_modified_column();
CREATE TRIGGER update_permissions_modification_time BEFORE UPDATE ON permissions FOR EACH ROW EXECUTE FUNCTION update_modified_column();

-- Commentaires sur les tables
COMMENT ON TABLE usagers IS 'Table des usagers du système R-DGTT';
COMMENT ON TABLE permissions IS 'Table des permissions du système';
COMMENT ON TABLE usager_permissions IS 'Table de liaison usager-permissions';
COMMENT ON TABLE sessions_connexion IS 'Table des sessions de connexion';
COMMENT ON TABLE audit_logs IS 'Table des logs d''audit';

-- Commentaires sur les colonnes importantes
COMMENT ON COLUMN usagers.role IS 'Rôle de l''usager (ADMIN, DGTT, DC, SEV, SAF, STIAS, etc.)';
COMMENT ON COLUMN usagers.statut IS 'Statut de l''usager (ACTIF, INACTIF, VERROUILLE, etc.)';
COMMENT ON COLUMN usagers.password IS 'Mot de passe hashé avec BCrypt';
COMMENT ON COLUMN audit_logs.niveau_securite IS 'Niveau de sécurité du log (INFO, WARNING, ALERTE, CRITIQUE, etc.)';

