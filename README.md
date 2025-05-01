# Online Cosmetic Store Backend

## 1. Introduction

The purpose of this document is to specify the requirements for the backend system of the Online Cosmetic Store. The system will allow users to browse cosmetic products, place orders, review products, and manage their shopping experience through a web application. This backend will be built using the Spring framework and will include core functionalities such as CRUD operations, database interaction, API documentation, and exception handling.

The Online Cosmetic Store will manage products, orders, and suppliers in the domain of cosmetics. Key features include managing product details, categorizing products, associating products with suppliers, and tracking orders with multiple products.

## 2. Database
The application is configured to use **H2** as the default database profile, with **PostgreSQL** as an alternate profile. Both databases are supported for different environments (development and production).


# 🛡️ Authentication & Authorization Overview

The **Online Cosmetic Store Backend** uses a robust authentication and authorization system powered by **Spring Security** and **JWT tokens**. It supports both **email/password login** and **OAuth2 providers** (like Google or GitHub,Facebook). Upon successful login, users receive an **access token** and a **refresh token** for secure API interaction.

---

## 👤 User Roles

The system defines **three roles** with different permissions:

| Role      | Description                                  | Permissions                          |
|-----------|----------------------------------------------|--------------------------------------|
| `CUSTOMER` | End users who browse and purchase products   | View products and categories         |
| `EMPLOYEE` | Admins responsible for managing the store    | Create, update, delete catalog data  |
| `SUPPLIER` | Partners handling orders and logistics       | Access and manage assigned orders    |

---

## 🔐 Authentication Endpoints

| Endpoint                | Method | Description                           | Access Level |
|-------------------------|--------|---------------------------------------|--------------|
| `/api/auth/register`    | POST   | Register a new user (default: CUSTOMER) | Public       |
| `/api/auth/login`       | POST   | Authenticate and receive JWT tokens   | Public       |
| `/api/auth/refresh-token` | POST | Obtain new access token using refresh token | Public |
| `/oauth2/**`            | GET    | OAuth2 login routes (e.g., Google)    | Public       |

---

## 📌 Role-Based API Access

| Endpoint                                             | Methods        | Roles Allowed          | Description                     |
|------------------------------------------------------|----------------|-------------------------|---------------------------------|
| `/api/products/**`, `/api/categories/**`            | `GET`          | CUSTOMER, EMPLOYEE      | View products and categories    |
| `/api/products/**`, `/api/categories/**`, `/api/suppliers/**` | `POST`, `DELETE` | EMPLOYEE                | Manage products, categories, and suppliers |
| `/api/orders/**`                                     | ALL            | SUPPLIER                | View and manage orders          |

---

## 🔁 Token Lifecycle

- After successful login, the system returns:
  - `accessToken`: Short-lived JWT token for authorization
  - `refreshToken`: Long-lived token for refreshing access tokens

 ---
 
 ## 🌐 OAuth2 Login Support

- Users can log in via OAuth2 (e.g., **Google**).
- If the user does not already exist in the system, they are **automatically registered** as a `CUSTOMER`.

---

## 🚫 Access Control Behavior

- Requests without proper authentication or authorization will receive:
  - `401 Unauthorized` – When the token is missing or invalid.
  - `403 Forbidden` – When the authenticated user lacks sufficient permissions.

> 🔒 Access is controlled via Spring Security filters and role-based method annotations.

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
