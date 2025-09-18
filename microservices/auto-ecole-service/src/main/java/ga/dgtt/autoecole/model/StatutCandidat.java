package ga.dgtt.autoecole.model;

/**
 * Énumération des statuts possibles pour un candidat
 * 
 * Les statuts suivent le workflow de formation et d'examen :
 * 1. ENROLE - Candidat enrôlé, en attente de paiement
 * 2. PAIEMENT_EN_ATTENTE - Paiement en cours de traitement
 * 3. PRE_ENROLE - Paiement validé, en formation
 * 4. EN_FORMATION - Candidat en cours de formation
 * 5. EVALUATION_EN_COURS - Évaluations en cours
 * 6. EVALUATION_COMPLETE - Toutes les évaluations terminées
 * 7. DOSSIER_VALIDE - Dossier validé pour l'examen
 * 8. EXAMEN_PROGRAMME - Examen programmé
 * 9. EXAMEN_EN_COURS - Examen en cours
 * 10. EXAMEN_REUSSI - Examen réussi
 * 11. PERMIS_GENERE - Permis généré
 * 12. PERMIS_DELIVRE - Permis délivré
 * 13. REJETE - Candidat rejeté
 * 14. SUSPENDU - Candidat suspendu
 */
public enum StatutCandidat {
    ENROLE("Enrôlé", "Candidat enrôlé, en attente de paiement"),
    PAIEMENT_EN_ATTENTE("Paiement en attente", "Paiement en cours de traitement"),
    PRE_ENROLE("Pré-enrôlé", "Paiement validé, en formation"),
    EN_FORMATION("En formation", "Candidat en cours de formation"),
    EVALUATION_EN_COURS("Évaluation en cours", "Évaluations en cours"),
    EVALUATION_COMPLETE("Évaluation complète", "Toutes les évaluations terminées"),
    DOSSIER_VALIDE("Dossier validé", "Dossier validé pour l'examen"),
    EXAMEN_PROGRAMME("Examen programmé", "Examen programmé"),
    EXAMEN_EN_COURS("Examen en cours", "Examen en cours"),
    EXAMEN_REUSSI("Examen réussi", "Examen réussi"),
    PERMIS_GENERE("Permis généré", "Permis généré"),
    PERMIS_DELIVRE("Permis délivré", "Permis délivré"),
    REJETE("Rejeté", "Candidat rejeté"),
    SUSPENDU("Suspendu", "Candidat suspendu");
    
    private final String libelle;
    private final String description;
    
    StatutCandidat(String libelle, String description) {
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
     * Vérifie si le candidat peut commencer sa formation
     */
    public boolean peutCommencerFormation() {
        return this == PRE_ENROLE || this == EN_FORMATION;
    }
    
    /**
     * Vérifie si le candidat peut passer les évaluations
     */
    public boolean peutPasserEvaluations() {
        return this == EN_FORMATION || this == EVALUATION_EN_COURS;
    }
    
    /**
     * Vérifie si le candidat peut passer l'examen
     */
    public boolean peutPasserExamen() {
        return this == DOSSIER_VALIDE || this == EXAMEN_PROGRAMME;
    }
    
    /**
     * Vérifie si le candidat a réussi son examen
     */
    public boolean aReussiExamen() {
        return this == EXAMEN_REUSSI || this == PERMIS_GENERE || this == PERMIS_DELIVRE;
    }
    
    /**
     * Vérifie si le candidat est actif
     */
    public boolean estActif() {
        return this != REJETE && this != SUSPENDU;
    }
    
    /**
     * Vérifie si le candidat est en attente d'action
     */
    public boolean estEnAttenteAction() {
        return this == ENROLE || this == PAIEMENT_EN_ATTENTE || this == EVALUATION_EN_COURS || 
               this == DOSSIER_VALIDE || this == EXAMEN_PROGRAMME;
    }
}
