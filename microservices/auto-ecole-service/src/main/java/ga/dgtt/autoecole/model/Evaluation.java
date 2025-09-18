package ga.dgtt.autoecole.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Entité représentant une évaluation d'un candidat
 * 
 * Chaque candidat doit passer 3 évaluations :
 * - Code de la route (3 passages)
 * - Créneau (3 passages)
 * - Conduite en ville (3 passages)
 */
@Entity
@Table(name = "evaluations")
@EntityListeners(AuditingEntityListener.class)
public class Evaluation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Le candidat est obligatoire")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidat_id", nullable = false)
    private Candidat candidat;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type_evaluation", nullable = false)
    private TypeEvaluation typeEvaluation;
    
    @Min(value = 1, message = "Le numéro de passage doit être au moins 1")
    @Max(value = 3, message = "Le numéro de passage ne peut pas dépasser 3")
    @Column(name = "numero_passage", nullable = false)
    private Integer numeroPassage;
    
    @NotNull(message = "La date d'évaluation est obligatoire")
    @Column(name = "date_evaluation", nullable = false)
    private LocalDateTime dateEvaluation;
    
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
    
    @CreatedDate
    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;
    
    @LastModifiedDate
    @Column(name = "date_modification")
    private LocalDateTime dateModification;
    
    // Constructeurs
    public Evaluation() {}
    
    public Evaluation(Candidat candidat, TypeEvaluation typeEvaluation, Integer numeroPassage, 
                     String examinateurNom, String examinateurPrenom) {
        this.candidat = candidat;
        this.typeEvaluation = typeEvaluation;
        this.numeroPassage = numeroPassage;
        this.examinateurNom = examinateurNom;
        this.examinateurPrenom = examinateurPrenom;
        this.dateEvaluation = LocalDateTime.now();
    }
    
    // Méthodes utilitaires
    public String getNomCompletExaminateur() {
        return examinateurPrenom + " " + examinateurNom;
    }
    
    public boolean isComplete() {
        return note != null && estReussi != null;
    }
    
    public boolean isReussi() {
        return estReussi != null && estReussi;
    }
    
    public boolean isEchec() {
        return estReussi != null && !estReussi;
    }
    
    public String getStatutEvaluation() {
        if (estReussi == null) {
            return "En cours";
        }
        return estReussi ? "Réussi" : "Échec";
    }
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Candidat getCandidat() { return candidat; }
    public void setCandidat(Candidat candidat) { this.candidat = candidat; }
    
    public TypeEvaluation getTypeEvaluation() { return typeEvaluation; }
    public void setTypeEvaluation(TypeEvaluation typeEvaluation) { this.typeEvaluation = typeEvaluation; }
    
    public Integer getNumeroPassage() { return numeroPassage; }
    public void setNumeroPassage(Integer numeroPassage) { this.numeroPassage = numeroPassage; }
    
    public LocalDateTime getDateEvaluation() { return dateEvaluation; }
    public void setDateEvaluation(LocalDateTime dateEvaluation) { this.dateEvaluation = dateEvaluation; }
    
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
    
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
    
    public LocalDateTime getDateModification() { return dateModification; }
    public void setDateModification(LocalDateTime dateModification) { this.dateModification = dateModification; }
}
