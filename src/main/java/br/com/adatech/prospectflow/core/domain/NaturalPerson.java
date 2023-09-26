package br.com.adatech.prospectflow.core.domain;


import br.com.adatech.prospectflow.core.utils.EmailValidator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class NaturalPerson extends Client{
    public NaturalPerson() {}
    public NaturalPerson(String mcc, String cpf, String name, String email) {
        super(mcc, cpf, name, email);
        this.setType(ClientType.PF);
    }

}
