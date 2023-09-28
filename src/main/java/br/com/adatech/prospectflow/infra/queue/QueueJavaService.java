package br.com.adatech.prospectflow.infra.queue;

import br.com.adatech.prospectflow.core.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QueueJavaService {
    /** Estrutura de dados em java que representa uma fila FIFO **/
    private Queue<Client> queue = null;

   // @Autowired
    public QueueJavaService(Queue<Client> queue){
       // this.queue = queue;
    }
    public QueueJavaService(){
       // this.queue = new ArrayDeque<>();
    }
    public void send(Client client){
      //  queue.offer(client);
    }
    public Client receive(){
       // return queue.poll();
        return null;
    }
    public Queue<Client> getQueue(){
        return queue;
    }
}
