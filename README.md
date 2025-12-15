# EscapeRoom

This project is a Java-based application for managing an Escape Room. It provides functionalities for booking, user management, and other related operations. The application is designed to be run in a containerized environment using Docker.

## Running the Project

To run this project, you will need to have Docker and Docker Compose installed on your machine.

1.  **Create a `.env` file:**
    Before running the application, you need to create a `.env` file in the `S3.3-escape-room` directory with the following content:

    ```
    DB_DATABASE=your_database_name
    DB_USER=your_database_user
    DB_PASSWORD=your_database_password
    ```

2.  **Build and run the application:**
    Navigate to the `S3.3-escape-room` directory and run the following command:

    ```bash
    docker-compose up --build
    ```

    This command will build the Docker images and start the application and the database. The application will be accessible at `http://localhost:8080`.

## Project Structure

-   `S3.3-escape-room/`: The root directory of the project.
    -   `src/`: Contains the Java source code.
    -   `db/`: Contains the database initialization scripts.
    -   `pom.xml`: The Maven project configuration file.
    -   `docker-compose.yaml`: The Docker Compose configuration file.
    -   `app.jar`: The compiled Java application.

## Technologies Used

-   Java 21
-   Maven
-   MySQL 8.0
-   Docker

## Contributors

-   

## Further Contributions

Contributions are welcome! If you'd like to contribute, please follow these steps:

1.  Fork the repository.
2.  Create a new branch for your feature or bug fix.
3.  Make your changes.
4.  Submit a pull request with a clear description of your changes.
