package ga.dgtt.autoecole.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Entité représentant un log d'audit
 * 
 * Cette entité enregistre toutes les actions effectuées sur les entités
 * pour assurer la traçabilité et la conformité aux exigences de sécurité.
 */
@Entity
@Table(name = "audit_logs")
@EntityListeners(AuditingEntityListener.class)
public class AuditLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "L'entité est obligatoire")
    @Size(max = 100, message = "L'entité ne peut pas dépasser 100 caractères")
    @Column(name = "entite", nullable = false)
    private String entite;
    
    @Column(name = "entite_id")
    private Long entiteId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false)
    private ActionAudit action;
    
    @NotBlank(message = "L'utilisateur est obligatoire")
    @Size(max = 255, message = "L'utilisateur ne peut pas dépasser 255 caractères")
    @Column(name = "utilisateur", nullable = false)
    private String utilisateur;
    
    @Size(max = 100, message = "Le rôle ne peut pas dépasser 100 caractères")
    @Column(name = "role_utilisateur")
    private String roleUtilisateur;
    
    @Column(name = "adresse_ip")
    private String adresseIp;
    
    @Column(name = "user_agent")
    private String userAgent;
    
    @Column(name = "donnees_avant", length = 4000)
    private String donneesAvant;
    
    @Column(name = "donnees_apres", length = 4000)
    private String donneesApres;
    
    @Column(name = "message", length = 1000)
    private String message;
    
    @Column(name = "niveau_securite")
    @Enumerated(EnumType.STRING)
    private NiveauSecurite niveauSecurite = NiveauSecurite.INFO;
    
    @CreatedDate
    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auto_ecole_id")
    private AutoEcole autoEcole;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidat_id")
    private Candidat candidat;
    
    // Constructeurs
    public AuditLog() {}
    
    public AuditLog(String entite, Long entiteId, ActionAudit action, String utilisateur, String message) {
        this.entite = entite;
        this.entiteId = entiteId;
        this.action = action;
        this.utilisateur = utilisateur;
        this.message = message;
    }
    
    // Méthodes utilitaires
    public String getDescriptionComplete() {
        return String.format("%s %s sur %s (ID: %d) par %s", 
                           action.getLibelle(), entite, entiteId, utilisateur);
    }
    
    public boolean isActionCritique() {
        return niveauSecurite == NiveauSecurite.CRITIQUE || niveauSecurite == NiveauSecurite.ALERTE;
    }
    
    public boolean isActionSensible() {
        return action == ActionAudit.SUPPRESSION || action == ActionAudit.MODIFICATION_DONNEES_SENSIBLES;
    }
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getEntite() { return entite; }
    public void setEntite(String entite) { this.entite = entite; }
    
    public Long getEntiteId() { return entiteId; }
    public void setEntiteId(Long entiteId) { this.entiteId = entiteId; }
    
    public ActionAudit getAction() { return action; }
    public void setAction(ActionAudit action) { this.action = action; }
    
    public String getUtilisateur() { return utilisateur; }
    public void setUtilisateur(String utilisateur) { this.utilisateur = utilisateur; }
    
    public String getRoleUtilisateur() { return roleUtilisateur; }
    public void setRoleUtilisateur(String roleUtilisateur) { this.roleUtilisateur = roleUtilisateur; }
    
    public String getAdresseIp() { return adresseIp; }
    public void setAdresseIp(String adresseIp) { this.adresseIp = adresseIp; }
    
    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
    
    public String getDonneesAvant() { return donneesAvant; }
    public void setDonneesAvant(String donneesAvant) { this.donneesAvant = donneesAvant; }
    
    public String getDonneesApres() { return donneesApres; }
    public void setDonneesApres(String donneesApres) { this.donneesApres = donneesApres; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public NiveauSecurite getNiveauSecurite() { return niveauSecurite; }
    public void setNiveauSecurite(NiveauSecurite niveauSecurite) { this.niveauSecurite = niveauSecurite; }
    
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
    
    public AutoEcole getAutoEcole() { return autoEcole; }
    public void setAutoEcole(AutoEcole autoEcole) { this.autoEcole = autoEcole; }
    
    public Candidat getCandidat() { return candidat; }
    public void setCandidat(Candidat candidat) { this.candidat = candidat; }
}
