package ga.dgtt.autoecole.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service pour l'envoi de SMS
 * 
 * Ce service gère l'envoi de SMS via l'API Airtel Money
 * (simulation pour le moment, intégration réelle à venir)
 */
@Service
public class SMSService {
    
    @Value("${app.sms.api.key:simulation_key}")
    private String smsApiKey;
    
    @Value("${app.sms.api.url:https://api.airtel.ga/sms}")
    private String smsApiUrl;
    
    @Value("${app.sms.enabled:true}")
    private boolean smsEnabled;
    
    /**
     * Envoie un SMS
     */
    public void envoyerSMS(String numeroTelephone, String message) {
        if (!smsEnabled) {
            System.out.println("SMS désactivé - Message pour " + numeroTelephone + ": " + message);
            return;
        }
        
        try {
            // Note: Intégration API SMS réelle à implémenter quand les clés API seront disponibles
            System.out.println("=== SIMULATION SMS ===");
            System.out.println("Destinataire: " + numeroTelephone);
            System.out.println("Message: " + message);
            System.out.println("API Key: " + smsApiKey);
            System.out.println("API URL: " + smsApiUrl);
            System.out.println("=====================");
            
            // Simulation d'un délai d'envoi
            Thread.sleep(100);
            
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi du SMS: " + e.getMessage());
        }
    }
    
    /**
     * Vérifie le statut d'un SMS envoyé
     */
    public String verifierStatutSMS(String messageId) {
        if (!smsEnabled) {
            return "SIMULATION";
        }
        
        try {
            // Note: Vérification du statut via l'API réelle à implémenter quand les clés API seront disponibles
            System.out.println("Vérification du statut SMS: " + messageId);
            return "DELIVERED";
            
        } catch (Exception e) {
            System.err.println("Erreur lors de la vérification du statut: " + e.getMessage());
            return "ERROR";
        }
    }
    
    /**
     * Envoie un SMS de confirmation de paiement
     */
    public void envoyerConfirmationPaiement(String numeroTelephone, String montant, String reference) {
        String message = String.format(
            "R-DGTT: Paiement de %s FCFA confirmé. Référence: %s. Merci!",
            montant, reference
        );
        envoyerSMS(numeroTelephone, message);
    }
    
    /**
     * Envoie un SMS de notification de statut
     */
    public void envoyerNotificationStatut(String numeroTelephone, String statut, String details) {
        String message = String.format(
            "R-DGTT: Votre demande est maintenant %s. %s",
            statut, details
        );
        envoyerSMS(numeroTelephone, message);
    }
    
    /**
     * Envoie un SMS de rappel
     */
    public void envoyerRappel(String numeroTelephone, String typeRappel, String date) {
        String message = String.format(
            "R-DGTT: Rappel - %s prévu le %s. Merci de vous préparer.",
            typeRappel, date
        );
        envoyerSMS(numeroTelephone, message);
    }
}