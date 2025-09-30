# CDL RERA Application

# Spring Boot REST API

## 📌 Overview
This is a Spring Boot application that provides REST APIs for managing application data.  
It follows a layered architecture with **Controller → Service → Repository** and uses JPA/Hibernate for persistence.

---

## 🛠 Tech Stack
- **Java** 17+
- **Spring Boot** 3.x
- **Maven** (Build Tool)
- **PostgreSQL** (Database)
- **Spring Data JPA**
- **JUnit 5 / Mockito** for testing

---

## 🚀 Getting Started

### 1️⃣ Prerequisites
Make sure you have installed:
- Java 17 or higher → `java -version`
- Maven 3.8+ → `mvn -v`
- PostgreSQL 

---

### 2️⃣ Clone the Repository
```bash
git clone https://github.com/your-username/your-repo.git
cd your-repo


mvn clean install
mvn spring-boot:run


src/
 ├── main/
 │   ├── java/                     # Java source code
 │   │   └── com.example.project    # Your application packages
 │   └── resources/                 # Configurations & static files
 │       ├── application.yml
 │       └── static/                # Static resources
 └── test/                          # Unit and integration tests


mvn clean package


java -jar target/your-app-name-0.0.1-SNAPSHOT.jar
