# BACKEND NIKE STORE
![Sneakers](docs/shoe.png)

This is the backend of a sneaker store that works with REST. 
The user can register and log in. 
He can also view the sneakers and add them to the cart, 
and after that, he can place an order. 
The administrator can view, add, edit, delete sneakers
# Technologies
* Java
* Maven
* Spring Boot
* Spring Web
* Spring Security
* Spring Data JPA
* Spring Validator
* Spring Test
* JWT
* PostgresSQL
* Lombok
* Docker
* Swagger

# Swagger documentation
Documentation is available at

http://85.193.81.230:8080/swagger-ui/index.html#/
# Docker Container
Docker container is available at

https://hub.docker.com/repository/docker/zvaryyka/my-nike-app/general
# Application properties
spring.datasource.driverClassName=org.postgresql.Driver
spring.datasource.url=your_url
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.show_sql=true
jwt.secret=your_secret