# MyChat

MyChat is a real-time messaging web application developed with Java and Spring Boot. It allows users to register, log in, exchange messages in real time, and view currently online users.

## Features

- User registration and authentication
- Password hashing with BCrypt
- Real-time messaging using WebSocket and STOMP
- Message persistence in MySQL
- Chat history with timestamps and date separators
- Online user list with live updates
- Web-based user interface

## Technologies

- **Java**
- **Spring Boot**
- **Spring Security**
- **Spring Data JPA**
- **MySQL**
- **WebSocket and STOMP**
- **HTML, CSS and JavaScript**
- **Maven**

## Requirements

- Java JDK
- MySQL Server
- Git

## Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/YOUR-USERNAME/MyChat.git
   cd MyChat
   ```

2. Create a MySQL database named `mychat`.

3. Configure the database connection using your local application configuration. Do not commit real passwords or other credentials.

4. Run the application using the Maven Wrapper:

   **Windows:**

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

5. Open the application in your browser at:

   `http://localhost:8080`

## Security

Passwords are hashed using BCrypt. Database credentials should be configured locally and must never be committed to the repository.

## Project Status

This project is under development. Further improvements may include additional user management features, interface refinements, and expanded security measures.

## Author

XLeonardoXP
