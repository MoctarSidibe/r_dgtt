package ga.dgtt.permis.model;

/**
 * Énumération des statuts possibles pour un candidat
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
    
    public boolean peutPasserExamen() {
        return this == DOSSIER_VALIDE || this == EXAMEN_PROGRAMME;
    }
    
    public boolean aReussiExamen() {
        return this == EXAMEN_REUSSI || this == PERMIS_GENERE || this == PERMIS_DELIVRE;
    }
    
    public boolean estActif() {
        return this != REJETE && this != SUSPENDU;
    }
}
