# Device API

This API is used to manage devices in the system. It provides endpoints for creating, retrieving, updating, and deleting devices.

## Techstack
- **Backend**: Java 21, Spring Boot 3 and Gradle 8+.
- **Database**: PostgreSQL for storing device information.
- **Docker**: For containerizing the application and its dependencies.
- **Testcontainers**: For integration testing with real database instances.
- **Documentation**: Swagger/OpenAPI for API documentation.

## Run locally
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd  device-api 
   ```
2. Set up the PostgreSQL database:
   - You can use Docker to run a PostgreSQL instance:
   - ```bash
     Use the provided docker-compose.yml file to start and run the PostgreSQL database using Docker Compose.
     ```
   - Update the `application.yaml` file with the correct database connection details if necessary.
   - Alternatively, you can set up PostgreSQL locally and create a database named `devices` with the appropriate user and password.
   - Ensure that the database is running and accessible before starting the application.
3. Build the application using Gradle:
   ```bash
   ./gradlew build
   ```
4. Run the application:
   ```bash
   ./gradlew bootRun
   ```
5. The API will be available at `http://localhost:8080/api/devices`.
6. Access the Swagger UI for API documentation at `http://localhost:8080/swagger-ui.html`.
6. To run tests, use the following command:
   ```bash
   ./gradlew test
   ```
   
## Docker Compose   

1. Ensure you have Docker and Docker Compose installed on your machine.
2. Navigate to the project directory where the `docker-compose.yml` file is located.
3. Run the following command to start the application and its dependencies:
   ```bash
   docker-compose up
   ```
4. The API will be available at `http://localhost:8080/api/devices`.
5. To stop the application and its dependencies, use the following command:
   ```bash
   docker-compose down
   ```

## Domain Validation
- **Device Creation Time**: Creation time cannot be updated.
- **Device Name & Brand**: Name and brand properties cannot be updated if the device is in use.
- **Device Deletion**: In use devices cannot be deleted.

## Error Handling
- **400 Bad Request**: Returned when the request body is invalid or missing required fields.
- **404 Not Found**: Returned when a device with the specified ID does not exist.
- **500 Internal Server Error**: Returned when an unexpected error occurs on the server.

## Future Improvements
- Implement authentication and authorization to secure the API endpoints.
- Add pagination and sorting capabilities to the GET /api/devices endpoint.
- Implement batch operations for creating and updating multiple devices at once.
- Integrate with external services for device monitoring and management.

## Conclusion
This API provides a comprehensive set of endpoints for managing devices in the system. By following the request and response formats outlined above, you can easily integrate with the API to create, retrieve, update, and delete devices as needed. Always ensure to handle errors appropriately to maintain a smooth experience when using the API.
