package ga.dgtt.permis.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service pour l'envoi de SMS
 */
@Service
public class SMSService {
    
    @Value("${app.sms.api.key:simulation_key}")
    private String smsApiKey;
    
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
            // Simulation de l'envoi de SMS
            System.out.println("=== SIMULATION SMS ===");
            System.out.println("Destinataire: " + numeroTelephone);
            System.out.println("Message: " + message);
            System.out.println("=====================");
            
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi du SMS: " + e.getMessage());
        }
    }
}
