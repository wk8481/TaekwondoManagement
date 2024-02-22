# Taekwondo Project - Programming 5

## Student Information
- Name: William Kasasa
- KdG Email: william.kasasa@student.kdg.be
- Student ID: 0162810-44
- Academic Year: 2023-2024
- Group: ACS202

## Description
This project is a Taekwondo management system developed using Java with Spring Boot, Hibernate, and PostgreSQL. It allows for the management of instructors, students, techniques, and their relationships.

## Domain Entities
### Instructor
- Represents a Taekwondo instructor.
- Attributes:
    - id: unique identifier
    - title: title of the instructor
    - name: name of the instructor
- Relationships:
    - One-to-Many with Student: an instructor can have multiple students
    - One-to-Many with Technique: an instructor can teach multiple techniques

### Student
- Represents a Taekwondo student.
- Attributes:
    - id: unique identifier
    - name: name of the student
    - start: start date of the student's training
- Relationships:
    - Many-to-One with Instructor: a student belongs to one instructor
    - One-to-Many with StudentTechnique: a student can have multiple techniques

### Technique
- Represents a Taekwondo technique.
- Attributes:
    - id: unique identifier
    - name: name of the technique
    - type: type of the technique (enum)
    - description: description of the technique
- Relationships:
    - Many-to-One with Instructor: a technique is taught by one instructor
    - One-to-Many with StudentTechnique: a technique can be practiced by multiple students

### StudentTechnique
- Represents the proficiency level of a student in a technique.
- Attributes:
    - id: unique identifier
    - student: reference to the student
    - technique: reference to the technique
    - level: proficiency level (enum)

## Build and Run Instructions
1. Ensure Docker is running on your system.
2. Clone this repository.
3. Navigate to the project directory.
4. Run `docker-compose up` to start the PostgreSQL database.
5. Open the project in your IDE.
6. Configure the following properties in `src/main/resources/application.properties`:
   spring.datasource.url=jdbc:postgresql://localhost:5600/taekwondo
   spring.datasource.username=admin
   spring.datasource.password=admin

arduino
Copy code
7. Build and run the project using Gradle:
   ./gradlew build
   ./gradlew bootRun

markdown
Copy code
8. Access the application at `http://localhost:8081`.

## Dependencies
- Spring Boot
- Spring Data JPA
- Spring Web
- Thymeleaf
- PostgreSQL
- Bootstrap
- WebJars
- Hibernate
- Jakarta Persistence
- Java 17