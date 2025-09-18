package ga.dgtt.autres.model;

/**
 * Énumération des types de partenaires externes
 */
public enum TypePartenaire {
    DGDI("DGDI", "Direction Générale de la Documentation et de l'Immigration"),
    POLICE("Police Nationale", "Police Nationale - Sécurité Routière"),
    CONTROLE_TECHNIQUE("Contrôle Technique", "Centre de Contrôle Technique"),
    ASSURANCE("Assurance", "Compagnie d'Assurance"),
    DOUANES("Douanes", "Service des Douanes"),
    TRESOR_PUBLIC("Trésor Public", "Trésor Public"),
    INTERPOL("Interpol", "Interpol"),
    PORTAIL_CITOYEN("Portail Citoyen", "Portail Citoyen"),
    AUTRE("Autre", "Autre partenaire");
    
    private final String libelle;
    private final String description;
    
    TypePartenaire(String libelle, String description) {
        this.libelle = libelle;
        this.description = description;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    public String getDescription() {
        return description;
    }
    
    public boolean estGouvernemental() {
        return this == DGDI || this == POLICE || this == DOUANES || this == TRESOR_PUBLIC;
    }
    
    public boolean estPrive() {
        return this == ASSURANCE || this == CONTROLE_TECHNIQUE;
    }
    
    public boolean estInternational() {
        return this == INTERPOL;
    }
    
    public boolean estCitoyen() {
        return this == PORTAIL_CITOYEN;
    }
}
