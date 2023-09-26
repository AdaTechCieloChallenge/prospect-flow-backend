package br.com.adatech.prospectflow.adapters;

import br.com.adatech.prospectflow.core.domain.Client;
import br.com.adatech.prospectflow.core.domain.ClientType;

import java.util.Optional;

public interface ClientPersistenceAdapter {
    Client register(Client client);
    Client change(String cnpjOrCpf, ClientType clientType, Client updatedClient);
    Optional<Client> findOne(String cnpjOrCpf, ClientType clientType);
    void delete(String cnpjOrCpf, ClientType clientType);
}
