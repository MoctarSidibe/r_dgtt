package ga.dgtt.autoecole.service;

import ga.dgtt.autoecole.model.*;
import ga.dgtt.autoecole.repository.AutoEcoleRepository;
import ga.dgtt.autoecole.repository.CandidatRepository;
import ga.dgtt.autoecole.repository.AuditLogRepository;
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
 * Service pour la gestion des auto-écoles
 * 
 * Ce service implémente la logique métier pour la gestion des auto-écoles
 * incluant la création, validation, inspection, et gestion des candidats.
 */
@Service
@Transactional
public class AutoEcoleService {
    
    @Autowired
    private AutoEcoleRepository autoEcoleRepository;
    
    @Autowired
    private CandidatRepository candidatRepository;
    
    @Autowired
    private AuditLogRepository auditLogRepository;
    
    @Autowired
    private QRCodeService qrCodeService;
    
    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private PaiementService paiementService;
    
    /**
     * Crée une nouvelle auto-école
     */
    public AutoEcole creerAutoEcole(AutoEcole autoEcole, String utilisateur) {
        // Générer le numéro de demande
        String numeroDemande = genererNumeroDemande();
        autoEcole.setNumeroDemande(numeroDemande);
        
        // Générer le QR code
        String qrCode = qrCodeService.genererQRCode("AUTO_ECOLE:" + numeroDemande);
        autoEcole.setQrCode(qrCode);
        
        // Sauvegarder
        AutoEcole autoEcoleSauvegardee = autoEcoleRepository.save(autoEcole);
        
        // Enregistrer l'audit
        enregistrerAudit(ActionAudit.CREATION, "AutoEcole", autoEcoleSauvegardee.getId(), 
                        utilisateur, "Création d'une nouvelle auto-école: " + autoEcole.getNom());
        
        // Envoyer notification
        notificationService.envoyerNotificationCreation(autoEcoleSauvegardee);
        
        return autoEcoleSauvegardee;
    }
    
    /**
     * Trouve une auto-école par ID
     */
    @Transactional(readOnly = true)
    public Optional<AutoEcole> trouverParId(Long id) {
        return autoEcoleRepository.findById(id);
    }
    
    /**
     * Trouve une auto-école par numéro de demande
     */
    @Transactional(readOnly = true)
    public Optional<AutoEcole> trouverParNumeroDemande(String numeroDemande) {
        return autoEcoleRepository.findByNumeroDemande(numeroDemande);
    }
    
    /**
     * Trouve toutes les auto-écoles avec pagination
     */
    @Transactional(readOnly = true)
    public Page<AutoEcole> trouverToutes(Pageable pageable) {
        return autoEcoleRepository.findAll(pageable);
    }
    
    /**
     * Trouve les auto-écoles par statut
     */
    @Transactional(readOnly = true)
    public List<AutoEcole> trouverParStatut(StatutAutoEcole statut) {
        return autoEcoleRepository.findByStatut(statut);
    }
    
    /**
     * Trouve les auto-écoles par critères
     */
    @Transactional(readOnly = true)
    public Page<AutoEcole> rechercherParCriteres(String ville, String province, 
                                                StatutAutoEcole statut, String nom, 
                                                Pageable pageable) {
        return autoEcoleRepository.findByCriteres(ville, province, statut, nom, pageable);
    }
    
    /**
     * Met à jour une auto-école
     */
    public AutoEcole mettreAJour(AutoEcole autoEcole, String utilisateur) {
        AutoEcole autoEcoleExistante = autoEcoleRepository.findById(autoEcole.getId())
                .orElseThrow(() -> new RuntimeException("Auto-école non trouvée"));
        
        // Enregistrer les données avant modification
        String donneesAvant = serializeAutoEcole(autoEcoleExistante);
        
        // Mettre à jour
        AutoEcole autoEcoleMiseAJour = autoEcoleRepository.save(autoEcole);
        
        // Enregistrer l'audit
        String donneesApres = serializeAutoEcole(autoEcoleMiseAJour);
        enregistrerAudit(ActionAudit.MODIFICATION, "AutoEcole", autoEcole.getId(), 
                        utilisateur, "Modification de l'auto-école: " + autoEcole.getNom(),
                        donneesAvant, donneesApres);
        
        return autoEcoleMiseAJour;
    }
    
