package br.com.adatech.prospectflow.adapters;

import br.com.adatech.prospectflow.core.domain.Client;

public interface QueueAdapter {
    void send(Client client);
    Client consume();
}
