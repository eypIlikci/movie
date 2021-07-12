# movie

### Teknolojiler- Tech
* JAVA(JDK-11)
* PostgreSQL
* HİBERNATE ORM
* JPA
* LOMBOK
* THYMELEAF
* JavaxValidation
* JMS
* Spring Framework--> Boot, Security, MVC, DATA

### Properties Settings- Ayarlar
```
örnek.value={value}
```
 {value} değeri yerine gerçek değer yazılmalı.
 {value} should be replaced current value

#### PostgreSql properties
```
spring.datasource.url={PostgreSql Server URL}
spring.datasource.username={PostgreSql Server Username}
spring.datasource.password={PostgreSql Server Password}
```

#### SMTP properties
```
spring.mail.host={host}
spring.mail.port={port}
spring.mail.username={username}
spring.mail.password={password}
```

### Kurulum - Setup
```
$ git clone https://github.com/eypIlikci/movie.git
$ cd movie/
$ sudo apt-get install maven
$ mvn -N io.takari:maven:wrapper
$ ./mvnw package
$ cd target/
$ java -jar movie-0.0.1-SNAPSHOT.jar
```

* email: test.admin@test.com şifre-password: Aa12345 ; admin giriş(login)
* email: test.standard@test.com şifre-password: Aa12345; standard giriş(login)

