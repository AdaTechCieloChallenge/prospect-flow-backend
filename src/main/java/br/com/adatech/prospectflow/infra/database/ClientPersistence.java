package br.com.adatech.prospectflow.infra.database;


import br.com.adatech.prospectflow.adapters.ClientPersistenceAdapter;
import br.com.adatech.prospectflow.core.domain.Client;
import br.com.adatech.prospectflow.core.domain.ClientType;
import br.com.adatech.prospectflow.core.domain.LegalPerson;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ClientPersistence implements ClientPersistenceAdapter {
    private final ClientRepository clientRepository;
    private final LegalPersonRepository legalPersonRepository;
    private final NaturalPersonRepository naturalPersonRepository;

    @Autowired
    public ClientPersistence(ClientRepository clientRepository, LegalPersonRepository legalPersonRepository, NaturalPersonRepository naturalPersonRepository) {
        this.clientRepository = clientRepository;
        this.legalPersonRepository = legalPersonRepository;
        this.naturalPersonRepository = naturalPersonRepository;
    }

    @Override
    public Client register(Client client) {
        //Validando se o cadastro não existe para cada tipo de cliente.
        ClientType type = client.getType();
        switch (type){
            case PF -> {
                Optional<Client> prospect = this.findOne(client.getCpf(), type);
                //Se o cadastra já existir
                if(prospect.isPresent()){
                    throw new IllegalStateException("This client as a natural person already exists.");
                }
            }
            case PJ -> {
                LegalPerson legalPerson = (LegalPerson) client; //downcasting
                Optional<Client> prospect = this.findOne(legalPerson.getCnpj(), type);
                if(prospect.isPresent()){
                    throw new IllegalStateException("This client as a legal person already exists.");
                }
            }
            default -> throw new IllegalArgumentException("Invalid client type or id provided to find one.");
        }
        //Se não houver algum cadastro existente
        return this.clientRepository.save(client);
    }

    @Override
    public Client change(String cnpjOrCpf, ClientType clientType, Client updatedClient) {
        if(clientNotExists(cnpjOrCpf, clientType)){
            throw new EntityNotFoundException("Client not found.");
        }
        return this.clientRepository.save(updatedClient);
    }

    @Override
    public Optional<Client> findOne(String cnpjOrCpf, ClientType clientType) {
        switch (clientType){
            case PF -> {
                Optional<Client> prospect = this.naturalPersonRepository.findById(cnpjOrCpf).map(client -> (Client) client);
                if(prospect.isEmpty()){
                    throw new NoSuchElementException("This natural person client is not registered yet.");
                }
                return prospect;
            }
            case PJ -> {
                Optional<Client> prospect = this.legalPersonRepository.findById(cnpjOrCpf).map(client -> (Client) client);
                if(prospect.isEmpty()){
                    throw new NoSuchElementException("This legal person client is not registered yet.");
                }
                return prospect;
            }
            default -> throw new IllegalArgumentException("Invalid client type provided to find one.");
        }
    }

    @Override
    public void delete(String cnpjOrCpf, ClientType clientType) {
        if(clientNotExists(cnpjOrCpf, clientType)){
            throw new EntityNotFoundException("Client not found to be deleted.");
        }
        switch (clientType){
            case PF -> {
                this.naturalPersonRepository.deleteById(cnpjOrCpf);
            }
            case PJ -> {
                this.legalPersonRepository.deleteById(cnpjOrCpf);
            }
            default -> throw new IllegalArgumentException("Invalid client type provided to delete.");
        }
    }
    public boolean clientNotExists(String cnpjOrCpf, ClientType clientType){
        return this.findOne(cnpjOrCpf, clientType).isEmpty();
    }

}
