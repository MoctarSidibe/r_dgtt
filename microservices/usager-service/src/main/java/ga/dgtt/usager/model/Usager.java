package ga.dgtt.usager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entité représentant un usager du système R-DGTT
 * 
 * Cette entité gère tous les utilisateurs du système incluant
 * les administrateurs, les employés des différents services,
 * et les gestionnaires d'auto-écoles.
 */
@Entity
@Table(name = "usagers")
@EntityListeners(AuditingEntityListener.class)
public class Usager {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    @Size(min = 3, max = 50, message = "Le nom d'utilisateur doit contenir entre 3 et 50 caractères")
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    @Column(name = "password", nullable = false)
    private String password;
    
    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 255, message = "Le nom ne peut pas dépasser 255 caractères")
    @Column(name = "nom", nullable = false)
    private String nom;
    
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 255, message = "Le prénom ne peut pas dépasser 255 caractères")
    @Column(name = "prenom", nullable = false)
    private String prenom;
    
    @Column(name = "telephone")
    private String telephone;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private RoleUsager role;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutUsager statut = StatutUsager.ACTIF;
    
    @Column(name = "derniere_connexion")
    private LocalDateTime derniereConnexion;
    
    @Column(name = "nombre_tentatives_connexion")
    private Integer nombreTentativesConnexion = 0;
    
    @Column(name = "date_verrouillage")
    private LocalDateTime dateVerrouillage;
    
    @Column(name = "mot_de_passe_expire")
    private Boolean motDePasseExpire = false;
    
    @Column(name = "date_expiration_mot_de_passe")
    private LocalDateTime dateExpirationMotDePasse;
    
    @Column(name = "deuxieme_facteur_active")
    private Boolean deuxiemeFacteurActive = false;
    
    @Column(name = "cle_2fa")
    private String cle2FA;
    
    @Column(name = "avatar_url")
    private String avatarUrl;
    
    @Column(name = "langue_preferee")
    private String languePreferee = "fr";
    
    @Column(name = "timezone")
    private String timezone = "Africa/Libreville";
    
    @Column(name = "notes", length = 1000)
    private String notes;
    
    @CreatedDate
    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;
    
    @LastModifiedDate
    @Column(name = "date_modification")
    private LocalDateTime dateModification;
    
    @OneToMany(mappedBy = "usager", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SessionConnexion> sessionsConnexion = new ArrayList<>();
    
    @OneToMany(mappedBy = "usager", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AuditLog> auditLogs = new ArrayList<>();
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "usager_permissions",
        joinColumns = @JoinColumn(name = "usager_id"),
        inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private List<Permission> permissions = new ArrayList<>();
    
    // Constructeurs
    public Usager() {}
    
    public Usager(String username, String email, String password, String nom, String prenom, RoleUsager role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
    }
    
    // Méthodes utilitaires
    public String getNomComplet() {
        return prenom + " " + nom;
    }
    
    public boolean isActif() {
        return statut == StatutUsager.ACTIF;
    }
    
    public boolean isVerrouille() {
        return statut == StatutUsager.VERROUILLE || 
               (dateVerrouillage != null && dateVerrouillage.isAfter(LocalDateTime.now().minusHours(24)));
    }
    
    public boolean isMotDePasseExpire() {
        return motDePasseExpire || 
               (dateExpirationMotDePasse != null && dateExpirationMotDePasse.isBefore(LocalDateTime.now()));
    }
    
    public boolean aPermission(String permission) {
        return permissions.stream().anyMatch(p -> p.getNom().equals(permission));
    }
    
    public boolean aRole(RoleUsager role) {
        return this.role == role;
    }
    
    public void incrementerTentativesConnexion() {
        this.nombreTentativesConnexion++;
        if (this.nombreTentativesConnexion >= 5) {
            this.statut = StatutUsager.VERROUILLE;
            this.dateVerrouillage = LocalDateTime.now();
        }
    }
    
    public void reinitialiserTentativesConnexion() {
        this.nombreTentativesConnexion = 0;
        this.dateVerrouillage = null;
        if (this.statut == StatutUsager.VERROUILLE) {
            this.statut = StatutUsager.ACTIF;
        }
    }
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    
    public RoleUsager getRole() { return role; }
    public void setRole(RoleUsager role) { this.role = role; }
    
    public StatutUsager getStatut() { return statut; }
    public void setStatut(StatutUsager statut) { this.statut = statut; }
    
    public LocalDateTime getDerniereConnexion() { return derniereConnexion; }
    public void setDerniereConnexion(LocalDateTime derniereConnexion) { this.derniereConnexion = derniereConnexion; }
    
    public Integer getNombreTentativesConnexion() { return nombreTentativesConnexion; }
    public void setNombreTentativesConnexion(Integer nombreTentativesConnexion) { this.nombreTentativesConnexion = nombreTentativesConnexion; }
    
    public LocalDateTime getDateVerrouillage() { return dateVerrouillage; }
    public void setDateVerrouillage(LocalDateTime dateVerrouillage) { this.dateVerrouillage = dateVerrouillage; }
    
    public Boolean getMotDePasseExpire() { return motDePasseExpire; }
    public void setMotDePasseExpire(Boolean motDePasseExpire) { this.motDePasseExpire = motDePasseExpire; }
    
    public LocalDateTime getDateExpirationMotDePasse() { return dateExpirationMotDePasse; }
    public void setDateExpirationMotDePasse(LocalDateTime dateExpirationMotDePasse) { this.dateExpirationMotDePasse = dateExpirationMotDePasse; }
    
    public Boolean getDeuxiemeFacteurActive() { return deuxiemeFacteurActive; }
    public void setDeuxiemeFacteurActive(Boolean deuxiemeFacteurActive) { this.deuxiemeFacteurActive = deuxiemeFacteurActive; }
    
    public String getCle2FA() { return cle2FA; }
    public void setCle2FA(String cle2FA) { this.cle2FA = cle2FA; }
    
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    
    public String getLanguePreferee() { return languePreferee; }
    public void setLanguePreferee(String languePreferee) { this.languePreferee = languePreferee; }
    
    public String getTimezone() { return timezone; }
    public void setTimezone(String timezone) { this.timezone = timezone; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
    
    public LocalDateTime getDateModification() { return dateModification; }
    public void setDateModification(LocalDateTime dateModification) { this.dateModification = dateModification; }
    
    public List<SessionConnexion> getSessionsConnexion() { return sessionsConnexion; }
    public void setSessionsConnexion(List<SessionConnexion> sessionsConnexion) { this.sessionsConnexion = sessionsConnexion; }
    
    public List<AuditLog> getAuditLogs() { return auditLogs; }
    public void setAuditLogs(List<AuditLog> auditLogs) { this.auditLogs = auditLogs; }
    
    public List<Permission> getPermissions() { return permissions; }
    public void setPermissions(List<Permission> permissions) { this.permissions = permissions; }
}
