# Pet Adoption App

Pet Adoption App is a Spring Boot-based application designed to manage pet adoptions.
It provides functionalities to handle users, animals, adoptions, and preferences. 
The application features a login panel with different views for regular users and administrators. 

## Features

- **User Management**: CRUD operations for users.
- **Animal Management**: CRUD operations for animals.
- **Adoption Management**: CRUD operations for adoptions.
- **Preference Management**: CRUD operations for user preferences regarding animals.

## Technologies Used

- **Spring Boot**: Framework for building the application.
- **Spring Data JPA**: For database operations.
- **Spring Security**: For securing the application with Basic Auth.
- **OpenAPI 3.0**: Specification for defining the RESTful API of the application.
- **Hibernate**: ORM tool.
- **MySQL**: Primary database for the application.
- **MapStruct**: For mapping between entities and DTOs.
- **Lombok**: To reduce boilerplate code.
- **Thymeleaf**: Template engine for rendering dynamic web pages on the server-side.
- **Docker**: Used for running integration tests in isolated containers, ensuring a consistent testing environment.
- **JUnit 5**: For unit testing.
- **Mockito**: For mocking in tests.
- **OpenAI GPT**: For implementing chat functionality.


### Installation

1. **Clone the repository:**

   ```sh
   git clone https://github.com/MayaStachowiak/pet-adoption-app.git
   cd pet-adoption-app


### Set up the MySQL database

Create a database named pet_adoption and update the
src/main/resources/application.properties file with your MySQL credentials.

   ```sh
   spring.datasource.url=jdbc:mysql://localhost:3306/pet_adoption
   spring.datasource.username=root
   spring.datasource.password=root
 ```

### Access the application

The application will be available at http://localhost:8080.


### Running Tests
```sh
mvn test
```

### Error Handling

In the Pet Adoption App, error handling is managed using a Global Exception Handler in Spring Boot. This ensures that the application consistently handles errors and provides meaningful feedback.


----------------------------------------------------------------------------------------------



![image](https://github.com/MayaStachowiak/pet_adoption_app/assets/107054955/4cc56b4d-4c4d-4d0b-9423-3d8be278ae9f)

![image](https://github.com/MayaStachowiak/pet_adoption_app/assets/107054955/b4bf59af-4be4-4299-9461-28e36ff65454)


