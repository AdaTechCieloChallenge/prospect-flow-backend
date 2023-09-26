package br.com.adatech.prospectflow.application;

import br.com.adatech.prospectflow.adapters.ClientPersistenceAdapter;
import br.com.adatech.prospectflow.core.domain.ClientType;
import br.com.adatech.prospectflow.core.usecases.ClientPersistenceUseCase;
import br.com.adatech.prospectflow.core.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        return null;
    }

    @Override
    public Client change(String cnpjOrCpf, ClientType clientType, Client updatedClient) {
        return null;
    }

    @Override
    public Optional<Client> findOne(String cnpjOrCpf, ClientType clientType) {
        return null;
    }

    @Override
    public void delete(String cnpjOrCpf, ClientType clientType) {

    }

}
