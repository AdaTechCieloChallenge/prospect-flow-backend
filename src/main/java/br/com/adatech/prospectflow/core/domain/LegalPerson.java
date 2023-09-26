package br.com.adatech.prospectflow.core.domain;

import java.util.Objects;

public class LegalPerson extends Client {
    private String cnpj; //número de 14 dígitos formatado com zeros à esquerda
    private String corporateName; //máximo de 50 caracteres

    public LegalPerson(String mcc, String cpf, String name, String email, String cnpj, String corporateName) {
        super(mcc, cpf, name, email);
        this.cnpj = cnpj;
        this.corporateName = corporateName;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        if(cnpj.length() != 14){
            throw new IllegalArgumentException("Invalid CNPJ!");
        }
        this.cnpj = cnpj;
    }

    public String getCorporateName() {
        return corporateName;
    }

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
