package ga.dgtt.autoecole.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Entité représentant un document d'un candidat
 * 
 * Cette entité stocke les informations sur les documents joints
 * par un candidat lors de son enrôlement.
 */
@Entity
@Table(name = "documents_candidat")
@EntityListeners(AuditingEntityListener.class)
public class DocumentCandidat {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Le candidat est obligatoire")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidat_id", nullable = false)
    private Candidat candidat;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type_document", nullable = false)
    private TypeDocumentCandidat typeDocument;
    
    @NotBlank(message = "Le nom du fichier est obligatoire")
    @Size(max = 255, message = "Le nom du fichier ne peut pas dépasser 255 caractères")
    @Column(name = "nom_fichier", nullable = false)
    private String nomFichier;
    
    @NotBlank(message = "L'URL du fichier est obligatoire")
    @Size(max = 500, message = "L'URL du fichier ne peut pas dépasser 500 caractères")
    @Column(name = "url_fichier", nullable = false, length = 500)
    private String urlFichier;
    
    @Size(max = 50, message = "Le type MIME ne peut pas dépasser 50 caractères")
    @Column(name = "type_mime")
    private String typeMime;
    
    @Column(name = "taille_fichier")
    private Long tailleFichier;
    
    @Column(name = "hash_fichier")
    private String hashFichier;
    
    @Column(name = "qr_code")
    private String qrCode;
    
    @Column(name = "est_valide")
    private Boolean estValide = false;
    
    @Column(name = "date_validation")
    private LocalDateTime dateValidation;
    
    @Size(max = 255, message = "Le validateur ne peut pas dépasser 255 caractères")
    @Column(name = "validateur")
    private String validateur;
    
    @Column(name = "commentaires", length = 1000)
    private String commentaires;
    
    @CreatedDate
    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;
    
    @LastModifiedDate
    @Column(name = "date_modification")
    private LocalDateTime dateModification;
    
    // Constructeurs
    public DocumentCandidat() {}
    
    public DocumentCandidat(Candidat candidat, TypeDocumentCandidat typeDocument, 
                           String nomFichier, String urlFichier) {
        this.candidat = candidat;
        this.typeDocument = typeDocument;
        this.nomFichier = nomFichier;
        this.urlFichier = urlFichier;
    }
    
    // Méthodes utilitaires
    public boolean isValide() {
        return estValide != null && estValide;
    }
    
    public boolean isEnAttenteValidation() {
        return estValide == null || !estValide;
    }
    
    public String getExtensionFichier() {
        if (nomFichier != null && nomFichier.contains(".")) {
            return nomFichier.substring(nomFichier.lastIndexOf(".") + 1).toLowerCase();
        }
        return "";
    }
    
    public boolean isImage() {
        String extension = getExtensionFichier();
        return extension.equals("jpg") || extension.equals("jpeg") || 
               extension.equals("png") || extension.equals("gif");
    }
    
    public boolean isPdf() {
        return getExtensionFichier().equals("pdf");
    }
    
    public boolean isDocument() {
        String extension = getExtensionFichier();
        return extension.equals("doc") || extension.equals("docx") || 
               extension.equals("pdf") || extension.equals("txt");
    }
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Candidat getCandidat() { return candidat; }
    public void setCandidat(Candidat candidat) { this.candidat = candidat; }
    
    public TypeDocumentCandidat getTypeDocument() { return typeDocument; }
    public void setTypeDocument(TypeDocumentCandidat typeDocument) { this.typeDocument = typeDocument; }
    
    public String getNomFichier() { return nomFichier; }
    public void setNomFichier(String nomFichier) { this.nomFichier = nomFichier; }
    
    public String getUrlFichier() { return urlFichier; }
    public void setUrlFichier(String urlFichier) { this.urlFichier = urlFichier; }
    
    public String getTypeMime() { return typeMime; }
    public void setTypeMime(String typeMime) { this.typeMime = typeMime; }
    
    public Long getTailleFichier() { return tailleFichier; }
    public void setTailleFichier(Long tailleFichier) { this.tailleFichier = tailleFichier; }
    
    public String getHashFichier() { return hashFichier; }
    public void setHashFichier(String hashFichier) { this.hashFichier = hashFichier; }
    
    public String getQrCode() { return qrCode; }
    public void setQrCode(String qrCode) { this.qrCode = qrCode; }
    
    public Boolean getEstValide() { return estValide; }
    public void setEstValide(Boolean estValide) { this.estValide = estValide; }
    
    public LocalDateTime getDateValidation() { return dateValidation; }
    public void setDateValidation(LocalDateTime dateValidation) { this.dateValidation = dateValidation; }
    
    public String getValidateur() { return validateur; }
    public void setValidateur(String validateur) { this.validateur = validateur; }
    
    public String getCommentaires() { return commentaires; }
    public void setCommentaires(String commentaires) { this.commentaires = commentaires; }
    
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
    
    public LocalDateTime getDateModification() { return dateModification; }
    public void setDateModification(LocalDateTime dateModification) { this.dateModification = dateModification; }
}
