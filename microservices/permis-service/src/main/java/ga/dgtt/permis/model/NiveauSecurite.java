package ga.dgtt.permis.model;

/**
 * Énumération des niveaux de sécurité pour les logs d'audit
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
    
    public boolean necessiteAlerte() {
        return this == ALERTE || this == CRITIQUE;
    }
    
    public boolean isCritique() {
        return this == CRITIQUE;
    }
    
    public boolean isSecurite() {
        return this == SECURITE || this == CRITIQUE;
    }
}
