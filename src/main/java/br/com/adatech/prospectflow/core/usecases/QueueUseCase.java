package br.com.adatech.prospectflow.core.usecases;

import br.com.adatech.prospectflow.core.domain.Client;

import java.util.Queue;

public interface QueueUseCase {
    void send(Client client);
    Client consume();
    Queue<Client> getQueue();
    void remove(Client client);
}
