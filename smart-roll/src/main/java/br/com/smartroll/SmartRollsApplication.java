package br.com.smartroll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe que implementa a lógica principal da aplicação.
 */
@SpringBootApplication
public class SmartRollsApplication {
    /**
     * Lógica da main.
     * @param args Argumentos de entrada como linhas de comando.
     */
    public static void main(String[] args) {
        SpringApplication.run(SmartRollsApplication.class, args);
    }
}
