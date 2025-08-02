# 🚀 Medical Appointment Management API

## 🌟 Overview
`medical-api` is a Java-based application designed to manage medical appointments. It allows users to register doctors and patients, as well as to schedule and cancel appointments. The application supports full CRUD operations for both doctors and patients and provides robust features for managing appointments.

---

## ⚙️ Features
* Physician Management (CRUD)

    * Register, update, retrieve, and delete doctor records.
* Patient Management (CRUD)

    * Register, update, retrieve, and delete patient records.
* Appointment Scheduling

    * Schedule, update, retrieve, and cancel appointments for patients with doctors.
* Authentication & Authorization:

    * JWT-based security for login and API access.

---

## 🎨 Design

The design of the mobile application is available at this link: [Figma](https://www.figma.com/design/YzrxzqS5arel2xQzzXZDgU/design-med-api?node-id=0-1&p=f)

---

## 📄 Documentation

The documentation of the application's features can be accessed at this link: [Trello](https://trello.com/b/lkJPfhfc/api-voll-med-business-rules)

---

## 🛠 Technologies
* **Java**: The core programming language used for developing the application.
* **Java 21 SDK**: The application runs with Java 21 for enhanced performance and new features.
* **Spring Boot 3**: Framework for building the application quickly and efficiently with embedded server support.
* **Maven**: Build automation tool used for managing dependencies and running tasks.
* **MySQL**: Database for storing the application data (doctors, patients, appointments).
* **JPA/Hibernate**: Object-Relational Mapping (ORM) for managing data persistence between Java objects and MySQL.
* **Flyway**: Database migration tool for managing version-controlled schema changes.
* **Validation**: Bean validation for input validation in various parts of the application.
* **Spring Security & JWT Token**: Provides security features including authentication and authorization with JWT tokens.
* **SpringDoc**: Automatically generates API documentation (OpenAPI/Swagger) from your Spring controllers.
* **JUnit 5**: Framework for unit testing the application.
* **MockMvc**: For testing the controllers in isolation.

---

## 📦 Installation Guide
### 🖥️ Prerequisites
* **JDK 21** or later
* **Maven** 
* **MySQL**: Ensure you have MySQL installed and running, or you can use a cloud-based MySQL service.

1. **Clone the repository**
    ```bash 
   git clone https://github.com/josesreyesdev/Medical-API.git
   cd Medical-API
    ```
2. **Configure the MySQL Database**
   
    Make sure your MySQL server is running. You can create a new database with the following commands:
    ```sql
   CREATE DATABASE medical_db;
   ```
3. **Set up application properties**

   In the `src/main/resources/application.properties` (or `application.yml`), configure your database connection. Modify the following:
    ``` properties
   spring.datasource.url=jdbc:mysql://localhost:3306/medical_db
   spring.datasource.username=your_db_username
   spring.datasource.password=your_db_password
   spring.flyway.enabled=true
   ```
4. **Build and run the application**
    ```bash
   ./mvnw spring-boot:run
   ```
   Alternatively, you can build the JAR file and run it:
    ```bash
   ./mvnw clean package
   java -jar target/medical-api-0.0.1-SNAPSHOT.jar
   ```
   The application should now be running at `http://localhost:8080/api/`.
5. **Access API Documentation**

   Once the application is running, you can access the automatically generated API documentation via Swagger UI at:
    ```bash
   http://localhost:8080/swagger-ui/
   ```
   This will provide you with an interactive interface to test the API endpoints.

---

## 🔐 Security
The API is secured using Spring Security with JWT tokens for authentication. You will need to log in to receive a token, which must be included in the Authorization header of requests.
#### Authentication Example
To authenticate, send a `POST` request to `/api/auth` with the following payload:
```json
{
  "email": "your_email@example.com",
  "password": "your_password"
}
```
On success, you will receive a JWT token that you can use for subsequent requests.
```json
{
  "accessToken": "your_jwt_token_here"
}
```

---

## 📝 License
Project developing by [Jose S Reyes](https://github.com/josesreyesdev/)
