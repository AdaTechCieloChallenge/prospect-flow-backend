package br.com.adatech.prospectflow.infra.queue;

import br.com.adatech.prospectflow.core.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QueueJavaService {
    /** Estrutura de dados em java que representa uma fila FIFO **/
    private final Queue<Client> queue;

    @Autowired
    public QueueJavaService(Queue<Client> queue){
        this.queue = queue;
    }
    public void send(Client client){
      queue.offer(client);
    }
    public Client receive(){
       return queue.poll();
    }
    public Queue<Client> getQueue(){
        return queue;
    }
}
