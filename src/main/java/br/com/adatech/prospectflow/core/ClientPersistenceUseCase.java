package br.com.adatech.prospectflow.core;

import br.com.adatech.prospectflow.core.domain.Client;

public interface ClientPersistenceUseCase {
    Client register(Client client);
    Client change(Client client);
    Client findOne(Client client);
    void delete(Client client);
}
