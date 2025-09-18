package ga.dgtt.usager.model;

/**
 * Énumération des rôles d'usager dans le système R-DGTT
 * 
 * Cette énumération définit tous les rôles possibles
 * avec leurs permissions et responsabilités.
 */
public enum RoleUsager {
    // Rôles administratifs
    ADMIN("Administrateur", "Administrateur système avec tous les droits", 1),
    SUPER_ADMIN("Super Administrateur", "Super administrateur avec accès complet", 0),
    
    // Rôles du Ministère
    DGTT("Directeur Général Transports Terrestres", "Directeur Général des Transports Terrestres", 2),
    DC("Directeur du Centre", "Directeur du Centre National de l'Examen du Permis de Conduire", 3),
    
    // Rôles des Services
    SEV("Service Examen et Validation", "Service Examen et Validation - Bureau Enregistrement", 4),
    SAF("Service Administratif et Financier", "Service Administratif et Financier - Bureau 108", 5),
    STIAS("STIAS", "Service Technique, Informatique, des Archives et de la Statistiques", 6),
    
    // Rôles des Auto-Écoles
    AUTO_ECOLE_ADMIN("Administrateur Auto-École", "Administrateur d'auto-école", 7),
    AUTO_ECOLE_FORMATEUR("Formateur Auto-École", "Formateur dans une auto-école", 8),
    
    // Rôles des Partenaires Externes
    DGDI("DGDI", "Direction Générale de la Documentation et de l'Immigration", 9),
    POLICE("Police Nationale", "Police Nationale - Sécurité Routière", 10),
    CONTROLE_TECHNIQUE("Contrôle Technique", "Centre de Contrôle Technique", 11),
    ASSURANCE("Assurance", "Compagnie d'Assurance", 12),
    DOUANES("Douanes", "Service des Douanes", 13),
    TRESOR_PUBLIC("Trésor Public", "Trésor Public", 14),
    INTERPOL("Interpol", "Interpol", 15),
    
    // Rôles du Public
    CITOYEN("Citoyen", "Citoyen utilisant le portail public", 16),
    VISITEUR("Visiteur", "Visiteur sans compte", 17);
    
    private final String libelle;
    private final String description;
    private final Integer niveau;
    
    RoleUsager(String libelle, String description, Integer niveau) {
        this.libelle = libelle;
        this.description = description;
        this.niveau = niveau;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Integer getNiveau() {
        return niveau;
    }
    
    /**
     * Vérifie si le rôle est administratif
     */
    public boolean isAdministratif() {
        return this == ADMIN || this == SUPER_ADMIN || this == DGTT || this == DC;
    }
    
    /**
     * Vérifie si le rôle est de service
     */
    public boolean isService() {
        return this == SEV || this == SAF || this == STIAS;
    }
    
    /**
     * Vérifie si le rôle est d'auto-école
     */
    public boolean isAutoEcole() {
        return this == AUTO_ECOLE_ADMIN || this == AUTO_ECOLE_FORMATEUR;
    }
    
    /**
     * Vérifie si le rôle est partenaire externe
     */
    public boolean isPartenaireExterne() {
        return this == DGDI || this == POLICE || this == CONTROLE_TECHNIQUE || 
               this == ASSURANCE || this == DOUANES || this == TRESOR_PUBLIC || this == INTERPOL;
    }
    
    /**
     * Vérifie si le rôle est public
     */
    public boolean isPublic() {
        return this == CITOYEN || this == VISITEUR;
    }
    
    /**
     * Vérifie si le rôle a un niveau supérieur ou égal
     */
    public boolean aNiveauSuperieurOuEgal(RoleUsager autreRole) {
        return this.niveau <= autreRole.niveau;
    }
    
    /**
     * Vérifie si le rôle peut gérer les utilisateurs
     */
    public boolean peutGererUtilisateurs() {
        return this == SUPER_ADMIN || this == ADMIN || this == DGTT;
    }
    
    /**
     * Vérifie si le rôle peut voir tous les dossiers
     */
    public boolean peutVoirTousDossiers() {
        return this == SUPER_ADMIN || this == ADMIN || this == DGTT || this == DC;
    }
    
    /**
     * Vérifie si le rôle peut approuver des dossiers
     */
    public boolean peutApprouverDossiers() {
        return this == SUPER_ADMIN || this == ADMIN || this == DGTT || this == DC || this == SEV || this == SAF;
    }
    
    /**
     * Vérifie si le rôle peut accéder aux rapports
     */
    public boolean peutAccederRapports() {
        return this == SUPER_ADMIN || this == ADMIN || this == DGTT || this == DC || this == STIAS;
    }
}
