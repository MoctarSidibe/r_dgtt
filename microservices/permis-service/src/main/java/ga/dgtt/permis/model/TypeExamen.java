package ga.dgtt.permis.model;

/**
 * Énumération des types d'examen de permis de conduire
 * 
 * Cette énumération définit les différents types d'examens
 * que peuvent passer les candidats.
 */
public enum TypeExamen {
    CODE_ROUTE("Code de la route", "Examen théorique du code de la route", 20, 5),
    CONDUITE_PRATIQUE("Conduite pratique", "Examen pratique de conduite", 20, 3),
    CONDUITE_URBAINE("Conduite urbaine", "Examen de conduite en ville", 20, 3),
    CONDUITE_AUTOROUTE("Conduite autoroute", "Examen de conduite sur autoroute", 20, 2),
    CONDUITE_NUIT("Conduite de nuit", "Examen de conduite de nuit", 20, 1),
    CONDUITE_METEO("Conduite par mauvais temps", "Examen de conduite par mauvais temps", 20, 1);
    
    private final String libelle;
    private final String description;
    private final Integer noteMaximale;
    private final Integer nombreErreursMaximales;
    
    TypeExamen(String libelle, String description, Integer noteMaximale, Integer nombreErreursMaximales) {
        this.libelle = libelle;
        this.description = description;
        this.noteMaximale = noteMaximale;
        this.nombreErreursMaximales = nombreErreursMaximales;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Integer getNoteMaximale() {
        return noteMaximale;
    }
    
    public Integer getNombreErreursMaximales() {
        return nombreErreursMaximales;
    }
    
    /**
     * Vérifie si l'examen est théorique
     */
    public boolean isTheorique() {
        return this == CODE_ROUTE;
    }
    
    /**
     * Vérifie si l'examen est pratique
     */
    public boolean isPratique() {
        return this != CODE_ROUTE;
    }
    
    /**
     * Vérifie si l'examen est obligatoire
     */
    public boolean isObligatoire() {
        return this == CODE_ROUTE || this == CONDUITE_PRATIQUE;
    }
    
    /**
     * Vérifie si l'examen est optionnel
     */
    public boolean isOptionnel() {
        return !isObligatoire();
    }
    
    /**
     * Vérifie si l'examen est en extérieur
     */
    public boolean isExterieur() {
        return this.isPratique();
    }
    
    /**
     * Vérifie si l'examen est en intérieur
     */
    public boolean isInterieur() {
        return this.isTheorique();
    }
    
    /**
     * Calcule la note minimale pour réussir
     */
    public Double getNoteMinimale() {
        return noteMaximale * 0.7; // 70% pour réussir
    }
    
    /**
     * Vérifie si une note est suffisante pour réussir
     */
    public boolean isNoteSuffisante(Double note) {
        return note != null && note >= getNoteMinimale();
    }
    
    /**
     * Vérifie si le nombre d'erreurs est acceptable
     */
    public boolean isNombreErreursAcceptable(Integer nombreErreurs) {
        return nombreErreurs != null && nombreErreurs <= nombreErreursMaximales;
    }
}
