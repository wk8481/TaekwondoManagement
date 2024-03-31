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

7. Build and run the project using Gradle:
   ./gradlew build
   ./gradlew bootRun



8. Access the application at `http://localhost:8082`.

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


Certainly! Here's the complete README.md file for Week 2 of the Taekwondo Student Technique System:

```markdown
# Taekwondo Student Technique System - Week 2

## HTTP Requests and Responses

### Retrieving All Students
#### Action: GET all students
#### Request:
```http
GET http://localhost:8082/api/students
Content-Type: application/json
```

#### Response: 200 OK
```http
HTTP/1.1 200 OK
Content-Type: application/json

[
    {
        "id": 1,
        "name": "John Doe",
        "startDate": "2023-01-15"
    },
    {
        "id": 2,
        "name": "Jane Smith",
        "startDate": "2023-02-20"
    }
]
```

#### Response: 404 Not Found (if no students found)
```http
HTTP/1.1 404 Not Found
Content-Type: application/json

{
    "message": "No students found."
}
```

### Retrieving One Student by ID
#### Action: GET student by ID
#### Request:
```http
GET http://localhost:8082/api/students/1
Content-Type: application/json
```

#### Response: 200 OK
```http
HTTP/1.1 200 OK
Content-Type: application/json

{
    "id": 1,
    "name": "John Doe",
    "startDate": "2023-01-15"
}
```

#### Response: 404 Not Found (if student not found)
```http
HTTP/1.1 404 Not Found
Content-Type: application/json

{
    "message": "Student not found."
}
```

### Retrieving All Techniques of a Specific Student
#### Action: GET all techniques of a student
#### Request:
```http
GET http://localhost:8082/api/students/1/techniques
Content-Type: application/json
```

#### Response: 200 OK
```http
HTTP/1.1 200 OK
Content-Type: application/json

[
    {
        "id": 1,
        "name": "Roundhouse Kick",
        "type": "Kicking",
        "description": "Powerful kick to the body."
    },
    {
        "id": 2,
        "name": "Front Kick",
        "type": "Kicking",
        "description": "Quick kick to the opponent's face."
    }
]
```

#### Response: 404 Not Found (if no techniques found for student)
```http
HTTP/1.1 404 Not Found
Content-Type: application/json

{
    "message": "No techniques found for this student."
}
```

### Searching Students
#### Action: GET students by search query
#### Request:
```http
GET http://localhost:8082/api/students/search?query=John
Content-Type: application/json
```

#### Response: 200 OK
```http
HTTP/1.1 200 OK
Content-Type: application/json

[
    {
        "id": 1,
        "name": "John Doe",
        "startDate": "2023-01-15"
    }
]
```

#### Response: 204 No Content (if no students found)
```http
HTTP/1.1 204 No Content
Content-Type: application/json

{
    "message": "No students found."
}
```

### Deleting a Student by ID
#### Action: DELETE student by ID
#### Request:
```http
DELETE http://localhost:8082/api/students/2
Content-Type: application/json
```

#### Response: 204 No Content (if student deleted successfully)
```http
HTTP/1.1 204 No Content
```

#### Response: 404 Not Found (if student not found)
```http
HTTP/1.1 404 Not Found
Content-Type: application/json

{
    "message": "Student not found."
}
```

This README.md provides a summary of the HTTP requests and responses for the Taekwondo Student Technique System implemented in Week 2. Each request/response pair is formatted for easy understanding and reference.
```

Here is the requested information formatted in markdown:

### Week 3 - Added the PATCH and POST Endpoints with JavaScript:

#### API: Create an Issue (Bad Request)

```http
POST http://localhost:8082/api/students
Accept: application/json
Content-Type: application/json

{
  "name": "",
  "startDate": "This is a bad date"
}
```

#### API: Create a Student (Good Request)

```http
POST http://localhost:8082/api/students
Accept: application/json
Content-Type: application/json

{
  "name": "John Doe",
  "startDate": "2020-01-01"
}
```

#### API: Change a Student's Start Date (No Content)

```http
PATCH http://localhost:8082/api/students/2
Content-Type: application/json

{
  "startDate": "The new start date"
}
```

#### API: Change a Student's Start Date (Not Found)

```http
PATCH http://localhost:8082/api/students/999
Content-Type: application/json

{
  "startDate": "The new start date"
}
```

#### API: Change a Student's Start Date (Bad Request)

```http
PATCH http://localhost:8082/api/students/1
Content-Type: application/json

{
  "startDate": ""
}
```
Some fixing is needed

Need to fix some of the loading


### Week 4

#### Users and their Passwords:

| Username | Password   | 
|----------|------------|
| wk8481   | zyxxx25wiwi| 
| TheCEO   | ceo123     |

#### API: Retrieve All Students

```http
GET http://localhost:8082/api/students
```
This endpoint is accessible for all users.

[Click here to access](http://localhost:8082/api/students)

#### Hyperlink to Page Accessible by All Users

[Click here to access the page for all users](http://localhost:8082/api/students)

#### Hyperlink to Page Requiring Authentication

[Click here to access the authenticated page](http://localhost:8082/students/add)

This endpoint `/students/add` is accessible to authenticated users for posting data.

### Week 5

#### Users, Passwords, and Roles:

| Username | Password     | Role  |   
|----------|--------------|-------|
| wk8481   | zyxxx25wiwi  | User |
| TheCEO   | ceo123       | Admin|

The user can access their own self like delete themself as are linked, user is one to many of students

The admin can access all pages, update all and access everything

Need to fix csrf and login stuff

### Week 6

#### Testing

Spring Profiles
test profile: used for testing purposes
Build and Run (gradle)
build: ./gradlew build
run with profile: ./gradlew test -Dspring.profiles.active=test

Have to fix my test

