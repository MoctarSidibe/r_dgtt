package ga.dgtt.permis.repository;

import ga.dgtt.permis.model.Candidat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CandidatRepository extends JpaRepository<Candidat, Long> {
    
    // Rechercher par numéro de licence
    Optional<Candidat> findByNumeroLicence(String numeroLicence);
    
    // Rechercher par numéro d'évaluation
    Optional<Candidat> findByNumeroEvaluation(String numeroEvaluation);
    
    // Rechercher par auto-école
    List<Candidat> findByAutoEcoleId(Long autoEcoleId);
    
    // Rechercher par statut
    List<Candidat> findByStatut(String statut);
    
    // Rechercher par catégorie de permis
    List<Candidat> findByCategoriePermis(String categoriePermis);
    
    // Rechercher par nom et prénom
    List<Candidat> findByNomAndPrenom(String nom, String prenom);
    
    // Rechercher par nationalité
    List<Candidat> findByNationalite(String nationalite);
    
    // Rechercher par date de naissance
    List<Candidat> findByDateNaissance(LocalDate dateNaissance);
    
    // Rechercher par lieu de naissance
    List<Candidat> findByLieuNaissance(String lieuNaissance);
    
    // Rechercher les candidats ayant payé
    List<Candidat> findByMontantPaiementIsNotNull();
    
    // Rechercher les candidats n'ayant pas payé
    List<Candidat> findByMontantPaiementIsNull();
    
    // Compter les candidats par statut
    @Query("SELECT COUNT(c) FROM Candidat c WHERE c.statut = :statut")
    long countByStatut(@Param("statut") String statut);
    
    // Compter les candidats par auto-école
    @Query("SELECT COUNT(c) FROM Candidat c WHERE c.autoEcoleId = :autoEcoleId")
    long countByAutoEcoleId(@Param("autoEcoleId") Long autoEcoleId);
    
    // Compter les candidats par catégorie de permis
    @Query("SELECT COUNT(c) FROM Candidat c WHERE c.categoriePermis = :categoriePermis")
    long countByCategoriePermis(@Param("categoriePermis") String categoriePermis);
    
    // Rechercher les candidats en cours de formation
    @Query("SELECT c FROM Candidat c WHERE c.statut = 'EN_COURS'")
    List<Candidat> findCandidatsEnCours();
    
    // Rechercher les candidats ayant réussi
    @Query("SELECT c FROM Candidat c WHERE c.statut = 'REUSSI'")
    List<Candidat> findCandidatsReussis();
    
    // Rechercher les candidats ayant échoué
    @Query("SELECT c FROM Candidat c WHERE c.statut = 'ECHEC'")
    List<Candidat> findCandidatsEchecs();
}
