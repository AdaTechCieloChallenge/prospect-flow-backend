package br.com.adatech.prospectflow.application;

import br.com.adatech.prospectflow.adapters.ClientPersistenceAdapter;
import br.com.adatech.prospectflow.core.domain.ClientType;
import br.com.adatech.prospectflow.core.usecases.ClientPersistenceUseCase;
import br.com.adatech.prospectflow.core.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientPersistenceService  implements ClientPersistenceUseCase {

    private final ClientPersistenceAdapter clientPersistenceAdapter;

    @Autowired
    public ClientPersistenceService(ClientPersistenceAdapter clientPersistenceAdapter){
        this.clientPersistenceAdapter = clientPersistenceAdapter;
    }
    @Override
    public Client register(Client client) {

        return this.clientPersistenceAdapter.register(client);
    }

    @Override
    public Client change(String cnpjOrCpf, ClientType clientType, Client updatedClient) {
        return this.clientPersistenceAdapter.change(cnpjOrCpf, clientType, updatedClient);
    }

    @Override
    public Optional<Client> findOne(String cnpjOrCpf, ClientType clientType) {
        return this.clientPersistenceAdapter.findOne(cnpjOrCpf, clientType);
    }

    @Override
    public void delete(String cnpjOrCpf, ClientType clientType) {
        this.clientPersistenceAdapter.delete(cnpjOrCpf, clientType);
    }

}
