package br.com.adatech.prospectflow.controller;

import br.com.adatech.prospectflow.core.domain.Client;
import br.com.adatech.prospectflow.core.domain.ClientType;
import br.com.adatech.prospectflow.core.usecases.dtos.LegalPersonDTO;
import br.com.adatech.prospectflow.core.usecases.dtos.NaturalPersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class ClientController {
    private final ClientService clientService;
    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/create-legal-person")
    public ResponseEntity<?> createLegalPerson(@RequestBody LegalPersonDTO legalPersonDTO){
        return this.clientService.createLegalPerson(legalPersonDTO);
    }
    @PostMapping("/create-natural-person")
    public ResponseEntity<?> createNaturalPerson(@RequestBody NaturalPersonDTO naturalPersonDTO){
        return this.clientService.createNaturalPerson(naturalPersonDTO);
    }
    @GetMapping("/find-client/{clientId}/{clientType}")
    public ResponseEntity<?> findClient(@PathVariable(value = "clientId") String clientId, @PathVariable(value = "clientType") String clientType) {
        return this.clientService.findClient(clientId, clientType);
    }
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody String clientId, @RequestBody ClientType type, @RequestBody Client updatedClient){
        return this.clientService.update(clientId, type, updatedClient);
    }
    @DeleteMapping("/delete/{clientId}/{clientType}")
    public ResponseEntity<?> delete(@PathVariable(value = "clientId") String clientId, @PathVariable(value = "clientType") String clientType){
        return this.clientService.delete(clientId, ClientType.convertFromString(clientType));
    }
}