    /**
     * Valide le paiement d'une auto-école
     */
    public AutoEcole validerPaiement(Long id, String referencePaiement, String utilisateur) {
        AutoEcole autoEcole = autoEcoleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auto-école non trouvée"));
        
        // Vérifier le paiement
        boolean paiementValide = paiementService.verifierPaiement(referencePaiement, 
                                                                 autoEcole.getMontantPaiement());
        
        if (!paiementValide) {
            throw new RuntimeException("Paiement invalide");
        }
        
        // Mettre à jour le statut
        autoEcole.setStatut(StatutAutoEcole.PAIEMENT_VALIDE);
        autoEcole.setDatePaiement(LocalDateTime.now());
        autoEcole.setReferencePaiement(referencePaiement);
        
        AutoEcole autoEcoleMiseAJour = autoEcoleRepository.save(autoEcole);
        
        // Enregistrer l'audit
        enregistrerAudit(ActionAudit.PAIEMENT, "AutoEcole", autoEcole.getId(), 
                        utilisateur, "Paiement validé pour l'auto-école: " + autoEcole.getNom());
        
        // Envoyer notification
        notificationService.envoyerNotificationPaiementValide(autoEcoleMiseAJour);
        
        return autoEcoleMiseAJour;
    }
    
    /**
     * Programme une inspection
     */
    public AutoEcole programmerInspection(Long id, String inspecteurNom, String inspecteurPrenom, 
                                        String utilisateur) {
        AutoEcole autoEcole = autoEcoleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auto-école non trouvée"));
        
        // Vérifier que le paiement est validé
        if (autoEcole.getStatut() != StatutAutoEcole.PAIEMENT_VALIDE) {
            throw new RuntimeException("Le paiement doit être validé avant l'inspection");
        }
        
        // Mettre à jour le statut
        autoEcole.setStatut(StatutAutoEcole.INSPECTION_EN_COURS);
        autoEcole.setInspecteurNom(inspecteurNom);
        autoEcole.setInspecteurPrenom(inspecteurPrenom);
        
        AutoEcole autoEcoleMiseAJour = autoEcoleRepository.save(autoEcole);
        
        // Enregistrer l'audit
        enregistrerAudit(ActionAudit.INSPECTION, "AutoEcole", autoEcole.getId(), 
                        utilisateur, "Inspection programmée pour l'auto-école: " + autoEcole.getNom());
        
        return autoEcoleMiseAJour;
    }
    
    /**
     * Valide une inspection
     */
    public AutoEcole validerInspection(Long id, String rapportInspection, String utilisateur) {
        AutoEcole autoEcole = autoEcoleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auto-école non trouvée"));
        
        // Vérifier que l'inspection est en cours
        if (autoEcole.getStatut() != StatutAutoEcole.INSPECTION_EN_COURS) {
            throw new RuntimeException("L'inspection doit être en cours");
        }
        
        // Mettre à jour le statut
        autoEcole.setStatut(StatutAutoEcole.INSPECTION_VALIDEE);
        autoEcole.setRapportInspection(rapportInspection);
        autoEcole.setDateInspection(LocalDateTime.now());
        
        AutoEcole autoEcoleMiseAJour = autoEcoleRepository.save(autoEcole);
        
        // Enregistrer l'audit
        enregistrerAudit(ActionAudit.INSPECTION, "AutoEcole", autoEcole.getId(), 
                        utilisateur, "Inspection validée pour l'auto-école: " + autoEcole.getNom());
        
        return autoEcoleMiseAJour;
    }
    
