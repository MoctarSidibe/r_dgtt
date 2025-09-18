package ga.dgtt.autoecole.repository;

import ga.dgtt.autoecole.model.AutoEcole;
import ga.dgtt.autoecole.model.StatutAutoEcole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité AutoEcole
 * 
 * Ce repository fournit les méthodes d'accès aux données pour les auto-écoles
 * avec des requêtes personnalisées pour les besoins métier.
 */
@Repository
public interface AutoEcoleRepository extends JpaRepository<AutoEcole, Long> {
    
    /**
     * Trouve une auto-école par son numéro de demande
     */
    Optional<AutoEcole> findByNumeroDemande(String numeroDemande);
    
    /**
     * Trouve une auto-école par son email
     */
    Optional<AutoEcole> findByEmail(String email);
    
    /**
     * Trouve une auto-école par son téléphone
     */
    Optional<AutoEcole> findByTelephone(String telephone);
    
    /**
     * Trouve les auto-écoles par statut
     */
    List<AutoEcole> findByStatut(StatutAutoEcole statut);
    
    /**
     * Trouve les auto-écoles par statut avec pagination
     */
    Page<AutoEcole> findByStatut(StatutAutoEcole statut, Pageable pageable);
    
    /**
     * Trouve les auto-écoles par ville
     */
    List<AutoEcole> findByVille(String ville);
    
    /**
     * Trouve les auto-écoles par province
     */
    List<AutoEcole> findByProvince(String province);
    
    /**
     * Trouve les auto-écoles par nom (recherche partielle)
     */
    @Query("SELECT a FROM AutoEcole a WHERE LOWER(a.nom) LIKE LOWER(CONCAT('%', :nom, '%'))")
    List<AutoEcole> findByNomContainingIgnoreCase(@Param("nom") String nom);
    
    /**
     * Trouve les auto-écoles par nom du propriétaire (recherche partielle)
     */
    @Query("SELECT a FROM AutoEcole a WHERE LOWER(a.proprietaireNom) LIKE LOWER(CONCAT('%', :nom, '%')) OR LOWER(a.proprietairePrenom) LIKE LOWER(CONCAT('%', :nom, '%'))")
    List<AutoEcole> findByProprietaireContainingIgnoreCase(@Param("nom") String nom);
    
    /**
     * Trouve les auto-écoles créées entre deux dates
     */
    @Query("SELECT a FROM AutoEcole a WHERE a.dateCreation BETWEEN :dateDebut AND :dateFin")
    List<AutoEcole> findByDateCreationBetween(@Param("dateDebut") LocalDateTime dateDebut, 
                                            @Param("dateFin") LocalDateTime dateFin);
    
    /**
     * Trouve les auto-écoles avec autorisation expirée
     */
    @Query("SELECT a FROM AutoEcole a WHERE a.dateExpirationAutorisation IS NOT NULL AND a.dateExpirationAutorisation < :dateActuelle")
    List<AutoEcole> findWithAutorisationExpiree(@Param("dateActuelle") LocalDateTime dateActuelle);
    
    /**
     * Trouve les auto-écoles avec autorisation expirant bientôt
     */
    @Query("SELECT a FROM AutoEcole a WHERE a.dateExpirationAutorisation IS NOT NULL AND a.dateExpirationAutorisation BETWEEN :dateActuelle AND :dateLimite")
    List<AutoEcole> findWithAutorisationExpirantBientot(@Param("dateActuelle") LocalDateTime dateActuelle, 
                                                       @Param("dateLimite") LocalDateTime dateLimite);
    
    /**
     * Trouve les auto-écoles en attente d'inspection
     */
    @Query("SELECT a FROM AutoEcole a WHERE a.statut = :statut AND a.datePaiement IS NOT NULL")
    List<AutoEcole> findEnAttenteInspection(@Param("statut") StatutAutoEcole statut);
    
    /**
     * Trouve les auto-écoles avec paiement en attente
     */
    @Query("SELECT a FROM AutoEcole a WHERE a.statut = :statut AND a.datePaiement IS NULL")
    List<AutoEcole> findAvecPaiementEnAttente(@Param("statut") StatutAutoEcole statut);
    
    /**
     * Compte les auto-écoles par statut
     */
    @Query("SELECT a.statut, COUNT(a) FROM AutoEcole a GROUP BY a.statut")
    List<Object[]> countByStatut();
    
    /**
     * Compte les auto-écoles par statut (enum)
     */
    long countByStatut(StatutAutoEcole statut);
    
    /**
     * Compte les auto-écoles par ville
     */
    @Query("SELECT a.ville, COUNT(a) FROM AutoEcole a GROUP BY a.ville ORDER BY COUNT(a) DESC")
    List<Object[]> countByVille();
    
    /**
     * Compte les auto-écoles par province
     */
    @Query("SELECT a.province, COUNT(a) FROM AutoEcole a GROUP BY a.province ORDER BY COUNT(a) DESC")
    List<Object[]> countByProvince();
    
    /**
     * Trouve les auto-écoles avec le plus de candidats
     */
    @Query("SELECT a, SIZE(a.candidats) as nbCandidats FROM AutoEcole a ORDER BY nbCandidats DESC")
    List<Object[]> findWithMostCandidats(Pageable pageable);
    
    /**
     * Trouve les auto-écoles actives (avec autorisation valide)
     */
    @Query("SELECT a FROM AutoEcole a WHERE a.statut IN :statutsActifs")
    List<AutoEcole> findActives(@Param("statutsActifs") List<StatutAutoEcole> statutsActifs);
    
    /**
     * Trouve les auto-écoles par critères multiples
     */
    @Query("SELECT a FROM AutoEcole a WHERE " +
           "(:ville IS NULL OR LOWER(a.ville) = LOWER(:ville)) AND " +
           "(:province IS NULL OR LOWER(a.province) = LOWER(:province)) AND " +
           "(:statut IS NULL OR a.statut = :statut) AND " +
           "(:nom IS NULL OR LOWER(a.nom) LIKE LOWER(CONCAT('%', :nom, '%')))")
    Page<AutoEcole> findByCriteres(@Param("ville") String ville, 
                                  @Param("province") String province, 
                                  @Param("statut") StatutAutoEcole statut, 
                                  @Param("nom") String nom, 
                                  Pageable pageable);
    
    /**
     * Vérifie si une auto-école existe avec cet email
     */
    boolean existsByEmail(String email);
    
    /**
     * Vérifie si une auto-école existe avec ce téléphone
     */
    boolean existsByTelephone(String telephone);
    
    /**
     * Vérifie si une auto-école existe avec ce numéro de demande
     */
    boolean existsByNumeroDemande(String numeroDemande);
}
