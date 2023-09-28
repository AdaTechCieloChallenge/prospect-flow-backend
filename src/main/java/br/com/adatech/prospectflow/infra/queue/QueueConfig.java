package br.com.adatech.prospectflow.infra.queue;

import br.com.adatech.prospectflow.core.domain.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayDeque;
import java.util.Queue;

@Configuration
public class QueueConfig {
    @Bean
    public Queue<Client> queue() {
        return new ArrayDeque<>();
    }
}
