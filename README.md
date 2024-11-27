# Library and Book Management Application


[![](https://img.shields.io/badge/Spring%20Boot%20Version-3.2.1-orange)](/pom.xml)
[![](https://img.shields.io/badge/Java%20Version-21-orange)](/pom.xml)

## Introduction

**Manage Your Library Seamlessly:** A Comprehensive Platform for Book Enthusiasts.

This Library and Book Management Application is your one-stop solution for everything related to books and libraries. Whether you're managing a personal collection or a community library, this app allows you to:

- Add, update, and delete books.
- Write, read, and moderate reviews and comments.
- Participate in reading challenges and track your progress.
- View exhaustive statistics on your reading habits.
- Collaborate and engage with other book lovers.

**TL;DR:**

- Easily manage your book collection with a user-friendly interface.
- Track and share your thoughts through reviews and comments.
- Engage in personalized or group reading challenges.
- Explore analytics for insights into your reading trends.
- Secure, scalable, and modern backend and frontend technologies.

### Benefits

After using this application:

- You'll save time managing your books and library operations.
- Collaborate better with fellow readers or members of your library.
- Gain valuable insights into reading patterns through analytics.
- Experience a delightful, organized reading journey.

The application uses a state-of-the-art tech stack for reliability and scalability, providing a seamless experience for users. Whether you're a casual reader, librarian, or book enthusiast, this app caters to all.

---

## Application Architecture

To support its versatile features, the application uses the following tech stack:

- **Spring Boot** (Backend) for API development and database management.
- **React and TypeScript** (Frontend) for a smooth and interactive user experience.
- **PostgreSQL** for relational database management.
- **AWS S3** for storing book covers, images, and files.
- **Amazon SQS** for handling asynchronous processes like notifications and updates.
- **Keycloak** for secure authentication and role-based access control.

<p align="center">
  <img src="https://via.placeholder.com/750x666.png?text=Library+Management+Architecture" alt="Library and Book Management Application Architecture">
</p>

The architecture supports real-time updates, secure data handling, and a modular design to accommodate future enhancements.

---

## Key Features

- **Book Management:** Add, update, and organize your library effortlessly.
- **Reviews and Comments:** Share insights and engage in discussions with other readers.
- **Reading Challenges:** Create and join challenges to keep your reading habits exciting.
- **Statistics and Insights:** Understand your reading trends with daily, weekly, and yearly reports.
- **Social Features:** Connect and collaborate with friends, track shared challenges, and recommend books.

---

## Local Project Setup

### Requirements

**Mandatory Requirements:**

- **Java 21** or higher for backend development.
- **Docker Engine** and **Docker Compose** for setting up the local environment.
- **Node.js** and **npm/yarn** for frontend development.
- **PostgreSQL** for database setup.

Check your environment:

```bash
$ java -version
```

```bash
$ docker --version
```

```bash
$ node -v
```

```bash
$ npm -v
```

1. Ensure your Docker Engine is running.
2. Start required services with docker-compose up.
3. Run the backend:
    - Navigate to the `backend` directory.
    - ```bash
      ./mvnw spring-boot:run
      ```
4. Frontend will be started with backend
5. Access the application at `http://localhost:8080`.
6. Access Keycloak Admin (optional): `http://localhost:8888`.

