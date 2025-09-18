package ga.dgtt.permis.service;

import ga.dgtt.permis.model.Examen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Service pour l'envoi de notifications
 */
@Service
public class NotificationService {
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private SMSService smsService;
    
    private String emailFrom = "noreply@dgtt-portail.com";
    
    /**
     * Envoie une notification d'examen programmé
     */
    public void envoyerNotificationExamenProgramme(Examen examen) {
        // Email
        envoyerEmailExamenProgramme(examen);
        
        // SMS
        envoyerSMSExamenProgramme(examen);
    }
    
    /**
     * Envoie une notification de résultat d'examen
     */
    public void envoyerNotificationResultatExamen(Examen examen) {
        // Email
        envoyerEmailResultatExamen(examen);
        
        // SMS
        envoyerSMSResultatExamen(examen);
    }
    
    /**
     * Envoie une notification à STIAS
     */
    public void envoyerNotificationStias(Examen examen) {
        // Email
        envoyerEmailStias(examen);
    }
    
    private void envoyerEmailExamenProgramme(Examen examen) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(examen.getCandidat().getAutoEcole().getEmail());
        message.setFrom(emailFrom);
        message.setSubject("R-DGTT - Examen programmé");
        
        String contenu = String.format(
            "Bonjour,\n\n" +
            "Un examen a été programmé pour votre candidat.\n\n" +
            "Détails :\n" +
            "- Candidat : %s\n" +
            "- Numéro d'examen : %s\n" +
            "- Date : %s\n" +
            "- Lieu : %s\n" +
            "- Examinateur : %s\n\n" +
            "Cordialement,\n" +
            "L'équipe R-DGTT",
            examen.getNomCompletCandidat(),
            examen.getNumeroExamen(),
            examen.getDateExamen(),
            examen.getLieuExamen(),
            examen.getNomCompletExaminateur()
        );
        
        message.setText(contenu);
        mailSender.send(message);
    }
    
    private void envoyerEmailResultatExamen(Examen examen) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(examen.getCandidat().getAutoEcole().getEmail());
        message.setFrom(emailFrom);
        message.setSubject("R-DGTT - Résultat d'examen");
        
        String contenu = String.format(
            "Bonjour,\n\n" +
            "L'examen de votre candidat est terminé.\n\n" +
            "Détails :\n" +
            "- Candidat : %s\n" +
            "- Note : %s/20\n" +
            "- Résultat : %s\n" +
            "- Commentaires : %s\n\n" +
            "Cordialement,\n" +
            "L'équipe R-DGTT",
            examen.getNomCompletCandidat(),
            examen.getNote(),
            examen.isReussi() ? "Réussi" : "Échec",
            examen.getCommentaires()
        );
        
        message.setText(contenu);
        mailSender.send(message);
    }
    
    private void envoyerEmailStias(Examen examen) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("stias@dgtt-portail.com");
        message.setFrom(emailFrom);
        message.setSubject("R-DGTT - Dossier à traiter");
        
        String contenu = String.format(
            "Bonjour,\n\n" +
            "Un dossier de permis est prêt pour traitement.\n\n" +
            "Détails :\n" +
            "- Candidat : %s\n" +
            "- Numéro d'examen : %s\n" +
            "- Résultat : %s\n" +
            "- Procès-verbal : %s\n\n" +
            "Cordialement,\n" +
            "L'équipe R-DGTT",
            examen.getNomCompletCandidat(),
            examen.getNumeroExamen(),
            examen.isReussi() ? "Réussi" : "Échec",
            examen.getProcesVerbalUrl()
        );
        
        message.setText(contenu);
        mailSender.send(message);
    }
    
    private void envoyerSMSExamenProgramme(Examen examen) {
        String message = String.format(
            "R-DGTT: Examen programmé pour %s le %s à %s. Examinateur: %s",
            examen.getNomCompletCandidat(),
            examen.getDateExamen(),
            examen.getLieuExamen(),
            examen.getNomCompletExaminateur()
        );
        
        smsService.envoyerSMS(examen.getCandidat().getAutoEcole().getTelephone(), message);
    }
    
    private void envoyerSMSResultatExamen(Examen examen) {
        String message = String.format(
            "R-DGTT: Examen de %s terminé. Note: %s/20. Résultat: %s",
            examen.getNomCompletCandidat(),
            examen.getNote(),
            examen.isReussi() ? "Réussi" : "Échec"
        );
        
        smsService.envoyerSMS(examen.getCandidat().getAutoEcole().getTelephone(), message);
    }
}
