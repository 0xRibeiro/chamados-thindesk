# Chamados ThinDesk

Microserviço de chamados técnicos desenvolvido com Java 21, Spring Boot e MongoDB. Reutiliza a autenticação do Login-Seguro por sessões compartilhadas no MongoDB.

## Como executar

É necessário ter Java 21, acesso ao MongoDB e o Login-Seguro funcionando com sessões no MongoDB e o perfil `UsuarioTecnico`.

Crie um arquivo `.env` na raiz do projeto:

```properties
MONGODB_URI=mongodb+srv://USUARIO:SENHA@SEU_CLUSTER/
```

O Login-Seguro e o microserviço devem utilizar o mesmo MongoDB, o banco `login_seguro` e a mesma configuração de sessões.

Inicie o Login-Seguro na porta `8080`. Depois, execute este projeto:

```bash
./mvnw spring-boot:run
```

No Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

O microserviço estará disponível em `http://localhost:8081`.

É preciso usar os endpoints do login-seguro para logar e utilizar o csrf token gerado para
as sessões serem reconhecidas.

Exemplo:

1. Faça `GET http://localhost:8080/login` e copie o valor do campo `_csrf` do HTML.
2. Faça `POST http://localhost:8080/login`, usando `x-www-form-urlencoded` com `email`, `password` e `_csrf`.
3. Após o login, faça novamente o GET de `/login` e copie o novo `_csrf`.
4. Nas requisições POST e PUT do microserviço, envie esse valor no header `X-CSRF-TOKEN`.

## Endpoints

URL base: `http://localhost:8081`

| Método | Rota | Função |
|---|---|---|
| POST | `/api/chamados` | Abrir chamado |
| GET | `/api/chamados/usuario/{id}` | Listar chamados do usuário |
| GET | `/api/chamados/{id}` | Consultar chamado Individual |
| PUT | `/api/chamados/{id}/resolver` | Resolver chamado |

Envie os corpos em JSON.

**Abertura:**

```json
{
  "titulo": "Impressora com problema",
  "descricao": "Não imprime os documentos enviados."
}
```

O sistema registra automaticamente o usuário autenticado, a data de abertura e o status `ABERTO`. Retorna `201`.

**Resolução — exige perfil `UsuarioTecnico`:**

```json
{
  "solucao": "Driver reinstalado e impressão testada."
}
```

O sistema registra a solução, a data de resolução e o status `RESOLVIDO`. Retorna `200`.

## Regras

- Título, descrição e solução são obrigatórios nas respectivas operações.
- O usuário consulta apenas seus próprios chamados.
- Somente o técnico pode resolver chamados.
- Chamados resolvidos não podem ser resolvidos novamente.
- Os dados são persistidos em `login_seguro`, na coleção `chamados`.

Erros previstos: `400` para dados inválidos, `401` para falta de autenticação, `403` para acesso negado ou CSRF inválido, `404` para chamado inexistente e `409` para chamado já resolvido.
