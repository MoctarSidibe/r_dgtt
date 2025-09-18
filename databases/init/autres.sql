-- Database: autres
-- Description: Base de données pour la gestion des partenaires externes

-- Création de la base de données
CREATE DATABASE autres;

-- Connexion à la base de données
\c autres;

-- Extension pour UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Table des partenaires externes
CREATE TABLE partenaires_externes (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    type_partenaire VARCHAR(50) NOT NULL,
    api_url VARCHAR(500) NOT NULL,
    api_key VARCHAR(255) NOT NULL,
    api_secret VARCHAR(255),
    email_contact VARCHAR(255) NOT NULL,
    telephone_contact VARCHAR(20) NOT NULL,
    statut VARCHAR(50) NOT NULL DEFAULT 'ACTIF',
    derniere_synchronisation TIMESTAMP,
    nombre_requetes BIGINT DEFAULT 0,
    nombre_erreurs BIGINT DEFAULT 0,
    timeout_ms INTEGER DEFAULT 30000,
    retry_attempts INTEGER DEFAULT 3,
    notes TEXT,
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table des intégrations
CREATE TABLE integrations (
    id BIGSERIAL PRIMARY KEY,
    partenaire_id BIGINT REFERENCES partenaires_externes(id) ON DELETE CASCADE,
    service_source VARCHAR(100) NOT NULL,
    service_destination VARCHAR(100) NOT NULL,
    type_integration VARCHAR(50) NOT NULL,
    endpoint VARCHAR(500) NOT NULL,
    methode_http VARCHAR(10) NOT NULL,
    format_donnees VARCHAR(50) NOT NULL,
    statut VARCHAR(50) NOT NULL DEFAULT 'ACTIF',
    derniere_execution TIMESTAMP,
    prochaine_execution TIMESTAMP,
    intervalle_minutes INTEGER DEFAULT 60,
    nombre_executions BIGINT DEFAULT 0,
    nombre_succes BIGINT DEFAULT 0,
    nombre_echecs BIGINT DEFAULT 0,
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table des échanges de données
CREATE TABLE echanges_donnees (
    id BIGSERIAL PRIMARY KEY,
    integration_id BIGINT REFERENCES integrations(id) ON DELETE CASCADE,
    direction VARCHAR(20) NOT NULL, -- INBOUND ou OUTBOUND
    type_donnee VARCHAR(100) NOT NULL,
    donnees TEXT NOT NULL,
    statut VARCHAR(50) NOT NULL DEFAULT 'EN_ATTENTE',
    date_envoi TIMESTAMP,
    date_reception TIMESTAMP,
    date_traitement TIMESTAMP,
    erreur_message TEXT,
    qr_code TEXT,
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Table des webhooks
CREATE TABLE webhooks (
    id BIGSERIAL PRIMARY KEY,
    partenaire_id BIGINT REFERENCES partenaires_externes(id) ON DELETE CASCADE,
    nom VARCHAR(255) NOT NULL,
    url VARCHAR(500) NOT NULL,
    secret VARCHAR(255),
    evenements TEXT NOT NULL, -- JSON array des événements
    statut VARCHAR(50) NOT NULL DEFAULT 'ACTIF',
    derniere_execution TIMESTAMP,
    nombre_executions BIGINT DEFAULT 0,
    nombre_succes BIGINT DEFAULT 0,
    nombre_echecs BIGINT DEFAULT 0,
    timeout_ms INTEGER DEFAULT 30000,
    retry_attempts INTEGER DEFAULT 3,
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP
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
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    partenaire_id BIGINT REFERENCES partenaires_externes(id) ON DELETE SET NULL
);

-- Index pour les performances
CREATE INDEX idx_partenaires_nom ON partenaires_externes(nom);
CREATE INDEX idx_partenaires_type ON partenaires_externes(type_partenaire);
CREATE INDEX idx_partenaires_statut ON partenaires_externes(statut);
CREATE INDEX idx_integrations_partenaire_id ON integrations(partenaire_id);
CREATE INDEX idx_integrations_service_source ON integrations(service_source);
CREATE INDEX idx_integrations_service_destination ON integrations(service_destination);
CREATE INDEX idx_integrations_statut ON integrations(statut);
CREATE INDEX idx_echanges_integration_id ON echanges_donnees(integration_id);
CREATE INDEX idx_echanges_direction ON echanges_donnees(direction);
CREATE INDEX idx_echanges_statut ON echanges_donnees(statut);
CREATE INDEX idx_echanges_date_creation ON echanges_donnees(date_creation);
CREATE INDEX idx_webhooks_partenaire_id ON webhooks(partenaire_id);
CREATE INDEX idx_webhooks_statut ON webhooks(statut);
CREATE INDEX idx_audit_logs_entite ON audit_logs(entite);
CREATE INDEX idx_audit_logs_utilisateur ON audit_logs(utilisateur);
CREATE INDEX idx_audit_logs_date ON audit_logs(date_creation);

-- Fonction pour mettre à jour la date de modification
CREATE OR REPLACE FUNCTION update_modified_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.date_modification = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Triggers pour mettre à jour automatiquement la date de modification
CREATE TRIGGER update_partenaires_modification_time BEFORE UPDATE ON partenaires_externes FOR EACH ROW EXECUTE FUNCTION update_modified_column();
CREATE TRIGGER update_integrations_modification_time BEFORE UPDATE ON integrations FOR EACH ROW EXECUTE FUNCTION update_modified_column();
CREATE TRIGGER update_webhooks_modification_time BEFORE UPDATE ON webhooks FOR EACH ROW EXECUTE FUNCTION update_modified_column();

-- Insertion des partenaires externes par défaut
INSERT INTO partenaires_externes (nom, type_partenaire, api_url, api_key, email_contact, telephone_contact) VALUES
('DGDI', 'DGDI', 'https://api.dgdi.ga/v1', 'dgdi_api_key_2024', 'contact@dgdi.ga', '+24112345678'),
('Police Nationale', 'POLICE', 'https://api.police.ga/v1', 'police_api_key_2024', 'contact@police.ga', '+24112345679'),
('Contrôle Technique', 'CONTROLE_TECHNIQUE', 'https://api.controle-technique.ga/v1', 'ct_api_key_2024', 'contact@ct.ga', '+24112345680'),
('Assurance', 'ASSURANCE', 'https://api.assurance.ga/v1', 'assurance_api_key_2024', 'contact@assurance.ga', '+24112345681'),
('Douanes', 'DOUANES', 'https://api.douanes.ga/v1', 'douanes_api_key_2024', 'contact@douanes.ga', '+24112345682'),
('Trésor Public', 'TRESOR_PUBLIC', 'https://api.tresor.ga/v1', 'tresor_api_key_2024', 'contact@tresor.ga', '+24112345683'),
('Interpol', 'INTERPOL', 'https://api.interpol.int/v1', 'interpol_api_key_2024', 'contact@interpol.int', '+33123456789'),
('Portail Citoyen', 'PORTAIL_CITOYEN', 'https://api.portail-citoyen.ga/v1', 'portail_api_key_2024', 'contact@portail-citoyen.ga', '+24112345684');

-- Commentaires sur les tables
COMMENT ON TABLE partenaires_externes IS 'Table des partenaires externes du système R-DGTT';
COMMENT ON TABLE integrations IS 'Table des intégrations entre services';
COMMENT ON TABLE echanges_donnees IS 'Table des échanges de données avec les partenaires';
COMMENT ON TABLE webhooks IS 'Table des webhooks pour les notifications';
COMMENT ON TABLE audit_logs IS 'Table des logs d''audit';

-- Commentaires sur les colonnes importantes
COMMENT ON COLUMN partenaires_externes.type_partenaire IS 'Type de partenaire (DGDI, POLICE, ASSURANCE, etc.)';
COMMENT ON COLUMN partenaires_externes.statut IS 'Statut du partenaire (ACTIF, INACTIF, SUSPENDU, etc.)';
COMMENT ON COLUMN integrations.type_integration IS 'Type d''intégration (API, WEBHOOK, FILE, etc.)';
COMMENT ON COLUMN integrations.format_donnees IS 'Format des données (JSON, XML, CSV, etc.)';
COMMENT ON COLUMN echanges_donnees.direction IS 'Direction de l''échange (INBOUND, OUTBOUND)';
COMMENT ON COLUMN webhooks.evenements IS 'Liste des événements déclencheurs (JSON array)';

