package br.com.adatech.prospectflow.controller;

import br.com.adatech.prospectflow.core.domain.Client;
import br.com.adatech.prospectflow.core.domain.ClientType;
import br.com.adatech.prospectflow.core.domain.LegalPerson;
import br.com.adatech.prospectflow.core.domain.NaturalPerson;
import br.com.adatech.prospectflow.core.usecases.dtos.LegalPersonDTO;
import br.com.adatech.prospectflow.core.usecases.dtos.NaturalPersonDTO;
import br.com.adatech.prospectflow.core.usecases.dtos.UpdateDTO;
import br.com.adatech.prospectflow.infra.database.ClientPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientPersistence clientPersistence;

    @Autowired
    public ClientService(ClientPersistence clientPersistence) {
        this.clientPersistence = clientPersistence;
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
        try{
            LegalPerson legalPerson = new LegalPerson(mcc, cpf, name, email, cnpj, corporateName);
            return ResponseEntity.ok(legalPerson);
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
        try{
            NaturalPerson naturalPerson = new NaturalPerson(mcc, cpf, name, email);
            return ResponseEntity.ok(naturalPerson);
        } catch(IllegalArgumentException exception){
            //Exception dispara pela validação
            System.err.println("An error occurred during data tranference for Natural Person: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }

    }
    /** Serviço responsável pela consulta de um prospect. **/
    public ResponseEntity<?> findClient(String clientId, String clientType) {
        try{
            if (clientPersistence.clientNotExists(clientId, ClientType.convertFromString(clientType))){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client not registered yet.");
            }
            Optional<Client> prospect = clientPersistence.findOne(clientId, ClientType.convertFromString(clientType));
            if (prospect.isPresent())
                return ResponseEntity.ok(prospect);
            else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client not registered yet");
        }catch (NoSuchElementException e){
            System.err.println("An error occured while consulting a client: "+ e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client not registered yet.");
        }
    }
    /** Serviço responsável pela alteração (atualização) dos dados de um determinado prospect. **/
    public ResponseEntity<?> update(String cnpjOrCpf, String clientType, UpdateDTO updateDTO) {
        ClientType type = ClientType.convertFromString(clientType);
        try{
            if(this.clientPersistence.clientNotExists(cnpjOrCpf, type)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client not registered yet.");
            }
            Client updatedClient;
            switch (type){
                case PF -> {
                    String cpf = updateDTO.cpf();
                    String mcc = updateDTO.mcc();
                    String name = updateDTO.name();
                    String email = updateDTO.email();
                    try{
                        //lança exceção em função das validações das regras.
                        updatedClient = new NaturalPerson(mcc, cpf, name, email);
                        Optional<Client> prospect = this.clientPersistence.findOne(cpf, type);

                        Client oldClient;
                        if(prospect.isPresent()) {
                            oldClient = prospect.get();
                            String uuid = oldClient.getUuid();
                            updatedClient.setUuid(uuid); //Finalização da tranferência de dados.

                            //chamar persistência para atualizar o registro.

                            return ResponseEntity.ok(updatedClient);
                        }
                        else
                            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred while updating.");
                    }catch (IllegalArgumentException e){
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
                    try{
                        updatedClient = new LegalPerson(mcc, cpf, name, email, cnpj, corporateName);
                        Optional<Client> prospect =  this.clientPersistence.findOne(cnpj, type);

                        Client oldClient;
                        if(prospect.isPresent()){
                            oldClient = prospect.get();
                            String uuid = oldClient.getUuid();
                            updatedClient.setUuid(uuid);

                            //chama a persistência

                            return ResponseEntity.ok(updatedClient);
                        }else
                            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred while updating.");
                    }catch (IllegalArgumentException e){
                        System.err.println("An error occurred during data tranference for update Legal Person: " + e.getMessage());
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
                    }
                }
            }
        }catch(NoSuchElementException e){
            System.err.println("Nothing found while consulting this client: "+ e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client not registered yet.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred during update.");
    }

    /** Serviço responsável pela exclusão dos dados de um determinado prospect. **/
    public ResponseEntity<?> delete(String clientId, ClientType type){
        try{
            if(this.clientPersistence.clientNotExists(clientId, type)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client not registered yet.");
            }
            return ResponseEntity.noContent().build();
        }catch (NoSuchElementException e){
            System.err.println("An error occured while consulting a client: "+ e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client not registered yet.");
        }
    }
}
