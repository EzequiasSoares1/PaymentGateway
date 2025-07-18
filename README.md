# 💳 Payment Gateway

Este projeto é um **gateway de pagamentos** que integra diferentes provedores como o **Mercado Pago**, permitindo alternar entre eles conforme a necessidade. A ideia é facilitar a escolha do gateway mais vantajoso para cada transação — considerando fatores como taxas, disponibilidade ou tipo de operação.

Foi desenvolvido com o objetivo de **estudo e aprendizado**, abordando conceitos como:

- Comunicação entre **microsserviços**
- Integração com **gateways de pagamento**
- Utilização do **Apache Camel** para orquestração
- Envio de **notificações por e-mail**

Este projeto também serve como um exemplo prático para outros desenvolvedores que desejam entender ou implementar integrações com gateways de pagamento.

---

## 🧩 Módulos do Sistema

### 📧 EmailNotify
- **Função**: Envio de e-mails via REST ou mensageria (RabbitMQ)
- **Tecnologias**: `Spring Boot`, `Spring Email`, `RabbitMQ`

### 🔌 GatewayPay
- **Função**: Integração com provedores de pagamento (atualmente Mercado Pago)
- **Tecnologias**: `Spring Boot`, `Apache Camel`, `Lombok`

### 🧾 SysGateway
- **Função**: Atua como orquestrador — registra as transações e aciona o envio de notificações
- **Tecnologias**: `Spring Boot`, `Banco H2` (para testes)

---
## 🔗 Integrações com Gateways de Pagamento

| Gateway      | Status         | Tipo de Integração | Documentação                                               | Observações                          |
|--------------|----------------|--------------------|-------------------------------------------------------------|--------------------------------------|
| Mercado Pago | ✅ Integrado    | SDK                | [Documentação Oficial](https://www.mercadopago.com.br/developers/pt) | Integracao via Checkout Transparente |
| Stripe       | 🚧 Nao Inicado | SDK       | [Link para documentação](#)                                |                                      |


## ✅ Requisitos

- Java 21
- Conta ativa no gateway de seu agrado para testes de integração.

---

### ▶️ Como executar o projeto

> ⚠️ **Antes de iniciar**, insira as **variáveis de ambiente** no application.properties com as **credenciais do gateway de pagamento** no módulo `gateway-pay`.

- Em caso de testes com cartão, é necessário utilizar o **front-end de testes**, desenvolvido separadamente em React.
- Cada gateway pode ter um **front específico** para testes. Verifique no repositório ou documentação correspondente.
- Não esqueça de adicionar as **credenciais de acesso do front** (ex: `PUBLIC_KEY`, `ACCESS_TOKEN`, etc.), conforme exigido pelo gateway em uso.

---

#### 🔨 Build com Maven (no módulo específico ou em todos)

```bash
# Compilar todo o projeto
mvn clean install

# Ou compilar apenas um módulo específico (exemplo: gateway-pay)
cd gateway-pay
mvn clean install
```

## 📬 Collection de Testes (Postman)

Este projeto inclui uma **collection do Postman** para facilitar os testes das APIs dos microsserviços.

