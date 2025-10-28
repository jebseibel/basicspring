# 🧩 Database Setup Guide

## 1️⃣ Create a .env File

Create a file named `.env` in your project root (same folder as `build.gradle`).

```env
# .env
DB_USERNAME=myuser
DB_PASSWORD=mypass
DB_NAME=basicspring
```

⚠️ **Never commit .env files to GitHub** — add it to `.gitignore`.

## 2️⃣ Update application.yml

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/${DB_NAME}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

✅ Spring automatically reads `.env` if you use the `spring-dotenv` dependency.

## 3️⃣ Create the Database (MySQL)

Open a terminal or MySQL shell:

```bash
mysql -u root -p
```

Then inside MySQL:

```sql
CREATE DATABASE basicspring;
CREATE USER 'myuser'@'localhost' IDENTIFIED BY 'mypass';
GRANT ALL PRIVILEGES ON basicspring.* TO 'myuser'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```

## 4️⃣ Verify Connection

Run:

```bash
./gradlew bootRun
```

You should see a line like:

```
Connected to MySQL Database: basicspring
```