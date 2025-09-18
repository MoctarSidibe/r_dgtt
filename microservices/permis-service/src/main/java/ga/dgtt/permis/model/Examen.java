package ga.dgtt.permis.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entité représentant un examen de permis de conduire
 * 
 * Cette entité gère les examens finaux des candidats
 * après validation de leur dossier par SAF.
 */
@Entity
@Table(name = "examens")
@EntityListeners(AuditingEntityListener.class)
public class Examen {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le numéro d'examen est obligatoire")
    @Size(max = 100, message = "Le numéro d'examen ne peut pas dépasser 100 caractères")
    @Column(name = "numero_examen", unique = true)
    private String numeroExamen;
    
    @NotNull(message = "Le candidat est obligatoire")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidat_id", nullable = false)
    private Candidat candidat;
    
    @NotNull(message = "L'auto-école est obligatoire")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auto_ecole_id", nullable = false)
    private AutoEcole autoEcole;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type_examen", nullable = false)
    private TypeExamen typeExamen;
    
    @NotNull(message = "La date d'examen est obligatoire")
    @Column(name = "date_examen", nullable = false)
    private LocalDateTime dateExamen;
    
    @NotBlank(message = "Le lieu d'examen est obligatoire")
    @Size(max = 255, message = "Le lieu d'examen ne peut pas dépasser 255 caractères")
    @Column(name = "lieu_examen", nullable = false)
    private String lieuExamen;
    
    @NotBlank(message = "Le nom de l'examinateur est obligatoire")
    @Size(max = 255, message = "Le nom de l'examinateur ne peut pas dépasser 255 caractères")
    @Column(name = "examinateur_nom", nullable = false)
    private String examinateurNom;
    
    @NotBlank(message = "Le prénom de l'examinateur est obligatoire")
    @Size(max = 255, message = "Le prénom de l'examinateur ne peut pas dépasser 255 caractères")
    @Column(name = "examinateur_prenom", nullable = false)
    private String examinateurPrenom;
    
    @Column(name = "examinateur_matricule")
    private String examinateurMatricule;
    
    @Min(value = 0, message = "La note ne peut pas être négative")
    @Max(value = 20, message = "La note ne peut pas dépasser 20")
    @Column(name = "note")
    private Double note;
    
    @Min(value = 0, message = "Le nombre d'erreurs ne peut pas être négatif")
    @Column(name = "nombre_erreurs")
    private Integer nombreErreurs = 0;
    
    @Column(name = "temps_realise")
    private Integer tempsRealise; // en minutes
    
    @Column(name = "est_reussi")
    private Boolean estReussi = false;
    
    @Column(name = "commentaires", length = 1000)
    private String commentaires;
    
    @Column(name = "qr_code")
    private String qrCode;
    
    @Column(name = "proces_verbal_url")
    private String procesVerbalUrl;
    
    @Column(name = "signature_examinateur")
    private String signatureExaminateur;
    
    @Column(name = "signature_candidat")
    private String signatureCandidat;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutExamen statut = StatutExamen.PROGRAMME;
    
    @CreatedDate
    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;
    
    @LastModifiedDate
    @Column(name = "date_modification")
    private LocalDateTime dateModification;
    
