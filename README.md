# Corridas API

API REST para o sistema de gerenciamento de corridas de rua.

## Tecnologias

- Java 21
- Spring Boot 4.0.1
- PostgreSQL 18
- JWT para autenticação

## Pré-requisitos

- Java 21+
- Docker (para o PostgreSQL)

## Executando

### 1. Subir o banco de dados

```bash
docker-compose up -d
```

### 2. Executar a API

```bash
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`

## Testando com o App Mobile (ngrok)

Para testar o app mobile com a API local, é necessário expor a API usando o ngrok.

### 1. Criar conta no ngrok

Acesse [ngrok.com](https://ngrok.com) e crie uma conta gratuita.

### 2. Instalar o ngrok

```bash
# Windows (com chocolatey)
choco install ngrok

# Ou baixe diretamente do site
```

### 3. Autenticar o ngrok

Após criar a conta, copie seu authtoken do dashboard e execute:

```bash
ngrok config add-authtoken SEU_AUTH_TOKEN
```

### 4. Expor a API

Com a API rodando na porta 8080:

```bash
ngrok http 8080
```

### 5. Copiar a URL

O ngrok irá gerar uma URL pública como:
```
https://xxxx-xxx-xxx.ngrok-free.app
```

Use essa URL no arquivo `constants/api.ts` do projeto mobile.

## Endpoints

### Autenticação
- `POST /api/auth/login` - Login
- `POST /api/auth/registro/organizador` - Registro de organizador
- `POST /api/auth/registro/atleta` - Registro de atleta

### Eventos
- `GET /api/eventos` - Listar eventos do organizador
- `POST /api/eventos` - Criar evento
- `GET /api/public/eventos` - Listar eventos públicos

### Categorias
- `GET /api/eventos/{id}/categorias` - Listar categorias do evento
- `POST /api/eventos/{id}/categorias` - Criar categoria

### Inscrições
- `GET /api/atleta/inscricoes` - Listar inscrições do atleta
- `POST /api/atleta/eventos/{id}/inscricoes` - Inscrever-se em evento
