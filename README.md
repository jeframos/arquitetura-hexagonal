# Arquitetura Hexagonal

Aplicacao de exemplo em Java com Spring Boot para gerenciamento de clientes seguindo o padrao de arquitetura hexagonal. O projeto expoe uma API REST, persiste dados no MongoDB, consulta endereco por CEP via cliente HTTP e utiliza Kafka para publicar e consumir eventos relacionados a validacao de CPF.

## Objetivos do projeto

- Demonstrar a separacao entre regras de negocio, portas e adaptadores.
- Mostrar integracao entre API REST, mensageria e persistencia.
- Garantir disciplina arquitetural com testes usando ArchUnit.

## Tecnologias utilizadas

- Java 21
- Spring Boot 3.4.3
- Spring Web
- Spring Data MongoDB
- Spring for Apache Kafka
- Spring Cloud OpenFeign
- Spring Validation
- MapStruct
- Lombok
- Maven Wrapper
- ArchUnit
- Docker Compose
- WireMock para simular o servico de endereco

## Estrutura da arquitetura

O projeto esta organizado em camadas bem definidas:

- `application/core`: dominio e casos de uso.
- `application/ports/in`: contratos de entrada consumidos pelos adaptadores de entrada.
- `application/ports/out`: contratos de saida implementados pelos adaptadores de infraestrutura.
- `adapters/in`: controllers REST e consumers Kafka.
- `adapters/out`: persistencia, cliente HTTP e produtor Kafka.
- `config`: configuracao e composicao das dependencias.

Os testes em `src/test/java/com/ramos/hexagonal/architecture` validam regras de dependencia entre camadas e convencoes de nomenclatura.

## Fluxo funcional

### Cadastro de cliente

1. A API recebe um `POST /api/v1/customers`.
2. O caso de uso busca o endereco a partir do CEP via cliente Feign.
3. O cliente e salvo no MongoDB.
4. O CPF e publicado no topico Kafka `tp-cpf-validation`.

### Atualizacao assincrona do CPF

1. Um consumer escuta o topico `tp-cpf-validated`.
2. A mensagem recebida e convertida para o dominio.
3. O cliente e atualizado no MongoDB com o resultado da validacao.

## Requisitos para rodar localmente

- Java 21 instalado e configurado no `PATH`
- Docker e Docker Compose
- Acesso a uma instancia WireMock local para simular o servico de endereco

## Configuracao local

As propriedades principais estao em [application.yml](C:/temp/hexagonal/src/main/resources/application.yml):

- API Spring Boot: `http://localhost:8081`
- MongoDB: `localhost:27017`
- Base Mongo: `hexagonal`
- Usuario Mongo: `root`
- Senha Mongo: `example`
- Servico de endereco: `http://localhost:8082/addresses`

## Subindo as dependencias

### 1. Infraestrutura com Docker

Suba MongoDB, Kafka, Zookeeper, Kafdrop e Mongo Express:

```powershell
docker compose -f docker-local/docker-compose.yml up -d
```

Servicos disponiveis apos a subida:

- Kafka: `localhost:9092`
- Kafdrop: `http://localhost:9000`
- MongoDB: `localhost:27017`
- Mongo Express: `http://localhost:8083`

Para acompanhar os containers:

```powershell
docker ps
```

### 2. Mock do servico de endereco

O projeto espera um servico HTTP em `http://localhost:8082/addresses/{zipCode}`. Uma forma simples de simular isso localmente e usar o WireMock.

Exemplo de execucao:

```powershell
java -jar wiremock-standalone-4.0.0-beta.29.jar --port 8082
```

Se o jar estiver em outro diretorio, execute o comando a partir dele ou informe o caminho completo.

Exemplo de resposta esperada para um CEP:

```json
{
  "street": "Avenida Paulista",
  "city": "Sao Paulo",
  "state": "SP"
}
```

Uma stub WireMock compativel pode responder a `GET /addresses/01234000` com o JSON acima.

## Executando a aplicacao

No Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

No Linux ou macOS:

```bash
./mvnw spring-boot:run
```

A aplicacao sera iniciada na porta `8081`.

## Executando os testes

```powershell
.\mvnw.cmd test
```

Os testes atuais focam em validacao arquitetural com ArchUnit.

## Endpoints da API

Base path: `http://localhost:8081/api/v1/customers`

### Criar cliente

`POST /api/v1/customers`

Payload:

```json
{
  "name": "Joao Silva",
  "cpf": "12345678900",
  "zipCode": "01234000"
}
```

Resposta:

- `200 OK`

### Buscar cliente por ID

`GET /api/v1/customers/{id}`

Resposta esperada:

```json
{
  "name": "Joao Silva",
  "address": {
    "street": "Avenida Paulista",
    "city": "Sao Paulo",
    "state": "SP"
  },
  "cpf": "12345678900",
  "isValidCpf": false
}
```

### Atualizar cliente

`PUT /api/v1/customers/{id}`

Payload:

```json
{
  "name": "Joao Silva",
  "cpf": "12345678900",
  "zipCode": "01311000"
}
```

Resposta:

- `204 No Content`

### Remover cliente

`DELETE /api/v1/customers/{id}`

Resposta:

- `204 No Content`

## Kafka

Topicos utilizados no fluxo:

- `tp-cpf-validation`: envio do CPF para validacao.
- `tp-cpf-validated`: recebimento do resultado da validacao.

O consumer utiliza o `group.id` `ramos`.

## MongoDB

O banco utilizado localmente e `hexagonal`.

Para acessar o container Mongo:

```powershell
docker exec -it <id-do-container> mongosh -u root -p
```

Depois informe a senha `example` e selecione a base:

```javascript
use hexagonal
show collections
db.customers.find()
```

## Observacoes de desenvolvimento

- O projeto usa `MapStruct` para conversao entre request, response, dominio e entidades.
- `Lombok` reduz codigo boilerplate em DTOs e mensagens.
- A composicao dos casos de uso fica centralizada em classes `*Config`.
- O diretorio `target/` contem artefatos gerados de build e nao faz parte do codigo-fonte principal.

## Melhorias que fazem sentido para a proxima iteracao

- Adicionar testes de integracao para controller, MongoDB e Kafka.
- Documentar o contrato do servico de validacao de CPF.
- Expor colecao Postman ou OpenAPI/Swagger.
- Externalizar configuracoes sensiveis via variaveis de ambiente.
