# PawPal API - Secure Pet Adoption Backend

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![H2](https://img.shields.io/badge/H2-red?style=for-the-badge)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)

A secure, RESTful API for a pet adoption platform, built with Java and the Spring ecosystem. This project demonstrates a clean, layered architecture and robust security implementation for managing pet listings, user authentication, and adoption workflows from end-to-end.

---

## Key Features

-   **Stateless JWT Authentication:** Secure user registration and login using Spring Security and JSON Web Tokens.
-   **Role-Based Access Control (RBAC):** Distinct permissions for regular users (Adopters) and Admins.
    -   **Users/Adopters:** Can view available pets and submit/view their own adoption requests.
    -   **Admins:** Can perform full CRUD operations on pet listings and approve/reject any adoption request.
-   **Complete Adoption Workflow:** Manages the lifecycle of adoption requests from `PENDING` to `APPROVED` or `REJECTED`.
-   **Professional Architecture:** Follows a professional Controller-Service-Repository pattern to ensure separation of concerns, high maintainability, and testability.

## Tech Stack

-   **Backend:** Java 17, Spring Boot 3
-   **Security:** Spring Security, JWT
-   **Database:** Spring Data JPA / Hibernate, H2 (In-Memory for development), easily configurable for MySQL/PostgreSQL
-   **Build Tool:** Maven
-   **API Testing:** VS Code REST Client (`api-tests.http`)

## API Endpoints

The API is fully testable using the `api-tests.http` file in this repository with the [VS Code REST Client extension](https://marketplace.visualstudio.com/items?itemName=humao.rest-client).

#### Authentication (`/api/auth`)
| Method | Endpoint        | Description                |
|--------|-----------------|----------------------------|
| `POST` | `/signup`       | Register a new user account. |
| `POST` | `/login`        | Log in to get a JWT.         |

#### User (Adopter) Endpoints (`/api/user`)
| Method | Endpoint                  | Description                           |
|--------|---------------------------|---------------------------------------|
| `GET`  | `/pets`                   | Get a list of all available pets.     |
| `GET`  | `/search/{name}`          | Search for available pets by name.    |
| `POST` | `/request-adoption`       | Submit an adoption request for a pet. |
| `GET`  | `/my-requests/{userId}`   | View all requests made by the user.   |

#### Admin Endpoints (`/api/admin`)
| Method | Endpoint                          | Description                               |
|--------|-----------------------------------|-------------------------------------------|
| `POST` | `/pet`                            | Add a new pet (requires form-data).       |
| `GET`  | `/pets`                           | Get a list of all pets (any status).      |
| `GET`  | `/pet/{petId}`                    | Get a single pet by its ID.               |
| `PUT`  | `/pet/{petId}`                    | Update a pet's details.                   |
| `DELETE`| `/pet/{petId}`                   | Delete a pet listing.                     |
| `GET`  | `/adoption-requests`              | Get all adoption requests in the system.  |
| `GET`  | `/adoption-request/{reqId}/{status}`| Approve or reject an adoption request.    |

## How to Run Locally

1.  Clone the repository:
    ```bash
    git clone https://github.com/tanvikreddy123/pawpal-api.git
    ```
2.  Navigate into the project directory:
    ```bash
    cd pawpal-api
    ```
3.  Run the application using Maven:
    ```bash
    mvn spring-boot:run
    ```
4.  The server will start on `http://localhost:8080`.