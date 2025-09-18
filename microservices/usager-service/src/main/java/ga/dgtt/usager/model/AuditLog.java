package ga.dgtt.usager.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
public class AuditLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "entite", nullable = false, length = 100)
    private String entite;
    
    @Column(name = "entite_id")
    private Long entiteId;
    
    @Column(name = "action", nullable = false, length = 50)
    private String action;
    
    @Column(name = "utilisateur", nullable = false, length = 255)
    private String utilisateur;
    
    @Column(name = "role_utilisateur", length = 100)
    private String roleUtilisateur;
    
    @Column(name = "adresse_ip", length = 45)
    private String adresseIp;
    
    @Column(name = "user_agent", columnDefinition = "TEXT")
    private String userAgent;
    
    @Column(name = "donnees_avant", columnDefinition = "TEXT")
    private String donneesAvant;
    
    @Column(name = "donnees_apres", columnDefinition = "TEXT")
    private String donneesApres;
    
    @Column(name = "message", columnDefinition = "TEXT")
    private String message;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "niveau_securite", length = 50)
    private NiveauSecurite niveauSecurite = NiveauSecurite.INFO;
    
    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;
    
    // Constructeurs
    public AuditLog() {
        this.dateCreation = LocalDateTime.now();
    }
    
    public AuditLog(String entite, Long entiteId, String action, String utilisateur, String roleUtilisateur) {
        this();
        this.entite = entite;
        this.entiteId = entiteId;
        this.action = action;
        this.utilisateur = utilisateur;
        this.roleUtilisateur = roleUtilisateur;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getEntite() {
        return entite;
    }
    
    public void setEntite(String entite) {
        this.entite = entite;
    }
    
    public Long getEntiteId() {
        return entiteId;
    }
    
    public void setEntiteId(Long entiteId) {
        this.entiteId = entiteId;
    }
    
    public String getAction() {
        return action;
    }
    
    public void setAction(String action) {
        this.action = action;
    }
    
    public String getUtilisateur() {
        return utilisateur;
    }
    
    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }
    
    public String getRoleUtilisateur() {
        return roleUtilisateur;
    }
    
    public void setRoleUtilisateur(String roleUtilisateur) {
        this.roleUtilisateur = roleUtilisateur;
    }
    
    public String getAdresseIp() {
        return adresseIp;
    }
    
    public void setAdresseIp(String adresseIp) {
        this.adresseIp = adresseIp;
    }
    
    public String getUserAgent() {
        return userAgent;
    }
    
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    
    public String getDonneesAvant() {
        return donneesAvant;
    }
    
    public void setDonneesAvant(String donneesAvant) {
        this.donneesAvant = donneesAvant;
    }
    
    public String getDonneesApres() {
        return donneesApres;
    }
    
    public void setDonneesApres(String donneesApres) {
        this.donneesApres = donneesApres;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public NiveauSecurite getNiveauSecurite() {
        return niveauSecurite;
    }
    
    public void setNiveauSecurite(NiveauSecurite niveauSecurite) {
        this.niveauSecurite = niveauSecurite;
    }
    
    public LocalDateTime getDateCreation() {
        return dateCreation;
    }
    
    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
    
    // Méthodes utilitaires
    public void setDonnees(String donneesAvant, String donneesApres) {
        this.donneesAvant = donneesAvant;
        this.donneesApres = donneesApres;
    }
    
    public void setContexte(String adresseIp, String userAgent) {
        this.adresseIp = adresseIp;
        this.userAgent = userAgent;
    }
    
    @Override
    public String toString() {
        return "AuditLog{" +
                "id=" + id +
                ", entite='" + entite + '\'' +
                ", entiteId=" + entiteId +
                ", action='" + action + '\'' +
                ", utilisateur='" + utilisateur + '\'' +
                ", niveauSecurite=" + niveauSecurite +
                ", dateCreation=" + dateCreation +
                '}';
    }
}
