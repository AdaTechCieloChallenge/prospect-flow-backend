package br.com.adatech.prospectflow.infra.queue;

import br.com.adatech.prospectflow.core.domain.Client;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Queue;

@Service
public class FIFOService {
    /** Estrutura de dados em java que representa uma fila FIFO **/
    private final Queue<Client> queue;

    public FIFOService(){
        this.queue = new ArrayDeque<>();
    }
    public void send(Client client){
        queue.offer(client);
    }
    public Client receive(){
        return queue.poll();
    }
}
