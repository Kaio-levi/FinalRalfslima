
## Propósito
Forneça orientações rápidas e práticas para que um agente de IA contribua corretamente e com baixo risco neste repositório.

## Arquitetura geral (arquivos essenciais)
- Backend: Java 17 + Spring Boot (Maven). Arquivos principais:
  - `pom.xml` (dependências, versão Java, plugin spring-boot)
  - `src/main/resources/application.properties` (credenciais do banco, configuração do token JWT)
  - `src/main/java/Loja/Loja_de_Jogos/LojaDeJogosApplication.java` (ponto de entrada)
  - Pacotes: `Controllers/`, `Services/`, `Repositories/`, `Models/`, `dtos/`, `infra/` — siga estes limites: Controllers expõem REST, Services têm lógica de negócio, Repositories são interfaces Spring Data JPA, Models são entidades JPA.

- Frontend: Angular 17 em `gameStore-front/`.
  - `gameStore-front/package.json` (scripts de desenvolvimento)
  - `gameStore-front/src/app/services/` (clientes de API, ex: `game.service.ts` usa `http://localhost:8080/api`)
  - `gameStore-front/src/app/pages/` (páginas de funcionalidades)

## Executar / buildar / testar (comandos práticos)
- Backend (Windows, raiz do repo `FinalRalfslima`):
  - Executar dev: `mvnw.cmd spring-boot:run` (ou `./mvnw spring-boot:run` no Unix)
  - Buildar JAR: `mvnw.cmd clean package` → artefato em `target/`
  - Testes: `mvnw.cmd test`

- Frontend (dentro de `gameStore-front`):
  - Instalar: `npm install`
  - Servidor dev: `npm start` (executa `ng serve` → http://localhost:4200)
  - Buildar: `npm run build`
  - Testes: `npm test`

## Premissas importantes de execução/local
- Backend espera MySQL em `jdbc:mysql://localhost:3306/lojajogos` com usuário `root` e senha `admin` (ver `application.properties`). Se rodar localmente, atualize credenciais ou use Docker. Testes podem esperar um banco rodando se não forem mockados.
- Frontend consome API base `http://localhost:8080/api` (ver `game.service.ts`). Ao alterar rotas do backend, atualize os serviços do frontend.
- JWT: `api.security.token.service=123` está definido nas propriedades — tratamento de token implementado no backend (ver `infra/`).

## Convenções específicas do projeto
- Pacotes Java seguem separação clara: Controllers → Services → Repositories → Models → dtos. Coloque preocupações transversais em `infra/`.
- Frontend organiza páginas em `src/app/pages/` e componentes compartilhados em `src/app/shared/` — adicione novos elementos de UI nestas pastas.
- Use anotações Lombok nas classes de modelo; garanta que o processamento de anotações está ativo (configurado no `pom.xml`).

## Pontos de integração & externos para atenção
- Banco de dados: MySQL (conector no `pom.xml`). Evite hardcode de URLs diferentes; prefira sobrescrever por ambiente.
- Segurança: Spring Security + dependência java-jwt. Alterar fluxo de autenticação pode exigir mudanças tanto no backend (`infra/`) quanto no serviço de autenticação do frontend (`gameStore-front/src/app/services/auth.service.ts`).
- OpenAPI: `springdoc-openapi-starter-webmvc-ui` presente — Swagger UI normalmente em `/swagger-ui/index.html`.

## Pequenos "faça e não faça" para agentes de IA
- Faça: Atualize tanto assinaturas dos controllers do backend quanto chamadas dos serviços do frontend ao mudar contratos REST. Exemplo: `GET /api/games` é usado por `game.service.ts`.
- Faça: Rode backend e frontend localmente para verificar integração antes de alterar formatos de DTO.
- Não faça: Alterar credenciais do banco ou remover privilégios em `application.properties` sem adicionar sobrescrita segura por ambiente.
- Não faça: Remover anotações Lombok; se substituir, garanta que a compilação ainda funciona (Java 17 + maven compiler plugin).

## Onde buscar exemplos
- Padrões de API REST: `src/main/java/Loja/Loja_de_Jogos/Controllers/` (controllers mapeiam endpoints usados pelo `gameStore-front`)
- Lógica de negócio: `src/main/java/Loja/Loja_de_Jogos/Services/`
- Clientes de API no frontend: `gameStore-front/src/app/services/*.service.ts` (ex: `game.service.ts` mostra base e endpoints da API)

Se algo nestas notas estiver pouco claro ou quiser exemplos adicionais (ex: abrir um controller ou service específico para documentar formatos de request/response), diga qual área e eu expando as instruções.
