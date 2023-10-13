package br.com.smartroll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Classe que implementa a lógica principal da aplicação.
 */
@SpringBootApplication
@EntityScan(basePackages = "br.com.smartroll.repository.entity")
public class SmartRollsApplication {
    /**
     * Lógica da main.
     * @param args Argumentos de entrada como linhas de comando.
     */
    public static void main(String[] args) {
        SpringApplication.run(SmartRollsApplication.class, args);
    }
}
