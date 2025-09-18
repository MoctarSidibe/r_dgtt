package ga.dgtt.autoecole;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Application principale du service Auto-École
 * 
 * Ce service gère :
 * - Les demandes d'ouverture d'auto-écoles
 * - L'enrôlement des candidats
 * - La gestion des évaluations
 * - La génération de documents avec QR codes
 * - L'intégration avec les services de paiement
 * 
 * @author R-DGTT Development Team
 * @version 1.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing
@EnableAsync
@EnableScheduling
public class AutoEcoleServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoEcoleServiceApplication.class, args);
    }
}
