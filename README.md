The Online Cosmetic Store system allows users to manage products, orders, and suppliers in the domain of cosmetics. The system includes features like managing product details, categorizing products, associating products with suppliers, and tracking orders with multiple products.
Project:Structure
src/: The main directory for the project's source code, containing all Java classes and resources.
main/: The directory for the application's main source code.
java/: This directory contains the packages and Java classes that form the core of the application.
com.example.OnlineCosmeticSrore/:The package containing the application's classes.
controller/:  The package where the controllers are located.
bootstrap/:where we create sample values ​​for the table
dto/: A package containing objects used for transferring data between application subsystems via getters/setters.
entities/: A package containing entities—objects with names and attributes.
exceptions/: A package containing exceptions.
mapper/: A package containing mappers that help link database tables to application objects.
repositories/: A package containing JPA repositories.
service/: A package containing Spring Boot services.
resources/:A directory for application resources such as configuration files and static assets.
application.properties:The Spring Boot configuration file containing application settings.
application-dev.properties:Where we connect to the H2 database
application-prod.properties:Where we connect to the PostgeSQL database
test/: A directory for test source code and resources.
pom.xml: The Maven project file defining dependencies and project configuration.
target/: A directory where Maven stores compiled classes, artifacts, and test results.
