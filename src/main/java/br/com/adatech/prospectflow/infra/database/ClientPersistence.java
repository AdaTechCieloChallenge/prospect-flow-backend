package br.com.adatech.prospectflow.infra.database;


import br.com.adatech.prospectflow.adapters.ClientPersistenceAdapter;
import br.com.adatech.prospectflow.core.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientPersistence implements ClientPersistenceAdapter {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientPersistence(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
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