    @OneToMany(mappedBy = "examen", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DocumentExamen> documents = new ArrayList<>();
    
    @OneToMany(mappedBy = "examen", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AuditLog> auditLogs = new ArrayList<>();
    
    // Constructeurs
    public Examen() {}
    
    public Examen(String numeroExamen, Candidat candidat, AutoEcole autoEcole, 
                  TypeExamen typeExamen, LocalDateTime dateExamen, String lieuExamen,
                  String examinateurNom, String examinateurPrenom) {
        this.numeroExamen = numeroExamen;
        this.candidat = candidat;
        this.autoEcole = autoEcole;
        this.typeExamen = typeExamen;
        this.dateExamen = dateExamen;
        this.lieuExamen = lieuExamen;
        this.examinateurNom = examinateurNom;
        this.examinateurPrenom = examinateurPrenom;
    }
    
    // Méthodes utilitaires
    public String getNomCompletExaminateur() {
        return examinateurPrenom + " " + examinateurNom;
    }
    
    public String getNomCompletCandidat() {
        return candidat.getPrenom() + " " + candidat.getNom();
    }
    
    public boolean isComplete() {
        return note != null && estReussi != null && procesVerbalUrl != null;
    }
    
    public boolean isReussi() {
        return estReussi != null && estReussi;
    }
    
    public boolean isEchec() {
        return estReussi != null && !estReussi;
    }
    
    public String getStatutExamen() {
        if (estReussi == null) {
            return "En cours";
        }
        return estReussi ? "Réussi" : "Échec";
    }
    
    public boolean peutEtreValide() {
        return statut == StatutExamen.TERMINE && isComplete();
    }
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNumeroExamen() { return numeroExamen; }
    public void setNumeroExamen(String numeroExamen) { this.numeroExamen = numeroExamen; }
    
    public Candidat getCandidat() { return candidat; }
    public void setCandidat(Candidat candidat) { this.candidat = candidat; }
    
    public AutoEcole getAutoEcole() { return autoEcole; }
    public void setAutoEcole(AutoEcole autoEcole) { this.autoEcole = autoEcole; }
    
    public TypeExamen getTypeExamen() { return typeExamen; }
    public void setTypeExamen(TypeExamen typeExamen) { this.typeExamen = typeExamen; }
    
    public LocalDateTime getDateExamen() { return dateExamen; }
    public void setDateExamen(LocalDateTime dateExamen) { this.dateExamen = dateExamen; }
    
    public String getLieuExamen() { return lieuExamen; }
    public void setLieuExamen(String lieuExamen) { this.lieuExamen = lieuExamen; }
    
    public String getExaminateurNom() { return examinateurNom; }
    public void setExaminateurNom(String examinateurNom) { this.examinateurNom = examinateurNom; }
    
    public String getExaminateurPrenom() { return examinateurPrenom; }
    public void setExaminateurPrenom(String examinateurPrenom) { this.examinateurPrenom = examinateurPrenom; }
    
    public String getExaminateurMatricule() { return examinateurMatricule; }
    public void setExaminateurMatricule(String examinateurMatricule) { this.examinateurMatricule = examinateurMatricule; }
    
    public Double getNote() { return note; }
    public void setNote(Double note) { this.note = note; }
    
    public Integer getNombreErreurs() { return nombreErreurs; }
    public void setNombreErreurs(Integer nombreErreurs) { this.nombreErreurs = nombreErreurs; }
    
    public Integer getTempsRealise() { return tempsRealise; }
    public void setTempsRealise(Integer tempsRealise) { this.tempsRealise = tempsRealise; }
    
    public Boolean getEstReussi() { return estReussi; }
    public void setEstReussi(Boolean estReussi) { this.estReussi = estReussi; }
    
    public String getCommentaires() { return commentaires; }
    public void setCommentaires(String commentaires) { this.commentaires = commentaires; }
    
    public String getQrCode() { return qrCode; }
    public void setQrCode(String qrCode) { this.qrCode = qrCode; }
    
    public String getProcesVerbalUrl() { return procesVerbalUrl; }
    public void setProcesVerbalUrl(String procesVerbalUrl) { this.procesVerbalUrl = procesVerbalUrl; }
    
    public String getSignatureExaminateur() { return signatureExaminateur; }
    public void setSignatureExaminateur(String signatureExaminateur) { this.signatureExaminateur = signatureExaminateur; }
    
    public String getSignatureCandidat() { return signatureCandidat; }
    public void setSignatureCandidat(String signatureCandidat) { this.signatureCandidat = signatureCandidat; }
    
    public StatutExamen getStatut() { return statut; }
    public void setStatut(StatutExamen statut) { this.statut = statut; }
    
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
    
    public LocalDateTime getDateModification() { return dateModification; }
    public void setDateModification(LocalDateTime dateModification) { this.dateModification = dateModification; }
    
    public List<DocumentExamen> getDocuments() { return documents; }
    public void setDocuments(List<DocumentExamen> documents) { this.documents = documents; }
    
    public List<AuditLog> getAuditLogs() { return auditLogs; }
    public void setAuditLogs(List<AuditLog> auditLogs) { this.auditLogs = auditLogs; }
}
