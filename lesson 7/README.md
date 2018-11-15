## Part 1 - Introduction to Software Security

PowerPoint presentation: https://github.com/mihaita-tinta/switch-to-java/blob/master/lesson%207/security.pptx

## Part 2 - Going to Production

### Standalone

Now that you built your API is time for us to move it to production.
Before we do that, we need to understand how it works locally

```bash
	java -jar my-app.jar
```

Enter http://localhost:8080/

We need more options to understand how the application is working

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

You can enable more endpoints
https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html

```yml
management:
  endpoints:
   web:
    exposure:
     include: info,health,beans,mappings,metrics
```

Change the logger level of you application while running by using the actuator endpoint: loggers

### Docker

We can run our API inside docker.

* You need to create a file: *Dockerfile*

```txt
FROM openjdk:8-jre-alpine

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JAVA_OPTS=""

ADD *.jar /app.jar

EXPOSE 8080:8080
CMD echo "The application will start ..." && \
    java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app.jar

```

* You need to create the image: *docker build . -t car-0.0.1*
* You can now run the image: *docker run -p 8080:8080 5fd1a0ff0ef0*

### Heroku

Setup
	https://devcenter.heroku.com/articles/getting-started-with-java#set-up

Start from the documentation

	https://devcenter.heroku.com/categories/java-support
