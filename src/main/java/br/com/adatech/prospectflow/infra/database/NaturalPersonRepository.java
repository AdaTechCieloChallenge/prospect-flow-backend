package br.com.adatech.prospectflow.infra.database;

import br.com.adatech.prospectflow.core.domain.NaturalPerson;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NaturalPersonRepository extends JpaRepository<NaturalPerson, String> {
}
