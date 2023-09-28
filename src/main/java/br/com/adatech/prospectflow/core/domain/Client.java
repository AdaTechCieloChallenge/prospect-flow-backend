package br.com.adatech.prospectflow.core.domain;

import br.com.adatech.prospectflow.core.utils.EmailValidator;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Client implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String uuid;
    private String mcc;
    @Column(unique = true)
    private String cpf;
    @Column(unique = true)
    private String email;
    private String name;
    private ClientType type = null;
    private int version;
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @Column(name = "created_at")
    private Timestamp createdAt;

    public Client(){}

    public Client(String mcc, String cpf,  String name, String email) {
        this.setMcc(mcc);
        this.setCpf(cpf);
        this.setName(name);
        this.setEmail(email);
        this.setUuid(UUID.randomUUID().toString());
        // Mecanismos de controle de instância no frontend propagados por herança.
        this.setVersion(0);
        Timestamp creationTime = new Timestamp(System.currentTimeMillis());
        this.setCreatedAt(creationTime);
        this.setUpdatedAt(creationTime);
    }

    public Client(String mcc, String cpf,  String name, String email, Timestamp updatedAt) {
        this.setMcc(mcc);
        this.setCpf(cpf);
        this.setName(name);
        this.setEmail(email);
        this.setUuid(UUID.randomUUID().toString());
        // Mecanismos de controle de instância no frontend propagados por herança.
        this.setUpdatedAt(updatedAt);
    }


    /** Validação de dados pelo próprio modelo através dos setters no construtor. **/
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public void setType(ClientType type){
        this.type = type;
    };

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Client{" +
                "uuid='" + uuid + '\'' +
                ", mcc='" + mcc + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", version=" + version +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(uuid, client.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
