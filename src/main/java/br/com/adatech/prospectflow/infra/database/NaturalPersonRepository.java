package br.com.adatech.prospectflow.infra.database;

import br.com.adatech.prospectflow.core.domain.Client;
import br.com.adatech.prospectflow.core.domain.NaturalPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface NaturalPersonRepository extends JpaRepository<NaturalPerson, String> {
    Optional<Client> findByCpf(String cpf);
    @Modifying
    @Query("DELETE FROM NaturalPerson e WHERE e.cpf = :cpf")
    void deleteByCpf(@Param("cpf") String cpf);
}
