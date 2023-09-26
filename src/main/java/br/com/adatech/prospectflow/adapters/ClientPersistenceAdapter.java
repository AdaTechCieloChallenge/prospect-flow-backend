package br.com.adatech.prospectflow.adapters;

import br.com.adatech.prospectflow.core.domain.Client;

public interface ClientPersistenceAdapter {
    Client register(Client client);
    Client change(Client client);
    Client findOne(Client client);
    void delete(Client client);
}
