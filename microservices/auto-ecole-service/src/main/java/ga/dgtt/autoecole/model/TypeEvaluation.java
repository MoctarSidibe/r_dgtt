package ga.dgtt.autoecole.model;

/**
 * Énumération des types d'évaluation pour un candidat
 * 
 * Chaque candidat doit passer 3 types d'évaluations :
 * - CODE_ROUTE : Code de la route (3 passages)
 * - CRENEAU : Créneau (3 passages)  
 * - CONDUITE_VILLE : Conduite en ville (3 passages)
 */
public enum TypeEvaluation {
    CODE_ROUTE("Code de la route", "Évaluation théorique du code de la route", 3),
    CRENEAU("Créneau", "Évaluation pratique du créneau", 3),
    CONDUITE_VILLE("Conduite en ville", "Évaluation pratique de la conduite en ville", 3);
    
    private final String libelle;
    private final String description;
    private final Integer nombrePassagesRequis;
    
    TypeEvaluation(String libelle, String description, Integer nombrePassagesRequis) {
        this.libelle = libelle;
        this.description = description;
        this.nombrePassagesRequis = nombrePassagesRequis;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Integer getNombrePassagesRequis() {
        return nombrePassagesRequis;
    }
    
    /**
     * Vérifie si le type d'évaluation est théorique
     */
    public boolean isTheorique() {
        return this == CODE_ROUTE;
    }
    
    /**
     * Vérifie si le type d'évaluation est pratique
     */
    public boolean isPratique() {
        return this == CRENEAU || this == CONDUITE_VILLE;
    }
    
    /**
     * Vérifie si le type d'évaluation est en extérieur
     */
    public boolean isExterieur() {
        return this == CONDUITE_VILLE;
    }
    
    /**
     * Vérifie si le type d'évaluation est en intérieur
     */
    public boolean isInterieur() {
        return this == CODE_ROUTE || this == CRENEAU;
    }
}
