📧 Microsserviços — Cadastro de Usuário e Envio de E-mail
Sistema baseado em arquitetura de microsserviços que realiza o cadastro de usuários e dispara automaticamente um e-mail de boas-vindas via mensageria assíncrona com RabbitMQ.

📐 Arquitetura

<img width="491" height="300" alt="image" src="https://github.com/user-attachments/assets/0c64e10a-d408-4590-8dec-bedde8225c9d" />

O microsserviço user expõe uma API REST para cadastro de usuários. Após salvar o usuário no banco de dados, publica uma mensagem na fila do RabbitMQ. O microsserviço email escuta essa fila, envia o e-mail de boas-vindas via SMTP e registra o resultado no seu próprio banco de dados.

🗂️ Estrutura do Projeto
microsservicos-envio-email/
├── user/               # Microsserviço de usuários
│   ├── src/main/java/com/ms/user/
│   │   ├── controllers/    # Endpoints REST
│   │   ├── services/       # Regras de negócio
│   │   ├── producers/      # Publicação de mensagens no RabbitMQ
│   │   ├── models/         # Entidade JPA
│   │   ├── dtos/           # Objetos de transferência de dados
│   │   ├── repositories/   # Acesso ao banco de dados
│   │   └── configs/        # Configuração do RabbitMQ
│   └── src/main/resources/
│       └── application.properties
│
└── email/              # Microsserviço de e-mail
    ├── src/main/java/com/ms/email/
    │   ├── consumers/      # Consumidor da fila RabbitMQ
    │   ├── services/       # Lógica de envio de e-mail
    │   ├── models/         # Entidade JPA
    │   ├── dtos/           # Objetos de transferência de dados
    │   ├── repositories/   # Acesso ao banco de dados
    │   ├── enums/          # Status do envio (SENT / ERROR)
    │   └── configs/        # Configuração do RabbitMQ
    └── src/main/resources/
        └── application.properties

🚀 Tecnologias Utilizadas

Tecnologia           Versão           Função
Java                  17        Linguagem principal
Spring Boot         3.4.4          Framework base
Spring AMQP           —      Integração com RabbitMQ
Spring Data JPA       —        Persistência de dados
Spring Mail           —        Envio de e-mails via SMTP
Spring Validation     —            Validação de DTOs
PostgreSQL            —        Banco de dados relacional
RabbitMQ (CloudAMQP)  —        Broker de mensagens
Maven                 —    Gerenciamento de dependências

⚙️ Pré-requisitos

Java 17+
Maven 3.8+
PostgreSQL rodando localmente
RabbitMQ (instância local ou conta no CloudAMQP)
Conta Gmail com Senha de App gerada

🔧 Configuração
Bancos de Dados
Crie dois bancos no PostgreSQL:
sqlCREATE DATABASE "ms-user";
CREATE DATABASE "ms-email";

Microsserviço user — application.properties

propertiesspring.application.name=user
server.port=8081

spring.datasource.url=jdbc:postgresql://localhost:5432/ms-user
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
spring.jpa.hibernate.ddl-auto=update

spring.rabbitmq.addresses=amqps://SEU_ENDERECO_CLOUDAMQP

broker.queue.email.name=default.email

Microsserviço email — application.properties

propertiesspring.application.name=email
server.port=8082

spring.datasource.url=jdbc:postgresql://localhost:5432/ms-email
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
spring.jpa.hibernate.ddl-auto=update

spring.rabbitmq.addresses=amqps://SEU_ENDERECO_CLOUDAMQP

broker.queue.email.name=default.email

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=SEU_EMAIL@gmail.com
spring.mail.password=SENHA_DE_APP_GMAIL
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

▶️ Como Executar
Execute cada microsserviço em um terminal separado, a partir da raiz do respectivo módulo:
bash# Terminal 1 — Microsserviço de usuário
cd user
./mvnw spring-boot:run

# Terminal 2 — Microsserviço de e-mail
cd email
./mvnw spring-boot:run

📡 Endpoints
POST /users — Cadastrar usuário
URL: http://localhost:8081/users
Body (JSON):
json{
  "name": "João Silva",
  "email": "joao.silva@exemplo.com"
}
Resposta (201 Created):
json{
  "userId": "a1b2c3d4-e5f6-...",
  "name": "João Silva",
  "email": "joao.silva@exemplo.com"
}
Após o cadastro, o usuário receberá automaticamente um e-mail de boas-vindas com o assunto "Cadastro realizado com sucesso".

🔄 Fluxo de Processamento

O cliente envia um POST /users com nome e e-mail.
O MS User valida e persiste o usuário na tabela TB_USERS.
O MS User publica uma mensagem na fila default.email do RabbitMQ contendo os dados do e-mail.
O MS Email consome a mensagem da fila.
O MS Email envia o e-mail via SMTP (Gmail) e persiste o registro na tabela TB_EMAILS com status SENT ou ERROR.
