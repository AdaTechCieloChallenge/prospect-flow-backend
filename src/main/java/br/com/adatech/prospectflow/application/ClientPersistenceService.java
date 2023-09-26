package br.com.adatech.prospectflow.application;

import br.com.adatech.prospectflow.adapters.ClientPersistenceAdapter;
import br.com.adatech.prospectflow.core.ClientPersistenceUseCase;
import br.com.adatech.prospectflow.core.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientPersistenceService  implements ClientPersistenceUseCase {

    private final ClientPersistenceAdapter clientPersistenceAdapter;

    @Autowired
    public ClientPersistenceService(ClientPersistenceAdapter clientPersistenceAdapter){
        this.clientPersistenceAdapter = clientPersistenceAdapter;
    }
    @Override
    public Client register(Client client) {
        return null;
    }

    @Override
    public Client change(Client client) {
        return null;
    }

    @Override
    public Client findOne(Client client) {
        return null;
    }

    @Override
    public void delete(Client client) {

    }
}
