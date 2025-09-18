package ga.dgtt.autres;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Application principale du service Autres (Partenaires Externes)
 * 
 * Ce service gère :
 * - L'intégration avec DGDI (Direction Générale Documentation et Immigration)
 * - L'intégration avec Police Nationale (Sécurité Routière)
 * - L'intégration avec Centre de Contrôle Technique
 * - L'intégration avec Compagnies d'Assurance
 * - L'intégration avec Douanes
 * - L'intégration avec Trésor Public
 * - L'intégration avec Interpol
 * - L'intégration avec Portail Citoyen
 * 
 * @author R-DGTT Development Team
 * @version 1.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing
public class AutresServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutresServiceApplication.class, args);
    }
}
