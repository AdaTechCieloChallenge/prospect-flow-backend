package br.com.adatech.prospectflow.core.domain;


import br.com.adatech.prospectflow.core.utils.EmailValidator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Timestamp;


@Entity
public class NaturalPerson extends Client{
    public NaturalPerson() {}
    public NaturalPerson(String mcc, String cpf, String name, String email) {
        super(mcc, cpf, name, email);
        this.setType(ClientType.PF);
    }

    public NaturalPerson(String mcc, String cpf, String name, String email, Timestamp updatedAt) {
        super(mcc, cpf, name, email, updatedAt);
        this.setType(ClientType.PF);
    }

    public String toString() {
        return "LegalPerson{" +
                ", mcc='" + super.getMcc() + '\'' +
                ", cpf='" + super.getCpf() + '\'' +
                ", name='" + super.getName() + '\'' +
                ", email='" + super.getEmail() + '\'' +
                ", email='" + super.getEmail() + '\'' +
                ", created_at='" + super.getCreatedAt() + '\'' +
                ", updated_at='" + super.getUpdatedAt() + '\'' +
                ", version='" + super.getVersion() + '\'' +
                '}';
    }

}
