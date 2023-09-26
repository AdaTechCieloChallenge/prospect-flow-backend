package br.com.adatech.prospectflow.infra.database;

import br.com.adatech.prospectflow.core.domain.LegalPerson;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LegalPersonRepository extends JpaRepository<LegalPerson, String> {
}
