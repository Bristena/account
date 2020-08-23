# ING savings account
- Endpoint that will allow an existing user to open a savings account:
    - the account can be opened Monday-Friday between 09:00-18:00
    - one saving account/user
    
The endpoint can be used via Swagger

## Prerequisites
    1. Java version 11
    2. Gradle 6.5
    3. MySQL Server 8.0

## Running the application

1. Run the ing.sql file in a MySql console/MySql Workbench
2. In the 'src/main/resources/application.properties' set the mysql database connection properties(username, password, url)
3. Run 'gradlew.bat bootRun' in a terminal to start the application
