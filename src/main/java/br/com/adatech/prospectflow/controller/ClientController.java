package br.com.adatech.prospectflow.controller;

import br.com.adatech.prospectflow.core.domain.Client;
import br.com.adatech.prospectflow.core.domain.ClientType;
import br.com.adatech.prospectflow.core.usecases.dtos.LegalPersonDTO;
import br.com.adatech.prospectflow.core.usecases.dtos.NaturalPersonDTO;
import br.com.adatech.prospectflow.core.usecases.dtos.UpdateDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Queue;

@RestController
@RequestMapping("api/v1")
@Tag(name = "Prospects", description="Endpoints for managing prospects ")
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
    @GetMapping("/find-client/{cnpjOrCpf}/{clientType}")
    public ResponseEntity<?> findClient(@PathVariable(value = "cnpjOrCpf") String cnpjOrCpf, @PathVariable(value = "clientType") String clientType) {
        return this.clientService.findClient(cnpjOrCpf, clientType);
    }
    @PutMapping("/update")
    public ResponseEntity<?> update(
            @RequestParam("cnpjOrCpf") String cnpjOrCpf,
            @RequestParam("clientType") String type,
            @RequestBody UpdateDTO updateDTO
    ){
        return this.clientService.update(cnpjOrCpf, type, updateDTO);
    }
    @DeleteMapping("/delete/{cnpjOrCpf}/{clientType}")
    public ResponseEntity<?> delete(@PathVariable(value = "cnpjOrCpf") String cnpjOrCpf, @PathVariable(value = "clientType") String clientType){
        return this.clientService.delete(cnpjOrCpf, clientType);
    }

    @GetMapping("/consume-prospect")
    public ResponseEntity<?> dequeueNextProspect(){
        return this.clientService.dequeueNextProspect();
    }
    @GetMapping("/prospects")
    public ResponseEntity<?> getQueueOfProspects(){
        return this.clientService.getQueueOfProspects();
    }
}
