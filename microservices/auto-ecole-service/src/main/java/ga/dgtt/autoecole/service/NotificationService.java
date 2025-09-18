package ga.dgtt.autoecole.service;

import ga.dgtt.autoecole.model.AutoEcole;
import ga.dgtt.autoecole.model.Candidat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Service pour l'envoi de notifications
 * 
 * Ce service gère l'envoi d'emails et SMS pour notifier les utilisateurs
 * des changements de statut et des actions importantes.
 */
@Service
public class NotificationService {
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private SMSService smsService;
    
    @Value("${app.notification.email.from:noreply@dgtt-portail.com}")
    private String emailFrom;
    
    @Value("${app.notification.sms.enabled:true}")
    private boolean smsEnabled;
    
    /**
     * Envoie une notification de création d'auto-école
     */
    public void envoyerNotificationCreation(AutoEcole autoEcole) {
        // Email
        envoyerEmailCreation(autoEcole);
        
        // SMS
        if (smsEnabled) {
            envoyerSMSCreation(autoEcole);
        }
    }
    
    /**
     * Envoie une notification de paiement validé
     */
    public void envoyerNotificationPaiementValide(AutoEcole autoEcole) {
        // Email
        envoyerEmailPaiementValide(autoEcole);
        
        // SMS
        if (smsEnabled) {
            envoyerSMSPaiementValide(autoEcole);
        }
    }
    
    /**
     * Envoie une notification d'autorisation
     */
    public void envoyerNotificationAutorisation(AutoEcole autoEcole) {
        // Email
        envoyerEmailAutorisation(autoEcole);
        
        // SMS
        if (smsEnabled) {
            envoyerSMSAutorisation(autoEcole);
        }
    }
    
    /**
     * Envoie une notification d'enrôlement de candidat
     */
    public void envoyerNotificationEnrolement(Candidat candidat) {
        // Email
        envoyerEmailEnrolement(candidat);
        
        // SMS
        if (smsEnabled) {
            envoyerSMSEnrolement(candidat);
        }
    }
    
    /**
     * Envoie un email de création d'auto-école
     */
    private void envoyerEmailCreation(AutoEcole autoEcole) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(autoEcole.getEmail());
        message.setFrom(emailFrom);
        message.setSubject("R-DGTT - Demande d'ouverture d'auto-école créée");
        
        String contenu = String.format(
            "Bonjour %s,\n\n" +
            "Votre demande d'ouverture d'auto-école a été créée avec succès.\n\n" +
            "Détails de la demande :\n" +
            "- Numéro de demande : %s\n" +
            "- Nom de l'auto-école : %s\n" +
            "- Statut : %s\n\n" +
            "Vous recevrez un SMS avec le lien de paiement sous peu.\n\n" +
            "Cordialement,\n" +
            "L'équipe R-DGTT",
            autoEcole.getNomCompletProprietaire(),
            autoEcole.getNumeroDemande(),
            autoEcole.getNom(),
            autoEcole.getStatut().getLibelle()
        );
        
