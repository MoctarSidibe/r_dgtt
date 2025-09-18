package ga.dgtt.autres.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Entité représentant un partenaire externe
 * 
 * Cette entité gère les partenaires externes qui interagissent
 * avec le système R-DGTT.
 */
@Entity
@Table(name = "partenaires_externes")
@EntityListeners(AuditingEntityListener.class)
public class PartenaireExterne {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le nom du partenaire est obligatoire")
    @Size(max = 255, message = "Le nom ne peut pas dépasser 255 caractères")
    @Column(name = "nom", nullable = false)
    private String nom;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type_partenaire", nullable = false)
    private TypePartenaire typePartenaire;
    
    @NotBlank(message = "L'URL de l'API est obligatoire")
    @Size(max = 500, message = "L'URL ne peut pas dépasser 500 caractères")
    @Column(name = "api_url", nullable = false, length = 500)
    private String apiUrl;
    
    @NotBlank(message = "La clé API est obligatoire")
    @Size(max = 255, message = "La clé API ne peut pas dépasser 255 caractères")
    @Column(name = "api_key", nullable = false)
    private String apiKey;
    
    @Column(name = "api_secret")
    private String apiSecret;
    
    @NotBlank(message = "L'email de contact est obligatoire")
    @Email(message = "L'email doit être valide")
    @Column(name = "email_contact", nullable = false)
    private String emailContact;
    
    @NotBlank(message = "Le téléphone de contact est obligatoire")
    @Pattern(regexp = "^\\+241[0-9]{8}$", message = "Le téléphone doit être au format +241XXXXXXXX")
    @Column(name = "telephone_contact", nullable = false)
    private String telephoneContact;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutPartenaire statut = StatutPartenaire.ACTIF;
    
    @Column(name = "derniere_synchronisation")
    private LocalDateTime derniereSynchronisation;
    
    @Column(name = "nombre_requetes")
    private Long nombreRequetes = 0L;
    
    @Column(name = "nombre_erreurs")
    private Long nombreErreurs = 0L;
    
    @Column(name = "timeout_ms")
    private Integer timeoutMs = 30000;
    
    @Column(name = "retry_attempts")
    private Integer retryAttempts = 3;
    
    @Column(name = "notes", length = 1000)
    private String notes;
    
    @CreatedDate
    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;
    
    @LastModifiedDate
    @Column(name = "date_modification")
    private LocalDateTime dateModification;
    
    // Constructeurs
    public PartenaireExterne() {}
    
    public PartenaireExterne(String nom, TypePartenaire typePartenaire, String apiUrl,
                           String apiKey, String emailContact, String telephoneContact) {
        this.nom = nom;
        this.typePartenaire = typePartenaire;
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
        this.emailContact = emailContact;
        this.telephoneContact = telephoneContact;
    }
    
    // Méthodes utilitaires
    public boolean estActif() {
        return statut == StatutPartenaire.ACTIF;
    }
    
    public boolean estSuspendu() {
        return statut == StatutPartenaire.SUSPENDU;
    }
    
    public void incrementerRequetes() {
        this.nombreRequetes++;
    }
    
    public void incrementerErreurs() {
        this.nombreErreurs++;
    }
    
    public double getTauxErreur() {
        if (nombreRequetes == 0) return 0.0;
        return (double) nombreErreurs / nombreRequetes * 100;
    }
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public TypePartenaire getTypePartenaire() { return typePartenaire; }
    public void setTypePartenaire(TypePartenaire typePartenaire) { this.typePartenaire = typePartenaire; }
    
    public String getApiUrl() { return apiUrl; }
    public void setApiUrl(String apiUrl) { this.apiUrl = apiUrl; }
    
    public String getApiKey() { return apiKey; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }
    
    public String getApiSecret() { return apiSecret; }
    public void setApiSecret(String apiSecret) { this.apiSecret = apiSecret; }
    
    public String getEmailContact() { return emailContact; }
    public void setEmailContact(String emailContact) { this.emailContact = emailContact; }
    
    public String getTelephoneContact() { return telephoneContact; }
    public void setTelephoneContact(String telephoneContact) { this.telephoneContact = telephoneContact; }
    
    public StatutPartenaire getStatut() { return statut; }
    public void setStatut(StatutPartenaire statut) { this.statut = statut; }
    
    public LocalDateTime getDerniereSynchronisation() { return derniereSynchronisation; }
    public void setDerniereSynchronisation(LocalDateTime derniereSynchronisation) { this.derniereSynchronisation = derniereSynchronisation; }
    
    public Long getNombreRequetes() { return nombreRequetes; }
    public void setNombreRequetes(Long nombreRequetes) { this.nombreRequetes = nombreRequetes; }
    
    public Long getNombreErreurs() { return nombreErreurs; }
    public void setNombreErreurs(Long nombreErreurs) { this.nombreErreurs = nombreErreurs; }
    
    public Integer getTimeoutMs() { return timeoutMs; }
    public void setTimeoutMs(Integer timeoutMs) { this.timeoutMs = timeoutMs; }
    
    public Integer getRetryAttempts() { return retryAttempts; }
    public void setRetryAttempts(Integer retryAttempts) { this.retryAttempts = retryAttempts; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
    
    public LocalDateTime getDateModification() { return dateModification; }
    public void setDateModification(LocalDateTime dateModification) { this.dateModification = dateModification; }
}
