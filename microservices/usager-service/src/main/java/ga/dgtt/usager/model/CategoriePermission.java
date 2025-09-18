package ga.dgtt.usager.model;

/**
 * Énumération des catégories de permissions
 */
public enum CategoriePermission {
    LECTURE("Lecture", "Permissions de lecture"),
    ECRITURE("Écriture", "Permissions d'écriture"),
    SUPPRESSION("Suppression", "Permissions de suppression"),
    ADMINISTRATION("Administration", "Permissions d'administration"),
    SECURITE("Sécurité", "Permissions de sécurité"),
    RAPPORTS("Rapports", "Permissions de rapports"),
    AUDIT("Audit", "Permissions d'audit"),
    NOTIFICATIONS("Notifications", "Permissions de notifications");
    
    private final String libelle;
    private final String description;
    
    CategoriePermission(String libelle, String description) {
        this.libelle = libelle;
        this.description = description;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    public String getDescription() {
        return description;
    }
}
