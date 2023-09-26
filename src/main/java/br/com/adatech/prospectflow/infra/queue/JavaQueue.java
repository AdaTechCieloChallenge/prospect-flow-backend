package br.com.adatech.prospectflow.infra.queue;

import br.com.adatech.prospectflow.adapters.QueueAdapter;
import br.com.adatech.prospectflow.core.domain.Client;
import org.springframework.stereotype.Service;

@Service
public class JavaQueue implements QueueAdapter {
    @Override
    public void send(Client client) {

    }
    @Override
    public Client consume() {
        return null;
    }
}
