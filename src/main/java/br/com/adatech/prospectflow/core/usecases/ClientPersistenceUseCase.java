package br.com.adatech.prospectflow.core.usecases;

import br.com.adatech.prospectflow.core.domain.Client;
import br.com.adatech.prospectflow.core.domain.ClientType;

import java.util.Optional;

public interface ClientPersistenceUseCase {
    Client register(Client client);
    Client change(String cnpjOrCpf, ClientType clientType, Client updatedClient);
    Optional<Client> findOne(String cnpjOrCpf, ClientType clientType);
    boolean clientNotExists(String cnpjOrCpf, ClientType clientType);
    void delete(String cnpjOrCpf, ClientType clientType);
}
