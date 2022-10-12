# Frello

## Setup

A nossa aplicação é separada em:

- Uma API REST ("back-end"). Está localizada sob o diretório `frello`;
- Uma aplicação React ("front-end"). Está localizada sob o diretório
  `frello-frontend`.

As duas aplicações têm requisitos para o ambiente de desenvolvimentos. Eles
estão listados abaixo.

Utilizamos [Nix] (o gerenciador de pacotes) para facilitar a criação de
ambientes de desenvolvimento reproduzíveis.

### Back-end

Esta aplicação utiliza um banco de dados PostgreSQL, e assume uma instância em
funcionamento.

A aplicação requer que algumas variáveis de ambiente sejam definidas. Para o
desenvolvimento local, verificar as definições em `scripts/env.sh`, que é
automaticamente importado se estiver no Nix shell.

Se está utilizando Nix, após entrar no shell utilizando `nix develop`, execute
`pg_ctl start` para inicializar servidor do banco de dados.

- Em relação ao banco de dados PostgreSQL inicializado pelo Nix shell:
  - O shell inicializa ele por padrão utilizando os comandos `pg_ctl init` e
    `pg_ctl start`.
  - O shell não interrompe o banco de dados por padrão. Para interromper o
    servidor do banco de dados, utilize `pg_ctl stop`.

Se está utilizando Nix, as demais dependências (`jdk` e `maven`) também são
instaladas e fornecidas automaticamente ao shell. Caso contrário, devem ser
instaladas manualmente e acessíveis.

Uma vez que as dependências estejam configuradas, o servidor pode ser
inicializado utilizando uma destas duas opções:

- `mvn compile exec:java` para compilar e executar a aplicação.
- `mvn package` para compilar a aplicação, o `jar` estará localizado em
  `frello/target` e poderá então ser passado ao `java`.

Lembre-se que os comandos relacionados ao servidor devem ser executados sob o
diretório `frello`, e não sob a raiz do repositório.

### Front-end

A única dependência assumida é Node.js (testamos na versão 18.x LTS) e o
gerenciador de pacotes `yarn`.

Essas dependências são fornecidas automaticamente pelo shell Nix. Caso
contrário, devem ser instaladas manualmente.

Os comandos do front-end devem ser executados sob o diretório `frello-frontend`.

## Especificidades

Abaixo, listamos algumas observações sobre a nossa aplicação.

### Back-end (Java)

- Após discussão com os professores Fábio e Daniel, concluímos que seria
  proveitoso utilizar a biblioteca [Lombok] para ajudar em relação ao
  boilerplate que adviria da implementação manual de getters e setters.
- Ao contrário dos exemplos fornecidos em sala, estamos utilizando _prepared SQL
  statements_ para reduzir os riscos de SQL injection.

[lombok]: https://projectlombok.org/
[nix]: https://nixos.org/
