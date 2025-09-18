package ga.dgtt.permis.model;

/**
 * Énumération des types de documents d'examen
 */
public enum TypeDocumentExamen {
    PROCES_VERBAL("Procès-verbal", "Procès-verbal d'examen", true),
    PHOTO_CANDIDAT("Photo candidat", "Photo du candidat pendant l'examen", true),
    PHOTO_EXAMINATEUR("Photo examinateur", "Photo de l'examinateur", false),
    VIDEO_EXAMEN("Vidéo examen", "Enregistrement vidéo de l'examen", false),
    SIGNATURE_CANDIDAT("Signature candidat", "Signature numérique du candidat", true),
    SIGNATURE_EXAMINATEUR("Signature examinateur", "Signature numérique de l'examinateur", true),
    RAPPORT_TECHNIQUE("Rapport technique", "Rapport technique de l'examen", false),
    AUTRES("Autres documents", "Autres documents liés à l'examen", false);
    
    private final String libelle;
    private final String description;
    private final boolean obligatoire;
    
    TypeDocumentExamen(String libelle, String description, boolean obligatoire) {
        this.libelle = libelle;
        this.description = description;
        this.obligatoire = obligatoire;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    public String getDescription() {
        return description;
    }
    
    public boolean isObligatoire() {
        return obligatoire;
    }
    
    public boolean isSignature() {
        return this == SIGNATURE_CANDIDAT || this == SIGNATURE_EXAMINATEUR;
    }
    
    public boolean isPhoto() {
        return this == PHOTO_CANDIDAT || this == PHOTO_EXAMINATEUR;
    }
    
    public boolean isVideo() {
        return this == VIDEO_EXAMEN;
    }
}