    /**
     * Génère une autorisation provisoire
     */
    public AutoEcole genererAutorisationProvisoire(Long id, String utilisateur) {
        AutoEcole autoEcole = autoEcoleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auto-école non trouvée"));
        
        // Vérifier que l'inspection est validée
        if (autoEcole.getStatut() != StatutAutoEcole.INSPECTION_VALIDEE) {
            throw new RuntimeException("L'inspection doit être validée avant l'autorisation");
        }
        
        // Générer l'autorisation
        String autorisation = genererNumeroAutorisation();
        LocalDateTime dateExpiration = LocalDateTime.now().plusMonths(6);
        
        // Mettre à jour le statut
        autoEcole.setStatut(StatutAutoEcole.AUTORISATION_PROVISOIRE);
        autoEcole.setAutorisationProvisoire(autorisation);
        autoEcole.setDateAutorisation(LocalDateTime.now());
        autoEcole.setDateExpirationAutorisation(dateExpiration);
        
        AutoEcole autoEcoleMiseAJour = autoEcoleRepository.save(autoEcole);
        
        // Enregistrer l'audit
        enregistrerAudit(ActionAudit.APPROBATION, "AutoEcole", autoEcole.getId(), 
                        utilisateur, "Autorisation provisoire générée pour l'auto-école: " + autoEcole.getNom());
        
        // Envoyer notification
        notificationService.envoyerNotificationAutorisation(autoEcoleMiseAJour);
        
        return autoEcoleMiseAJour;
    }
    
    /**
     * Enrôle un candidat
     */
    public Candidat enrollerCandidat(Long autoEcoleId, Candidat candidat, String utilisateur) {
        AutoEcole autoEcole = autoEcoleRepository.findById(autoEcoleId)
                .orElseThrow(() -> new RuntimeException("Auto-école non trouvée"));
        
        // Vérifier que l'auto-école peut enrôler des candidats
        if (!autoEcole.getStatut().peutEnrollerCandidats()) {
            throw new RuntimeException("L'auto-école ne peut pas enrôler de candidats dans son état actuel");
        }
        
        // Générer les numéros
        candidat.setNumeroLicence(genererNumeroLicence());
        candidat.setNumeroEvaluation(genererNumeroEvaluation());
        
        // Générer le QR code
        String qrCode = qrCodeService.genererQRCode("CANDIDAT:" + candidat.getNumeroLicence());
        candidat.setQrCode(qrCode);
        
        // Associer à l'auto-école
        candidat.setAutoEcole(autoEcole);
        
        // Sauvegarder
        Candidat candidatSauvegarde = candidatRepository.save(candidat);
        
        // Enregistrer l'audit
        enregistrerAudit(ActionAudit.CREATION, "Candidat", candidatSauvegarde.getId(), 
                        utilisateur, "Candidat enrôlé: " + candidat.getNomComplet());
        
        // Envoyer notification
        notificationService.envoyerNotificationEnrolement(candidatSauvegarde);
        
        return candidatSauvegarde;
    }
    
    /**
     * Génère un numéro de demande unique
     */
    private String genererNumeroDemande() {
        String prefixe = "AE";
        String timestamp = String.valueOf(System.currentTimeMillis());
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return prefixe + timestamp + uuid;
    }
    
    /**
     * Génère un numéro d'autorisation unique
     */
    private String genererNumeroAutorisation() {
        String prefixe = "AUTH";
        String timestamp = String.valueOf(System.currentTimeMillis());
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return prefixe + timestamp + uuid;
    }
    
    /**
     * Génère un numéro de licence unique
     */
    private String genererNumeroLicence() {
        String prefixe = "LIC";
        String timestamp = String.valueOf(System.currentTimeMillis());
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return prefixe + timestamp + uuid;
    }
    
