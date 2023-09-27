package br.com.adatech.prospectflow.infra.database;

import br.com.adatech.prospectflow.core.domain.Client;
import br.com.adatech.prospectflow.core.domain.LegalPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface LegalPersonRepository extends JpaRepository<LegalPerson, String> {
    Optional<Client> findByCnpj(String cnpj);
    @Modifying
    @Query("DELETE FROM LegalPerson e WHERE e.cnpj = :cnpj")
    void deleteByCnpj(@Param("cnpj") String cnpj);
}
