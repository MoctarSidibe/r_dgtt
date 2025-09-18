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
 * Entité représentant une auto-école
 * (Référence vers le service Auto-École)
 */
@Entity
@Table(name = "auto_ecoles")
@EntityListeners(AuditingEntityListener.class)
public class AutoEcole {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le nom de l'auto-école est obligatoire")
    @Size(max = 255, message = "Le nom ne peut pas dépasser 255 caractères")
    @Column(name = "nom", nullable = false)
    private String nom;
    
    @NotBlank(message = "Le nom du propriétaire est obligatoire")
    @Size(max = 255, message = "Le nom du propriétaire ne peut pas dépasser 255 caractères")
    @Column(name = "proprietaire_nom", nullable = false)
    private String proprietaireNom;
    
    @NotBlank(message = "Le prénom du propriétaire est obligatoire")
    @Size(max = 255, message = "Le prénom du propriétaire ne peut pas dépasser 255 caractères")
    @Column(name = "proprietaire_prenom", nullable = false)
    private String proprietairePrenom;
    
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @NotBlank(message = "Le téléphone est obligatoire")
    @Pattern(regexp = "^\\+241[0-9]{8}$", message = "Le téléphone doit être au format +241XXXXXXXX")
    @Column(name = "telephone", nullable = false)
    private String telephone;
    
    @NotBlank(message = "L'adresse est obligatoire")
    @Size(max = 500, message = "L'adresse ne peut pas dépasser 500 caractères")
    @Column(name = "adresse", nullable = false, length = 500)
    private String adresse;
    
    @NotBlank(message = "La ville est obligatoire")
    @Size(max = 100, message = "La ville ne peut pas dépasser 100 caractères")
    @Column(name = "ville", nullable = false)
    private String ville;
    
    @NotBlank(message = "La province est obligatoire")
    @Size(max = 100, message = "La province ne peut pas dépasser 100 caractères")
    @Column(name = "province", nullable = false)
    private String province;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutAutoEcole statut = StatutAutoEcole.EN_ATTENTE;
    
    @Column(name = "numero_demande", unique = true)
    private String numeroDemande;
    
    @Column(name = "qr_code")
    private String qrCode;
    
    @CreatedDate
    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;
    
    @LastModifiedDate
    @Column(name = "date_modification")
    private LocalDateTime dateModification;
    
    @OneToMany(mappedBy = "autoEcole", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Candidat> candidats = new ArrayList<>();
    
    @OneToMany(mappedBy = "autoEcole", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Examen> examens = new ArrayList<>();
    
    // Constructeurs
    public AutoEcole() {}
    
    public AutoEcole(String nom, String proprietaireNom, String proprietairePrenom, 
                    String email, String telephone, String adresse, String ville, String province) {
        this.nom = nom;
        this.proprietaireNom = proprietaireNom;
        this.proprietairePrenom = proprietairePrenom;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.ville = ville;
        this.province = province;
    }
    
    // Méthodes utilitaires
    public String getNomCompletProprietaire() {
        return proprietairePrenom + " " + proprietaireNom;
    }
    
    public boolean peutEnrollerCandidats() {
        return statut == StatutAutoEcole.AUTORISATION_PROVISOIRE || statut == StatutAutoEcole.AUTORISATION_VALIDE;
    }
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getProprietaireNom() { return proprietaireNom; }
    public void setProprietaireNom(String proprietaireNom) { this.proprietaireNom = proprietaireNom; }
    
    public String getProprietairePrenom() { return proprietairePrenom; }
    public void setProprietairePrenom(String proprietairePrenom) { this.proprietairePrenom = proprietairePrenom; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    
    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }
    
    public String getProvince() { return province; }
    public void setProvince(String province) { this.province = province; }
    
    public StatutAutoEcole getStatut() { return statut; }
    public void setStatut(StatutAutoEcole statut) { this.statut = statut; }
    
    public String getNumeroDemande() { return numeroDemande; }
    public void setNumeroDemande(String numeroDemande) { this.numeroDemande = numeroDemande; }
    
    public String getQrCode() { return qrCode; }
    public void setQrCode(String qrCode) { this.qrCode = qrCode; }
    
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
    
    public LocalDateTime getDateModification() { return dateModification; }
    public void setDateModification(LocalDateTime dateModification) { this.dateModification = dateModification; }
    
    public List<Candidat> getCandidats() { return candidats; }
    public void setCandidats(List<Candidat> candidats) { this.candidats = candidats; }
    
    public List<Examen> getExamens() { return examens; }
    public void setExamens(List<Examen> examens) { this.examens = examens; }
}
