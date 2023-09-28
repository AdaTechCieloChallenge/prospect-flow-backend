package br.com.adatech.prospectflow.infra.queue;

import br.com.adatech.prospectflow.adapters.QueueJavaAdapter;
import br.com.adatech.prospectflow.core.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Queue;

@Service
public class QueueJava implements QueueJavaAdapter {
    private final QueueJavaService queueJavaService;

    @Autowired
    public QueueJava(QueueJavaService queueJavaService){
        this.queueJavaService = queueJavaService;
    }
    @Override
    public void send(Client client) {
        this.queueJavaService.send(client);
    }
    @Override
    public Client consume() {
        return this.queueJavaService.receive();
    }
    @Override
    public Queue<Client> getQueue() {
        return this.queueJavaService.getQueue();
    }
    @Override
    public void remove(Client client) {
        this.queueJavaService.remove(client);
    }
}
