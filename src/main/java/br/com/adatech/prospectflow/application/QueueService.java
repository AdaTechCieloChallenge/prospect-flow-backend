package br.com.adatech.prospectflow.application;

import br.com.adatech.prospectflow.adapters.QueueJavaAdapter;
import br.com.adatech.prospectflow.core.usecases.QueueUseCase;
import br.com.adatech.prospectflow.core.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Queue;

@Service
public class QueueService implements QueueUseCase {
    private final QueueJavaAdapter queueJavaAdapter;
    @Autowired
    public QueueService(QueueJavaAdapter queueJavaAdapter) {
        this.queueJavaAdapter = queueJavaAdapter;
    }

    @Override
    public void send(Client client) {
        this.queueJavaAdapter.send(client);
    }

    @Override
    public Client consume() {
        return this.queueJavaAdapter.consume();
    }

    @Override
    public Queue<Client> getQueue() {
        return this.queueJavaAdapter.getQueue();
    }
}
