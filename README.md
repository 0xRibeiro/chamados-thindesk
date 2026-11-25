# ThinDesk

Sistema web de gerenciamento de chamados técnicos, desenvolvido para facilitar a abertura, acompanhamento e resolução de solicitações de suporte.

## Tecnologias utilizadas

- **Java 21** — Linguagem de programação.
- **Spring Boot** — Desenvolvimento da aplicação.
- **Spring Security** — Autenticação e controle de acesso.
- **Thymeleaf** — Renderização das páginas web.
- **MongoDB Atlas** — Armazenamento dos dados.
- **Bootstrap 5** — Estilização da interface.
- **Chart.js** — Criação dos gráficos do dashboard.
- **Maven** — Gerenciamento de dependências e compilação.

## Funcionalidades

- Cadastro de usuários.
- Autenticação segura com Spring Security.
- Proteção de senhas utilizando BCrypt.
- Controle de acesso por diferentes perfis.
- Abertura de chamados técnicos.
- Consulta e acompanhamento de chamados.
- Resolução de chamados por técnicos.
- Dashboard administrativo com indicadores e gráficos.
- Gerenciamento de usuários.

## Perfis de acesso

O sistema possui três perfis:

| Perfil | Permissões |
|---|---|
| UsuarioComum | Abrir e acompanhar seus chamados. |
| UsuarioTecnico | Visualizar e resolver chamados. |
| Admin | Acessar o dashboard e gerenciar usuários. |

## Dashboard

O painel administrativo apresenta informações sobre os chamados cadastrados, permitindo visualizar a quantidade total, os chamados abertos e os resolvidos, além de sua distribuição em gráfico.

![Dashboard do ThinDesk](docs/dashboard.png)

## Versionamento

O projeto utiliza o padrão **SemVer**, composto pelo formato `MAJOR.MINOR.PATCH`.

O desenvolvimento foi iniciado na versão **0.5.0**,pois já tinha certas funcionalidades.

Após a implementação e validação das funcionalidades principais, o sistema passou para a versão **1.0.0**, representando sua primeira versão estável.

O padrão de versionamento segue os seguintes critérios:

- **MAJOR:** incrementado quando são realizadas alterações maiores.
- **MINOR:** incrementado quando novas funcionalidades são adicionadas mantendo a compatibilidade.
- **PATCH:** incrementado quando são realizadas correções de erros sem introduzir novas funcionalidades incompatíveis.

### Histórico de versões

| Versão | Descrição |
|---|---|
| 0.5.0 | Versão inicial de desenvolvimento, antes da integração completa dos módulos. |
| 1.0.0 | Primeira versão estável, com autenticação, controle de chamados e dashboard administrativo. |

A versão atual do sistema é **1.0.0**.

## Aplicação publicada

**Acesse:** [INSERIR LINK DO DEPLOY]

A aplicação possui cadastro público, permitindo que qualquer pessoa crie uma conta e acesse as funcionalidades.

### Credenciais de demonstração

Utilize uma conta de demonstração para acessar a aplicação:

- **E-mail:** INSERIR E-MAIL
- **Senha:** INSERIR SENHA

## Como executar localmente

### Pré-requisitos

- Java 21 ou superior.
- MongoDB Atlas.
- Git.

### Instalação

1. Clone o repositório:

```bash
git clone INSERIR_LINK_DO_GITHUB
```

2. Acesse a pasta do projeto:

```bash
cd chamados-thindesk
```

3. Configure as variáveis de ambiente no arquivo `.env`:

```properties
MONGODB_URI=SUA_URI_DO_MONGODB
ADMIN_EMAIL=EMAIL_DO_ADMINISTRADOR
ADMIN_PASSWORD=SENHA_DO_ADMINISTRADOR
```

4. Execute a aplicação:

```bash
./mvnw spring-boot:run
```

No Windows, utilize:

```bash
mvnw.cmd spring-boot:run
```

5. Acesse a aplicação pelo navegador:

```text
http://localhost:8080
```
