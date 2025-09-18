package ga.dgtt.usager.model;

/**
 * Énumération des statuts possibles pour un usager
 */
public enum StatutUsager {
    ACTIF("Actif", "Utilisateur actif et autorisé à se connecter"),
    INACTIF("Inactif", "Utilisateur inactif, ne peut pas se connecter"),
    VERROUILLE("Verrouillé", "Compte verrouillé suite à trop de tentatives de connexion"),
    SUSPENDU("Suspendu", "Compte suspendu temporairement"),
    BANNI("Banni", "Compte banni définitivement"),
    EN_ATTENTE_VALIDATION("En attente de validation", "Compte en attente de validation par un administrateur"),
    EXPIRED("Expiré", "Compte expiré, nécessite un renouvellement");
    
    private final String libelle;
    private final String description;
    
    StatutUsager(String libelle, String description) {
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
     * Vérifie si l'usager peut se connecter
     */
    public boolean peutSeConnecter() {
        return this == ACTIF;
    }
    
    /**
     * Vérifie si l'usager est bloqué
     */
    public boolean estBloque() {
        return this == VERROUILLE || this == SUSPENDU || this == BANNI;
    }
    
    /**
     * Vérifie si l'usager nécessite une action
     */
    public boolean necessiteAction() {
        return this == EN_ATTENTE_VALIDATION || this == EXPIRED;
    }
    
    /**
     * Vérifie si l'usager est temporairement indisponible
     */
    public boolean estTemporairementIndisponible() {
        return this == VERROUILLE || this == SUSPENDU;
    }
    
    /**
     * Vérifie si l'usager est définitivement indisponible
     */
    public boolean estDefinitivementIndisponible() {
        return this == BANNI;
    }
}
