# This project is a currency translator.
 
##Based on a given currency and a value it will return the value in the new currency.

This project is built in java 8 with spring boot and it consumes the Open Exchange API to retrieve the current currency rate.
 
In order to store the user activity this system work with a MongoDB where all the results are store.

## Testing
The project comes ready with an instance of Gradle wrapper. To run the tests for the project execute the following command from within the project's root directory:

```
./gradlew clean test integrationTest testAcceptanceLocal -i
```
* `test` - executes unit tests
* `integrationTest` - executes spring-boot tests
* `testAcceptanceLocal` - executes cucumber tests 

## Running
This a Spring Boot application, so it is packed as a jar. 
To start the app, execute:
```
java -Dspring.profiles.active=<profile(s)> -Dserver.port=<port> -jar <pathToJar>.jar
```
Or run the main class(Application.java) from any IDE.

Note, tenant configuration must be supplied for the app to start up, see below for details.

## Used design Patterns: ##
1. Builder
2. Strategy
3. Singleton

## Next steps:
1. Insert date into result model in order to retrieve the last 10 items.
2. Create validation to make sure I don't keep more than 10 items into my repository.
3. Create health check, to test all the external dependencies.
4. Create a docker image with the application, and user docker compose to deploy.
5. It is necessary to insert authentication in the API.
6. Create a circuit breaker, to handle eventual dependency downtime.

##To launch the application##
