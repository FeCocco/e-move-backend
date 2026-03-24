# e-Move Backend

API RESTful robusta desenvolvida em **Spring Boot** para a plataforma **e-Move**, um planejador de rotas inteligente para veículos elétricos. Este backend gerencia toda a lógica de negócios, autenticação segura, e serve como ponte confiável para APIs externas de mapeamento e estações de recarga.

A API é consumida pelo nosso [frontend (e-Move Frontend)](https://github.com/FeCocco/e-move-frontend), proporcionando uma experiência completa e integrada para usuários de veículos elétricos.

---

## Funcionalidades da API

### Sistema de Autenticação Completo
- **Endpoints Seguros:** Login (`/api/login`), cadastro (`/api/cadastro`) e logout (`/api/logout`)
- **Autenticação JWT:** Geração e validação de tokens com algoritmo HS256
- **Cookies HttpOnly:** Armazenamento seguro de tokens para prevenir ataques XSS
- **Gestão de Sessão:** Sistema robusto de controle de sessões ativas
- **Validação Rigorosa:** Validação server-side completa para todos os dados recebidos

### Gerenciamento de Usuários
- **Perfil Completo:** Endpoint protegido (`/api/usuario/me`) para dados do usuário
- **Atualização e Exclusão:** Sistema de edição de informações pessoais (`PUT /api/usuario/me`) e exclusão de conta (deleção lógica via `DELETE /api/usuario/me` com rotina de limpeza agendada)
- **Validação de Duplicatas:** Prevenção de emails duplicados no sistema
- **Campos Abrangentes:** Nome, email, telefone, sexo, data de nascimento
- **Timestamps Automáticos:** Rastreamento de data de cadastro

### Sistema de Veículos Avançado
- **Catálogo Extenso:** Base pré-populada com 29+ modelos de veículos elétricos
- **Garagem Virtual:** Usuários podem adicionar/remover veículos (`/api/veiculos/meus-veiculos`)
- **Controle de Bateria:** Atualização do nível de carga com cálculo de autonomia
- **Tipos de Conectores:** Suporte para SAE Tipo 1, CCS Tipo 2, CHAdeMO, Tipo 2 AC, GBT
- **Marcas Populares:** Tesla, BYD, Chevrolet, BMW, Audi, Volkswagen, Nissan e muitas outras
- **Autonomia Estimada:** Cálculo automático baseado no nível da bateria atual

### Gestão de Rotas, Viagens e Estações
- **Rotas Diretas:** Integração com LocationIQ para cálculo de distância entre coordenadas
- **Estações Favoritas:** Integração com OpenChargeMap permitindo aos usuários buscar e favoritar estações de recarga próximas (`/api/estacoes/favoritas`)
- **Histórico de Viagens:** Rastreamento das viagens do usuário, salvando data, quilometragem, apelido da rota e cálculo automático do CO₂ preservado
- **Relacionamentos:** Arquitetura robusta Many-to-Many e One-to-Many conectando Usuários, Veículos, Viagens e Estações

---

## Tecnologias Utilizadas

### Core Framework
- **[Spring Boot 3.3.3](https://spring.io/projects/spring-boot)** - Framework principal com arquitetura moderna
- **[Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)** - LTS com recursos avançados
- **[Maven 3.9.11](https://maven.apache.org/)** - Gerenciamento de dependências e build

### Persistência de Dados
- **[Spring Data JPA](https://spring.io/projects/spring-data-jpa)** - Abstração ORM avançada
- **[Hibernate](https://hibernate.org/)** - ORM com otimizações de performance
- **[MariaDB/MySQL](https://mariadb.org/)** - Banco relacional confiável
- **HikariCP** - Pool de conexões de alta performance

### Segurança
- **[Spring Security](https://spring.io/projects/spring-security)** - Framework de segurança enterprise
- **[JWT (jjwt 0.11.5)](https://github.com/jwtk/jjwt)** - Tokens seguros com assinatura HMAC
- **Password Encoding** - Preparado para BCrypt

### Qualidade & Produtividade
- **[Lombok](https://projectlombok.org/)** - Redução de boilerplate code
- **[Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.devtools)** - Hot reload para desenvolvimento
- **Bean Validation** - Validação declarativa com annotations

### API & CORS
- **Spring Web MVC** - RESTful APIs robustas
- **CORS Configurado** - Comunicação segura com frontend React
- **Content Negotiation** - Suporte a JSON otimizado

---

## Pré-requisitos

### Ferramentas Obrigatórias
1. **[Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)** - Versão 17 ou superior
2. **[MariaDB](https://mariadb.org/download/)** ou **[MySQL Server](https://dev.mysql.com/downloads/mysql/)** - Versão 8.0+
3. **[Maven](https://maven.apache.org/download.cgi)** - Para build (opcional, usa wrapper incluso)

### Ferramentas Recomendadas
- **[IntelliJ IDEA](https://www.jetbrains.com/idea/)** ou **[Eclipse](https://www.eclipse.org/)** - IDEs com suporte Java
- **[DBeaver](https://dbeaver.io/)** ou **[MySQL Workbench](https://www.mysql.com/products/workbench/)** - Gerenciamento de banco
- **[Postman](https://www.postman.com/)** ou **[Insomnia](https://insomnia.rest/)** - Testes de API

---

## Configuração do Ambiente Local

### 1. Clone o Repositório

```bash
git clone https://github.com/FeCocco/e-move-backend.git
cd e-move-backend
```

### 2. Configure o Banco de Dados

Conecte-se ao seu servidor MariaDB/MySQL e execute:
```SQL
-- Criação do banco de dados
CREATE DATABASE EMOVE;

-- Criação do usuário da aplicação
CREATE USER 'User_emove'@'localhost' IDENTIFIED BY 'SUA_SENHA_SEGURA_AQUI';

-- Concessão de privilégios
GRANT ALL PRIVILEGES ON EMOVE.* TO 'User_emove'@'localhost';
FLUSH PRIVILEGES;

-- Verificação (opcional)
SHOW GRANTS FOR 'User_emove'@'localhost';
```

### 3. Configure as Variáveis de Ambiente

Na pasta src/main/resources/:

Renomeie application-dev.properties.template para application-dev.properties

Edite o arquivo e preencha os valores:

```Properties
# Configurações do Servidor
server.port=8080

# Configurações do Banco de Dados
spring.datasource.url=jdbc:mysql://localhost:3306/EMOVE
spring.datasource.username=User_emove
spring.datasource.password=SUA_SENHA_AQUI

# Configurações JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect

# Configurações JWT
jwt.secret=GERE_UMA_CHAVE_SECRETA_FORTE_AQUI_32_CARACTERES_MINIMO
jwt.expiration=86400000

# LocationIQ API Configuration
locationiq.api.key=YOUR_API_KEY
locationiq.api.url=[https://us1.locationiq.com/v1/search](https://us1.locationiq.com/v1/search)
locationiq.api.directions.url=[https://us1.locationiq.com/v1/directions/driving/](https://us1.locationiq.com/v1/directions/driving/)

# OpenChargeMap API
openchargemap.api.key=YOUR_API_KEY
openchargemap.api.url=[https://api-01.openchargemap.io/v3/poi](https://api-01.openchargemap.io/v3/poi)

# Pool de Conexões (HikariCP)
spring.datasource.hikari.connection-timeout=3000
spring.datasource.hikari.idle-timeout=30000
spring.datasource.hikari.max-lifetime=120000
spring.datasource.hikari.minimum-idle=2
spring.datasource.hikari.maximum-pool-size=5

# Performance
spring.jpa.open-in-view=false
```
### 4. Compile e Instale Dependências

#### Linux/macOS:
```bash
chmod +x mvnw  # Torna o wrapper executável
./mvnw clean install
```

#### Windows
```DOS
mvnw.cmd clean install
```
### 5. Inicie o Servidor

#### Via Maven Wrapper (Recomendado):
#### Linux/macOS
```bash
./mvnw spring-boot:run
```

#### Windows
```bash
mvnw.cmd spring-boot:run
```
#### Via IDE:

Execute a classe EMoveBackendApplication.java diretamente pela sua IDE.

### 6. Verificação

A API estará disponível em: http://localhost:8080

Teste rápido:

```bash
curl http://localhost:8080/api/veiculos
```
#### Experiência Completa: Para funcionalidade completa, inicie também o e-Move Frontend.

### Documentação da API
#### Endpoints de Autenticação

- POST /api/cadastro ->
Cadastra um novo usuário no sistema.
```Json
{
  "nome": "João Silva",
  "email": "joao@exemplo.com",
  "telefone": "11999999999",
  "sexo": "MASCULINO",
  "dataNascimento": "1990-01-01T00:00:00.000Z",
  "senha": "senhaSegura123"
}
```
- POST /api/login ->
Autentica usuário e gera token JWT.
```JSON
{
  "email": "joao@exemplo.com",
  "senha": "senhaSegura123"
}
```
- POST /api/logout ->
Remove token de autenticação.

#### Endpoints de Usuário

- GET /api/usuario/me ->
Retorna dados do usuário autenticado.

- PUT /api/usuario/me ->
Atualiza dados do usuário autenticado.

- DELETE /api/usuario/me ->
Desativa logicamente o usuário logado (exclusão definitiva de banco feita via rotina noturna).

#### Endpoints de Veículos

- GET /api/veiculos ->
Lista todos os veículos disponíveis no catálogo.

- GET /api/veiculos/meus-veiculos ->
Lista veículos da garagem do usuário autenticado.

- POST /api/veiculos/meus-veiculos/{veiculoId} ->
Adiciona veículo à garagem do usuário.

- DELETE /api/veiculos/meus-veiculos/{veiculoId} ->
Remove veículo da garagem do usuário.

- PUT /api/veiculos/{veiculoId}/bateria ->
Atualiza nível da bateria do veículo.
```JSON
{
  "nivelBateria": 85
}
```

### Roadmap Técnico
[x] Autenticação JWT - Sistema completo implementado

[x] CRUD de Veículos - Garagem virtual funcional

[x] Base de Dados - 29+ veículos pré-cadastrados

[x] API de Rotas - Integração com LocationIQ

[x] API de Estações - Integração OpenChargeMap com URL api-01

[x] Sistema de Viagens - Histórico e métricas de CO₂

[x] Password Encoding - BCrypt para senhas

[x] Rate Limiting - Proteção contra spam

[ ] API Documentation - Swagger/OpenAPI

[ ] Monitoring - Health checks e métricas

[ ] Cache Redis - Performance otimizada

<div align="center">

API construída com ⚡ pela equipe e-Move

Java: 17 | Spring Boot: 3.3.3

</div>