        message.setText(contenu);
        mailSender.send(message);
    }
    
    /**
     * Envoie un email de paiement validé
     */
    private void envoyerEmailPaiementValide(AutoEcole autoEcole) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(autoEcole.getEmail());
        message.setFrom(emailFrom);
        message.setSubject("R-DGTT - Paiement validé");
        
        String contenu = String.format(
            "Bonjour %s,\n\n" +
            "Votre paiement a été validé avec succès.\n\n" +
            "Détails :\n" +
            "- Montant : %s FCFA\n" +
            "- Référence : %s\n" +
            "- Date : %s\n\n" +
            "Votre demande sera maintenant traitée par nos services.\n\n" +
            "Cordialement,\n" +
            "L'équipe R-DGTT",
            autoEcole.getNomCompletProprietaire(),
            autoEcole.getMontantPaiement(),
            autoEcole.getReferencePaiement(),
            autoEcole.getDatePaiement()
        );
        
        message.setText(contenu);
        mailSender.send(message);
    }
    
    /**
     * Envoie un email d'autorisation
     */
    private void envoyerEmailAutorisation(AutoEcole autoEcole) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(autoEcole.getEmail());
        message.setFrom(emailFrom);
        message.setSubject("R-DGTT - Autorisation provisoire accordée");
        
        String contenu = String.format(
            "Bonjour %s,\n\n" +
            "Félicitations ! Votre autorisation provisoire d'exploitation a été accordée.\n\n" +
            "Détails :\n" +
            "- Numéro d'autorisation : %s\n" +
            "- Date d'émission : %s\n" +
            "- Date d'expiration : %s\n\n" +
            "Vous pouvez maintenant commencer vos activités d'auto-école.\n\n" +
            "Cordialement,\n" +
            "L'équipe R-DGTT",
            autoEcole.getNomCompletProprietaire(),
            autoEcole.getAutorisationProvisoire(),
            autoEcole.getDateAutorisation(),
            autoEcole.getDateExpirationAutorisation()
        );
        
        message.setText(contenu);
        mailSender.send(message);
    }
    
    /**
     * Envoie un email d'enrôlement de candidat
     */
    private void envoyerEmailEnrolement(Candidat candidat) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(candidat.getAutoEcole().getEmail());
        message.setFrom(emailFrom);
        message.setSubject("R-DGTT - Nouveau candidat enrôlé");
        
        String contenu = String.format(
            "Bonjour,\n\n" +
            "Un nouveau candidat a été enrôlé dans votre auto-école.\n\n" +
            "Détails du candidat :\n" +
            "- Nom : %s\n" +
            "- Numéro de licence : %s\n" +
            "- Catégorie : %s\n" +
            "- Date d'enrôlement : %s\n\n" +
            "Cordialement,\n" +
            "L'équipe R-DGTT",
            candidat.getNomComplet(),
            candidat.getNumeroLicence(),
            candidat.getCategoriePermis(),
            candidat.getDateCreation()
        );
        
        message.setText(contenu);
        mailSender.send(message);
    }
    
    /**
     * Envoie un SMS de création d'auto-école
     */
    private void envoyerSMSCreation(AutoEcole autoEcole) {
        String message = String.format(
            "R-DGTT: Votre demande d'ouverture d'auto-école %s a été créée. " +
            "Numéro: %s. Vous recevrez le lien de paiement par email.",
            autoEcole.getNom(),
            autoEcole.getNumeroDemande()
        );
        
        smsService.envoyerSMS(autoEcole.getTelephone(), message);
    }
    
    /**
     * Envoie un SMS de paiement validé
     */
    private void envoyerSMSPaiementValide(AutoEcole autoEcole) {
        String message = String.format(
            "R-DGTT: Votre paiement de %s FCFA a été validé. " +
            "Référence: %s. Votre demande sera traitée sous peu.",
            autoEcole.getMontantPaiement(),
            autoEcole.getReferencePaiement()
        );
        
        smsService.envoyerSMS(autoEcole.getTelephone(), message);
    }
    
    /**
     * Envoie un SMS d'autorisation
     */
    private void envoyerSMSAutorisation(AutoEcole autoEcole) {
        String message = String.format(
            "R-DGTT: Félicitations ! Votre autorisation provisoire %s a été accordée. " +
            "Valable jusqu'au %s. Vous pouvez commencer vos activités.",
            autoEcole.getAutorisationProvisoire(),
            autoEcole.getDateExpirationAutorisation()
        );
        
        smsService.envoyerSMS(autoEcole.getTelephone(), message);
    }
    
    /**
     * Envoie un SMS d'enrôlement de candidat
     */
    private void envoyerSMSEnrolement(Candidat candidat) {
        String message = String.format(
            "R-DGTT: Candidat %s enrôlé dans votre auto-école. " +
            "Numéro de licence: %s. Catégorie: %s.",
            candidat.getNomComplet(),
            candidat.getNumeroLicence(),
            candidat.getCategoriePermis()
        );
        
        smsService.envoyerSMS(candidat.getAutoEcole().getTelephone(), message);
    }
}
