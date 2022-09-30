
# authentication-service

authentication-service is designed as microservice for providing JWT authentication for other services.

authentication-service project is a Spring Boot project. For asynorouns communication Spring Webflux is used for api and repository definetaion.

## API Usage

#### Login authentication

```http
  POST /login
```

| Parametre | Tip     | Açıklama                |
| :-------- | :------- | :------------------------- |
| `userName` | `string` | **Mandatory**. user's name. |
| `password` | `string` | **Mandatory**. user's password. |

#### User Registration

```http
  POST /sign-up
```

| Parametre | Tip     | Açıklama                       |
| :-------- | :------- | :-------------------------------- |
| `userName`      | `string` | **Mandatory**. user's name. |
| `firstName`      | `string` | **Mandatory**. user's First Name.|
| `lastName`      | `string` | **Mandatory**. user's Last Name. |
| `email`      | `string` | **Mandatory**. user's E-mail. |
| `password`      | `string` | **Mandatory**. user's password. |
| `roles`      | `List` |  user's roles. |


  
## Deploy

For starting to project make sure about you have docker hub on the server.
Then easly start with docker-compose file.

```bash
  docker-compose up -d
```
