package ga.dgtt.permis.repository;

import ga.dgtt.permis.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    
    // Rechercher par entité
    List<AuditLog> findByEntite(String entite);
    
    // Rechercher par ID d'entité
    List<AuditLog> findByEntiteId(Long entiteId);
    
    // Rechercher par utilisateur
    List<AuditLog> findByUtilisateur(String utilisateur);
    
    // Rechercher par action
    List<AuditLog> findByAction(String action);
    
    // Rechercher par niveau de sécurité
    List<AuditLog> findByNiveauSecurite(String niveauSecurite);
    
    // Rechercher par date de création
    List<AuditLog> findByDateCreationBetween(LocalDateTime dateDebut, LocalDateTime dateFin);
    
    // Rechercher par examen
    List<AuditLog> findByExamenId(Long examenId);
    
    // Rechercher par candidat
    List<AuditLog> findByCandidatId(Long candidatId);
    
    // Rechercher par adresse IP
    List<AuditLog> findByAdresseIp(String adresseIp);
    
    // Rechercher par rôle utilisateur
    List<AuditLog> findByRoleUtilisateur(String roleUtilisateur);
    
    // Compter les logs par entité
    @Query("SELECT COUNT(a) FROM AuditLog a WHERE a.entite = :entite")
    long countByEntite(@Param("entite") String entite);
    
    // Compter les logs par utilisateur
    @Query("SELECT COUNT(a) FROM AuditLog a WHERE a.utilisateur = :utilisateur")
    long countByUtilisateur(@Param("utilisateur") String utilisateur);
    
    // Compter les logs par action
    @Query("SELECT COUNT(a) FROM AuditLog a WHERE a.action = :action")
    long countByAction(@Param("action") String action);
    
    // Rechercher les logs récents
    @Query("SELECT a FROM AuditLog a WHERE a.dateCreation >= :dateDebut ORDER BY a.dateCreation DESC")
    List<AuditLog> findLogsRecents(@Param("dateDebut") LocalDateTime dateDebut);
    
    // Rechercher les logs de sécurité
    @Query("SELECT a FROM AuditLog a WHERE a.niveauSecurite = 'SECURITE' ORDER BY a.dateCreation DESC")
    List<AuditLog> findLogsSecurite();
    
    // Rechercher les logs d'erreur
    @Query("SELECT a FROM AuditLog a WHERE a.niveauSecurite = 'ERREUR' ORDER BY a.dateCreation DESC")
    List<AuditLog> findLogsErreur();
}