    /**
     * Génère un numéro d'évaluation unique
     */
    private String genererNumeroEvaluation() {
        String prefixe = "EVAL";
        String timestamp = String.valueOf(System.currentTimeMillis());
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return prefixe + timestamp + uuid;
    }
    
    /**
     * Enregistre un log d'audit
     */
    private void enregistrerAudit(ActionAudit action, String entite, Long entiteId, 
                                String utilisateur, String message) {
        enregistrerAudit(action, entite, entiteId, utilisateur, message, null, null);
    }
    
    /**
     * Enregistre un log d'audit avec données avant/après
     */
    private void enregistrerAudit(ActionAudit action, String entite, Long entiteId, 
                                String utilisateur, String message, String donneesAvant, String donneesApres) {
        AuditLog auditLog = new AuditLog(entite, entiteId, action, utilisateur, message);
        auditLog.setDonneesAvant(donneesAvant);
        auditLog.setDonneesApres(donneesApres);
        auditLog.setNiveauSecurite(determinerNiveauSecurite(action));
        auditLogRepository.save(auditLog);
    }
    
    /**
     * Détermine le niveau de sécurité d'une action
     */
    private NiveauSecurite determinerNiveauSecurite(ActionAudit action) {
        if (action.isCritique()) {
            return NiveauSecurite.CRITIQUE;
        } else if (action.isDonneesSensibles()) {
            return NiveauSecurite.SECURITE;
        } else if (action.isModification()) {
            return NiveauSecurite.WARNING;
        } else {
            return NiveauSecurite.INFO;
        }
    }
    
    /**
     * Sérialise une auto-école pour l'audit
     */
    private String serializeAutoEcole(AutoEcole autoEcole) {
        return String.format("AutoEcole{id=%d, nom='%s', statut=%s, email='%s'}", 
                           autoEcole.getId(), autoEcole.getNom(), 
                           autoEcole.getStatut(), autoEcole.getEmail());
    }
    
    /**
     * Liste les candidats d'une auto-école
     */
    @Transactional(readOnly = true)
    public List<Candidat> listerCandidats(Long autoEcoleId) {
        return candidatRepository.findByAutoEcoleId(autoEcoleId);
    }
    
    /**
     * Récupère les statistiques des auto-écoles
     */
    @Transactional(readOnly = true)
    public java.util.Map<String, Object> recupererStatistiques() {
        java.util.Map<String, Object> stats = new java.util.HashMap<>();
        
        // Statistiques générales
        long totalAutoEcoles = autoEcoleRepository.count();
        long totalCandidats = candidatRepository.count();
        
        // Statistiques par statut
        long autoEcolesEnAttente = autoEcoleRepository.countByStatut(StatutAutoEcole.EN_ATTENTE);
        long autoEcolesApprouvees = autoEcoleRepository.countByStatut(StatutAutoEcole.AUTORISATION_VALIDE);
        long autoEcolesRejetees = autoEcoleRepository.countByStatut(StatutAutoEcole.REJETE);
        
        // Statistiques des candidats
        long candidatsEnCours = candidatRepository.countByStatut(ga.dgtt.autoecole.model.StatutCandidat.EN_FORMATION);
        long candidatsReussis = candidatRepository.countByStatut(ga.dgtt.autoecole.model.StatutCandidat.EXAMEN_REUSSI);
        long candidatsEchecs = candidatRepository.countByStatut(ga.dgtt.autoecole.model.StatutCandidat.REJETE);
        
        stats.put("totalAutoEcoles", totalAutoEcoles);
        stats.put("totalCandidats", totalCandidats);
        stats.put("autoEcolesEnAttente", autoEcolesEnAttente);
        stats.put("autoEcolesApprouvees", autoEcolesApprouvees);
        stats.put("autoEcolesRejetees", autoEcolesRejetees);
        stats.put("candidatsEnCours", candidatsEnCours);
        stats.put("candidatsReussis", candidatsReussis);
        stats.put("candidatsEchecs", candidatsEchecs);
        
        return stats;
    }
}
