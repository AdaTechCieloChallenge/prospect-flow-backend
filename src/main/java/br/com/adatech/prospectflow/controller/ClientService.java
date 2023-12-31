package br.com.adatech.prospectflow.controller;

import br.com.adatech.prospectflow.adapters.ClientPersistenceAdapter;
import br.com.adatech.prospectflow.adapters.QueueJavaAdapter;
import br.com.adatech.prospectflow.core.domain.Client;
import br.com.adatech.prospectflow.core.domain.ClientType;
import br.com.adatech.prospectflow.core.domain.LegalPerson;
import br.com.adatech.prospectflow.core.domain.NaturalPerson;
import br.com.adatech.prospectflow.core.usecases.dtos.LegalPersonDTO;
import br.com.adatech.prospectflow.core.usecases.dtos.NaturalPersonDTO;
import br.com.adatech.prospectflow.core.usecases.dtos.UpdateDTO;
import br.com.adatech.prospectflow.infra.database.ClientPersistence;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Queue;

@Service
public class ClientService {
    private final ClientPersistenceAdapter clientPersistence;
    private final QueueJavaAdapter queueServiceJavai;

    @Autowired
    public ClientService(ClientPersistence clientPersistence, QueueJavaAdapter queueJava) {
        this.clientPersistence = clientPersistence;
        this.queueServiceJavai = queueJava;
    }
    /** Serviço responsável pelo cadastro de um prospect do tipo Pessoa Jurídica. **/
    public ResponseEntity<?> createLegalPerson(LegalPersonDTO legalPersonDTO){
        String cnpj = legalPersonDTO.cnpj();//CNPJ
        String corporateName = legalPersonDTO.corporateName(); //Razão Social
        String mcc = legalPersonDTO.mcc(); //Merchant Category Code;
        String cpf = legalPersonDTO.cpf(); //CPF do contato do estabelecimento
        String name = legalPersonDTO.name(); //None do contato do estabelecimento
        String email = legalPersonDTO.email(); //Email do contato do estabelecimento
        /*
         Validação dos dados ocorrendo no escopo da fase de cadastro, conforme especificação,
         habilitada pela modelagem do domínio.
         Isso torna a aplicação coerente com o Princípio da Responsabilidade Única.
         */
        try{//legalPerson
            LegalPerson legalPerson = new LegalPerson(mcc, cpf, name, email, cnpj, corporateName);

            //Chamar persistência.
            LegalPerson prospectRegistered = null;
            try{//register
                prospectRegistered = (LegalPerson) this.clientPersistence.register(legalPerson);

                //Envia prospect registrado para fila de atendimento.
                queueServiceJavai.send(prospectRegistered);

                return ResponseEntity.status(HttpStatus.CREATED).body(prospectRegistered);
            }catch(EntityExistsException | IllegalArgumentException e){
                System.err.println("An error occurred during registration process of legal person: " + e.getMessage());
                e.getCause();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        } catch(IllegalArgumentException exception){
            //Exception dispara pela validação
            System.err.println("An error occurred during data tranference for Legal Person: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }
    /** Serviço responsável pelo cadastro de um prospect do tipo Pessoa Física. **/
    public ResponseEntity<?> createNaturalPerson(NaturalPersonDTO naturalPersonDTO){
        String mcc = naturalPersonDTO.mcc(); //Merchant Category Code;
        String cpf = naturalPersonDTO.cpf(); //CPF da pessoa
        String name = naturalPersonDTO.name(); //None da pessoa
        String email = naturalPersonDTO.email(); //Email da pessoa
        /*
         Validação dos dados ocorrendo no escopo da fase de cadastro, conforme especificação,
         habilitada pela modelagem do domínio.
         Isso torna a aplicação coerente com o Princípio da Responsabilidade Única.
         */
        try{//naturalPerson
            NaturalPerson naturalPerson = new NaturalPerson(mcc, cpf, name, email);
            //Chamar Persistência
            NaturalPerson prospectRegistered = null;
            try{//register
                prospectRegistered = (NaturalPerson) this.clientPersistence.register(naturalPerson);

                //Envia prospect registrado para fila de atendimento.
                queueServiceJavai.send(prospectRegistered);

                return ResponseEntity.status(HttpStatus.CREATED).body(prospectRegistered);
            }catch(EntityExistsException | IllegalArgumentException e){
                System.err.println("An error occurred during registration process of natural person: "+ e.getMessage());
                e.getCause();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        } catch(IllegalArgumentException exception){
            //Exception dispara pela validação
            System.err.println("An error occurred during data tranference for NaturalPerson: "+ exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }
    /** Serviço responsável pela consulta de um prospect. **/
    public ResponseEntity<?> findClient(String cnpjOrCpf, String clientType) {
        try{
            if (clientPersistence.clientNotExists(cnpjOrCpf, ClientType.convertFromString(clientType))){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not registered yet.");
            }
            Optional<Client> prospect = clientPersistence.findOne(cnpjOrCpf, ClientType.convertFromString(clientType));
            if (prospect.isPresent())
                return ResponseEntity.ok(prospect.get());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client not registered yet");
        }catch (NoSuchElementException e){
            System.err.println("An error occured while consulting a client: "+ e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client not registered yet.");
        }catch(IllegalArgumentException illegal){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(illegal.getMessage());
        }
    }
    /** Serviço responsável pela alteração (atualização) dos dados de um determinado prospect. **/
    public ResponseEntity<?> update(String cnpjOrCpf, String clientType, UpdateDTO updateDTO) {
        try{
            ClientType type = ClientType.convertFromString(clientType);
            if(this.clientPersistence.clientNotExists(cnpjOrCpf, type)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not registered yet.");
            }
            Client updatedClient;
            switch (type){
                case PF -> {
                    String cpf = updateDTO.cpf();
                    String mcc = updateDTO.mcc();
                    String name = updateDTO.name();
                    String email = updateDTO.email();

                    try{//Regras disparam exceptions.
                        Timestamp updatedAt= new Timestamp(System.currentTimeMillis());
                        updatedClient = new NaturalPerson(mcc, cpf, name, email, updatedAt);
                        Optional<Client> prospect = this.clientPersistence.findOne(cnpjOrCpf, type);

                        Client oldClient;
                        if(prospect.isPresent()) {
                            oldClient = prospect.get();
                            Timestamp oldCreatedAt = oldClient.getCreatedAt();
                            int oldVersion = oldClient.getVersion();
                            String uuid = oldClient.getUuid();
                            updatedClient.setUuid(uuid); //Finalização da tranferência de dados.

                            //Atualizar versões para remanejar a posição do prospect alterado na fila.
                            updatedClient.setVersion(oldVersion + 1);
                            updatedClient.setCreatedAt(oldCreatedAt);

                            //Persistência para atualizar (alterar) o registro.
                            try{//change
                                this.clientPersistence.change(cnpjOrCpf, type, updatedClient);

                                //Remove a versão antiga da fila e manda o com a nova para o final dela.
                                queueServiceJavai.remove(oldClient);
                                queueServiceJavai.send(updatedClient);

                                return ResponseEntity.ok(updatedClient);
                            }catch(EntityNotFoundException e){
                                System.err.println("Update failure during update of natural person: " + e.getMessage());
                                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
                            }catch(IllegalArgumentException illegal){
                                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(illegal.getMessage());
                            }
                        }
                        else
                            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client is not registered yet.");
                    }catch (IllegalArgumentException | NoSuchElementException e){
                        System.err.println("An error occurred during data tranference for update Natural Person: " + e.getMessage());
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
                    }
                }
                case PJ -> {
                    String cnpj = updateDTO.cnpj();
                    String corporateName = updateDTO.corporateName();
                    String mcc = updateDTO.mcc();
                    String cpf = updateDTO.cpf();
                    String name = updateDTO.name();
                    String email = updateDTO.email();
                    try{//Regras
                        Timestamp updatedAt= new Timestamp(System.currentTimeMillis());
                        updatedClient = new LegalPerson(mcc, cpf, name, email, cnpj, corporateName, updatedAt);
                        Optional<Client> prospect =  this.clientPersistence.findOne(cnpjOrCpf, type);

                        Client oldClient;
                        if(prospect.isPresent()){
                            oldClient = prospect.get();
                            Timestamp oldCreatedAt = oldClient.getCreatedAt();
                            int oldVersion = oldClient.getVersion();
                            String uuid = oldClient.getUuid();
                            updatedClient.setUuid(uuid);

                            //Atualizar versões para remanejar a posição do prospect alterado na fila.
                            updatedClient.setVersion(oldVersion + 1);
                            updatedClient.setCreatedAt(oldCreatedAt);

                            //Persistência para atualizar (alterar) o registro.
                            try{//change
                                this.clientPersistence.change(cnpjOrCpf, type, updatedClient);

                                queueServiceJavai.remove(oldClient);
                                queueServiceJavai.send(updatedClient);

                                return ResponseEntity.ok(updatedClient);
                            }catch(EntityNotFoundException e){
                                System.err.println("Update failure during persistence of LegalPerson: " + e.getMessage());
                                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
                            }catch (IllegalArgumentException illegal){
                                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(illegal.getMessage());
                            }
                        }else
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Prospect is not present to be updated.");
                    }catch (IllegalArgumentException | NoSuchElementException e){
                        System.err.println("An error occurred during data tranference for update Legal Person: " + e.getMessage());
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
                    }
                }
            }
        }catch(NoSuchElementException e){
            System.err.println("Nothing found while consulting this client: "+ e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not registered yet.");
        }catch (IllegalArgumentException illegal){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(illegal.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An unknown error occurred during update.");
    }

    /** Serviço responsável pela exclusão dos dados de um determinado prospect. **/
    public ResponseEntity<?> delete(String cnpjOrCpf, String clientType){
        try{
            ClientType type = ClientType.convertFromString(clientType);
            if(this.clientPersistence.clientNotExists(cnpjOrCpf, type)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not registered yet.");
            }

            Optional<Client> prospectToBeDeleted = this.clientPersistence.findOne(cnpjOrCpf, ClientType.convertFromString(clientType));
            //Cliente é removido da fila ao ser deletado, caso esteja lá.
            prospectToBeDeleted.ifPresent(queueServiceJavai::remove);

            this.clientPersistence.delete(cnpjOrCpf, type);


            return ResponseEntity.noContent().build();
        }catch (NoSuchElementException | EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not registered yet.");
        }catch (IllegalArgumentException illegal){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(illegal.getMessage());
        }
    }

    /** Serviço responsável pela consumo dos prospects na fila. **/
    public ResponseEntity<?> dequeueNextProspect(){
        Client prospect = queueServiceJavai.consume();
        if(prospect == null){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(prospect);
    }
    /** Serviço responsável por mostrar os prospects da fila. **/
    public ResponseEntity<?> getQueueOfProspects(){
        Queue<Client> prospects = queueServiceJavai.getQueue();
        if(prospects.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return new ResponseEntity<>(prospects, HttpStatus.OK);
    }
}
