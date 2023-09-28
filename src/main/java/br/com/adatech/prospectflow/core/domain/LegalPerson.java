package br.com.adatech.prospectflow.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Timestamp;
import java.util.Objects;
@Entity
public class LegalPerson extends Client {
    @Column(unique = true)
    private String cnpj;
    private String corporateName;

    public LegalPerson() {}

    public LegalPerson(String mcc, String cpf, String name, String email, String cnpj, String corporateName) {
        super(mcc, cpf, name, email);
        this.setCnpj(cnpj);
        this.setCorporateName(corporateName);
        this.setType(ClientType.PJ); // atribui o ClientType correto, transmitido por herança, para a subclasse.
    }

    public LegalPerson(String mcc, String cpf, String name, String email, String cnpj, String corporateName, Timestamp updatedAt) {
        super(mcc, cpf, name, email, updatedAt);
        this.setCnpj(cnpj);
        this.setCorporateName(corporateName);
        this.setType(ClientType.PJ); // atribui o ClientType correto, transmitido por herança, para a subclasse.
    }

    public String getCnpj() {
        return cnpj;
    }

    /** número de 14 dígitos formatado com zeros à esquerda **/
    public void setCnpj(String cnpj) {
        if(cnpj.length() != 14){
            throw new IllegalArgumentException("Invalid CNPJ!");
        }
        this.cnpj = cnpj;
    }

    public String getCorporateName() {
        return corporateName;
    }

    /** máximo de 50 caracteres **/
    public void setCorporateName(String corporateName) {
        if(corporateName.length() > 50 || corporateName.isEmpty()){
            throw new IllegalArgumentException("Invalid Corporate Name!");
        }
        this.corporateName = corporateName;
    }

    @Override
    public String toString() {
        return "LegalPerson{" +
                "cnpj='" + cnpj + '\'' +
                ", corporateName='" + corporateName + '\'' +
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LegalPerson that = (LegalPerson) o;
        return Objects.equals(cnpj, that.cnpj) && Objects.equals(corporateName, that.corporateName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cnpj, corporateName);
    }
}
