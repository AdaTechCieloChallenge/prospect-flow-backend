# Desafio Individual Bootcamp Cielo Adatech

## Cielo Prospect Flow

### Documentação


* [Instruções de uso](https://maven.apache.org/guides/index.html)
* [Considerações Finais](https://docs.spring.io/spring-boot/docs/3.1.4/maven-plugin/reference/html/)


## Etapas principais

- Pré-cadastro de clientes
- Fila de atendimento
- Web Front End

## Planejamento da Solução

Criar um serviço agnóstico ao sistema de filas escolhido e ao SGBD escolhido, utilizando DDD e Clean Archtecture.

### Pré-cadastro de clientes

Adaptadores iniciais para h2 e extensíveis a quaisquer outros bancos.

### Fila de Atendimento

Adaptadores para Estrutura de dados Java extensíveis a quaisquer outros sistemas de fila ou mensageria.

### Web Front End

- Desenvolvimento orientado a wireframes.
- Painel administrativo baseado em SPA.
- Componentização com shadcn/ui.
- React.

### Funcionamento da APIT REST

- localhost:8080/swagger-ui.html

## Exemplo de funcionamento:
### Prospect Flow API REST (Java Spring Boot):

```bash

Rota de cadastro de NaturalPerson: POST localhost:8080/api/v1/create-natural-person
Recebe:

{
"mcc": "1234",
"cpf": "12345678901",
"name": "Teste",
"email": "teste@teste.com"
}

Retorna (em caso de sucesso): 201
{
"uuid": "05a538f4-bc0f-4ce4-ae79-043c42873139",
"mcc": "1234",
"cpf": "12345678901",
"email": "teste@teste.com",
"name": "Teste",
"type": "PF",
"version": 0,
"updatedAt": "2023-09-28T12:11:05.637+00:00",
"createdAt": "2023-09-28T12:11:05.637+00:00"
}
Retorna (em caso de fracasso) uma das seguintes mensagens contendo os detalhes do erro numa string.
Em acontecendo UMA das seguintes mensagens de erro possíveis, ela deverá ser mostrada na tela:
- 400 BAD REQUEST. Body: This client as a natural person already exists.
- 400 BAD REQUEST. Body: Invalid MCC!
- 400 BAD REQUEST. Body: Invalid CPF!
- 400 BAD REQUEST. Body: Invalid name!
- 400 BAD REQUEST. Body: Invalid email!

Rota de cadastro de Legal Person: POST localhost:8080/api/v1/create-legal-person
Recebe:
{
"mcc": "1234",
"cpf": "12345678901",
"name": "Testes",
"email": "teste@teste.com",
"cnpj": "12345678901234",
"corporateName": "Teste"
}

Retorna (em caso de sucesso): 201
{
"uuid": "acafd464-ee25-44ea-bc9f-a8b700580d6a",
"mcc": "1234",
"cpf": "12345678901",
"email": "teste@teste.com",
"name": "Testes",
"type": "PJ",
"version": 0,
"updatedAt": "2023-09-28T12:13:45.158+00:00",
"createdAt": "2023-09-28T12:13:45.158+00:00",
"cnpj": "12345678901234",
"corporateName": "Teste"
}
Retorna (em caso de fracasso) uma das seguintes mensagens contendo os detalhes do erro numa string.
Em acontecendo UMA das seguintes mensagens de erro possíveis, ela deverá ser mostrada na tela:
- 400 BAD REQUEST. Body: This client as a legal person already exists.
- 400 BAD REQUEST. Body: Invalid MCC!
- 400 BAD REQUEST. Body: Invalid CPF!
- 400 BAD REQUEST. Body: Invalid name!
- 400 BAD REQUEST. Body: Invalid email!
- 400 BAD REQUEST. Body: Invalid CNPJ!
- 400 BAD REQUEST. Body: Invalid Corporate Name!

Rota de consulta:
Consulta a registros de Legal Person: GET  localhost:8080/api/v1/find-client/12345678901234/pj
Em que "12345678901234" é o cnpj e "pj" é o tipo de cliente.
Retorna (em caso de sucesso): 200
{
"uuid": "05a538f4-bc0f-4ce4-ae79-043c42873139",
"mcc": "1234",
"cpf": "12345678901",
"email": "teste@teste.com",
"name": "Teste",
"type": "PF",
"version": 0,
"updatedAt": "2023-09-28T12:11:05.637+00:00",
"createdAt": "2023-09-28T12:11:05.637+00:00"
}
Retorna (em caso de fracasso) uma das seguintes mensagens contendo os detalhes do erro numa string.
Em acontecendo UMA das seguintes mensagens de erro possíveis, ela deverá ser mostrada na tela:
- 400 BAD REQUEST. Invalid client type string: p (em que 'p' refere-se ao tipo de cliente erroneamente informado na rota: localhost:8080/api/v1/find-client/12345678901234/p)
- 404 NOT FOUND. Client not registered yet.

Consulta a registros de Natural Person: GET localhost:8080/api/v1/find-client/12345678901/pf
Em que "12345678901" é o campo cpf e "pf" é o tipo de cliente.
Retorna:
{
"uuid": "acafd464-ee25-44ea-bc9f-a8b700580d6a",
"mcc": "1234",
"cpf": "12345678901",
"email": "teste@teste.com",
"name": "Testes",
"type": "PJ",
"version": 0,
"updatedAt": "2023-09-28T12:13:45.158+00:00",
"createdAt": "2023-09-28T12:13:45.158+00:00",
"cnpj": "12345678901234",
"corporateName": "Teste"
}
Retorna (em caso de fracasso) uma das seguintes mensagens contendo os detalhes do erro numa string.
Em acontecendo UMA das seguintes mensagens de erro possíveis, ela deverá ser mostrada na tela:
- 400 BAD REQUEST. Invalid client type string: p (em que 'p' refere-se ao tipo de cliente erroneamente informado na rota: localhost:8080/api/v1/find-client/12345678901234/p)
- 404 NOT FOUND. Client not registered yet.


Rota de atualização:
Atualiza Natural Person: PUT localhost:8080/api/v1/update?cnpjOrCpf=12345678901&clientType=pf
Recebe dado já atualizado (via body):
{
"mcc": "1235",
"cpf": "12345678901",
"name": "Teste",
"email": "teste@teste.com"
}
Retorna (em caso de sucesso): 200
{
"uuid": "05a538f4-bc0f-4ce4-ae79-043c42873139",
"mcc": "1235",
"cpf": "12345678901",
"email": "teste@teste.com",
"name": "Teste",
"type": "PF",
"version": 1,
"updatedAt": "2023-09-28T12:24:36.082+00:00",
"createdAt": "2023-09-28T12:11:05.637+00:00"
}
Retorna (em caso de fracasso) uma das seguintes mensagens contendo os detalhes do erro numa string.
Em acontecendo UMA das seguintes mensagens de erro possíveis, ela deverá ser mostrada na tela:
- 400 BAD REQUEST. Invalid client type string: p (em que 'p' refere-se ao tipo de cliente erroneamente informado na rota: localhost:8080/api/v1/find-client/12345678901/p)
- 400 BAD REQUEST. Quando "cnpjOrCpf" é passado com a grafia incorreta. Contém:
  {
  "timestamp": "2023-09-28T13:16:10.725+00:00",
  "status": 400,
  "error": "Bad Request",
  "path": "/api/v1/update"
  } (FRONTEND DEVE MOSTRAR MENSAGEM DE ERRO DESCONHECIDO NESTE CASO).
- 404 NOT FOUND. Client not registered yet.


Atualiza Legal Person: PUT localhost:8080/api/v1/update?cnpjOrCpf=12345678901234&clientType=pj
Recebe:
{
"mcc": "1234",
"cpf": "12345678901",
"name": "Tests",
"email": "teste@novo.com",
"cnpj": "12345678901234",
"corporateName": "Teste"
}

Retorna: 200
{
"uuid": "acafd464-ee25-44ea-bc9f-a8b700580d6a",
"mcc": "1234",
"cpf": "12345678901",
"email": "teste@novo.com",
"name": "Tests",
"type": "PJ",
"version": 1,
"updatedAt": "2023-09-28T12:29:59.077+00:00",
"createdAt": "2023-09-28T12:13:45.158+00:00",
"cnpj": "12345678901234",
"corporateName": "Teste"
}
Retorna (em caso de fracasso) uma das seguintes mensagens contendo os detalhes do erro numa string.
Em acontecendo UMA das seguintes mensagens de erro possíveis, ela deverá ser mostrada na tela:
- 400 BAD REQUEST. Invalid client type string: p (em que 'p' refere-se ao tipo de cliente erroneamente informado na rota: localhost:8080/api/v1/find-client/12345678901/p)
- 400 BAD REQUEST. Quando "cnpjOrCpf" é passado com a grafia incorreta. Contém:
  {
  "timestamp": "2023-09-28T13:16:10.725+00:00",
  "status": 400,
  "error": "Bad Request",
  "path": "/api/v1/update"
  }  (FRONTEND DEVE MOSTRAR MENSAGEM DE ERRO DESCONHECIDO NESTE CASO).
- 404 NOT FOUND. Client not registered yet.


Rota de exclusão:
Exclusão de Natural Person: DELETE localhost:8080/api/v1/delete/12345678901/pf
Retorna: 204
Retorna (em fracasso):
- 400 BAD REQUEST. Invalid client type string: p (se DELETE localhost:8080/api/v1/delete/12345678901/p)
- 404 NOT FOUND. Client not registered yet.

Exclusão de Legal Person: DELETE localhost:8080/api/v1/delete/12345678901234/pj
Retorna (em sucesso): 204
Retorna (em fracasso):
- 400 BAD REQUEST. Invalid client type string: p (se DELETE localhost:8080/api/v1/delete/12345678901234/p)
- 404 NOT FOUND. Client not registered yet.


Rota de listagem da fila: GET localhost:8080/api/v1/prospects
Retorna: 200
[
{
"uuid": "acafd464-ee25-44ea-bc9f-a8b700580d6a",
"mcc": "1234",
"cpf": "12345678901",
"email": "teste@teste.com",
"name": "Testes",
"type": "PJ",
"version": 0,
"updatedAt": "2023-09-28T12:13:45.158+00:00",
"createdAt": "2023-09-28T12:13:45.158+00:00",
"cnpj": "12345678901234",
"corporateName": "Teste"
},
{
"uuid": "05a538f4-bc0f-4ce4-ae79-043c42873139",
"mcc": "1235",
"cpf": "12345678901",
"email": "teste@teste.com",
"name": "Teste",
"type": "PF",
"version": 1,
"updatedAt": "2023-09-28T12:24:36.082+00:00",
"createdAt": "2023-09-28T12:11:05.637+00:00"
}
]
Retorna (caso vazia): 204 NO CONTENT.

Rota de consumo da fila: GET localhost:8080/api/v1/consume-prospect
Retorna (em sucesso, apenas um dos casos):
- um registro do tipo NaturalPerson:
  {
  "uuid": "05a538f4-bc0f-4ce4-ae79-043c42873139",
  "mcc": "1234",
  "cpf": "12345678901",
  "email": "teste@teste.com",
  "name": "Teste",
  "type": "PF",
  "version": 0,
  "updatedAt": "2023-09-28T12:11:05.637+00:00",
  "createdAt": "2023-09-28T12:11:05.637+00:00"
  }

- um registro do tipo LegalPerson:
  {
  "uuid": "acafd464-ee25-44ea-bc9f-a8b700580d6a",
  "mcc": "1234",
  "cpf": "12345678901",
  "email": "teste@teste.com",
  "name": "Testes",
  "type": "PJ",
  "version": 0,
  "updatedAt": "2023-09-28T12:13:45.158+00:00",
  "createdAt": "2023-09-28T12:13:45.158+00:00",
  "cnpj": "12345678901234",
  "corporateName": "Teste"
  }
  Retorna (caso vazia): 204 NO CONTENT.
```
## Instruções

- Clone o repositório
- Execute a aplicação
- Verifique o banco de dados em memória: http://localhost:8080/h2-console/
- Interaja com a documentação: http://localhost:8080/swagger-ui/index.html