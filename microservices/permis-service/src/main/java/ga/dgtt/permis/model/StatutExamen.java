package ga.dgtt.permis.model;

/**
 * Énumération des statuts possibles pour un examen
 * 
 * Les statuts suivent le workflow d'examen :
 * 1. PROGRAMME - Examen programmé
 * 2. EN_COURS - Examen en cours
 * 3. TERMINE - Examen terminé
 * 4. VALIDE - Examen validé
 * 5. REJETE - Examen rejeté
 * 6. ANNULE - Examen annulé
 */
public enum StatutExamen {
    PROGRAMME("Programmé", "Examen programmé"),
    EN_COURS("En cours", "Examen en cours"),
    TERMINE("Terminé", "Examen terminé"),
    VALIDE("Validé", "Examen validé"),
    REJETE("Rejeté", "Examen rejeté"),
    ANNULE("Annulé", "Examen annulé");
    
    private final String libelle;
    private final String description;
    
    StatutExamen(String libelle, String description) {
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
     * Vérifie si l'examen peut être démarré
     */
    public boolean peutEtreDemarre() {
        return this == PROGRAMME;
    }
    
    /**
     * Vérifie si l'examen est en cours
     */
    public boolean estEnCours() {
        return this == EN_COURS;
    }
    
    /**
     * Vérifie si l'examen est terminé
     */
    public boolean estTermine() {
        return this == TERMINE || this == VALIDE || this == REJETE;
    }
    
    /**
     * Vérifie si l'examen est validé
     */
    public boolean estValide() {
        return this == VALIDE;
    }
    
    /**
     * Vérifie si l'examen est rejeté
     */
    public boolean estRejete() {
        return this == REJETE;
    }
    
    /**
     * Vérifie si l'examen est annulé
     */
    public boolean estAnnule() {
        return this == ANNULE;
    }
    
    /**
     * Vérifie si l'examen peut être modifié
     */
    public boolean peutEtreModifie() {
        return this == PROGRAMME || this == EN_COURS;
    }
    
    /**
     * Vérifie si l'examen peut être annulé
     */
    public boolean peutEtreAnnule() {
        return this == PROGRAMME || this == EN_COURS;
    }
    
    /**
     * Vérifie si l'examen peut être validé
     */
    public boolean peutEtreValide() {
        return this == TERMINE;
    }
    
    /**
     * Vérifie si l'examen est actif
     */
    public boolean estActif() {
        return this != ANNULE;
    }
}
