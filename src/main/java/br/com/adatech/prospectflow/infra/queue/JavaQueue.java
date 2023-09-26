package br.com.adatech.prospectflow.infra.queue;

import br.com.adatech.prospectflow.adapters.QueueAdapter;
import br.com.adatech.prospectflow.core.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Queue;

@Service
public class JavaQueue implements QueueAdapter {
    private final FIFOService fifoService;

    @Autowired
    public JavaQueue(FIFOService fifoService){
        this.fifoService = fifoService;
    }
    @Override
    public void send(Client client) {
        fifoService.send(client);
    }
    @Override
    public Client consume() {
        return fifoService.receive();
    }
}
