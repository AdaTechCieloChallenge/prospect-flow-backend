package br.com.adatech.prospectflow.infra.database;


import br.com.adatech.prospectflow.adapters.ClientPersistenceAdapter;
import br.com.adatech.prospectflow.core.domain.Client;
import br.com.adatech.prospectflow.core.domain.ClientType;
import br.com.adatech.prospectflow.core.domain.LegalPerson;
import br.com.adatech.prospectflow.core.domain.NaturalPerson;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ClientPersistence implements ClientPersistenceAdapter {
    private final LegalPersonRepository legalPersonRepository;
    private final NaturalPersonRepository naturalPersonRepository;

    @Autowired
    public ClientPersistence(LegalPersonRepository legalPersonRepository, NaturalPersonRepository naturalPersonRepository) {
        this.legalPersonRepository = legalPersonRepository;
        this.naturalPersonRepository = naturalPersonRepository;
    }

    @Override
    public Client register(Client client) {
        ClientType type = client.getType();
        switch (type){
            case PF -> {
                try{
                    //Se cliente já existe.
                    if(!clientNotExists(client.getCpf(), client.getType())){
                        throw new EntityExistsException("This client as a natural person already exists.");
                    }
                    //Caso contrário persista-o
                    NaturalPerson naturalPerson = (NaturalPerson) client;
                    NaturalPerson naturalPersonSaved = null;
                    try{//save
                        naturalPersonSaved = this.naturalPersonRepository.save(naturalPerson);
                    }catch (PersistenceException e){
                        System.err.println("Error occurred during natural client persistence: " + e.getMessage());
                        System.out.println(client);
                        System.err.println("Not persisted.");
                        e.getCause();
                    }
                    return naturalPersonSaved;
                }catch(IllegalArgumentException e){
                    System.err.println("Error while veryfing if client exists: "+ e.getMessage());
                    e.getCause();
                }
            }
            case PJ -> {
                try{ //findOne
                    LegalPerson legalPerson = (LegalPerson) client; //downcasting
                    //Se já existe
                    if(!clientNotExists(legalPerson.getCnpj(), legalPerson.getType())){
                        throw new EntityExistsException("This client as a legal person already exists.");
                    }
                    //Caso contrário persista-o

                    LegalPerson legalPersonSaved = null;
                    try{//save
                        legalPersonSaved = this.legalPersonRepository.save(legalPerson);
                    }catch (PersistenceException e){
                        System.err.println("Error occurred during legal client persistence: " + e.getMessage());
                        System.out.println(client);
                        System.err.println("Not persisted.");
                        e.getCause();
                    }
                    return legalPersonSaved;
                }catch(IllegalArgumentException e){
                    System.err.println("Error while veryfing if client exists: "+ e.getMessage());
                    e.getCause();
                }
            }
            default -> throw new IllegalArgumentException("Invalid client type or id provided to find one.");
        }
        throw new IllegalArgumentException("Invalid client type or id provided to register method.");
    }

    @Override
    public Client change(String cnpjOrCpf, ClientType clientType, Client updatedClient) {
        if(clientNotExists(cnpjOrCpf, clientType)){
            throw new EntityNotFoundException("Client not registered yet.");
        }
        switch (clientType){
            case PF -> {
                NaturalPerson naturalPerson = (NaturalPerson) updatedClient;
                NaturalPerson naturalPersonSaved = null;
                try{//save (update)
                    naturalPersonSaved = this.naturalPersonRepository.save(naturalPerson);
                }catch (PersistenceException e){
                    System.err.println("Error occurred during natural client uptade: " + e.getMessage());
                    System.out.println(updatedClient);
                    System.err.println("Not persisted.");
                    e.getCause();
                }
                return naturalPersonSaved;
            }
            case PJ -> {
                LegalPerson legalPerson = (LegalPerson) updatedClient;
                LegalPerson legalPersonSaved = null;
                try{//save
                    legalPersonSaved = this.legalPersonRepository.save(legalPerson);
                }catch (PersistenceException e){
                    System.err.println("Error occurred during legal client update: " + e.getMessage());
                    System.out.println(updatedClient);
                    System.err.println("Not persisted.");
                    e.getCause();
                }
                return legalPersonSaved;
            }
            default -> throw new IllegalArgumentException("Invalid client type provided to find one.");
        }
    }

    @Override
    public Optional<Client> findOne(String cnpjOrCpf, ClientType clientType) {
        switch (clientType){
            case PF -> {
                try{
                    return this.naturalPersonRepository.findByCpf(cnpjOrCpf).map(client -> (Client) client);
                }catch(Exception e){
                    return Optional.empty();
                }
            }
            case PJ -> {
                try{
                    return this.legalPersonRepository.findByCnpj(cnpjOrCpf).map(client -> (Client) client);
                }catch (Exception e){
                    return Optional.empty();
                }
            }
            default -> throw new IllegalArgumentException("Invalid client type provided to find one.");
        }
    }

    @Override
    @Transactional
    public void delete(String cnpjOrCpf, ClientType clientType) {
        if(clientNotExists(cnpjOrCpf, clientType)){
            throw new EntityNotFoundException("Client not registered yet.");
        }
        switch (clientType){
            case PF -> this.naturalPersonRepository.deleteByCpf(cnpjOrCpf);
            case PJ -> this.legalPersonRepository.deleteByCnpj(cnpjOrCpf);
            default -> throw new IllegalArgumentException("Invalid client type provided to delete.");
        }
    }
    public boolean clientNotExists(String cnpjOrCpf, ClientType clientType){
        Optional<Client> clientFound;
        try{
            clientFound = this.findOne(cnpjOrCpf, clientType);
            return clientFound.isEmpty();
        }catch(IllegalArgumentException e){
            System.err.println("Error while veryfing if client exists: "+ e.getMessage());
            e.getCause();
            throw new RuntimeException("Invalid argument: " + e.getMessage());
        }catch (NoSuchElementException notfound){
            return true;
        }
    }
}
