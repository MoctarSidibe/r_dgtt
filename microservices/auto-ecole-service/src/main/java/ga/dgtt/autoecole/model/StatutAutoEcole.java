package ga.dgtt.autoecole.model;

/**
 * Énumération des statuts possibles pour une auto-école
 * 
 * Les statuts suivent le workflow de validation :
 * 1. EN_ATTENTE - Demande soumise, en attente de paiement
 * 2. PAIEMENT_EN_ATTENTE - Paiement en cours de traitement
 * 3. PAIEMENT_VALIDE - Paiement validé, en attente d'inspection
 * 4. INSPECTION_EN_COURS - Inspection programmée
 * 5. INSPECTION_VALIDEE - Inspection validée, en attente d'autorisation
 * 6. AUTORISATION_PROVISOIRE - Autorisation provisoire accordée (6 mois)
 * 7. AUTORISATION_VALIDE - Auto-école opérationnelle
 * 8. RENOUVELLEMENT_EN_COURS - Renouvellement en cours
 * 9. SUSPENDU - Auto-école suspendue
 * 10. REJETE - Demande rejetée
 * 11. FERME - Auto-école fermée
 */
public enum StatutAutoEcole {
    EN_ATTENTE("En attente", "Demande soumise, en attente de paiement"),
    PAIEMENT_EN_ATTENTE("Paiement en attente", "Paiement en cours de traitement"),
    PAIEMENT_VALIDE("Paiement validé", "Paiement validé, en attente d'inspection"),
    INSPECTION_EN_COURS("Inspection en cours", "Inspection programmée"),
    INSPECTION_VALIDEE("Inspection validée", "Inspection validée, en attente d'autorisation"),
    AUTORISATION_PROVISOIRE("Autorisation provisoire", "Autorisation provisoire accordée (6 mois)"),
    AUTORISATION_VALIDE("Autorisation valide", "Auto-école opérationnelle"),
    RENOUVELLEMENT_EN_COURS("Renouvellement en cours", "Renouvellement en cours"),
    SUSPENDU("Suspendu", "Auto-école suspendue"),
    REJETE("Rejeté", "Demande rejetée"),
    FERME("Fermé", "Auto-école fermée");
    
    private final String libelle;
    private final String description;
    
    StatutAutoEcole(String libelle, String description) {
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
     * Vérifie si l'auto-école peut enrôler des candidats
     */
    public boolean peutEnrollerCandidats() {
        return this == AUTORISATION_PROVISOIRE || this == AUTORISATION_VALIDE;
    }
    
    /**
     * Vérifie si l'auto-école est active
     */
    public boolean estActive() {
        return this == AUTORISATION_PROVISOIRE || this == AUTORISATION_VALIDE || this == RENOUVELLEMENT_EN_COURS;
    }
    
    /**
     * Vérifie si l'auto-école peut être renouvelée
     */
    public boolean peutEtreRenouvelee() {
        return this == AUTORISATION_PROVISOIRE || this == AUTORISATION_VALIDE;
    }
    
    /**
     * Vérifie si l'auto-école est en attente d'action
     */
    public boolean estEnAttenteAction() {
        return this == EN_ATTENTE || this == PAIEMENT_EN_ATTENTE || this == PAIEMENT_VALIDE || 
               this == INSPECTION_EN_COURS || this == INSPECTION_VALIDEE;
    }
}
