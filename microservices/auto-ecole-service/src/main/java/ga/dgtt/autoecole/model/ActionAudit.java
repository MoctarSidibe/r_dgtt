package ga.dgtt.autoecole.model;

/**
 * Énumération des actions d'audit possibles
 * 
 * Cette énumération définit toutes les actions qui peuvent être enregistrées
 * dans les logs d'audit pour assurer la traçabilité complète.
 */
public enum ActionAudit {
    CREATION("Création", "Création d'une nouvelle entité"),
    LECTURE("Lecture", "Consultation d'une entité"),
    MODIFICATION("Modification", "Modification d'une entité"),
    SUPPRESSION("Suppression", "Suppression d'une entité"),
    CONNEXION("Connexion", "Connexion d'un utilisateur"),
    DECONNEXION("Déconnexion", "Déconnexion d'un utilisateur"),
    TENTATIVE_CONNEXION("Tentative de connexion", "Tentative de connexion échouée"),
    CHANGEMENT_MOT_DE_PASSE("Changement de mot de passe", "Changement du mot de passe"),
    UPLOAD_FICHIER("Upload de fichier", "Téléchargement d'un fichier"),
    DOWNLOAD_FICHIER("Download de fichier", "Téléchargement d'un fichier"),
    GENERATION_DOCUMENT("Génération de document", "Génération d'un document"),
    ENVOI_EMAIL("Envoi d'email", "Envoi d'un email"),
    ENVOI_SMS("Envoi de SMS", "Envoi d'un SMS"),
    PAIEMENT("Paiement", "Effectuation d'un paiement"),
    VALIDATION("Validation", "Validation d'une entité"),
    REJET("Rejet", "Rejet d'une entité"),
    APPROBATION("Approbation", "Approbation d'une entité"),
    INSPECTION("Inspection", "Inspection d'une entité"),
    EVALUATION("Évaluation", "Évaluation d'un candidat"),
    EXAMEN("Examen", "Passage d'un examen"),
    GENERATION_QR_CODE("Génération QR Code", "Génération d'un QR Code"),
    SIGNATURE_NUMERIQUE("Signature numérique", "Signature numérique d'un document"),
    EXPORT_DONNEES("Export de données", "Export de données"),
    IMPORT_DONNEES("Import de données", "Import de données"),
    MODIFICATION_DONNEES_SENSIBLES("Modification données sensibles", "Modification de données sensibles"),
    ACCES_DONNEES_SENSIBLES("Accès données sensibles", "Accès à des données sensibles"),
    TENTATIVE_ACCES_NON_AUTORISE("Tentative d'accès non autorisé", "Tentative d'accès non autorisé"),
    ERREUR_SYSTEME("Erreur système", "Erreur système"),
    MAINTENANCE("Maintenance", "Opération de maintenance");
    
    private final String libelle;
    private final String description;
    
    ActionAudit(String libelle, String description) {
        this.libelle = libelle;
        this.description = description;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    public String getDescription() {
        return description;
    }
    
    /**
     * Vérifie si l'action est critique pour la sécurité
     */
    public boolean isCritique() {
        return this == SUPPRESSION || this == MODIFICATION_DONNEES_SENSIBLES || 
               this == TENTATIVE_ACCES_NON_AUTORISE || this == CHANGEMENT_MOT_DE_PASSE;
    }
    
    /**
     * Vérifie si l'action concerne des données sensibles
     */
    public boolean isDonneesSensibles() {
        return this == MODIFICATION_DONNEES_SENSIBLES || this == ACCES_DONNEES_SENSIBLES;
    }
    
    /**
     * Vérifie si l'action est une tentative d'accès
     */
    public boolean isTentativeAcces() {
        return this == TENTATIVE_CONNEXION || this == TENTATIVE_ACCES_NON_AUTORISE;
    }
    
    /**
     * Vérifie si l'action est une opération de modification
     */
    public boolean isModification() {
        return this == MODIFICATION || this == MODIFICATION_DONNEES_SENSIBLES || 
               this == CHANGEMENT_MOT_DE_PASSE || this == VALIDATION || this == REJET || 
               this == APPROBATION;
    }
    
    /**
     * Vérifie si l'action est une opération de création
     */
    public boolean isCreation() {
        return this == CREATION || this == UPLOAD_FICHIER || this == GENERATION_DOCUMENT || 
               this == GENERATION_QR_CODE || this == SIGNATURE_NUMERIQUE;
    }
    
    /**
     * Vérifie si l'action est une opération de lecture
     */
    public boolean isLecture() {
        return this == LECTURE || this == DOWNLOAD_FICHIER || this == EXPORT_DONNEES;
    }
}
