package ga.dgtt.usager.model;

/**
 * Enumération des niveaux de sécurité pour les logs d'audit
 */
public enum NiveauSecurite {
    
    /**
     * Niveau d'information - Actions normales
     */
    INFO("Information"),
    
    /**
     * Niveau d'avertissement - Actions suspectes
     */
    WARNING("Avertissement"),
    
    /**
     * Niveau d'erreur - Erreurs système
     */
    ERROR("Erreur"),
    
    /**
     * Niveau de sécurité - Tentatives d'intrusion
     */
    SECURITY("Sécurité"),
    
    /**
     * Niveau critique - Actions critiques
     */
    CRITICAL("Critique");
    
    private final String description;
    
    NiveauSecurite(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return description;
    }
}
