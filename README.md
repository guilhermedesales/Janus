# Janus - Gerenciador de Tarefas

**Janus** é um projeto em desenvolvimento com **Spring Boot** que propõe uma solução completa para organização pessoal e cuidado emocional, oferecendo duas interfaces integradas: **produtividade/foco** e **saúde mental**.


## > Proposta

Inspirado no deus romano Janus (guardião de começos e transições), o app busca oferecer equilíbrio entre **organização** e **bem-estar mental**, unindo:

- Uma interface para foco, estudos e produtividade (tarefas, categorias, prazos, etc.)
- Uma interface para suporte emocional, com recursos voltados ao autocuidado, descompressão e rotina saudável

## > Status do Projeto

**Em desenvolvimento**, começo do backend

## > Tecnologias utilizadas

- Java 17+
- Spring Boot
- Maven
- JPA/Hibernate
- Banco de dados:
  - Durante o desenvolvimento - H2
  - Em Produção - PostgreSQL
- IDE: IntelliJ

## > Funcionalidades Planejadas

### >> Módulo de Produtividade

- CRUD de tarefas: título, descrição, prioridade, status, datas de início/fim
- Classificação por categorias
- Filtros por prioridade, status e categoria
- Técnicas de organização:
  - **Pomodoro Timer**
  - **Matriz de Eisenhower**
- Interface web para cadastro e visualização de tarefas

### >> Módulo de Saúde Mental (Health Mind)

- Frases motivacionais e mensagens positivas
- Exercícios de respiração / relaxamento
- Diário emocional simples
- Sugestões de pausas e autocuidado
- Integração com sons relaxantes ou vídeos (links ou embeds)

## > Estrutura do Projeto

- `model/` - Entidades principais (tabelas do banco)
- `controller/` - Endpoints REST (camada de entrada)
- `repository/` - Interfaces JPA para acesso aos dados
- `service/` - Regras de negócio e lógica da aplicação


## > Futuras funcionalidades

 - Autenticação de usuário (principal objetivo desse projeto-estudo)
 
 - Um frontend

 - feats de acessibilidades
