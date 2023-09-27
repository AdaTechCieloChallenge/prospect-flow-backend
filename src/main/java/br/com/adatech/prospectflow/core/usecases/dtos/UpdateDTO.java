package br.com.adatech.prospectflow.core.usecases.dtos;

import br.com.adatech.prospectflow.core.domain.ClientType;

public record UpdateDTO(String mcc,
                        String cpf,
                        String name,
                        String email,
                        String cnpj,
                        String corporateName
                        ) {}
