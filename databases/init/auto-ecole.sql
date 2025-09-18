-- Script d'initialisation de la base de données Auto-École
-- R-DGTT Portail - Ministère des Transports, de la Marine Marchande et de la Logistique

-- Créer la base de données si elle n'existe pas
CREATE DATABASE IF NOT EXISTS auto_ecole_db;

-- Utiliser la base de données
\c auto_ecole_db;

-- Créer l'extension pour les UUIDs
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Créer l'extension pour les fonctions de hachage
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Table des auto-écoles
CREATE TABLE IF NOT EXISTS auto_ecoles (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    proprietaire_nom VARCHAR(255) NOT NULL,
    proprietaire_prenom VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telephone VARCHAR(20) NOT NULL,
    adresse VARCHAR(500) NOT NULL,
    ville VARCHAR(100) NOT NULL,
    province VARCHAR(100) NOT NULL,
    statut VARCHAR(50) NOT NULL DEFAULT 'EN_ATTENTE',
    numero_demande VARCHAR(100) UNIQUE,
    qr_code TEXT,
    montant_paiement DECIMAL(10,2) DEFAULT 100000.00,
    date_paiement TIMESTAMP,
    reference_paiement VARCHAR(100),
    autorisation_provisoire VARCHAR(100),
    date_autorisation TIMESTAMP,
    date_expiration_autorisation TIMESTAMP,
    rapport_inspection TEXT,
    date_inspection TIMESTAMP,
    inspecteur_nom VARCHAR(255),
    inspecteur_prenom VARCHAR(255),
    notes TEXT,
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table des candidats
CREATE TABLE IF NOT EXISTS candidats (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    date_naissance DATE NOT NULL,
    lieu_naissance VARCHAR(255) NOT NULL,
    nationalite VARCHAR(100) NOT NULL DEFAULT 'Gabonaise',
    categorie_permis VARCHAR(10) NOT NULL CHECK (categorie_permis IN ('A', 'B', 'C', 'D', 'E', 'F', 'G')),
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
    auto_ecole_id BIGINT NOT NULL REFERENCES auto_ecoles(id) ON DELETE CASCADE
);

-- Table des évaluations
CREATE TABLE IF NOT EXISTS evaluations (
    id BIGSERIAL PRIMARY KEY,
    candidat_id BIGINT NOT NULL REFERENCES candidats(id) ON DELETE CASCADE,
    type_evaluation VARCHAR(50) NOT NULL CHECK (type_evaluation IN ('CODE_ROUTE', 'CRENEAU', 'CONDUITE_VILLE')),
    numero_passage INTEGER NOT NULL CHECK (numero_passage BETWEEN 1 AND 3),
    date_evaluation TIMESTAMP NOT NULL,
    examinateur_nom VARCHAR(255) NOT NULL,
    examinateur_prenom VARCHAR(255) NOT NULL,
    examinateur_matricule VARCHAR(100),
    note DECIMAL(4,2) CHECK (note >= 0 AND note <= 20),
    nombre_erreurs INTEGER DEFAULT 0,
    temps_realise INTEGER, -- en minutes
    est_reussi BOOLEAN DEFAULT FALSE,
    commentaires TEXT,
    qr_code TEXT,
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table des documents auto-école
CREATE TABLE IF NOT EXISTS documents_auto_ecole (
    id BIGSERIAL PRIMARY KEY,
    auto_ecole_id BIGINT NOT NULL REFERENCES auto_ecoles(id) ON DELETE CASCADE,
    type_document VARCHAR(100) NOT NULL,
    nom_fichier VARCHAR(255) NOT NULL,
    url_fichier VARCHAR(500) NOT NULL,
    type_mime VARCHAR(50),
    taille_fichier BIGINT,
    hash_fichier VARCHAR(64),
    qr_code TEXT,
    est_valide BOOLEAN DEFAULT FALSE,
    date_validation TIMESTAMP,
    validateur VARCHAR(255),
    commentaires TEXT,
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table des documents candidat
CREATE TABLE IF NOT EXISTS documents_candidat (
    id BIGSERIAL PRIMARY KEY,
    candidat_id BIGINT NOT NULL REFERENCES candidats(id) ON DELETE CASCADE,
    type_document VARCHAR(100) NOT NULL,
    nom_fichier VARCHAR(255) NOT NULL,
    url_fichier VARCHAR(500) NOT NULL,
    type_mime VARCHAR(50),
    taille_fichier BIGINT,
    hash_fichier VARCHAR(64),
    qr_code TEXT,
    est_valide BOOLEAN DEFAULT FALSE,
    date_validation TIMESTAMP,
    validateur VARCHAR(255),
    commentaires TEXT,
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table des logs d'audit
CREATE TABLE IF NOT EXISTS audit_logs (
    id BIGSERIAL PRIMARY KEY,
    entite VARCHAR(100) NOT NULL,
    entite_id BIGINT,
    action VARCHAR(100) NOT NULL,
    utilisateur VARCHAR(255) NOT NULL,
    role_utilisateur VARCHAR(100),
    adresse_ip VARCHAR(45),
    user_agent TEXT,
    donnees_avant TEXT,
    donnees_apres TEXT,
    message TEXT,
    niveau_securite VARCHAR(50) DEFAULT 'INFO',
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    auto_ecole_id BIGINT REFERENCES auto_ecoles(id) ON DELETE SET NULL,
    candidat_id BIGINT REFERENCES candidats(id) ON DELETE SET NULL
);

-- Index pour améliorer les performances
CREATE INDEX IF NOT EXISTS idx_auto_ecoles_statut ON auto_ecoles(statut);
CREATE INDEX IF NOT EXISTS idx_auto_ecoles_email ON auto_ecoles(email);
CREATE INDEX IF NOT EXISTS idx_auto_ecoles_telephone ON auto_ecoles(telephone);
CREATE INDEX IF NOT EXISTS idx_auto_ecoles_numero_demande ON auto_ecoles(numero_demande);
CREATE INDEX IF NOT EXISTS idx_auto_ecoles_ville ON auto_ecoles(ville);
CREATE INDEX IF NOT EXISTS idx_auto_ecoles_province ON auto_ecoles(province);
CREATE INDEX IF NOT EXISTS idx_auto_ecoles_date_creation ON auto_ecoles(date_creation);

CREATE INDEX IF NOT EXISTS idx_candidats_statut ON candidats(statut);
CREATE INDEX IF NOT EXISTS idx_candidats_auto_ecole_id ON candidats(auto_ecole_id);
CREATE INDEX IF NOT EXISTS idx_candidats_categorie_permis ON candidats(categorie_permis);
CREATE INDEX IF NOT EXISTS idx_candidats_numero_licence ON candidats(numero_licence);
CREATE INDEX IF NOT EXISTS idx_candidats_numero_evaluation ON candidats(numero_evaluation);
CREATE INDEX IF NOT EXISTS idx_candidats_nom ON candidats(nom);
CREATE INDEX IF NOT EXISTS idx_candidats_prenom ON candidats(prenom);
CREATE INDEX IF NOT EXISTS idx_candidats_date_naissance ON candidats(date_naissance);

CREATE INDEX IF NOT EXISTS idx_evaluations_candidat_id ON evaluations(candidat_id);
CREATE INDEX IF NOT EXISTS idx_evaluations_type_evaluation ON evaluations(type_evaluation);
CREATE INDEX IF NOT EXISTS idx_evaluations_date_evaluation ON evaluations(date_evaluation);
CREATE INDEX IF NOT EXISTS idx_evaluations_est_reussi ON evaluations(est_reussi);

CREATE INDEX IF NOT EXISTS idx_documents_auto_ecole_auto_ecole_id ON documents_auto_ecole(auto_ecole_id);
CREATE INDEX IF NOT EXISTS idx_documents_auto_ecole_type_document ON documents_auto_ecole(type_document);
CREATE INDEX IF NOT EXISTS idx_documents_auto_ecole_est_valide ON documents_auto_ecole(est_valide);

CREATE INDEX IF NOT EXISTS idx_documents_candidat_candidat_id ON documents_candidat(candidat_id);
CREATE INDEX IF NOT EXISTS idx_documents_candidat_type_document ON documents_candidat(type_document);
CREATE INDEX IF NOT EXISTS idx_documents_candidat_est_valide ON documents_candidat(est_valide);

CREATE INDEX IF NOT EXISTS idx_audit_logs_entite ON audit_logs(entite);
CREATE INDEX IF NOT EXISTS idx_audit_logs_entite_id ON audit_logs(entite_id);
CREATE INDEX IF NOT EXISTS idx_audit_logs_action ON audit_logs(action);
CREATE INDEX IF NOT EXISTS idx_audit_logs_utilisateur ON audit_logs(utilisateur);
CREATE INDEX IF NOT EXISTS idx_audit_logs_niveau_securite ON audit_logs(niveau_securite);
CREATE INDEX IF NOT EXISTS idx_audit_logs_date_creation ON audit_logs(date_creation);
CREATE INDEX IF NOT EXISTS idx_audit_logs_auto_ecole_id ON audit_logs(auto_ecole_id);
CREATE INDEX IF NOT EXISTS idx_audit_logs_candidat_id ON audit_logs(candidat_id);

-- Triggers pour mettre à jour automatiquement la date de modification
CREATE OR REPLACE FUNCTION update_modified_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.date_modification = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_auto_ecoles_modified BEFORE UPDATE ON auto_ecoles
    FOR EACH ROW EXECUTE FUNCTION update_modified_column();

CREATE TRIGGER update_candidats_modified BEFORE UPDATE ON candidats
    FOR EACH ROW EXECUTE FUNCTION update_modified_column();

CREATE TRIGGER update_evaluations_modified BEFORE UPDATE ON evaluations
    FOR EACH ROW EXECUTE FUNCTION update_modified_column();

CREATE TRIGGER update_documents_auto_ecole_modified BEFORE UPDATE ON documents_auto_ecole
    FOR EACH ROW EXECUTE FUNCTION update_modified_column();

CREATE TRIGGER update_documents_candidat_modified BEFORE UPDATE ON documents_candidat
    FOR EACH ROW EXECUTE FUNCTION update_modified_column();

-- Vues pour faciliter les requêtes
CREATE OR REPLACE VIEW v_auto_ecoles_actives AS
SELECT 
    ae.*,
    COUNT(c.id) as nombre_candidats,
    COUNT(CASE WHEN c.statut = 'PERMIS_DELIVRE' THEN 1 END) as candidats_permis_delivre
FROM auto_ecoles ae
LEFT JOIN candidats c ON ae.id = c.auto_ecole_id
WHERE ae.statut IN ('AUTORISATION_PROVISOIRE', 'AUTORISATION_VALIDE', 'RENOUVELLEMENT_EN_COURS')
GROUP BY ae.id;

CREATE OR REPLACE VIEW v_candidats_en_formation AS
SELECT 
    c.*,
    ae.nom as auto_ecole_nom,
    ae.ville as auto_ecole_ville,
    COUNT(e.id) as nombre_evaluations,
    COUNT(CASE WHEN e.est_reussi = true THEN 1 END) as evaluations_reussies
FROM candidats c
JOIN auto_ecoles ae ON c.auto_ecole_id = ae.id
LEFT JOIN evaluations e ON c.id = e.candidat_id
WHERE c.statut IN ('EN_FORMATION', 'EVALUATION_EN_COURS', 'EVALUATION_COMPLETE')
GROUP BY c.id, ae.id;

-- Données de test (optionnel)
INSERT INTO auto_ecoles (nom, proprietaire_nom, proprietaire_prenom, email, telephone, adresse, ville, province, statut, numero_demande) VALUES
('Auto-École Excellence', 'MBOUMBA', 'Jean', 'jean.mboumba@example.com', '+241123456789', 'Avenue Léon Mba, Quartier Montagne Sainte', 'Libreville', 'Estuaire', 'EN_ATTENTE', 'AE20241201001'),
('Centre de Formation Routière', 'NGOMA', 'Marie', 'marie.ngoma@example.com', '+241123456790', 'Boulevard de l''Indépendance', 'Port-Gentil', 'Ogooué-Maritime', 'PAIEMENT_VALIDE', 'AE20241201002'),
('École de Conduite Moderne', 'OBAME', 'Pierre', 'pierre.obame@example.com', '+241123456791', 'Rue du Commerce', 'Franceville', 'Haut-Ogooué', 'AUTORISATION_PROVISOIRE', 'AE20241201003')
ON CONFLICT (numero_demande) DO NOTHING;

-- Données de test pour les candidats
INSERT INTO candidats (nom, prenom, date_naissance, lieu_naissance, nationalite, categorie_permis, numero_licence, numero_evaluation, statut, auto_ecole_id) VALUES
('DIOUF', 'Amadou', '1995-03-15', 'Libreville', 'Gabonaise', 'B', 'LIC20241201001', 'EVAL20241201001', 'EN_FORMATION', 1),
('MARTIN', 'Sophie', '1992-07-22', 'Port-Gentil', 'Gabonaise', 'B', 'LIC20241201002', 'EVAL20241201002', 'EVALUATION_EN_COURS', 1),
('KOUMA', 'Koffi', '1988-11-08', 'Franceville', 'Gabonaise', 'C', 'LIC20241201003', 'EVAL20241201003', 'PRE_ENROLE', 2)
ON CONFLICT (numero_licence) DO NOTHING;

-- Données de test pour les évaluations
INSERT INTO evaluations (candidat_id, type_evaluation, numero_passage, date_evaluation, examinateur_nom, examinateur_prenom, note, est_reussi) VALUES
(1, 'CODE_ROUTE', 1, '2024-12-01 09:00:00', 'INSPECTEUR', 'Jean', 18.5, true),
(1, 'CODE_ROUTE', 2, '2024-12-02 09:00:00', 'INSPECTEUR', 'Jean', 19.0, true),
(1, 'CRENEAU', 1, '2024-12-03 10:00:00', 'INSPECTEUR', 'Marie', 17.0, true),
(2, 'CODE_ROUTE', 1, '2024-12-01 14:00:00', 'INSPECTEUR', 'Pierre', 16.5, true),
(2, 'CRENEAU', 1, '2024-12-02 14:00:00', 'INSPECTEUR', 'Marie', 16.0, true)
ON CONFLICT DO NOTHING;

-- Données de test pour les logs d'audit
INSERT INTO audit_logs (entite, entite_id, action, utilisateur, message, niveau_securite, auto_ecole_id) VALUES
('AutoEcole', 1, 'CREATION', 'admin', 'Création d''une nouvelle auto-école: Auto-École Excellence', 'INFO', 1),
('AutoEcole', 2, 'PAIEMENT', 'admin', 'Paiement validé pour l''auto-école: Centre de Formation Routière', 'INFO', 2),
('Candidat', 1, 'CREATION', 'admin', 'Candidat enrôlé: Amadou DIOUF', 'INFO', 1)
ON CONFLICT DO NOTHING;

-- Statistiques de la base de données
SELECT 
    'Auto-Écoles' as table_name, 
    COUNT(*) as total_records 
FROM auto_ecoles
UNION ALL
SELECT 
    'Candidats' as table_name, 
    COUNT(*) as total_records 
FROM candidats
UNION ALL
SELECT 
    'Évaluations' as table_name, 
    COUNT(*) as total_records 
FROM evaluations
UNION ALL
SELECT 
    'Logs d''Audit' as table_name, 
    COUNT(*) as total_records 
FROM audit_logs;

-- Message de confirmation
DO $$
BEGIN
    RAISE NOTICE 'Base de données auto_ecole_db initialisée avec succès!';
    RAISE NOTICE 'Tables créées: auto_ecoles, candidats, evaluations, documents_auto_ecole, documents_candidat, audit_logs';
    RAISE NOTICE 'Index et triggers créés pour optimiser les performances';
    RAISE NOTICE 'Vues créées: v_auto_ecoles_actives, v_candidats_en_formation';
    RAISE NOTICE 'Données de test insérées';
END $$;
