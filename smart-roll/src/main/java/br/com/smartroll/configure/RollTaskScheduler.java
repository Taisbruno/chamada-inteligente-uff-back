package br.com.smartroll.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

/**
 * Classe responsável por configurar o TaskScheduler para agendamento de tarefas em toda a aplicação.
 */
@Configuration
public class RollTaskScheduler {

    /**
     * Cria e configura um TaskScheduler utilizando o ConcurrentTaskScheduler.
     *
     * @return Uma instância de TaskScheduler configurada para agendamento de tarefas concorrentes.
     */
    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }

}
