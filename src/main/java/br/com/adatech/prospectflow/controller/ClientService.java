package br.com.adatech.prospectflow.controller;

import br.com.adatech.prospectflow.core.domain.Client;
import br.com.adatech.prospectflow.core.domain.ClientType;
import br.com.adatech.prospectflow.core.domain.LegalPerson;
import br.com.adatech.prospectflow.core.domain.NaturalPerson;
import br.com.adatech.prospectflow.core.usecases.dtos.LegalPersonDTO;
import br.com.adatech.prospectflow.core.usecases.dtos.NaturalPersonDTO;
import br.com.adatech.prospectflow.infra.database.ClientPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
        if (clientPersistence.clientNotExists(clientId, ClientType.convertFromString(clientType))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client not registered yet.");
        }
        Optional<Client> prospect = clientPersistence.findOne(clientId, ClientType.convertFromString(clientType));
        if (prospect.isPresent())
            return ResponseEntity.ok(prospect);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client not registered yet");
    }
    /** Serviço responsável pela alteração (atualização) dos dados de um determinado prospect. **/
    public ResponseEntity<?> update(String clientId, ClientType type, Client updatedClient){
        if(this.clientPersistence.clientNotExists(clientId, type)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client not registered yet.");
        }
        return ResponseEntity.ok(this.clientPersistence.change(clientId, type, updatedClient));
    }
    /** Serviço responsável pela exclusão dos dados de um determinado prospect. **/
    public ResponseEntity<?> delete(String clientId, ClientType type){
        if(this.clientPersistence.clientNotExists(clientId, type)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client not registered yet.");
        }
        return ResponseEntity.noContent().build();
    }
}
