package br.com.adatech.prospectflow.application;

import br.com.adatech.prospectflow.adapters.QueueAdapter;
import br.com.adatech.prospectflow.core.usecases.QueueUseCase;
import br.com.adatech.prospectflow.core.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueueService implements QueueUseCase {
    private final QueueAdapter queueAdapter;
    @Autowired
    public QueueService(QueueAdapter queueAdapter) {
        this.queueAdapter = queueAdapter;
    }

    @Override
    public void send(Client client) {
        this.queueAdapter.send(client);
    }

    @Override
    public Client consume() {
        return this.queueAdapter.consume();
    }
}
