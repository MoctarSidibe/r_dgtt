package ga.dgtt.permis.service;

import ga.dgtt.permis.model.*;
import ga.dgtt.permis.repository.ExamenRepository;
import ga.dgtt.permis.repository.CandidatRepository;
import ga.dgtt.permis.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service pour la gestion des permis de conduire
 * 
 * Ce service implémente la logique métier pour la gestion des examens
 * et la coordination avec les autres services (SAF, STIAS, DGTT).
 */
@Service
@Transactional
public class PermisService {
    
    @Autowired
    private ExamenRepository examenRepository;
    
    @Autowired
    private CandidatRepository candidatRepository;
    
    @Autowired
    private AuditLogRepository auditLogRepository;
    
    @Autowired
    private QRCodeService qrCodeService;
    
    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private ProcesVerbalService procesVerbalService;
    
    @Autowired
    private SignatureNumeriqueService signatureService;
    
    /**
     * Reçoit un dossier candidat validé par SAF et programme l'examen
     */
    public Examen recevoirDossierSAF(Long candidatId, String utilisateur) {
        Candidat candidat = candidatRepository.findById(candidatId)
                .orElseThrow(() -> new RuntimeException("Candidat non trouvé"));
        
        // Vérifier que le candidat est dans le bon statut
        if (candidat.getStatut() != StatutCandidat.DOSSIER_VALIDE) {
            throw new RuntimeException("Le candidat doit avoir un dossier validé par SAF");
        }
        
        // Vérifier que l'auto-école peut présenter des candidats
        if (!candidat.getAutoEcole().peutEnrollerCandidats()) {
            throw new RuntimeException("L'auto-école ne peut pas présenter de candidats");
        }
        
        // Générer le numéro d'examen
        String numeroExamen = genererNumeroExamen();
        
        // Créer l'examen
        Examen examen = new Examen();
        examen.setNumeroExamen(numeroExamen);
        examen.setCandidat(candidat);
        examen.setAutoEcole(candidat.getAutoEcole());
        examen.setTypeExamen(TypeExamen.CONDUITE_PRATIQUE);
        examen.setStatut(StatutExamen.PROGRAMME);
        
        // Générer le QR code
        String qrCode = qrCodeService.genererQRCode("EXAMEN:" + numeroExamen);
        examen.setQrCode(qrCode);
        
        // Sauvegarder
        Examen examenSauvegarde = examenRepository.save(examen);
        
        // Mettre à jour le statut du candidat
        candidat.setStatut(StatutCandidat.EXAMEN_PROGRAMME);
        candidatRepository.save(candidat);
        
        // Enregistrer l'audit
        enregistrerAudit(ActionAudit.PROGRAMMATION_EXAMEN, "Examen", examenSauvegarde.getId(), 
                        utilisateur, "Examen programmé pour le candidat: " + candidat.getNomComplet());
        
        // Notifier
        notificationService.envoyerNotificationExamenProgramme(examenSauvegarde);
        
        return examenSauvegarde;
    }
    
    /**
     * Programme un examen avec date et examinateur
     */
    public Examen programmerExamen(Long examenId, LocalDateTime dateExamen, String lieuExamen,
                                 String examinateurNom, String examinateurPrenom, String utilisateur) {
        Examen examen = examenRepository.findById(examenId)
                .orElseThrow(() -> new RuntimeException("Examen non trouvé"));
        
        // Vérifier que l'examen peut être programmé
        if (examen.getStatut() != StatutExamen.PROGRAMME) {
            throw new RuntimeException("L'examen doit être en statut PROGRAMME");
        }
        
        // Mettre à jour les informations
        examen.setDateExamen(dateExamen);
        examen.setLieuExamen(lieuExamen);
        examen.setExaminateurNom(examinateurNom);
        examen.setExaminateurPrenom(examinateurPrenom);
        
        Examen examenMiseAJour = examenRepository.save(examen);
        
        // Enregistrer l'audit
        enregistrerAudit(ActionAudit.PROGRAMMATION_EXAMEN, "Examen", examen.getId(), 
                        utilisateur, "Examen programmé pour le " + dateExamen);
        
        // Notifier
        notificationService.envoyerNotificationExamenProgramme(examenMiseAJour);
        
        return examenMiseAJour;
    }
    
    /**
     * Démarre un examen
     */
    public Examen demarrerExamen(Long examenId, String utilisateur) {
        Examen examen = examenRepository.findById(examenId)
                .orElseThrow(() -> new RuntimeException("Examen non trouvé"));
        
        // Vérifier que l'examen peut être démarré
        if (examen.getStatut() != StatutExamen.PROGRAMME) {
            throw new RuntimeException("L'examen doit être programmé");
        }
        
        // Mettre à jour le statut
        examen.setStatut(StatutExamen.EN_COURS);
        examen.setDateExamen(LocalDateTime.now());
        
        Examen examenMiseAJour = examenRepository.save(examen);
        
        // Enregistrer l'audit
        enregistrerAudit(ActionAudit.DEBUT_EXAMEN, "Examen", examen.getId(), 
                        utilisateur, "Examen démarré");
        
        return examenMiseAJour;
    }
    
