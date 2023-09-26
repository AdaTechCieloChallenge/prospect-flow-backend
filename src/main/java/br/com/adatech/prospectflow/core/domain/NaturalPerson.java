package br.com.adatech.prospectflow.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class NaturalPerson extends Client{
    @Column(unique = true)
    private String email;

    public NaturalPerson() {}
    public NaturalPerson(String mcc, String cpf, String name, String email) {
        super(mcc, cpf, name, email); // valida as informações pelo construtor herdado.
        this.setType(ClientType.PF); // atribui o ClientType correto, transmitido por herança, para a subclasse.
    }

}
