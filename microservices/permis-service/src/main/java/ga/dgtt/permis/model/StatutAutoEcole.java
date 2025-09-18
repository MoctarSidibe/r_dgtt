package ga.dgtt.permis.model;

/**
 * Énumération des statuts possibles pour une auto-école
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
    
    public boolean peutEnrollerCandidats() {
        return this == AUTORISATION_PROVISOIRE || this == AUTORISATION_VALIDE;
    }
    
    public boolean estActive() {
        return this == AUTORISATION_PROVISOIRE || this == AUTORISATION_VALIDE || this == RENOUVELLEMENT_EN_COURS;
    }
}
