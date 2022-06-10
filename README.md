[![shields](https://img.shields.io/badge/made%20with-java-orange)](https://shields.io/)
[![shields](https://img.shields.io/badge/powered%20by-spring-green)](https://shields.io/)
____________________

# PayMyBuddy Application
> -- _We make moving your money easy !_ --


## Prerequisites

### Ensure you have this installed before use:

- Java 14.0.1
- Maven 3.8.4
- Node v16.14.2
- npm 8.5.0
- Angular-cli 9.1.3
- MySQL, Connector/J & Community Server

## Build
Build application with the command `mvn clean install`

## Run
To run the application you have to update the `application.properties` in the `resources` folder:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/paymybuddy?serverTimezone=UTC
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.show-sql = true
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.properties.hibernate.format_sql=true
spring.application.name=PayMyBuddy
server.port=8080
#App Properties
payMyBuddy.app.jwtSecret=payMyBuddySecretKey
payMyBuddy.app.jwtExpirationMs=86400000
```
Modify the `spring.datasource.username=your_username` and the `spring.datasource.password=your_password`.
for the back-end side and then with the command `npm run start` for the frond-end side.


## Try the application

You can run the [Data Script](src/main/resources/MySQL/DataTest.sql) to have some fake users so you can test all the features.

### Super user
You can use user with email '_admin@paymybuddy.com_' and password
'_admin123_' to connect as a super user with 'ADMIN' authorizations.

Otherwise you just have to register as a new user.

## Technology Stack
Component         | Technology
---               | ---
Frontend          | [Angular 9](https://github.com/angular/angular)
Backend (REST)    | [SpringBoot 2.5.6](https://projects.spring.io/spring-boot) (Java)
Database          | [MySQL](https://www.mysql.com/)
Security          | Spring Security & [JSON Web Token](https://jwt.io/)
Persistence       | JPA (Using Spring Data)
Client Build Tools| [angular-cli](https://github.com/angular/angular-cli) npm
Server Build Tools| Maven(Java)

## Documentation

### SQL Scripts
Contains the principal database and the database for the tests.
https://github.com/simoncourtecuisse/PayMyBuddy/tree/master/src/main/resources/MySQL

### Model Database (SQL)

![DatabaseModel_PMB](src/main/resources/Docs/DatabaseModel_PMB.png)

### UML Diagram

![UML_PMB](src/main/resources/Docs/UML_PMB.png)

### Other Resources
See [HELP.md](https://github.com/simoncourtecuisse/PayMyBuddy/blob/main/PayMyBuddy/HELP.md) for helpful documentation regarding Spring Boot and [README.md](/README.md) for important commands and documentation relating to Angular and its embedded server.