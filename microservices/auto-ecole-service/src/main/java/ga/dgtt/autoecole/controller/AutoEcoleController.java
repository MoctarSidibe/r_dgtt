package ga.dgtt.autoecole.controller;

import ga.dgtt.autoecole.model.AutoEcole;
import ga.dgtt.autoecole.model.Candidat;
import ga.dgtt.autoecole.model.StatutAutoEcole;
import ga.dgtt.autoecole.service.AutoEcoleService;
import ga.dgtt.autoecole.service.PaiementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Contrôleur REST pour la gestion des auto-écoles
 * 
 * Ce contrôleur expose les endpoints pour la gestion des auto-écoles
 * incluant la création, consultation, validation, et gestion des candidats.
 */
@RestController
@RequestMapping("/api/auto-ecole")
@Tag(name = "Auto-École", description = "Gestion des auto-écoles")
public class AutoEcoleController {
    
    @Autowired
    private AutoEcoleService autoEcoleService;
    
    @Autowired
    private PaiementService paiementService;
    
    /**
     * Crée une nouvelle auto-école
     */
    @PostMapping
    @Operation(summary = "Créer une auto-école", description = "Crée une nouvelle demande d'ouverture d'auto-école")
    public ResponseEntity<AutoEcole> creerAutoEcole(
            @Valid @RequestBody AutoEcole autoEcole,
            @Parameter(description = "Nom d'utilisateur") @RequestHeader("X-User") String utilisateur) {
        
        AutoEcole autoEcoleCreee = autoEcoleService.creerAutoEcole(autoEcole, utilisateur);
        return ResponseEntity.status(HttpStatus.CREATED).body(autoEcoleCreee);
    }
    
    /**
     * Récupère toutes les auto-écoles avec pagination
     */
    @GetMapping
    @Operation(summary = "Lister les auto-écoles", description = "Récupère la liste des auto-écoles avec pagination")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DC') or hasRole('SEV') or hasRole('SAF') or hasRole('STIAS') or hasRole('DGTT')")
    public ResponseEntity<Page<AutoEcole>> listerAutoEcoles(Pageable pageable) {
        Page<AutoEcole> autoEcoles = autoEcoleService.trouverToutes(pageable);
        return ResponseEntity.ok(autoEcoles);
    }
    
    /**
     * Récupère une auto-école par ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une auto-école", description = "Récupère une auto-école par son ID")
    public ResponseEntity<AutoEcole> recupererAutoEcole(@PathVariable Long id) {
        Optional<AutoEcole> autoEcole = autoEcoleService.trouverParId(id);
        return autoEcole.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Récupère une auto-école par numéro de demande
     */
    @GetMapping("/demande/{numeroDemande}")
    @Operation(summary = "Récupérer par numéro de demande", description = "Récupère une auto-école par son numéro de demande")
    public ResponseEntity<AutoEcole> recupererParNumeroDemande(@PathVariable String numeroDemande) {
        Optional<AutoEcole> autoEcole = autoEcoleService.trouverParNumeroDemande(numeroDemande);
        return autoEcole.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Recherche des auto-écoles par critères
     */
    @GetMapping("/recherche")
    @Operation(summary = "Rechercher des auto-écoles", description = "Recherche des auto-écoles par critères")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DC') or hasRole('SEV') or hasRole('SAF') or hasRole('STIAS') or hasRole('DGTT')")
    public ResponseEntity<Page<AutoEcole>> rechercherAutoEcoles(
            @RequestParam(required = false) String ville,
            @RequestParam(required = false) String province,
            @RequestParam(required = false) StatutAutoEcole statut,
            @RequestParam(required = false) String nom,
            Pageable pageable) {
        
        Page<AutoEcole> autoEcoles = autoEcoleService.rechercherParCriteres(ville, province, statut, nom, pageable);
        return ResponseEntity.ok(autoEcoles);
    }
    
    /**
     * Récupère les auto-écoles par statut
     */
    @GetMapping("/statut/{statut}")
    @Operation(summary = "Lister par statut", description = "Récupère les auto-écoles par statut")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DC') or hasRole('SEV') or hasRole('SAF') or hasRole('STIAS') or hasRole('DGTT')")
    public ResponseEntity<List<AutoEcole>> listerParStatut(@PathVariable StatutAutoEcole statut) {
        List<AutoEcole> autoEcoles = autoEcoleService.trouverParStatut(statut);
        return ResponseEntity.ok(autoEcoles);
    }
    
    /**
     * Met à jour une auto-école
     */
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une auto-école", description = "Met à jour les informations d'une auto-école")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DC') or hasRole('SEV') or hasRole('SAF') or hasRole('STIAS') or hasRole('DGTT')")
    public ResponseEntity<AutoEcole> mettreAJourAutoEcole(
            @PathVariable Long id,
            @Valid @RequestBody AutoEcole autoEcole,
            @Parameter(description = "Nom d'utilisateur") @RequestHeader("X-User") String utilisateur) {
        
        autoEcole.setId(id);
        AutoEcole autoEcoleMiseAJour = autoEcoleService.mettreAJour(autoEcole, utilisateur);
        return ResponseEntity.ok(autoEcoleMiseAJour);
    }
    
