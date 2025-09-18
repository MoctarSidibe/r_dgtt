package ga.dgtt.usager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Application principale du service Usager
 * 
 * Ce service gère :
 * - L'authentification et autorisation des utilisateurs
 * - La gestion des rôles et permissions
 * - Les profils utilisateurs
 * - La sécurité et audit des connexions
 * - L'intégration avec les autres services
 * 
 * @author R-DGTT Development Team
 * @version 1.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing
@EnableAsync
@EnableScheduling
public class UsagerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsagerServiceApplication.class, args);
    }
}
