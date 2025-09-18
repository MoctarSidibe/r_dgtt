package ga.dgtt.autres.model;

/**
 * Énumération des statuts possibles pour un partenaire externe
 */
public enum StatutPartenaire {
    ACTIF("Actif", "Partenaire actif et opérationnel"),
    INACTIF("Inactif", "Partenaire inactif"),
    SUSPENDU("Suspendu", "Partenaire suspendu temporairement"),
    EN_MAINTENANCE("En maintenance", "Partenaire en maintenance"),
    ERREUR("Erreur", "Partenaire en erreur"),
    TEST("Test", "Partenaire en mode test");
    
    private final String libelle;
    private final String description;
    
    StatutPartenaire(String libelle, String description) {
        this.libelle = libelle;
        this.description = description;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    public String getDescription() {
        return description;
    }
    
    public boolean estOperational() {
        return this == ACTIF || this == TEST;
    }
    
    public boolean necessiteAction() {
        return this == INACTIF || this == SUSPENDU || this == ERREUR;
    }
}
