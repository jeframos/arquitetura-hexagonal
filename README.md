# arquitetura-hexagonal

## Comando para executar o WireMock
Será necessário acessar o diretório que se encontra o arquivo wiremock (wiremock-standalone-4.0.0-beta.29.jar)
No meu caso está no diretório de donwloads
 - path local: C:\temp\wiremock
 - java -jar wiremock-standalone-4.0.0-beta.29.jar --port 8082

## Comando para executar o Docker

 - docker-compose up

### Comandos para listar processos DOCKER

 - docker ps

### Comando para acessar o container do Mongo DB

Após listar os processos docker, capturar o ID do container do Mongo DB, e executar o comando abaixo.
 - Comando CMD ou Powershell (Windows)
   - docker exec -it <id-container-mongo-DB> /bin/bash
 - Comando git bash
   - docker exec -it <id-container-mongo-DB> bash

Após acessar o container, executar o comando abaixo para acessar o mongo DB:
 - mongosh -u root -p
 - Em seguida informar a senha do mongo DB, presente no arquivo application.yml

Após se logar no Mongo DB, executar o comando abaixo:
 - use hexagonal (Esse é o nome do bd criado para os testes)

Após acessar o bd, executar o comando abaixo para listar as tabelas criadas:
 - show collections

O comando abaixo deve listar os registros populados na tabela:
 - db.customers.find()

