# Loja de Jogos — Backend

Este README documenta o backend Spring Boot da aplicação "Loja de Jogos".
Coloque prints de tela e qualquer anexo na seção "Screenshots" abaixo.

## Sumário

- Visão geral
- Funcionalidades
- Requisitos
- Variáveis de ambiente / application.properties
- Como rodar localmente
- Endpoints principais (resumo)
- Scripts / tarefas úteis
- Docker (build e run)
- Testes
- Debug / tips
- Screenshots (substitua pelos seus arquivos)

---

## Visão geral

O backend é uma API REST construída com Spring Boot. Ele fornece endpoints para gerenciar usuários, jogos, pedidos, categorias, autenticação e relatórios.

Estrutura principal:
- Pacote base: `Loja.Loja_de_Jogos`
- Controllers: `Controllers/` (ex.: `UsuarioController`, `GameController`, etc.)
- Services: `Services/` (lógica de negócio)
- Repositories: `Repositories/` (Spring Data JPA)
- Models: `Models/` (entidades JPA)
- DTOs: `dtos/` (objetos de transferência)

---

## Funcionalidades importantes

- Autenticação / autorização (filtros de token JWT)
- CRUD de usuários
- CRUD de jogos
- Listagem paginada de jogos/usuários
- Endpoint de contagem de usuários: `GET /api/usuarios/count` (retorna número total de usuários cadastrados)
- Endpoints para pedidos e carrinho
- Endpoints administrativos para relatórios e pagamentos

> Observação: algumas funcionalidades foram simplificadas para desenvolvimento (ex.: geração de chaves fake no frontend) e módulos relacionados a chaves persistentes foram removidos ou convertidos para stubs.

---

## Requisitos

- Java 17+ (compatível com Spring Boot 3)
- Maven (ou usar o wrapper `mvnw`/`mvnw.cmd` já incluído)
- Banco de dados PostgreSQL (Neon Postgres ou outro)
- Node/NPM apenas se for necessário rodar integrações frontend-local (opcional)

---

## Variáveis de ambiente / application.properties

As configurações de conexão ao banco e JWT ficam em `src/main/resources/application.properties`. Exemplo mínimo (substitua pelos seus valores):

```
spring.datasource.url=jdbc:postgresql://<HOST>:5432/<DBNAME>
spring.datasource.username=<DB_USER>
spring.datasource.password=<DB_PASSWORD>
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true

# Port (opcional)
server.port=8080

# JWT / Segurança (exemplos)
security.jwt.secret=troque-por-uma-chave-secreta
security.jwt.expiration=3600000
```

Se você usa o Neon Postgres, cole a URL JDBC provida pelo Neon em `spring.datasource.url`.

> Não comite credenciais sensíveis no repositório.

---

## Como rodar localmente

Usando o Maven Wrapper (recomendado):

PowerShell (Windows):

```powershell
cd "c:\Users\lucas\OneDrive\Área de Trabalho\Projeto\FinalRalfslima"
./mvnw spring-boot:run
```

Linux / macOS (exemplo):

```bash
./mvnw spring-boot:run
```

A API vai iniciar em `http://localhost:8080` (ou na porta definida em `application.properties`).

---

## Endpoints principais (resumo)

> Use o Postman / Insomnia ou cURL para testar.

Usuários
- GET /api/usuarios — lista usuários (paginado ou lista simples dependendo do controlador)
- GET /api/usuarios/{id} — ver usuário por id
- POST /api/usuarios — criar usuário
- PUT /api/usuarios/{id} — atualizar (se implementado)
- DELETE /api/usuarios/{id} — deletar (se implementado)
- GET /api/usuarios/count — retorna número total de usuários (Long)

Autenticação (exemplos)
- POST /api/auth/login — autenticar (retorna token) (dependendo do controller de auth existente)

Jogos
- GET /api/jogos — listar jogos (paginação/filters)
- GET /api/jogos/{id}
- POST /api/jogos
- PUT /api/jogos/{id}
- DELETE /api/jogos/{id}

Pedidos
- GET /api/pedidos
- POST /api/pedidos

Observação: verifique as rotas reais em `Controllers/` pois nomes exatos podem variar.

---

## Scripts / tarefas úteis

- Compilar:
```powershell
./mvnw clean package
```
- Rodar testes (se existirem):
```powershell
./mvnw test
```

---

## Docker (opcional)

Se houver um `Dockerfile` multi-stage na raiz do backend, você pode construir e rodar a imagem:

```powershell
cd "c:\Users\lucas\OneDrive\Área de Trabalho\Projeto\FinalRalfslima"
docker build -t loja-backend:latest .
# Rodar (exponha porta 8080 e configure variáveis de ambiente para DB)
docker run -e SPRING_DATASOURCE_URL="jdbc:postgresql://host:5432/db" -e SPRING_DATASOURCE_USERNAME=user -e SPRING_DATASOURCE_PASSWORD=pass -p 8080:8080 loja-backend:latest
```

---

## Testes e verificação rápida

- Verifique `GET /api/usuarios/count`:
```bash
curl http://localhost:8080/api/usuarios/count
```
Resposta esperada: um número (por exemplo `42`).

- Se a resposta for 401/403, confira se o endpoint exige autenticação e se o frontend está enviando token.

---

## Debug / problemas comuns

- CORS: se o frontend não consegue acessar o backend, verifique `@CrossOrigin` nos controllers ou configure CORS globalmente nas configurações de segurança.
- Banco: se o app não inicia, verifique a string JDBC, usuário, senha e se o banco aceita conexões (firewall, rede, Neon config).
- Dependências: rode `./mvnw dependency:resolve` se houver problemas de dependências.

---

## Screenshots

Substitua as imagens abaixo com prints das telas do admin, usuários, listagem de jogos, criação de jogo, etc.

### Painel Administrativo

![Painel Admin - estatísticas](./docs/screenshots/admin-dashboard.png)

### Lista de Usuários

![Lista de Usuários](./docs/screenshots/users-list.png)

### Tela de Edição de Jogo

![Editar Jogo](./docs/screenshots/edit-game.png)

> Crie a pasta `docs/screenshots` e coloque os PNGs com os nomes acima. Ajuste os caminhos se preferir.

---

Se quiser, eu posso gerar um `docker-compose.yml` mínimo para rodar o backend com um Postgres local para desenvolvimento. Quer que eu crie isso também?
