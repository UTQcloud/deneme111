# Backend setup (TaskManagement)

This backend uses Spring Boot + SQL Server.

## Quick start

1. Copy the config template:
   ```
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   ```
2. Edit `application.properties` with YOUR settings:
   - YOUR_PORT: Check SQL Server Configuration Manager (e.g., 1433, 58182)
   - YOUR_USERNAME: Usually `sa`
   - YOUR_PASSWORD: Your sa password
3. Create database: `CREATE DATABASE taskmanagement;`
4. Run: `mvn spring-boot:run`

## Important

- `application.properties` is gitignored - each developer has their own version
- Never commit your local `application.properties` with credentials
- Use `application.properties.example` as template for new team members
