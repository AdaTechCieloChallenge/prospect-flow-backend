package br.com.adatech.prospectflow.core.domain;

import br.com.adatech.prospectflow.core.utils.EmailValidator;
import com.google.gson.Gson;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Client {
    private String mcc;
    @Id
    private String cpf;
    private String name;
    private String email;

    private ClientType type = null;

    public Client(){}
    /** Validação de dados pelo próprio modelo através dos setters no construtor. **/
    public Client(String mcc, String cpf, String name, String email) {
        this.setMcc(mcc);
        this.setCpf(cpf);
        this.setName(name);
        this.setEmail(email);
    }

    public String getMcc() {
        return mcc;
    }

    /** número com no máximo 4 caracteres **/
    public void setMcc(String mcc) {
        if(mcc.length() > 4 || mcc.isEmpty()){
            throw  new IllegalArgumentException("Invalid MCC!");
        }
        this.mcc = mcc;
    }

    public String getCpf() {
        return cpf;
    }

    /** número de 11 dígitos formatado com zeros à esquerda **/
    public void setCpf(String cpf) {
        if(cpf.length() != 11){
            throw new IllegalArgumentException("Invalid CPF!");
        }
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }
    /** máximo de 50 caracteres **/
    public void setName(String name) {
        if(name.isEmpty() || name.length() > 50){
            throw new IllegalArgumentException("Invalid name!");
        }
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    /** Valida o email usando a expressão regular: "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\.]+)\\.([a-zA-Z]{2,5})$" **/
    public void setEmail(String email) {
        if(!EmailValidator.validateEmail(email)){
            throw new IllegalArgumentException("Invalid email!");
        }
        this.email = email;
    }

    public ClientType getType() {
        return type;
    }

    public void setType(ClientType type) {
        this.type = type;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return "Client{" +
                "mcc='" + mcc + '\'' +
                ", cpf='" + cpf + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(mcc, client.mcc) && Objects.equals(cpf, client.cpf) && Objects.equals(name, client.name) && Objects.equals(email, client.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mcc, cpf, name, email);
    }
}
