package br.com.adatech.prospectflow.core.usecases;

import br.com.adatech.prospectflow.core.domain.Client;

public interface QueueUseCase {
    void send(Client client);
    Client consume();
}
