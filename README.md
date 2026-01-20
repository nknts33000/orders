# Order Project

A Spring Boot application for user authentication, order management, and admin role control.

---

## Prerequisites

* **Java 21**
* **Maven Wrapper** (included: `mvnw` / `mvnw.cmd`) — no global Maven required
* **MySQL** accessible and running

  * Create an **empty database** (name provided via `DB_NAME`)
* **Redis** accessible and running

---

## Configuration

The application uses environment variables defined in `src/main/resources/application.properties`.

### Required Environment Variables

```text
JWT_SECRET
DB_ADDRESS        # e.g. localhost
DB_PORT           # e.g. 3306
DB_NAME
DB_USERNAME
DB_PASSWORD
REDIS_ADDRESS     # e.g. localhost
REDIS_PORT        # e.g. 6379
```

---

## One-Time Setup

### 1. Install Java 21

Verify installation:

```bash
java -version
```

### 2. Database Setup (MySQL)

* Ensure MySQL is running
* Create a database for the application
* Create a user with full privileges on that database

### 3. Redis Setup

* Ensure Redis is running and reachable
* Note the host and port

### 4. Clone the Repository

```bash
git clone <your-repo-url>
cd order-project
```

### 5. Configure Environment Variables

#### Windows (PowerShell)

```powershell
setx JWT_SECRET "..."
setx DB_ADDRESS "..."
setx DB_PORT "..."
setx DB_NAME "..."
setx DB_USERNAME "..."
setx DB_PASSWORD "..."
setx REDIS_ADDRESS "..."
setx REDIS_PORT "..."
```

Restart the shell after running `setx`.

Alternatively, for the same session only:

```powershell
$env:JWT_SECRET="your-secret"
```

#### macOS / Linux

```bash
export JWT_SECRET=...
export DB_ADDRESS=...
export DB_PORT=...
export DB_NAME=...
export DB_USERNAME=...
export DB_PASSWORD=...
export REDIS_ADDRESS=...
export REDIS_PORT=...
```

---

## Build & Run

From the repository root:

### Windows

```bash
./mvnw.cmd clean package
./mvnw.cmd spring-boot:run
```

### macOS / Linux

```bash
./mvnw clean package
./mvnw spring-boot:run
```

The application starts on **port 8080** by default.

---

## Quick Smoke Test (HTTP)

You can use **curl**, **Postman**, or any REST client.

### 1. Register User

```http
POST http://localhost:8080/auth/register
Content-Type: application/json

{
  "username": "alice",
  "password": "password123"
}
```

### 2. Login (Get JWT)

```http
POST http://localhost:8080/auth/login
Content-Type: application/json

{
  "username": "alice",
  "password": "password123"
}
```

Response contains a JWT token.

---

### 3. Create Order (Authenticated)

```http
POST http://localhost:8080/order/create
Authorization: Bearer <token>
Content-Type: application/json

{
  "description": "Sample order"
}
```

*(Fields depend on `OrderRequest`)*

---

### 4. Fetch Orders

```http
GET http://localhost:8080/order/get-all?page=0&size=10
Authorization: Bearer <token>
```

---

### 5. Admin-Only: Change User Role

Requires an **admin JWT token**.

```http
PUT http://localhost:8080/admin/change-status
Content-Type: application/json

{
  "userId": 1,
  "userRole": "ADMIN"
}
```

---

## Troubleshooting

* **Java version errors**: ensure `java -version` reports **21**
* **Database connection errors**:

  * Verify DB environment variables
  * Ensure MySQL user has privileges on `DB_NAME`
* **Redis connection errors**:

  * Verify `REDIS_ADDRESS` and `REDIS_PORT`
* **JWT / Authorization errors**:

  * Ensure header format is:

    ```
    Authorization: Bearer <token>
    ```

    (note the space after `Bearer`)

---

## Notes

* Do **not** commit secrets to the repository
* Use environment-specific values for dev / test / prod

---

Happy coding 🚀
