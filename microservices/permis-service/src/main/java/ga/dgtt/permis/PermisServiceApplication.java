package ga.dgtt.permis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Application principale du service Permis de Conduire
 * 
 * Ce service gère :
 * - La planification des examens
 * - La génération des procès-verbaux
 * - La validation des dossiers candidats
 * - La coordination avec STIAS
 * - La signature numérique des documents
 * 
 * @author R-DGTT Development Team
 * @version 1.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing
@EnableAsync
@EnableScheduling
public class PermisServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PermisServiceApplication.class, args);
    }
}
