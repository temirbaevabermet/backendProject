# Online Cosmetic Store Backend

## 1. Introduction

The purpose of this document is to specify the requirements for the backend system of the Online Cosmetic Store. The system will allow users to browse cosmetic products, place orders, review products, and manage their shopping experience through a web application. This backend will be built using the Spring framework and will include core functionalities such as CRUD operations, database interaction, API documentation, and exception handling.

The Online Cosmetic Store will manage products, orders, and suppliers in the domain of cosmetics. Key features include managing product details, categorizing products, associating products with suppliers, and tracking orders with multiple products.


# Online Cosmetic Store – Authentication & Security Setup

This document provides an overview of the authentication and security mechanisms implemented in the **Online Cosmetic Store** application, as well as instructions for testing the setup.

---

## 🔐 Security Overview

The application uses **Spring Security** with the following features:

### 1. **JWT Authentication**
- **Login**: Users receive an **access token** (valid for 1 hour) and a **refresh token** (valid for 7 days).
- **Token Refresh**: A refresh token can be used to request a new access token without re-authenticating.

### 2. **OAuth2 Login**
- OAuth2 login is supported via:
  - **Google**
  - **GitHub**
  - **Facebook**
- On first login, a user is registered with a default `CUSTOMER` role.
- A JWT token is generated on successful OAuth login and returned in the response.

### 3. **Role-Based Access Control**
- **CUSTOMER** and **EMPLOYEE** roles can view products and categories.
- **EMPLOYEE** can manage products, categories, and suppliers.
- **SUPPLIER** has access to order-related APIs.
- `/api/auth/**` and `/h2-console/**` are publicly accessible.

### 4. **Custom Filters**
- `JwtAuthFilter` processes JWTs in `Authorization` headers.
- Security context is populated for authenticated users.

---

## 📦 Endpoints Summary

| Endpoint | Method | Access | Description |
|----------|--------|--------|-------------|
| `/api/auth/register` | POST | Public | Register a new user |
| `/api/auth/login` | POST | Public | Authenticate with username & password |
| `/api/auth/refresh-token` | POST | Public | Obtain a new access token |
| `/oauth2/**` | GET | Public | OAuth2 login endpoints |
| `/api/products/**` | GET | Authenticated (CUSTOMER, EMPLOYEE) | View products |
| `/api/products/**` | POST/DELETE | Authenticated (EMPLOYEE) | Manage products |
| `/api/orders/**` | Any | Authenticated (SUPPLIER) | Manage orders |

---

## 🧪 Testing the Authentication System

### 🔧 Requirements
- Java 17+
- Spring Boot
- PostgreSQL or H2
- Postman or similar tool for API testing

### ✅ Register User
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "johndoe",
  "email": "john@example.com",
  "password": "password123"
}
```

### 🔐 Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "johndoe",
  "password": "password123"
}
```
✅ Returns:
```json
{
  "accessToken": "JWT_TOKEN",
  "refreshToken": "REFRESH_TOKEN"
}
```

### 🔄 Refresh Token
```http
POST /api/auth/refresh-token
Content-Type: application/json

{
  "refreshToken": "REFRESH_TOKEN"
}
```

### 🔗 OAuth2 Login
Visit one of the following URLs to initiate login:
- `http://localhost:8080/oauth2/authorization/google`
- `http://localhost:8080/oauth2/authorization/github`
- `http://localhost:8080/oauth2/authorization/facebook`

On success, a JWT token will be returned in the response.

---


## 🔧 Configuration Highlights

### application.properties
```properties
jwt.secret=...           # Long secret key
jwt.expirationMs=3600000
jwt.refreshExpirationMs=604800000

spring.security.oauth2.client.registration.google.client-id=...
spring.security.oauth2.client.registration.google.client-secret=...

spring.security.oauth2.client.registration.github.client-id=...
spring.security.oauth2.client.registration.github.client-secret=...

spring.security.oauth2.client.registration.facebook.client-id=...
spring.security.oauth2.client.registration.facebook.client-secret=...
```


---

## 📁 Project Modules Related to Security

- `AuthenticationController`: REST endpoints for login, register, and token refresh.
- `JwtUtils`: JWT creation and validation logic.
- `JwtAuthFilter`: JWT filter for authorization.
- `CustomOAuth2UserService`: Handles user info from OAuth providers.
- `OAuth2LoginSuccessHandler`: Returns token on successful OAuth login.
- `SecurityConfig`: Configures Spring Security.

---

## 📊 Developer Tools & Interfaces

You can access helpful developer tools via the following URLs:

- 🔍 **H2 Database Console**: [http://localhost:8080/h2](http://localhost:8080/h2)  
  > Used for inspecting in-memory database contents (dev mode only).
  
- 📘 **Swagger UI (OpenAPI)**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)  
  > Interactive API documentation interface.

- 📄 **OpenAPI JSON Spec**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)  
  > Raw OpenAPI documentation in JSON format.

---

> 🛠️ These tools are useful for debugging, testing, and understanding the available API endpoints.

## 📫 Contact
For questions or contributions, please contact +996 555829994;
