# Switch to Java
## Part 1 - Spring Web-MVC

Official documentation: https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html

The Spring Web MVC framework provides Model-View-Controller (MVC) architecture and ready components that can be used to
develop flexible and loosely coupled web applications. The MVC pattern results in separating the different aspects of
the application (input logic, business logic, and UI logic), while providing a loose coupling between these elements.
* The Model encapsulates the application data and in general they will consist of POJO.
* The View is responsible for rendering the model data and in general it generates HTML output that the client's 
browser can interpret.
* The Controller is responsible for processing user requests and building an appropriate model and passes it to the 
view for rendering.

Of course, since we are building REST services, we are not really interested in the view part.

### spring-boot-starter-web

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

This dependency adds multiple stuff to your application, like:
* a HTTP server (by default Tomcat)
* the Spring Web-MVC framework
* the Jackson JSON serialization library
* Java bean validation api.

### The REST controller

These classes define the endpoints of your service and have to be annotated with the **RestController** stereotype 
(which is a combination of **@Controller** and **ResponseBody** - which tells Spring MVC not to return a html view).

You can map endpoints (URLs) to methods using the **@RequestMapping** annotation and its variants: **GetMapping**, 
**PostMapping** etc.

Path params are defined like this:
```java
@GetMapping("/{id}")
public MyResource getMyResource(@PathVariable Long id)
```

Query params are defined with the **@RequestParam** annotation.

Body params are defined with the  **@RequestBody** annotation.

### Parameter validation

The *javax.validation.validation-api* contains the standard validation APIs. The reference implementation is 
*org.hibernate.validator* which is configured by Spring Boot.

#### Annotation based validation

You can use the various annotations from the *javax.validation.constraints* package:
* @NotNull 
* @Size
* @Email
* etc

In order to apply the validations in a rest controller you have to decorate the parameters with the *@Valid* annotation.

