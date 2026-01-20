Prereqs

Java 21 

Maven Wrapper is included (mvnw / mvnw.cmd); no global Maven needed.

MySQL accessible and reachable; create an empty database (name via DB_NAME).

Redis accessible; supply host/port.

Environment secrets/vars (from src/main/resources/application.properties):
JWT_SECRET
DB_ADDRESS (e.g., localhost)
DB_PORT (e.g., 3306)
DB_NAME
DB_USERNAME
DB_PASSWORD
REDIS_ADDRESS (e.g., localhost)
REDIS_PORT (e.g., 6379)
One-time setup
Install Java 21.
Ensure MySQL is running; create a database for this app and a user with full rights on it.
Ensure Redis is running and reachable.
Clone the repo.
Configure environment:
PowerShell example (adjust values):
  setx JWT_SECRET "...  setx DB_ADDRESS "..."  setx DB_PORT "..."  setx DB_NAME "..."  setx DB_USERNAME "..."  setx DB_PASSWORD "..."  setx REDIS_ADDRESS "..."  setx REDIS_PORT "..."
Restart the shell (or set in the same session with $env:VAR="value" before running).
nix shell example:
  export JWT_SECRET=...  export DB_ADDRESS=...  export DB_PORT=...  export DB_NAME=...  export DB_USERNAME=...  export DB_PASSWORD=...  export REDIS_ADDRESS=...  export REDIS_PORT=...
Build & run (from repo root)
Windows:
  ./mvnw.cmd clean package  ./mvnw.cmd spring-boot:run
macOS/Linux:
  ./mvnw clean package  ./mvnw spring-boot:run
The app defaults to port 8080 (Spring Boot default).
Quick smoke test (HTTP)
Use any REST client (curl/Postman). Replace placeholders with real values.
Register user:
  POST http://localhost:8080/auth/register  body: { "username": "alice", "password": "password123" }
Login to get JWT:
  POST http://localhost:8080/auth/login  body: { "username": "alice", "password": "password123" }
Response contains token.
Create order (needs Bearer <token>):
  POST http://localhost:8080/order/create  headers: Authorization: Bearer <token>  body: { "description": "..." }   // per OrderRequest fields
Fetch orders:
  GET http://localhost:8080/order/get-all?page=0&size=10  headers: Authorization: Bearer <token> 
Admin-only role change (requires admin token):
  PUT http://localhost:8080/admin/change-status  body: { "userId": 1, "userRole": "ADMIN" }
Troubleshooting tips
If build fails for Java version, confirm java -version is 21.
If DB connection fails, verify env vars and that MySQL user has rights on DB_NAME.
If Redis connection fails, ensure REDIS_ADDRESS/PORT match the running instance.
For token errors, ensure Authorization header is Bearer <token> (with the space).
