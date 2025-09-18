-- Database: permis
-- Description: Base de données pour la gestion des permis de conduire

-- Création de la base de données
CREATE DATABASE permis;

-- Connexion à la base de données
\c permis;

-- Extension pour UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Table des examens
CREATE TABLE examens (
    id BIGSERIAL PRIMARY KEY,
    numero_examen VARCHAR(100) UNIQUE NOT NULL,
    candidat_id BIGINT NOT NULL,
    auto_ecole_id BIGINT NOT NULL,
    type_examen VARCHAR(50) NOT NULL,
    date_examen TIMESTAMP NOT NULL,
    lieu_examen VARCHAR(255) NOT NULL,
    examinateur_nom VARCHAR(255) NOT NULL,
    examinateur_prenom VARCHAR(255) NOT NULL,
    examinateur_matricule VARCHAR(100),
    note DECIMAL(5,2),
    nombre_erreurs INTEGER DEFAULT 0,
    temps_realise INTEGER,
    est_reussi BOOLEAN DEFAULT FALSE,
    commentaires TEXT,
    qr_code TEXT,
    proces_verbal_url VARCHAR(500),
    signature_examinateur TEXT,
    signature_candidat TEXT,
    statut VARCHAR(50) NOT NULL DEFAULT 'PROGRAMME',
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table des candidats (référence vers auto-ecole service)
CREATE TABLE candidats (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    date_naissance DATE NOT NULL,
    lieu_naissance VARCHAR(255) NOT NULL,
    nationalite VARCHAR(100) NOT NULL DEFAULT 'Gabonaise',
    categorie_permis VARCHAR(10) NOT NULL,
    numero_licence VARCHAR(100) UNIQUE,
    numero_evaluation VARCHAR(100) UNIQUE,
    statut VARCHAR(50) NOT NULL DEFAULT 'ENROLE',
    montant_paiement DECIMAL(10,2),
    date_paiement TIMESTAMP,
    reference_paiement VARCHAR(100),
    qr_code TEXT,
    photo_url VARCHAR(500),
    piece_identite_url VARCHAR(500),
    certificat_medical_url VARCHAR(500),
    attestation_residence_url VARCHAR(500),
    notes TEXT,
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    auto_ecole_id BIGINT NOT NULL
);

-- Table des auto-écoles (référence vers auto-ecole service)
CREATE TABLE auto_ecoles (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    proprietaire_nom VARCHAR(255) NOT NULL,
    proprietaire_prenom VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    telephone VARCHAR(20) NOT NULL,
    adresse VARCHAR(500) NOT NULL,
    ville VARCHAR(100) NOT NULL,
    province VARCHAR(100) NOT NULL,
    statut VARCHAR(50) NOT NULL DEFAULT 'EN_ATTENTE',
    numero_demande VARCHAR(100) UNIQUE,
    qr_code TEXT,
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table des documents d'examen
CREATE TABLE documents_examen (
    id BIGSERIAL PRIMARY KEY,
    examen_id BIGINT REFERENCES examens(id) ON DELETE CASCADE,
    type_document VARCHAR(50) NOT NULL,
    nom_fichier VARCHAR(255) NOT NULL,
    url_fichier VARCHAR(500) NOT NULL,
    type_mime VARCHAR(50),
    taille_fichier BIGINT,
    hash_fichier VARCHAR(255),
    qr_code TEXT,
    est_valide BOOLEAN DEFAULT FALSE,
    date_validation TIMESTAMP,
    validateur VARCHAR(255),
    commentaires TEXT,
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
    examen_id BIGINT REFERENCES examens(id) ON DELETE SET NULL,
    candidat_id BIGINT REFERENCES candidats(id) ON DELETE SET NULL
);

-- Index pour les performances
CREATE INDEX idx_examens_numero ON examens(numero_examen);
CREATE INDEX idx_examens_candidat_id ON examens(candidat_id);
CREATE INDEX idx_examens_auto_ecole_id ON examens(auto_ecole_id);
CREATE INDEX idx_examens_statut ON examens(statut);
CREATE INDEX idx_examens_date ON examens(date_examen);
CREATE INDEX idx_candidats_nom ON candidats(nom);
CREATE INDEX idx_candidats_statut ON candidats(statut);
CREATE INDEX idx_candidats_auto_ecole_id ON candidats(auto_ecole_id);
CREATE INDEX idx_auto_ecoles_nom ON auto_ecoles(nom);
CREATE INDEX idx_auto_ecoles_statut ON auto_ecoles(statut);
CREATE INDEX idx_documents_examen_id ON documents_examen(examen_id);
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
CREATE TRIGGER update_examens_modification_time BEFORE UPDATE ON examens FOR EACH ROW EXECUTE FUNCTION update_modified_column();
CREATE TRIGGER update_candidats_modification_time BEFORE UPDATE ON candidats FOR EACH ROW EXECUTE FUNCTION update_modified_column();
CREATE TRIGGER update_auto_ecoles_modification_time BEFORE UPDATE ON auto_ecoles FOR EACH ROW EXECUTE FUNCTION update_modified_column();
CREATE TRIGGER update_documents_examen_modification_time BEFORE UPDATE ON documents_examen FOR EACH ROW EXECUTE FUNCTION update_modified_column();

-- Commentaires sur les tables
COMMENT ON TABLE examens IS 'Table des examens de permis de conduire';
COMMENT ON TABLE candidats IS 'Table des candidats au permis de conduire';
COMMENT ON TABLE auto_ecoles IS 'Table des auto-écoles';
COMMENT ON TABLE documents_examen IS 'Table des documents d''examen';
COMMENT ON TABLE audit_logs IS 'Table des logs d''audit';

-- Commentaires sur les colonnes importantes
COMMENT ON COLUMN examens.type_examen IS 'Type d''examen (CODE_ROUTE, CONDUITE_PRATIQUE, etc.)';
COMMENT ON COLUMN examens.statut IS 'Statut de l''examen (PROGRAMME, EN_COURS, TERMINE, etc.)';
COMMENT ON COLUMN candidats.categorie_permis IS 'Catégorie de permis (A, B, C, D, E, F, G)';
COMMENT ON COLUMN candidats.statut IS 'Statut du candidat (ENROLE, PRE_ENROLE, etc.)';
COMMENT ON COLUMN auto_ecoles.statut IS 'Statut de l''auto-école (EN_ATTENTE, AUTORISATION_PROVISOIRE, etc.)';

