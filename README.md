# PawPal API - Secure Pet Adoption Backend

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![H2](https://img.shields.io/badge/H2-red?style=for-the-badge)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![License](https://img.shields.io/badge/License-MIT-blue.svg)

A secure, RESTful API for a pet adoption platform, built with Java and the Spring ecosystem. This project demonstrates a clean, layered architecture and robust security implementation for managing pet listings, user authentication, and adoption workflows from end-to-end.

---

## Key Features

- **Stateless JWT Authentication:** Secure user registration and login using Spring Security and JSON Web Tokens.
- **Role-Based Access Control (RBAC):** Distinct permissions for regular users (Adopters) and Admins.
  - **Users/Adopters:** Can view available pets and submit/view their own adoption requests.
  - **Admins:** Can perform full CRUD operations on pet listings and approve/reject any adoption request.
- **Complete Adoption Workflow:** Manages the lifecycle of adoption requests from `PENDING` to `APPROVED` or `REJECTED`.
- **Professional Architecture:** Follows a professional Controller-Service-Repository pattern to ensure separation of concerns, high maintainability, and testability.

---

## Tech Stack

- **Backend:** Java 17, Spring Boot 3
- **Security:** Spring Security, JWT
- **Database:** Spring Data JPA / Hibernate, H2 (In-Memory for development), easily configurable for MySQL/PostgreSQL
- **Build Tool:** Maven
- **API Testing:** VS Code REST Client (`api-tests.http`)

---

## Live API

The API is currently deployed and live on Render. All endpoints can be accessed at the following base URL:

**`https://pawpal-api.onrender.com`**

---

## API Endpoints

The API is fully testable using the `api-tests.http` file in this repository with the [VS Code REST Client extension](https://marketplace.visualstudio.com/items?itemName=humao.rest-client).

#### Authentication (`/api/auth`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/signup` | Register a new user account. |
| `POST` | `/login` | Log in to get a JWT. |

#### User (Adopter) Endpoints (`/api/user`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/pets` | Get a list of all available pets. |
| `GET` | `/search/{name}` | Search for available pets by name. |
| `POST` | `/request-adoption` | Submit an adoption request for a pet. |
| `GET` | `/my-requests/{userId}` | View all requests made by the user. |

#### Admin Endpoints (`/api/admin`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/pet` | Add a new pet (requires form-data). |
| `GET` | `/pets` | Get a list of all pets (any status). |
| `GET` | `/pet/{petId}` | Get a single pet by its ID. |
| `PUT` | `/pet/{petId}` | Update a pet's details. |
| `DELETE`| `/pet/{petId}`| Delete a pet listing. |
| `GET` | `/adoption-requests` | Get all adoption requests in the system. |
| `GET` | `/adoption-request/{reqId}/{status}`| Approve or reject an adoption request. |

---

## How to Get Started

#### 1. Run Locally
- Clone the repository: `git clone https://github.com/tanvikreddy123/pawpal-api.git`
- Navigate into the project directory: `cd pawpal-api`
- Run the application using Maven: `mvn spring-boot:run`
- The server will start on `http://localhost:8081`.

#### 2. How to Use the API
Your API uses JWT for authentication. To access protected routes, you must first log in to receive a token.

**Example Authentication Flow (using Postman or cURL):**
1. **Sign Up:** `POST https://pawpal-api.onrender.com/api/auth/signup`
   - `Body: { "email": "testuser@example.com", "password": "password123", "name": "Test User" }`
2. **Login:** `POST https://pawpal-api.onrender.com/api/auth/login`
   - `Body: { "username": "testuser@example.com", "password": "password123" }`
   - The response will contain a `jwtToken`.
3. **Access Protected Route:** `GET https://pawpal-api.onrender.com/api/user/pets`
   - **Header:** `Authorization: Bearer <your_jwt_token_here>`

---

## Contact

For any questions or feedback, feel free to reach out to the author:
- **Author:** Tanvik Reddy Kotha
- **Email:** tanvikreddy123@gmail.com