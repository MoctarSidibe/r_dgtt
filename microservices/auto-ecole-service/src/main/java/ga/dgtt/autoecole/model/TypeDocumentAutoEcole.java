package ga.dgtt.autoecole.model;

/**
 * Énumération des types de documents requis pour une auto-école
 * 
 * Cette énumération définit tous les types de documents qui doivent être
 * fournis lors de la demande d'ouverture d'une auto-école.
 */
public enum TypeDocumentAutoEcole {
    // Documents d'identité
    PIECE_IDENTITE_PROPRIETAIRE("Pièce d'identité du propriétaire", "Carte nationale d'identité ou passeport", true),
    ATTESTATION_RESIDENCE("Attestation de résidence", "Attestation de résidence du propriétaire", true),
    
    // Documents administratifs
    REGISTRE_COMMERCE("Registre de commerce", "Extrait du registre de commerce", true),
    STATUTS_SOCIETE("Statuts de la société", "Statuts de la société si applicable", false),
    NIF("Numéro d'Identification Fiscale", "NIF de l'auto-école", true),
    
    // Documents techniques
    PLAN_LOCAL("Plan du local", "Plan détaillé du local d'exploitation", true),
    ATTESTATION_LOCAL("Attestation de propriété/location", "Attestation de propriété ou contrat de location", true),
    CERTIFICAT_CONFORMITE("Certificat de conformité", "Certificat de conformité du local", true),
    
    // Documents de formation
    DIPLOMES_FORMATEURS("Diplômes des formateurs", "Copies des diplômes des formateurs", true),
    ATTESTATIONS_FORMATION("Attestations de formation", "Attestations de formation des formateurs", true),
    EXPERIENCE_PROFESSIONNELLE("Expérience professionnelle", "Attestations d'expérience professionnelle", true),
    
    // Documents de sécurité
    ATTESTATION_SECURITE("Attestation de sécurité", "Attestation de sécurité du local", true),
    ASSURANCE_RESPONSABILITE("Assurance responsabilité civile", "Police d'assurance responsabilité civile", true),
    
    // Documents de matériel
    INVENTAIRE_VEHICULES("Inventaire des véhicules", "Liste des véhicules d'enseignement", true),
    CERTIFICATS_VEHICULES("Certificats des véhicules", "Certificats de conformité des véhicules", true),
    PERMIS_CONDUIRE_FORMATEURS("Permis de conduire des formateurs", "Copies des permis de conduire des formateurs", true),
    
    // Documents financiers
    JUSTIFICATIFS_FINANCIERS("Justificatifs financiers", "Relevés bancaires et justificatifs financiers", true),
    BUDGET_EXPLOITATION("Budget d'exploitation", "Budget prévisionnel d'exploitation", false),
    
    // Autres documents
    AUTRES("Autres documents", "Autres documents requis", false);
    
    private final String libelle;
    private final String description;
    private final boolean obligatoire;
    
    TypeDocumentAutoEcole(String libelle, String description, boolean obligatoire) {
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
        return this == PIECE_IDENTITE_PROPRIETAIRE || this == ATTESTATION_RESIDENCE;
    }
    
    /**
     * Vérifie si le document est administratif
     */
    public boolean isAdministratif() {
        return this == REGISTRE_COMMERCE || this == STATUTS_SOCIETE || this == NIF;
    }
    
    /**
     * Vérifie si le document est technique
     */
    public boolean isTechnique() {
        return this == PLAN_LOCAL || this == ATTESTATION_LOCAL || this == CERTIFICAT_CONFORMITE;
    }
    
    /**
     * Vérifie si le document est lié à la formation
     */
    public boolean isFormation() {
        return this == DIPLOMES_FORMATEURS || this == ATTESTATIONS_FORMATION || 
               this == EXPERIENCE_PROFESSIONNELLE || this == PERMIS_CONDUIRE_FORMATEURS;
    }
    
    /**
     * Vérifie si le document est lié à la sécurité
     */
    public boolean isSecurite() {
        return this == ATTESTATION_SECURITE || this == ASSURANCE_RESPONSABILITE;
    }
    
    /**
     * Vérifie si le document est lié au matériel
     */
    public boolean isMateriel() {
        return this == INVENTAIRE_VEHICULES || this == CERTIFICATS_VEHICULES;
    }
    
    /**
     * Vérifie si le document est financier
     */
    public boolean isFinancier() {
        return this == JUSTIFICATIFS_FINANCIERS || this == BUDGET_EXPLOITATION;
    }
}
