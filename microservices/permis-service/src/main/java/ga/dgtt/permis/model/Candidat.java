package ga.dgtt.permis.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entité représentant un candidat au permis de conduire
 * (Référence vers le service Auto-École)
 */
@Entity
@Table(name = "candidats")
@EntityListeners(AuditingEntityListener.class)
public class Candidat {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 255, message = "Le nom ne peut pas dépasser 255 caractères")
    @Column(name = "nom", nullable = false)
    private String nom;
    
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 255, message = "Le prénom ne peut pas dépasser 255 caractères")
    @Column(name = "prenom", nullable = false)
    private String prenom;
    
    @NotNull(message = "La date de naissance est obligatoire")
    @Past(message = "La date de naissance doit être dans le passé")
    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;
    
    @NotBlank(message = "Le lieu de naissance est obligatoire")
    @Size(max = 255, message = "Le lieu de naissance ne peut pas dépasser 255 caractères")
    @Column(name = "lieu_naissance", nullable = false)
    private String lieuNaissance;
    
    @NotBlank(message = "La nationalité est obligatoire")
    @Size(max = 100, message = "La nationalité ne peut pas dépasser 100 caractères")
    @Column(name = "nationalite", nullable = false)
    private String nationalite = "Gabonaise";
    
    @NotBlank(message = "La catégorie de permis est obligatoire")
    @Pattern(regexp = "^[A-G]$", message = "La catégorie doit être A, B, C, D, E, F, ou G")
    @Column(name = "categorie_permis", nullable = false)
    private String categoriePermis;
    
    @Column(name = "numero_licence", unique = true)
    private String numeroLicence;
    
    @Column(name = "numero_evaluation", unique = true)
    private String numeroEvaluation;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutCandidat statut = StatutCandidat.ENROLE;
    
    @Column(name = "montant_paiement")
    private Double montantPaiement;
    
    @Column(name = "date_paiement")
    private LocalDateTime datePaiement;
    
    @Column(name = "reference_paiement")
    private String referencePaiement;
    
    @Column(name = "qr_code")
    private String qrCode;
    
    @Column(name = "photo_url")
    private String photoUrl;
    
    @Column(name = "piece_identite_url")
    private String pieceIdentiteUrl;
    
    @Column(name = "certificat_medical_url")
    private String certificatMedicalUrl;
    
    @Column(name = "attestation_residence_url")
    private String attestationResidenceUrl;
    
    @Column(name = "notes", length = 1000)
    private String notes;
    
    @CreatedDate
    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;
    
    @LastModifiedDate
    @Column(name = "date_modification")
    private LocalDateTime dateModification;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auto_ecole_id", nullable = false)
    private AutoEcole autoEcole;
    
    @OneToMany(mappedBy = "candidat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Examen> examens = new ArrayList<>();
    
    @OneToMany(mappedBy = "candidat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AuditLog> auditLogs = new ArrayList<>();
    
    // Constructeurs
    public Candidat() {}
    
    public Candidat(String nom, String prenom, LocalDate dateNaissance, String lieuNaissance, 
                    String nationalite, String categoriePermis, AutoEcole autoEcole) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.lieuNaissance = lieuNaissance;
        this.nationalite = nationalite;
        this.categoriePermis = categoriePermis;
        this.autoEcole = autoEcole;
    }
    
    // Méthodes utilitaires
    public String getNomComplet() {
        return prenom + " " + nom;
    }
    
    public int getAge() {
        return LocalDate.now().getYear() - dateNaissance.getYear();
    }
    
    public boolean isPaiementEffectue() {
        return datePaiement != null && referencePaiement != null;
    }
    
    public boolean isDocumentsComplets() {
        return photoUrl != null && pieceIdentiteUrl != null && 
               certificatMedicalUrl != null && attestationResidenceUrl != null;
    }
    
    public boolean peutPasserExamen() {
        return statut == StatutCandidat.DOSSIER_VALIDE && isPaiementEffectue() && isDocumentsComplets();
    }
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    
    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }
    
    public String getLieuNaissance() { return lieuNaissance; }
    public void setLieuNaissance(String lieuNaissance) { this.lieuNaissance = lieuNaissance; }
    
    public String getNationalite() { return nationalite; }
    public void setNationalite(String nationalite) { this.nationalite = nationalite; }
    
    public String getCategoriePermis() { return categoriePermis; }
    public void setCategoriePermis(String categoriePermis) { this.categoriePermis = categoriePermis; }
    
    public String getNumeroLicence() { return numeroLicence; }
    public void setNumeroLicence(String numeroLicence) { this.numeroLicence = numeroLicence; }
    
    public String getNumeroEvaluation() { return numeroEvaluation; }
    public void setNumeroEvaluation(String numeroEvaluation) { this.numeroEvaluation = numeroEvaluation; }
    
    public StatutCandidat getStatut() { return statut; }
    public void setStatut(StatutCandidat statut) { this.statut = statut; }
    
    public Double getMontantPaiement() { return montantPaiement; }
    public void setMontantPaiement(Double montantPaiement) { this.montantPaiement = montantPaiement; }
    
    public LocalDateTime getDatePaiement() { return datePaiement; }
    public void setDatePaiement(LocalDateTime datePaiement) { this.datePaiement = datePaiement; }
    
    public String getReferencePaiement() { return referencePaiement; }
    public void setReferencePaiement(String referencePaiement) { this.referencePaiement = referencePaiement; }
    
    public String getQrCode() { return qrCode; }
    public void setQrCode(String qrCode) { this.qrCode = qrCode; }
    
    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
    
    public String getPieceIdentiteUrl() { return pieceIdentiteUrl; }
    public void setPieceIdentiteUrl(String pieceIdentiteUrl) { this.pieceIdentiteUrl = pieceIdentiteUrl; }
    
    public String getCertificatMedicalUrl() { return certificatMedicalUrl; }
    public void setCertificatMedicalUrl(String certificatMedicalUrl) { this.certificatMedicalUrl = certificatMedicalUrl; }
    
    public String getAttestationResidenceUrl() { return attestationResidenceUrl; }
    public void setAttestationResidenceUrl(String attestationResidenceUrl) { this.attestationResidenceUrl = attestationResidenceUrl; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
    
    public LocalDateTime getDateModification() { return dateModification; }
    public void setDateModification(LocalDateTime dateModification) { this.dateModification = dateModification; }
    
    public AutoEcole getAutoEcole() { return autoEcole; }
    public void setAutoEcole(AutoEcole autoEcole) { this.autoEcole = autoEcole; }
    
    public List<Examen> getExamens() { return examens; }
    public void setExamens(List<Examen> examens) { this.examens = examens; }
    
    public List<AuditLog> getAuditLogs() { return auditLogs; }
    public void setAuditLogs(List<AuditLog> auditLogs) { this.auditLogs = auditLogs; }
}
