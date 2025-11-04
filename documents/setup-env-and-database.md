# 🧩 Database Setup Guide

## 1️⃣ Create a .env File

Create a file named `.env` in your project root (same folder as `build.gradle`).
```env
# .env
DB_USERNAME=myuser
DB_PASSWORD=mypass
DB_NAME=cpss
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

### Generic Instructions

**To create the database:**
```sql
CREATE DATABASE xxxx;
```

**Look to see if the user is there:**
```sql
SELECT user, host FROM mysql.user;
```

**Drop the user if you need to:**
```sql
DROP USER 'xxxx_username'@'localhost';
FLUSH PRIVILEGES;
```

**Create a user if you need to:**
```sql
CREATE USER 'xxxx_username'@'localhost' IDENTIFIED BY '[password]';
GRANT ALL PRIVILEGES ON xxxx.* TO 'xxxx_username'@'localhost';
FLUSH PRIVILEGES;
```

### For our Project

**To create the database:**
```sql
CREATE DATABASE cpss;
```

**Look to see if the user is there:**
```sql
SELECT user, host FROM mysql.user;
```

**Drop the user if you need to:**
```sql
DROP USER 'cpss_username'@'localhost';
FLUSH PRIVILEGES;
```

**Create a user if you need to:**
```sql
CREATE USER 'cpss_username'@'localhost' IDENTIFIED BY '[password]';
GRANT ALL PRIVILEGES ON cpss.* TO 'cpss_username'@'localhost';
FLUSH PRIVILEGES;
```

## 4️⃣ Run the Application
```bash
./gradlew bootRun
```

You should see a line like:
```
Connected to MySQL Database: cpss
```