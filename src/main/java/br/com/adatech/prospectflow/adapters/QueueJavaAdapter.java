package br.com.adatech.prospectflow.adapters;

import br.com.adatech.prospectflow.core.domain.Client;

import java.util.Queue;

public interface QueueJavaAdapter {
    void send(Client client);
    Client consume();
    Queue<Client> getQueue();
}
