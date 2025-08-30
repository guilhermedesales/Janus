# Janus - Gerenciador de Tarefas

Janus é um projeto em desenvolvimento com Spring Boot que propõe uma solução completa para organização pessoal e cuidado emocional, oferecendo duas interfaces integradas: produtividade/foco e saúde mental.

## > Proposta

#### Inspirado no deus romano Janus (guardião de começos e transições), o app busca oferecer equilíbrio entre organização e bem-estar mental, unindo:

Uma interface para foco, estudos e produtividade (tarefas, categorias, prazos, etc.)

Uma interface para suporte emocional, com recursos voltados ao autocuidado, descompressão e rotina saudável

## > Status do Projeto

Em desenvolvimento, backend já com:

- Spring Security e JWT implementados

- DTOs para transferência de dados entre front-end e backend

- Configurações centralizadas (CORS, Swagger, SecurityConfig)

- Swagger UI funcionando para documentação e testes de API

## > Tecnologias utilizadas

- Java 17+

- Spring Boot

- Maven

- JPA/Hibernate

- Banco de dados:

  - Durante o desenvolvimento: H2

  - Em Produção: PostgreSQL

- IDE: IntelliJ

- Documentação de API: Swagger / OpenAPI 3

- Testes: Junit e Mockito

## > Funcionalidades Implementadas
### >> Backend / API

- CRUD de tarefas e categorias

- Cadastro e login de usuários com JWT

- Endpoints protegidos por Spring Security

- Conclusão de tarefas e atualização automática de status para atrasado

- Filtro de busca avançado de tarefas por categoria, prioridade, datas, prioridade e status

- DTOs para entrada/saída de dados

- Documentação de API com Swagger UI

## > Funcionalidades Planejadas
### >> Módulo de Produtividade

- Pomodoro Timer

- Matriz de Eisenhower

- Visualização e cadastro de tarefas via frontend

### >> Módulo de Saúde Mental (Health Mind)

- Frases motivacionais e mensagens positivas

- Exercícios de respiração / relaxamento

- Diário emocional simples

- Sugestões de pausas e autocuidado

- Integração com sons relaxantes ou vídeos (links ou embeds)

## > Estrutura do Projeto

`model/` - Entidades principais (tabelas do banco)

`dto/` - Objetos de transferência de dados entre front e back

`controller/` - Endpoints REST (camada de entrada)

`repository/` - Interfaces JPA para acesso aos dados

`service/` - Regras de negócio e lógica da aplicação

`config/` - Configurações de CORS, Swagger, Spring Security

`security/` - JWT e configurações de autenticação

## > Futuras funcionalidades

- Frontend completo integrado com backend

- Técnicas de organização e produtividade (Pomodoro, Eisenhower, calendário)

- Módulo Health Mind completo

- Features de acessibilidade
