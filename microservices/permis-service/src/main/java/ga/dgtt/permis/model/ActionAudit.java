package ga.dgtt.permis.model;

/**
 * Énumération des actions d'audit pour le service Permis
 */
public enum ActionAudit {
    CREATION("Création", "Création d'une nouvelle entité"),
    LECTURE("Lecture", "Consultation d'une entité"),
    MODIFICATION("Modification", "Modification d'une entité"),
    SUPPRESSION("Suppression", "Suppression d'une entité"),
    PROGRAMMATION_EXAMEN("Programmation examen", "Programmation d'un examen"),
    DEBUT_EXAMEN("Début examen", "Début d'un examen"),
    FIN_EXAMEN("Fin examen", "Fin d'un examen"),
    VALIDATION_EXAMEN("Validation examen", "Validation d'un examen"),
    GENERATION_PROCES_VERBAL("Génération procès-verbal", "Génération d'un procès-verbal"),
    SIGNATURE_NUMERIQUE("Signature numérique", "Signature numérique d'un document"),
    ENVOI_STIAS("Envoi STIAS", "Envoi du dossier à STIAS"),
    NOTIFICATION("Notification", "Envoi d'une notification"),
    ERREUR_SYSTEME("Erreur système", "Erreur système");
    
    private final String libelle;
    private final String description;
    
    ActionAudit(String libelle, String description) {
        this.libelle = libelle;
        this.description = description;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    public String getDescription() {
        return description;
    }
    
    public boolean isCritique() {
        return this == SUPPRESSION || this == SIGNATURE_NUMERIQUE || this == ERREUR_SYSTEME;
    }
    
    public boolean isModification() {
        return this == MODIFICATION || this == VALIDATION_EXAMEN || this == SIGNATURE_NUMERIQUE;
    }
    
    public boolean isCreation() {
        return this == CREATION || this == PROGRAMMATION_EXAMEN || this == GENERATION_PROCES_VERBAL;
    }
}
