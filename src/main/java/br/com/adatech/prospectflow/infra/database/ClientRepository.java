package br.com.adatech.prospectflow.infra.database;

import br.com.adatech.prospectflow.core.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
}
