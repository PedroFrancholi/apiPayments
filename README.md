# 💳 API Payments

API REST para gerenciamento de pagamentos, desenvolvida com Spring Boot. Permite realizar transferências, Pix, saques, depósitos e consultar o histórico de transações, com processamento assíncrono via RabbitMQ.

---

## 🚀 Tecnologias

- **Java 22**
- **Spring Boot 3.4.5**
- **Spring Data JPA** — persistência de dados
- **Spring AMQP** — mensageria com RabbitMQ
- **H2 Database** — banco de dados em memória
- **Hibernate 6** — ORM
- **Lombok** — redução de boilerplate

---

## ⚙️ Como rodar o projeto

### Pré-requisitos

- Java 22+
- Maven
- Docker Desktop

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/apiPayments.git
cd apiPayments
```

### 2. Rode localmente

Suba o RabbitMQ separadamente:

```bash
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:management
```

E rode a aplicação:

```bash
mvn spring-boot:run
```

### 4. Acesse

| Serviço | URL |
|---|---|
| API | http://localhost:8080 |
| H2 Console | http://localhost:8080/h2-console |
| RabbitMQ Painel | http://localhost:15672 |

**Credenciais H2:**
- JDBC URL: `jdbc:h2:mem:apiPayments`
- Username: `sa`
- Password: *(vazio)*

**Credenciais RabbitMQ:**
- Username: `guest`
- Password: `guest`

---

## 📡 Endpoints da API

### Componentes (Usuários)

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/component` | Criar componente |
| `GET` | `/component/{id}` | Buscar componente por ID |

### Contas

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/account` | Criar conta |
| `GET` | `/account/{nrAccount}` | Buscar conta por número |

### Transações

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/transaction` | Criar transação |
| `GET` | `/transaction/{nrAccount}` | Buscar transações de uma conta |
| `GET` | `/transaction/pending/{nrAccount}` | Buscar transações pendentes |

---

### Tipos de transação

| Código | Tipo | Comportamento |
|---|---|---|
| `1` | Transferência entre contas | Débita origem, credita destino — status `PENDING` |
| `2` | Saque | Débita origem — status `EFFECTIVATED` |
| `3` | Depósito | Credita origem — status `EFFECTIVATED` |
| `4` | Pix | Débita origem, credita destino — status `PENDING` |

### Filas RabbitMQ

| Fila | Descrição |
|---|---|
| `queue.transaction.pending` | Fila principal de processamento |
| `queue.transaction.pending.dlq` | Fila de mensagens com erro |
