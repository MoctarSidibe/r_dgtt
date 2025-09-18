package ga.dgtt.autoecole.model;

/**
 * Énumération des types de documents requis pour un candidat
 * 
 * Cette énumération définit tous les types de documents qui doivent être
 * fournis par un candidat lors de son enrôlement.
 */
public enum TypeDocumentCandidat {
    // Documents d'identité
    PHOTO_IDENTITE("Photo d'identité", "Photo d'identité récente du candidat", true),
    PIECE_IDENTITE("Pièce d'identité", "Carte nationale d'identité ou passeport", true),
    ATTESTATION_RESIDENCE("Attestation de résidence", "Attestation de résidence du candidat", true),
    
    // Documents médicaux
    CERTIFICAT_MEDICAL("Certificat médical", "Certificat médical de non contre-indication à la conduite", true),
    EXAMEN_OPHTALMOLOGIQUE("Examen ophtalmologique", "Examen ophtalmologique récent", true),
    
    // Documents de formation
    ATTESTATION_FORMATION("Attestation de formation", "Attestation de formation à la conduite", false),
    CERTIFICAT_CODE("Certificat de code", "Certificat de réussite au code de la route", false),
    
    // Documents administratifs
    FICHE_ENROLEMENT("Fiche d'enrôlement", "Fiche d'enrôlement remplie et signée", true),
    ENGAGEMENT_CANDIDAT("Engagement du candidat", "Engagement du candidat à respecter les règles", true),
    
    // Documents de paiement
    JUSTIFICATIF_PAIEMENT("Justificatif de paiement", "Justificatif de paiement des frais", true),
    QUITTANCE("Quittance", "Quittance de paiement", false),
    
    // Documents spécifiques
    AUTORISATION_PARENTS("Autorisation des parents", "Autorisation des parents pour les mineurs", false),
    ATTESTATION_SCOLARITE("Attestation de scolarité", "Attestation de scolarité pour les étudiants", false),
    
    // Autres documents
    AUTRES("Autres documents", "Autres documents requis", false);
    
    private final String libelle;
    private final String description;
    private final boolean obligatoire;
    
    TypeDocumentCandidat(String libelle, String description, boolean obligatoire) {
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
    
    /**
     * Vérifie si le document est lié à l'identité
     */
    public boolean isIdentite() {
        return this == PHOTO_IDENTITE || this == PIECE_IDENTITE || this == ATTESTATION_RESIDENCE;
    }
    
    /**
     * Vérifie si le document est médical
     */
    public boolean isMedical() {
        return this == CERTIFICAT_MEDICAL || this == EXAMEN_OPHTALMOLOGIQUE;
    }
    
    /**
     * Vérifie si le document est lié à la formation
     */
    public boolean isFormation() {
        return this == ATTESTATION_FORMATION || this == CERTIFICAT_CODE;
    }
    
    /**
     * Vérifie si le document est administratif
     */
    public boolean isAdministratif() {
        return this == FICHE_ENROLEMENT || this == ENGAGEMENT_CANDIDAT;
    }
    
    /**
     * Vérifie si le document est lié au paiement
     */
    public boolean isPaiement() {
        return this == JUSTIFICATIF_PAIEMENT || this == QUITTANCE;
    }
    
    /**
     * Vérifie si le document est spécifique aux mineurs
     */
    public boolean isMineur() {
        return this == AUTORISATION_PARENTS || this == ATTESTATION_SCOLARITE;
    }
    
    /**
     * Vérifie si le document est requis pour tous les candidats
     */
    public boolean isRequisTous() {
        return this == PHOTO_IDENTITE || this == PIECE_IDENTITE || this == ATTESTATION_RESIDENCE ||
               this == CERTIFICAT_MEDICAL || this == EXAMEN_OPHTALMOLOGIQUE ||
               this == FICHE_ENROLEMENT || this == ENGAGEMENT_CANDIDAT ||
               this == JUSTIFICATIF_PAIEMENT;
    }
}
