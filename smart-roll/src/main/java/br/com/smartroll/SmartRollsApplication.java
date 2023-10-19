package br.com.smartroll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Classe que implementa a lógica principal da aplicação.
 */
@SpringBootApplication
@EntityScan("br.com.smartroll.repository.entity")
@EnableJpaRepositories("br.com.smartroll.repository")
@EnableJpaAuditing
public class SmartRollsApplication {
    /**
     * Lógica da main.
     * @param args Argumentos de entrada como linhas de comando.
     */
    public static void main(String[] args) {
        SpringApplication.run(SmartRollsApplication.class, args);
    }
}
