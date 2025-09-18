package ga.dgtt.usager.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sessions_connexion")
public class SessionConnexion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "usager_id", nullable = false)
    private Long usagerId;
    
    @Column(name = "token", nullable = false, length = 500)
    private String token;
    
    @Column(name = "adresse_ip", length = 45)
    private String adresseIp;
    
    @Column(name = "user_agent", columnDefinition = "TEXT")
    private String userAgent;
    
    @Column(name = "date_connexion", nullable = false)
    private LocalDateTime dateConnexion;
    
    @Column(name = "date_deconnexion")
    private LocalDateTime dateDeconnexion;
    
    @Column(name = "est_active", nullable = false)
    private Boolean estActive = true;
    
    // Constructeurs
    public SessionConnexion() {}
    
    public SessionConnexion(Long usagerId, String token, String adresseIp, String userAgent) {
        this.usagerId = usagerId;
        this.token = token;
        this.adresseIp = adresseIp;
        this.userAgent = userAgent;
        this.dateConnexion = LocalDateTime.now();
        this.estActive = true;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUsagerId() {
        return usagerId;
    }
    
    public void setUsagerId(Long usagerId) {
        this.usagerId = usagerId;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
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
    
    public LocalDateTime getDateConnexion() {
        return dateConnexion;
    }
    
    public void setDateConnexion(LocalDateTime dateConnexion) {
        this.dateConnexion = dateConnexion;
    }
    
    public LocalDateTime getDateDeconnexion() {
        return dateDeconnexion;
    }
    
    public void setDateDeconnexion(LocalDateTime dateDeconnexion) {
        this.dateDeconnexion = dateDeconnexion;
    }
    
    public Boolean getEstActive() {
        return estActive;
    }
    
    public void setEstActive(Boolean estActive) {
        this.estActive = estActive;
    }
    
    // Méthodes utilitaires
    public void deconnecter() {
        this.estActive = false;
        this.dateDeconnexion = LocalDateTime.now();
    }
    
    public boolean estExpiree() {
        return !estActive || (dateDeconnexion != null);
    }
    
    @Override
    public String toString() {
        return "SessionConnexion{" +
                "id=" + id +
                ", usagerId=" + usagerId +
                ", token='" + token + '\'' +
                ", adresseIp='" + adresseIp + '\'' +
                ", dateConnexion=" + dateConnexion +
                ", estActive=" + estActive +
                '}';
    }
}
