package br.com.adatech.prospectflow.infra.database;

import br.com.adatech.prospectflow.core.domain.Client;
import br.com.adatech.prospectflow.core.domain.LegalPerson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LegalPersonRepository extends JpaRepository<LegalPerson, String> {
}
