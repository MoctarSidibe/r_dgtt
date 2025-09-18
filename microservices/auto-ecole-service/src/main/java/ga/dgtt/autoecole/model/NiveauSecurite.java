package ga.dgtt.autoecole.model;

/**
 * Énumération des niveaux de sécurité pour les logs d'audit
 * 
 * Cette énumération définit les niveaux de sécurité utilisés pour classifier
 * les actions enregistrées dans les logs d'audit.
 */
public enum NiveauSecurite {
    INFO("Information", "Action normale, niveau information"),
    WARNING("Avertissement", "Action nécessitant une attention particulière"),
    ALERTE("Alerte", "Action nécessitant une intervention immédiate"),
    CRITIQUE("Critique", "Action critique nécessitant une intervention urgente"),
    SECURITE("Sécurité", "Action liée à la sécurité du système"),
    CONFORMITE("Conformité", "Action liée à la conformité réglementaire");
    
    private final String libelle;
    private final String description;
    
    NiveauSecurite(String libelle, String description) {
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
     * Vérifie si le niveau nécessite une alerte
     */
    public boolean necessiteAlerte() {
        return this == ALERTE || this == CRITIQUE;
    }
    
    /**
     * Vérifie si le niveau est critique
     */
    public boolean isCritique() {
        return this == CRITIQUE;
    }
    
    /**
     * Vérifie si le niveau est lié à la sécurité
     */
    public boolean isSecurite() {
        return this == SECURITE || this == CRITIQUE;
    }
    
    /**
     * Vérifie si le niveau nécessite une notification
     */
    public boolean necessiteNotification() {
        return this == WARNING || this == ALERTE || this == CRITIQUE;
    }
    
    /**
     * Retourne la priorité numérique du niveau
     */
    public int getPriorite() {
        switch (this) {
            case INFO: return 1;
            case WARNING: return 2;
            case ALERTE: return 3;
            case CRITIQUE: return 4;
            case SECURITE: return 3;
            case CONFORMITE: return 2;
            default: return 1;
        }
    }
}
