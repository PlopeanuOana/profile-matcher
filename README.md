# Profile-matcher

Versions
- 
- java 17
- maven 3.9.5
- spring boot 3.2.2
- Docker version 20.10.12
- Docker compose v2.2.3

Start the application
-
1. Clone the project from repository - git clone https://github.com/PlopeanuOana/profile-matcher.git
2. Perform `mvn clean install` 
3. Navigate to the directory containing the `docker-compose.yml` file - _src/main/java/com/gameloft/profile/compose_ 
4. Run the following command to start the PostgreSQL database container:
    ```
    docker-compose up -d
    ```
5. Run the Spring Boot application using your preferred method. Typically, you can use Maven or an IDE to run the `ProfileApplication` class.
6. The application is running on locahost:8080
7. Import the postman collection `Profile.postman_collection.json`
8. **Create** a new player profile with a **uniq** _player_id_
9. **Get** the profile updated by providing the _player_id_
