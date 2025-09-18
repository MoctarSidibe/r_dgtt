package ga.dgtt.autoecole.repository;

import ga.dgtt.autoecole.model.AuditLog;
import ga.dgtt.autoecole.model.ActionAudit;
import ga.dgtt.autoecole.model.NiveauSecurite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository pour l'entité AuditLog
 * 
 * Ce repository fournit les méthodes d'accès aux données pour les logs d'audit
 * avec des requêtes personnalisées pour la surveillance et la conformité.
 */
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    
    /**
     * Trouve les logs d'audit par entité
     */
    List<AuditLog> findByEntite(String entite);
    
    /**
     * Trouve les logs d'audit par entité et ID
     */
    List<AuditLog> findByEntiteAndEntiteId(String entite, Long entiteId);
    
    /**
     * Trouve les logs d'audit par action
     */
    List<AuditLog> findByAction(ActionAudit action);
    
    /**
     * Trouve les logs d'audit par utilisateur
     */
    List<AuditLog> findByUtilisateur(String utilisateur);
    
    /**
     * Trouve les logs d'audit par niveau de sécurité
     */
    List<AuditLog> findByNiveauSecurite(NiveauSecurite niveauSecurite);
    
    /**
     * Trouve les logs d'audit par niveau de sécurité avec pagination
     */
    Page<AuditLog> findByNiveauSecurite(NiveauSecurite niveauSecurite, Pageable pageable);
    
    /**
     * Trouve les logs d'audit par date de création
     */
    List<AuditLog> findByDateCreationBetween(LocalDateTime dateDebut, LocalDateTime dateFin);
    
    /**
     * Trouve les logs d'audit par date de création avec pagination
     */
    Page<AuditLog> findByDateCreationBetween(LocalDateTime dateDebut, LocalDateTime dateFin, Pageable pageable);
    
    /**
     * Trouve les logs d'audit par adresse IP
     */
    List<AuditLog> findByAdresseIp(String adresseIp);
    
    /**
     * Trouve les logs d'audit critiques
     */
    @Query("SELECT a FROM AuditLog a WHERE a.niveauSecurite IN :niveauxCritiques")
    List<AuditLog> findCritiques(@Param("niveauxCritiques") List<NiveauSecurite> niveauxCritiques);
    
    /**
     * Trouve les logs d'audit critiques avec pagination
     */
    @Query("SELECT a FROM AuditLog a WHERE a.niveauSecurite IN :niveauxCritiques")
    Page<AuditLog> findCritiques(@Param("niveauxCritiques") List<NiveauSecurite> niveauxCritiques, Pageable pageable);
    
    /**
     * Trouve les logs d'audit par critères multiples
     */
    @Query("SELECT a FROM AuditLog a WHERE " +
           "(:entite IS NULL OR a.entite = :entite) AND " +
           "(:action IS NULL OR a.action = :action) AND " +
           "(:utilisateur IS NULL OR LOWER(a.utilisateur) LIKE LOWER(CONCAT('%', :utilisateur, '%'))) AND " +
           "(:niveauSecurite IS NULL OR a.niveauSecurite = :niveauSecurite) AND " +
           "(:dateDebut IS NULL OR a.dateCreation >= :dateDebut) AND " +
           "(:dateFin IS NULL OR a.dateCreation <= :dateFin)")
    Page<AuditLog> findByCriteres(@Param("entite") String entite, 
                                 @Param("action") ActionAudit action, 
                                 @Param("utilisateur") String utilisateur, 
                                 @Param("niveauSecurite") NiveauSecurite niveauSecurite, 
                                 @Param("dateDebut") LocalDateTime dateDebut, 
                                 @Param("dateFin") LocalDateTime dateFin, 
                                 Pageable pageable);
    
    /**
     * Trouve les actions récentes d'un utilisateur
     */
    @Query("SELECT a FROM AuditLog a WHERE a.utilisateur = :utilisateur AND a.dateCreation >= :dateLimite ORDER BY a.dateCreation DESC")
    List<AuditLog> findActionsRecentUtilisateur(@Param("utilisateur") String utilisateur, 
                                               @Param("dateLimite") LocalDateTime dateLimite);
    
    /**
     * Trouve les tentatives d'accès non autorisées
     */
    @Query("SELECT a FROM AuditLog a WHERE a.action = :action AND a.dateCreation >= :dateLimite")
    List<AuditLog> findTentativesAccesNonAutorise(@Param("action") ActionAudit action, 
                                                 @Param("dateLimite") LocalDateTime dateLimite);
    
    /**
     * Trouve les modifications de données sensibles
     */
    @Query("SELECT a FROM AuditLog a WHERE a.action = :action AND a.dateCreation >= :dateLimite")
    List<AuditLog> findModificationsDonneesSensibles(@Param("action") ActionAudit action, 
                                                    @Param("dateLimite") LocalDateTime dateLimite);
    
    /**
     * Compte les logs d'audit par action
     */
    @Query("SELECT a.action, COUNT(a) FROM AuditLog a GROUP BY a.action ORDER BY COUNT(a) DESC")
    List<Object[]> countByAction();
    
    /**
     * Compte les logs d'audit par utilisateur
     */
    @Query("SELECT a.utilisateur, COUNT(a) FROM AuditLog a GROUP BY a.utilisateur ORDER BY COUNT(a) DESC")
    List<Object[]> countByUtilisateur();
    
    /**
     * Compte les logs d'audit par niveau de sécurité
     */
    @Query("SELECT a.niveauSecurite, COUNT(a) FROM AuditLog a GROUP BY a.niveauSecurite ORDER BY COUNT(a) DESC")
    List<Object[]> countByNiveauSecurite();
    
    /**
     * Compte les logs d'audit par entité
     */
    @Query("SELECT a.entite, COUNT(a) FROM AuditLog a GROUP BY a.entite ORDER BY COUNT(a) DESC")
    List<Object[]> countByEntite();
    
    /**
     * Trouve les logs d'audit par auto-école
     */
    @Query("SELECT a FROM AuditLog a WHERE a.autoEcole.id = :autoEcoleId ORDER BY a.dateCreation DESC")
    List<AuditLog> findByAutoEcoleId(@Param("autoEcoleId") Long autoEcoleId);
    
    /**
     * Trouve les logs d'audit par candidat
     */
    @Query("SELECT a FROM AuditLog a WHERE a.candidat.id = :candidatId ORDER BY a.dateCreation DESC")
    List<AuditLog> findByCandidatId(@Param("candidatId") Long candidatId);
    
    /**
     * Trouve les logs d'audit nécessitant une alerte
     */
    @Query("SELECT a FROM AuditLog a WHERE a.niveauSecurite IN :niveauxAlerte AND a.dateCreation >= :dateLimite")
    List<AuditLog> findNecessitantAlerte(@Param("niveauxAlerte") List<NiveauSecurite> niveauxAlerte, 
                                        @Param("dateLimite") LocalDateTime dateLimite);
    
    /**
     * Supprime les logs d'audit anciens
     */
    @Query("DELETE FROM AuditLog a WHERE a.dateCreation < :dateLimite")
    int deleteAnciensLogs(@Param("dateLimite") LocalDateTime dateLimite);
}
