📧 Microsserviços — Cadastro de Usuário e Envio de E-mail
Sistema baseado em arquitetura de microsserviços que realiza o cadastro de usuários e dispara automaticamente um e-mail de boas-vindas via mensageria assíncrona com RabbitMQ.

📐 Arquitetura

<img width="491" height="300" alt="image" src="https://github.com/user-attachments/assets/0c64e10a-d408-4590-8dec-bedde8225c9d" />

O microsserviço user expõe uma API REST para cadastro de usuários. Após salvar o usuário no banco de dados, publica uma mensagem na fila do RabbitMQ. O microsserviço email escuta essa fila, envia o e-mail de boas-vindas via SMTP e registra o resultado no seu próprio banco de dados.

🗂️ Estrutura do Projeto
<img width="558" height="468" alt="image" src="https://github.com/user-attachments/assets/1f97b902-c575-4233-8731-7f8c73e79a76" />


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

<img width="545" height="219" alt="image" src="https://github.com/user-attachments/assets/8e0d8609-7e3d-4982-8e31-62788eccc54a" />

Microsserviço email — application.properties

<img width="543" height="363" alt="image" src="https://github.com/user-attachments/assets/692061c2-238e-4efa-a131-36fa84cca12e" />

▶️ Como Executar
Execute cada microsserviço em um terminal separado, a partir da raiz do respectivo módulo:
<img width="396" height="145" alt="image" src="https://github.com/user-attachments/assets/e48c7fe5-2b68-4214-886b-7430593b7afa" />

📡 Endpoints
<img width="336" height="259" alt="image" src="https://github.com/user-attachments/assets/b811c40a-f094-44ab-b157-c60c2cc72613" />

Após o cadastro, o usuário receberá automaticamente um e-mail de boas-vindas com o assunto "Cadastro realizado com sucesso".

🔄 Fluxo de Processamento

O cliente envia um POST /users com nome e e-mail.
O MS User valida e persiste o usuário na tabela TB_USERS.
O MS User publica uma mensagem na fila default.email do RabbitMQ contendo os dados do e-mail.
O MS Email consome a mensagem da fila.
O MS Email envia o e-mail via SMTP (Gmail) e persiste o registro na tabela TB_EMAILS com status SENT ou ERROR.