    /**
     * Valide le paiement d'une auto-école
     */
    @PostMapping("/{id}/paiement/valider")
    @Operation(summary = "Valider un paiement", description = "Valide le paiement d'une auto-école")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SAF')")
    public ResponseEntity<AutoEcole> validerPaiement(
            @PathVariable Long id,
            @RequestParam String referencePaiement,
            @Parameter(description = "Nom d'utilisateur") @RequestHeader("X-User") String utilisateur) {
        
        AutoEcole autoEcole = autoEcoleService.validerPaiement(id, referencePaiement, utilisateur);
        return ResponseEntity.ok(autoEcole);
    }
    
    /**
     * Programme une inspection
     */
    @PostMapping("/{id}/inspection/programmer")
    @Operation(summary = "Programmer une inspection", description = "Programme une inspection pour une auto-école")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SEV')")
    public ResponseEntity<AutoEcole> programmerInspection(
            @PathVariable Long id,
            @RequestParam String inspecteurNom,
            @RequestParam String inspecteurPrenom,
            @Parameter(description = "Nom d'utilisateur") @RequestHeader("X-User") String utilisateur) {
        
        AutoEcole autoEcole = autoEcoleService.programmerInspection(id, inspecteurNom, inspecteurPrenom, utilisateur);
        return ResponseEntity.ok(autoEcole);
    }
    
    /**
     * Valide une inspection
     */
    @PostMapping("/{id}/inspection/valider")
    @Operation(summary = "Valider une inspection", description = "Valide une inspection d'auto-école")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SEV')")
    public ResponseEntity<AutoEcole> validerInspection(
            @PathVariable Long id,
            @RequestParam String rapportInspection,
            @Parameter(description = "Nom d'utilisateur") @RequestHeader("X-User") String utilisateur) {
        
        AutoEcole autoEcole = autoEcoleService.validerInspection(id, rapportInspection, utilisateur);
        return ResponseEntity.ok(autoEcole);
    }
    
    /**
     * Génère une autorisation provisoire
     */
    @PostMapping("/{id}/autorisation/generer")
    @Operation(summary = "Générer une autorisation", description = "Génère une autorisation provisoire pour une auto-école")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DC')")
    public ResponseEntity<AutoEcole> genererAutorisationProvisoire(
            @PathVariable Long id,
            @Parameter(description = "Nom d'utilisateur") @RequestHeader("X-User") String utilisateur) {
        
        AutoEcole autoEcole = autoEcoleService.genererAutorisationProvisoire(id, utilisateur);
        return ResponseEntity.ok(autoEcole);
    }
    
    /**
     * Enrôle un candidat
     */
    @PostMapping("/{id}/candidats")
    @Operation(summary = "Enrôler un candidat", description = "Enrôle un nouveau candidat dans une auto-école")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AUTO_ECOLE')")
    public ResponseEntity<Candidat> enrollerCandidat(
            @PathVariable Long id,
            @Valid @RequestBody Candidat candidat,
            @Parameter(description = "Nom d'utilisateur") @RequestHeader("X-User") String utilisateur) {
        
        Candidat candidatEnrole = autoEcoleService.enrollerCandidat(id, candidat, utilisateur);
        return ResponseEntity.status(HttpStatus.CREATED).body(candidatEnrole);
    }
    
    /**
     * Récupère les candidats d'une auto-école
     */
    @GetMapping("/{id}/candidats")
    @Operation(summary = "Lister les candidats", description = "Récupère la liste des candidats d'une auto-école")
    public ResponseEntity<List<Candidat>> listerCandidats(@PathVariable Long id) {
        List<Candidat> candidats = autoEcoleService.listerCandidats(id);
        return ResponseEntity.ok(candidats);
    }
    
    /**
     * Génère un lien de paiement
     */
    @PostMapping("/{id}/paiement/lien")
    @Operation(summary = "Générer un lien de paiement", description = "Génère un lien de paiement pour une auto-école")
    public ResponseEntity<String> genererLienPaiement(@PathVariable Long id) {
        // Vérifier que l'auto-école existe
        autoEcoleService.trouverParId(id)
                .orElseThrow(() -> new RuntimeException("Auto-école non trouvée"));
        
        String lien = paiementService.genererLienPaiement("DEMANDE_" + id, 100000.0, "Paiement auto-école");
        return ResponseEntity.ok(lien);
    }
    
    /**
     * Récupère les statistiques des auto-écoles
     */
    @GetMapping("/statistiques")
    @Operation(summary = "Récupérer les statistiques", description = "Récupère les statistiques des auto-écoles")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DC') or hasRole('SEV') or hasRole('SAF') or hasRole('STIAS') or hasRole('DGTT')")
    public ResponseEntity<Object> recupererStatistiques() {
        Map<String, Object> statistiques = autoEcoleService.recupererStatistiques();
        return ResponseEntity.ok(statistiques);
    }
}
