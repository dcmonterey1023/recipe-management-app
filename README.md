# recipe-management-app

# Dependencies
- Java 17
- Spring 6
- Spring-boot 3
- Spring Data JPA
- H2 In Memory DB
- Lombok
- Maven 3.8.7
- Maven Assembler


# How To Run
Spring Run Configuration 
1. Create new maven run configuration
2. Configure
   - "clean install spring-boot:run" in Run command to run with tests
   - "clean install spring-boot:run -DskipTests" in Run command if run with no tests
   - Set Wroking Directory to /web folder

# How To Test
Tool: Postman v2.1 (import file `src/main/resources/static/Recipe Management App.postman_collection.json` for initial tests)

# How To Access H2 DB Console
1. Run the application
2. Using browser, navigate to `http://localhost:8080/h2-console/` or use different port if running with other port.
3. Set Saved Settings to `Generic H2 (Embedded)`
4. Set Setting Name to `Generic H2 (Embedded)`
5. Set Driver Class to `org.h2.Driver`
6. Set JDBC URL to `jdbc:h2:mem:testdb`
7. Username: sa
8. Password: <leave blank>
9. Click Connect