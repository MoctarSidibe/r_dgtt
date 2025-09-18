package ga.dgtt.autoecole.repository;

import ga.dgtt.autoecole.model.Candidat;
import ga.dgtt.autoecole.model.StatutCandidat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité Candidat
 * 
 * Ce repository fournit les méthodes d'accès aux données pour les candidats
 * avec des requêtes personnalisées pour les besoins métier.
 */
@Repository
public interface CandidatRepository extends JpaRepository<Candidat, Long> {
    
    /**
     * Trouve un candidat par son numéro de licence
     */
    Optional<Candidat> findByNumeroLicence(String numeroLicence);
    
    /**
     * Trouve un candidat par son numéro d'évaluation
     */
    Optional<Candidat> findByNumeroEvaluation(String numeroEvaluation);
    
    /**
     * Trouve les candidats par statut
     */
    List<Candidat> findByStatut(StatutCandidat statut);
    
    /**
     * Trouve les candidats par statut avec pagination
     */
    Page<Candidat> findByStatut(StatutCandidat statut, Pageable pageable);
    
    /**
     * Trouve les candidats par auto-école
     */
    List<Candidat> findByAutoEcoleId(Long autoEcoleId);
    
    /**
     * Trouve les candidats par auto-école avec pagination
     */
    Page<Candidat> findByAutoEcoleId(Long autoEcoleId, Pageable pageable);
    
    /**
     * Trouve les candidats par catégorie de permis
     */
    List<Candidat> findByCategoriePermis(String categoriePermis);
    
    /**
     * Trouve les candidats par nom (recherche partielle)
     */
    @Query("SELECT c FROM Candidat c WHERE LOWER(c.nom) LIKE LOWER(CONCAT('%', :nom, '%')) OR LOWER(c.prenom) LIKE LOWER(CONCAT('%', :nom, '%'))")
    List<Candidat> findByNomContainingIgnoreCase(@Param("nom") String nom);
    
    /**
     * Trouve les candidats par date de naissance
     */
    List<Candidat> findByDateNaissance(LocalDate dateNaissance);
    
    /**
     * Trouve les candidats nés entre deux dates
     */
    @Query("SELECT c FROM Candidat c WHERE c.dateNaissance BETWEEN :dateDebut AND :dateFin")
    List<Candidat> findByDateNaissanceBetween(@Param("dateDebut") LocalDate dateDebut, 
                                            @Param("dateFin") LocalDate dateFin);
    
    /**
     * Trouve les candidats par nationalité
     */
    List<Candidat> findByNationalite(String nationalite);
    
    /**
     * Trouve les candidats avec paiement effectué
     */
    @Query("SELECT c FROM Candidat c WHERE c.datePaiement IS NOT NULL AND c.referencePaiement IS NOT NULL")
    List<Candidat> findWithPaiementEffectue();
    
    /**
     * Trouve les candidats en attente de paiement
     */
    @Query("SELECT c FROM Candidat c WHERE c.datePaiement IS NULL")
    List<Candidat> findEnAttentePaiement();
    
    /**
     * Trouve les candidats prêts pour l'examen
     */
    @Query("SELECT c FROM Candidat c WHERE c.statut = :statut AND c.datePaiement IS NOT NULL")
    List<Candidat> findPretsPourExamen(@Param("statut") StatutCandidat statut);
    
    /**
     * Trouve les candidats par critères multiples
     */
    @Query("SELECT c FROM Candidat c WHERE " +
           "(:autoEcoleId IS NULL OR c.autoEcole.id = :autoEcoleId) AND " +
           "(:statut IS NULL OR c.statut = :statut) AND " +
           "(:categoriePermis IS NULL OR c.categoriePermis = :categoriePermis) AND " +
           "(:nom IS NULL OR LOWER(c.nom) LIKE LOWER(CONCAT('%', :nom, '%')) OR LOWER(c.prenom) LIKE LOWER(CONCAT('%', :nom, '%')))")
    Page<Candidat> findByCriteres(@Param("autoEcoleId") Long autoEcoleId, 
                                 @Param("statut") StatutCandidat statut, 
                                 @Param("categoriePermis") String categoriePermis, 
                                 @Param("nom") String nom, 
                                 Pageable pageable);
    
    /**
     * Compte les candidats par statut
     */
    @Query("SELECT c.statut, COUNT(c) FROM Candidat c GROUP BY c.statut")
    List<Object[]> countByStatut();
    
    /**
     * Compte les candidats par statut (enum)
     */
    long countByStatut(StatutCandidat statut);
    
    /**
     * Compte les candidats par catégorie de permis
     */
    @Query("SELECT c.categoriePermis, COUNT(c) FROM Candidat c GROUP BY c.categoriePermis ORDER BY COUNT(c) DESC")
    List<Object[]> countByCategoriePermis();
    
    /**
     * Compte les candidats par auto-école
     */
    @Query("SELECT c.autoEcole.nom, COUNT(c) FROM Candidat c GROUP BY c.autoEcole.id, c.autoEcole.nom ORDER BY COUNT(c) DESC")
    List<Object[]> countByAutoEcole();
    
    /**
     * Trouve les candidats créés entre deux dates
     */
    @Query("SELECT c FROM Candidat c WHERE c.dateCreation BETWEEN :dateDebut AND :dateFin")
    List<Candidat> findByDateCreationBetween(@Param("dateDebut") LocalDateTime dateDebut, 
                                           @Param("dateFin") LocalDateTime dateFin);
    
    /**
     * Trouve les candidats avec évaluations complètes
     */
    @Query("SELECT c FROM Candidat c WHERE c.statut = :statut AND " +
           "SIZE(c.evaluations) >= 9 AND " +
           "ALL(e IN c.evaluations WHERE e.estReussi = true)")
    List<Candidat> findWithEvaluationsCompletes(@Param("statut") StatutCandidat statut);
    
    /**
     * Vérifie si un candidat existe avec ce numéro de licence
     */
    boolean existsByNumeroLicence(String numeroLicence);
    
    /**
     * Vérifie si un candidat existe avec ce numéro d'évaluation
     */
    boolean existsByNumeroEvaluation(String numeroEvaluation);
}