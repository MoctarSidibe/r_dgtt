package ga.dgtt.permis.repository;

import ga.dgtt.permis.model.Examen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExamenRepository extends JpaRepository<Examen, Long> {
    
    // Rechercher par numéro d'examen
    Optional<Examen> findByNumeroExamen(String numeroExamen);
    
    // Rechercher par candidat
    List<Examen> findByCandidatId(Long candidatId);
    
    // Rechercher par auto-école
    List<Examen> findByAutoEcoleId(Long autoEcoleId);
    
    // Rechercher par statut
    List<Examen> findByStatut(String statut);
    
    // Rechercher par type d'examen
    List<Examen> findByTypeExamen(String typeExamen);
    
    // Rechercher par date d'examen
    List<Examen> findByDateExamenBetween(LocalDateTime dateDebut, LocalDateTime dateFin);
    
    // Rechercher par examinateur
    List<Examen> findByExaminateurNomAndExaminateurPrenom(String nom, String prenom);
    
    // Rechercher les examens réussis
    List<Examen> findByEstReussiTrue();
    
    // Rechercher les examens échoués
    List<Examen> findByEstReussiFalse();
    
    // Compter les examens par statut
    @Query("SELECT COUNT(e) FROM Examen e WHERE e.statut = :statut")
    long countByStatut(@Param("statut") String statut);
    
    // Compter les examens réussis par candidat
    @Query("SELECT COUNT(e) FROM Examen e WHERE e.candidatId = :candidatId AND e.estReussi = true")
    long countReussisByCandidatId(@Param("candidatId") Long candidatId);
    
    // Rechercher les examens en cours
    @Query("SELECT e FROM Examen e WHERE e.statut = 'EN_COURS'")
    List<Examen> findExamensEnCours();
    
    // Rechercher les examens programmés
    @Query("SELECT e FROM Examen e WHERE e.statut = 'PROGRAMME' AND e.dateExamen > :now")
    List<Examen> findExamensProgrammes(@Param("now") LocalDateTime now);
}
