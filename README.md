# e-Move Backend
[![CI do e-Move](https://github.com/FeCocco/e-move-backend/actions/workflows/ci.yml/badge.svg)](https://github.com/FeCocco/e-move-backend/actions/workflows/ci.yml)

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
- **Atualização de Perfil:** Sistema de edição de informações pessoais (`PUT /api/usuario/me`)
- **Validação de Duplicatas:** Prevenção de emails duplicados no sistema
- **Campos Abrangentes:** Nome, email, CPF, telefone, sexo, data de nascimento
- **Timestamps Automáticos:** Rastreamento de data de cadastro

### Sistema de Veículos Avançado
- **Catálogo Extenso:** Base pré-populada com 29+ modelos de veículos elétricos
- **Garagem Virtual:** Usuários podem adicionar/remover veículos (`/api/veiculos/meus-veiculos`)
- **Controle de Bateria:** Atualização do nível de carga com cálculo de autonomia
- **Tipos de Conectores:** Suporte para SAE Tipo 1, CCS Tipo 2, CHAdeMO, Tipo 2 AC, GBT
- **Marcas Populares:** Tesla, BYD, Chevrolet, BMW, Audi, Volkswagen, Nissan e muitas outras
- **Autonomia Estimada:** Cálculo automático baseado no nível da bateria atual

### Estrutura de Dados Preparada (Em desenvolvimento)
- **Rotas:** Sistema pronto para gerenciamento de rotas personalizadas
- **Estações:** Estrutura para favoritar estações de recarga
- **Viagens:** Rastreamento de viagens com métricas de CO₂ e quilometragem
- **Relacionamentos:** Arquitetura robusta Many-to-Many e One-to-Many

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
- **Password Encoding** - Preparado para BCrypt (em implementação)

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

### 2. 🗄️ Configure o Banco de Dados
Conecte-se ao seu servidor MariaDB/MySQL e execute:

```sql
-- Criação do banco de dados
CREATE DATABASE EMOVE CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Criação do usuário da aplicação
CREATE USER 'User_emove'@'localhost' IDENTIFIED BY 'SUA_SENHA_SEGURA_AQUI';

-- Concessão de privilégios
GRANT ALL PRIVILEGES ON EMOVE.* TO 'User_emove'@'localhost';
FLUSH PRIVILEGES;

-- Verificação (opcional)
SHOW GRANTS FOR 'User_emove'@'localhost';
```

> **Importante:** Substitua `SUA_SENHA_SEGURA_AQUI` por uma senha forte com pelo menos 12 caracteres, incluindo letras, números e símbolos especiais.

### 3. Configure as Variáveis de Ambiente
Na pasta `src/main/resources/`:

1. **Renomeie** `application-dev.properties.template` para `application-dev.properties`
2. **Edite o arquivo** e preencha os valores:

```properties
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

# Pool de Conexões (HikariCP)
spring.datasource.hikari.connection-timeout=3000
spring.datasource.hikari.idle-timeout=30000
spring.datasource.hikari.max-lifetime=120000
spring.datasource.hikari.minimum-idle=2
spring.datasource.hikari.maximum-pool-size=5

# Performance
spring.jpa.open-in-view=false
```

> **Dica de Segurança:** Para gerar uma chave JWT segura:
> ```bash
> # No terminal (Linux/macOS)
> openssl rand -base64 32
> 
> # Ou use um gerador online confiável
> ```

### 4. Compile e Instale Dependências
#### Linux/macOS:
```bash
chmod +x mvnw  # Torna o wrapper executável
./mvnw clean install
```

#### Windows:
```cmd
mvnw.cmd clean install
```

### 5. Inicie o Servidor
#### Via Maven Wrapper (Recomendado):
```bash
# Linux/macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

#### Via IDE:
Execute a classe `EMoveBackendApplication.java` diretamente pela sua IDE.

### 6. Verificação
A API estará disponível em: **[http://localhost:8080](http://localhost:8080)**

**Teste rápido:**
```bash
curl http://localhost:8080/api/veiculos
```

> **Experiência Completa:** Para funcionalidade completa, inicie também o **[e-Move Frontend](https://github.com/FeCocco/e-move-frontend)**.

---

## Documentação da API

### Endpoints de Autenticação

#### `POST /api/cadastro`
Cadastra um novo usuário no sistema.

**Request Body:**
```json
{
  "nome": "João Silva",
  "email": "joao@exemplo.com",
  "cpf": "12345678901",
  "telefone": "11999999999",
  "sexo": "MASCULINO",
  "dataNascimento": "1990-01-01",
  "senha": "senhaSegura123"
}
```

#### `POST /api/login`
Autentica usuário e gera token JWT.

**Request Body:**
```json
{
  "email": "joao@exemplo.com",
  "senha": "senhaSegura123"
}
```

#### `POST /api/logout`
Remove token de autenticação (limpa cookie).

### Endpoints de Usuário

#### `GET /api/usuario/me`
Retorna dados do usuário autenticado.

**Headers:** `Cookie: e-move-token=JWT_TOKEN`

#### `PUT /api/usuario/me`
Atualiza dados do usuário autenticado.

### Endpoints de Veículos

#### `GET /api/veiculos`
Lista todos os veículos disponíveis no catálogo.

#### `GET /api/veiculos/meus-veiculos`
Lista veículos da garagem do usuário autenticado.

#### `POST /api/veiculos/meus-veiculos/{veiculoId}`
Adiciona veículo à garagem do usuário.

#### `DELETE /api/veiculos/meus-veiculos/{veiculoId}`
Remove veículo da garagem do usuário.

#### `PUT /api/veiculos/{veiculoId}/bateria`
Atualiza nível da bateria do veículo.

**Request Body:**
```json
{
  "nivelBateria": 85
}
```

---

## Scripts de Build

```bash
# Desenvolvimento
./mvnw spring-boot:run                 # Inicia aplicação em modo dev
./mvnw clean compile                   # Compila o código
./mvnw test                           # Executa testes unitários

# Produção
./mvnw clean package                  # Gera JAR para produção
./mvnw clean install                  # Instala dependências e gera JAR
java -jar target/emove-0.1.0-alpha.1.jar  # Executa JAR gerado

# Utilitários
./mvnw dependency:tree               # Visualiza árvore de dependências
./mvnw clean                        # Limpa arquivos de build
```

---

## Arquitetura do Projeto

```
src/main/java/com/fegcocco/emovebackend/
├── EMoveBackendApplication.java    # Classe principal e inicialização
├── config/
│   └── SecurityConfig.java         # Configurações de segurança e CORS
├── controller/                     # Controladores REST
│   ├── UsuarioController.java     # Endpoints de usuários
│   └── VeiculoController.java     # Endpoints de veículos
├── dto/                           # Data Transfer Objects
│   ├── CadastroDTO.java          # DTO para cadastro
│   ├── LoginDTO.java             # DTO para login
│   ├── UsuarioDTO.java           # DTO de usuário
│   ├── VeiculoDTO.java           # DTO de veículo
│   └── ...                       # Outros DTOs
├── entity/                        # Entidades JPA
│   ├── Usuario.java              # Entidade usuário
│   ├── Veiculos.java             # Entidade veículo
│   ├── UsuarioVeiculo.java       # Relação Many-to-Many
│   ├── Rotas.java                # Entidade rotas (preparada)
│   ├── Estacoes.java             # Entidade estações (preparada)
│   └── Viagens.java              # Entidade viagens (preparada)
├── repository/                    # Repositórios Spring Data
│   ├── UsuarioRepository.java
│   ├── VeiculoRepository.java
│   └── UsuarioVeiculoRepository.java
└── service/                      # Camada de negócios
    ├── TokenService.java         # Serviço JWT
    └── VeiculoService.java       # Lógica de veículos
```

---

## Modelo de Dados

### Entidades Principais

#### **Usuario**
- ID único auto-incremento
- Nome, email, CPF, telefone
- Sexo (enum: MASCULINO/FEMININO)
- Data de nascimento e cadastro
- Senha (preparada para hash)
- Relacionamento com veículos

#### **Veiculos**
- Catálogo com 29+ modelos
- Marca, modelo, autonomia
- Tipos de conector (enum)
- Relacionamento Many-to-Many com usuários

#### **UsuarioVeiculo**
- Tabela de associação
- Nível de bateria personalizado
- Cálculo de autonomia estimada

#### **Estruturas Preparadas (Ainda a ser implementadas)**
- **Rotas:** Nome, coordenadas, distância
- **Estações:** Favoritos de estações de recarga
- **Viagens:** Histórico com métricas CO₂

---

## Catálogo de Veículos

O sistema vem pré-populado com uma base abrangente de veículos elétricos:

### Marcas Disponíveis
- **Tesla:** Model 3
- **BYD:** Dolphin, Dolphin Plus, Seal, Yuan Plus
- **Chevrolet:** Bolt EV
- **BMW:** i3, iX3, i4
- **Volkswagen:** ID.4
- **Nissan:** Leaf
- **Audi:** E-tron
- **Hyundai:** Kona Electric
- **Kia:** Niro EV
- **Porsche:** Taycan
- **Ford:** Mustang Mach-E
- **Volvo:** EX30, XC40 Recharge, C40 Recharge
- **Peugeot:** e-2008, e-208 GT
- **Renault:** Kwid E-Tech, Megane E-Tech
- **GWM:** Ora 03 Skin, Ora 03 GT
- **JAC:** E-JS1
- **Caoa Chery:** iCar
- **Mini:** Cooper S E

### Tipos de Conectores Suportados
- **SAE Tipo 1** (J1772)
- **CCS Tipo 2** (Combined Charging System)
- **CHAdeMO**
- **Tipo 2 AC**
- **GBT** (padrão chinês)

---

## Configurações de Segurança

### Recursos Implementados
- **CORS Configurado:** Comunicação segura com frontend
- **JWT com HMAC-SHA256:** Tokens criptografados
- **Cookies HttpOnly:** Proteção contra XSS
- **Validação Server-Side:** Prevenção de dados inválidos
- **Pool de Conexões Otimizado:** Performance e segurança

### Para Produção
```properties
# Configurações recomendadas para produção
cookie.setSecure(true)                    # Apenas HTTPS
cookie.setSameSite("Strict")              # Proteção CSRF
spring.profiles.active=prod               # Profile de produção
```

---

## Testes

```bash
# Executar todos os testes
./mvnw test

# Executar com relatório de cobertura
./mvnw test jacoco:report

# Executar testes específicos
./mvnw test -Dtest=UsuarioControllerTest
```

---

## Deploy

### Build para Produção
```bash
# Gerar JAR otimizado
./mvnw clean package -DskipTests

# JAR gerado em:
target/emove-0.1.0-alpha.1.jar
```

### Execução
```bash
# Com perfil de produção
java -jar -Dspring.profiles.active=prod target/emove-0.1.0-alpha.1.jar

# Com configurações específicas
java -jar -Xmx512m -Dserver.port=8080 target/emove-0.1.0-alpha.1.jar
```

---

## Contribuindo

1. **Fork** o projeto
2. **Crie** uma branch (`git checkout -b feature/NovaFuncionalidade`)
3. **Commit** suas mudanças (`git commit -m 'Add: Nova funcionalidade'`)
4. **Push** para a branch (`git push origin feature/NovaFuncionalidade`)
5. **Abra** um Pull Request

### Padrões de Commit
```
feat: nova funcionalidade
fix: correção de bug
docs: documentação
style: formatação
refactor: refatoração
test: testes
chore: manutenção
```

---

## Equipe de Desenvolvimento

- **Felipe Giacomini Cocco** - *RA 116525* 
- **Fernando Gabriel Perinotto** - *RA 115575* 
- **Jhonatas Kévin de Oliveira Braga** - *RA 116707* 
- **Lucas Santos Souza** - *RA 116852* 
- **Samuel Wilson Rufino** - *RA 117792* 

---

## Suporte

### Problemas e Sugestões
- **Email:** [emovesuporte@gmail.com](mailto:emovesuporte@gmail.com)
- **Issues:** [GitHub Issues](https://github.com/FeCocco/e-move-backend/issues)

### FAQ Técnico
- **Erro de conexão:** Verifique se MariaDB/MySQL está rodando
- **Token inválido:** Regenere a chave JWT no `application-dev.properties`
- **Permissão negada:** Execute `chmod +x mvnw` no Linux/macOS

---

## Licença

Este projeto ainda está definindo sua licença. Mais informações em breve.

---

## Roadmap Técnico

- [x] **Autenticação JWT** - Sistema completo implementado
- [x] **CRUD de Veículos** - Garagem virtual funcional
- [x] **Base de Dados** - 29+ veículos pré-cadastrados
- [x] **API de Rotas** - Integração OSRM/OpenRouteService
- [x] **API de Estações** - Integração OpenChargeMap
- [x] **Sistema de Viagens** - Histórico e métricas
- [x] **Password Encoding** - BCrypt para senhas
- [ ] **Rate Limiting** - Proteção contra spam
- [ ] **API Documentation** - Swagger/OpenAPI
- [ ] **Monitoring** - Health checks e métricas
- [ ] **Cache Redis** - Performance otimizada

---

<div align="center">

**[Conecte com o Frontend](https://github.com/FeCocco/e-move-frontend)**

*API construída com ⚡ pela equipe e-Move*

**Java:** `17` | **Spring Boot:** `3.3.3`

</div>
