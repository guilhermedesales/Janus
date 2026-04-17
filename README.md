# Janus - Backend

API de tarefas, categorias e dashboard do projeto Janus.

## Status da migracao

- Login, senha e autorizacao local foram removidos do Janus.
- A autenticacao vem da Auth API externa via JWT.
- O backend valida JWT localmente com HS256 (`JWT_SECRET`).
- O backend valida `sistemaId` do token (`SYSTEM_ID`).

## Contrato JWT esperado

Claims usadas pelo Janus:

- `sub` (UUID): identificador principal do usuario
- `email`: email do usuario
- `nome`: nome de exibicao
- `permissions`: permissoes no formato `recurso:acao`
- `authorities`: authorities adicionais (opcional)
- `sistemaId`: id do sistema alvo
- `exp`: expiracao

## Regras de seguranca

- Header obrigatorio: `Authorization: Bearer <token>`
- Se token invalido/expirado: `401 Unauthorized`
- Sem refresh no backend (refresh e responsabilidade do front + Auth API)
- Permissoes aplicadas com `@PreAuthorize` (ex.: `hasAuthority('tarefa:view')`)

## Variaveis de ambiente

- `JWT_SECRET`: mesmo segredo usado para assinar token na Auth API
- `SYSTEM_ID`: id do sistema que deve bater com `JWT.sistemaId`
- `DB_URL`, `DB_USER`, `DB_PASSWORD`: conexao do banco
- `APP_CORS_ALLOWED_ORIGINS`: origens permitidas no CORS

## Rodando com Docker Compose

```powershell
docker compose up -d --build
docker compose logs -f janus-app
```

Para parar:

```powershell
docker compose down
```

Se mudou `POSTGRES_USER`/`POSTGRES_PASSWORD` e o volume antigo ficou com credenciais antigas, recrie o volume:

```powershell
docker compose down -v
docker compose up -d --build
```

## Endpoints

- Swagger UI: `http://localhost:5000/swagger`
- Perfil autenticado: `GET /usuarios/me`
- Endpoints de negocio (`/tarefas`, `/categorias`, `/dash`) exigem token valido

