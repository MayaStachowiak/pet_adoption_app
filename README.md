# Pet Adoption App

Pet Adoption App is a Spring Boot-based application designed to manage pet adoptions.
It provides functionalities to handle users, animals, adoptions, and preferences. 
## Features

- **User Management**: CRUD operations for users.
- **Animal Management**: CRUD operations for animals.
- **Adoption Management**: CRUD operations for adoptions.
- **Preference Management**: CRUD operations for user preferences regarding animals.

## Technologies Used

- **Spring Boot**: Framework for building the application.
- **Spring Data JPA**: For database operations.
- **Hibernate**: ORM tool.
- **MySQL**: Primary database for the application.
- **Lombok**: To reduce boilerplate code.
- **MapStruct**: For mapping between entities and DTOs.
- **JUnit 5**: For unit testing.
- **Mockito**: For mocking in tests.
- **SpringDoc OpenAPI**: For API documentation.


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