    /**
     * Termine un examen avec les résultats
     */
    public Examen terminerExamen(Long examenId, Double note, Integer nombreErreurs, 
                                Integer tempsRealise, String commentaires, String utilisateur) {
        Examen examen = examenRepository.findById(examenId)
                .orElseThrow(() -> new RuntimeException("Examen non trouvé"));
        
        // Vérifier que l'examen est en cours
        if (examen.getStatut() != StatutExamen.EN_COURS) {
            throw new RuntimeException("L'examen doit être en cours");
        }
        
        // Calculer si l'examen est réussi
        boolean estReussi = examen.getTypeExamen().isNoteSuffisante(note) && 
                           examen.getTypeExamen().isNombreErreursAcceptable(nombreErreurs);
        
        // Mettre à jour les résultats
        examen.setNote(note);
        examen.setNombreErreurs(nombreErreurs);
        examen.setTempsRealise(tempsRealise);
        examen.setCommentaires(commentaires);
        examen.setEstReussi(estReussi);
        examen.setStatut(StatutExamen.TERMINE);
        
        Examen examenMiseAJour = examenRepository.save(examen);
        
        // Mettre à jour le statut du candidat
        Candidat candidat = examen.getCandidat();
        if (estReussi) {
            candidat.setStatut(StatutCandidat.EXAMEN_REUSSI);
        } else {
            candidat.setStatut(StatutCandidat.REJETE);
        }
        candidatRepository.save(candidat);
        
        // Enregistrer l'audit
        enregistrerAudit(ActionAudit.FIN_EXAMEN, "Examen", examen.getId(), 
                        utilisateur, "Examen terminé - Résultat: " + (estReussi ? "Réussi" : "Échec"));
        
        // Notifier
        notificationService.envoyerNotificationResultatExamen(examenMiseAJour);
        
        return examenMiseAJour;
    }
    
    /**
     * Valide un examen et génère le procès-verbal
     */
    public Examen validerExamen(Long examenId, String utilisateur) {
        Examen examen = examenRepository.findById(examenId)
                .orElseThrow(() -> new RuntimeException("Examen non trouvé"));
        
        // Vérifier que l'examen peut être validé
        if (!examen.peutEtreValide()) {
            throw new RuntimeException("L'examen doit être terminé pour être validé");
        }
        
        // Générer le procès-verbal
        String procesVerbalUrl = procesVerbalService.genererProcesVerbal(examen);
        examen.setProcesVerbalUrl(procesVerbalUrl);
        
        // Signer numériquement
        String signatureExaminateur = signatureService.signerDocument(examen, "EXAMINATEUR");
        String signatureCandidat = signatureService.signerDocument(examen, "CANDIDAT");
        examen.setSignatureExaminateur(signatureExaminateur);
        examen.setSignatureCandidat(signatureCandidat);
        
        // Mettre à jour le statut
        examen.setStatut(StatutExamen.VALIDE);
        
        Examen examenMiseAJour = examenRepository.save(examen);
        
        // Enregistrer l'audit
        enregistrerAudit(ActionAudit.VALIDATION_EXAMEN, "Examen", examen.getId(), 
                        utilisateur, "Examen validé et procès-verbal généré");
        
        // Envoyer à STIAS
        envoyerAStias(examenMiseAJour, utilisateur);
        
        return examenMiseAJour;
    }
    
    /**
     * Envoie le dossier à STIAS
     */
    public void envoyerAStias(Examen examen, String utilisateur) {
        // Mettre à jour le statut du candidat
        Candidat candidat = examen.getCandidat();
        candidat.setStatut(StatutCandidat.PERMIS_GENERE);
        candidatRepository.save(candidat);
        
        // Enregistrer l'audit
        enregistrerAudit(ActionAudit.ENVOI_STIAS, "Examen", examen.getId(), 
                        utilisateur, "Dossier envoyé à STIAS pour génération du permis");
        
        // Notifier STIAS
        notificationService.envoyerNotificationStias(examen);
    }
    
    /**
     * Trouve un examen par ID
     */
    @Transactional(readOnly = true)
    public Optional<Examen> trouverParId(Long id) {
        return examenRepository.findById(id);
    }
    
    /**
     * Trouve tous les examens avec pagination
     */
    @Transactional(readOnly = true)
    public Page<Examen> trouverTous(Pageable pageable) {
        return examenRepository.findAll(pageable);
    }
    
    /**
     * Trouve les examens par statut
     */
    @Transactional(readOnly = true)
    public List<Examen> trouverParStatut(StatutExamen statut) {
        return examenRepository.findByStatut(statut.name());
    }
    
    /**
     * Trouve les examens d'un candidat
     */
    @Transactional(readOnly = true)
    public List<Examen> trouverParCandidat(Long candidatId) {
        return examenRepository.findByCandidatId(candidatId);
    }
    
    /**
     * Trouve les examens d'une auto-école
     */
    @Transactional(readOnly = true)
    public List<Examen> trouverParAutoEcole(Long autoEcoleId) {
        return examenRepository.findByAutoEcoleId(autoEcoleId);
    }
    
    /**
     * Génère un numéro d'examen unique
     */
    private String genererNumeroExamen() {
        String prefixe = "EXAM";
        String timestamp = String.valueOf(System.currentTimeMillis());
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return prefixe + timestamp + uuid;
    }
    
    /**
     * Enregistre un log d'audit
     */
    private void enregistrerAudit(ActionAudit action, String entite, Long entiteId, 
                                String utilisateur, String message) {
        AuditLog auditLog = new AuditLog(entite, entiteId, action, utilisateur, message);
        auditLog.setNiveauSecurite(determinerNiveauSecurite(action));
        auditLogRepository.save(auditLog);
    }
    
    /**
     * Détermine le niveau de sécurité d'une action
     */
    private NiveauSecurite determinerNiveauSecurite(ActionAudit action) {
        if (action.isCritique()) {
            return NiveauSecurite.CRITIQUE;
        } else if (action.isModification()) {
            return NiveauSecurite.WARNING;
        } else {
            return NiveauSecurite.INFO;
        }
    }
}
