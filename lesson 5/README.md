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
@GetMapping("{id}")
public Location findById(@PathVariable Long id) {
	return repository.findById(id)
			.orElseThrow(() -> new LocationNotFoundException());
}
```

Query params are defined with the **@RequestParam** annotation.

Body params are defined with the  **@RequestBody** annotation.

```java
@PutMapping
public Location save(@RequestBody Location location) {
	return repository.save(location);
}
```

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

```java
@PutMapping
public Location save(@Valid @RequestBody Location location) {
	return repository.save(location);
}
```

We need to add our validation annotations in the domain class

```java
@Entity
public class Location {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private double latitude;
    @NotNull
    private double longitude;
    @NotNull
    private String address;
    @NotNull
    private String city;
    @NotNull
    private String state;
    @NotNull
    private String zip;
	// ... getter and setters
}
```

After we start the application, we can send a request

```sh
curl -X PUT -H "Content-Type: application/json" -d '{"address":"Crangasi", "city":"Bucuresti", "zip":"123-123"}' "http://localhost:8080/locations/"
```

We get in return a Bad Request status
```json
{
   "timestamp":"2018-10-26T07:17:27.759+0000",
   "status":400,
   "error":"Bad Request",
   "errors":[
      {
         "codes":[
            "NotNull.location.state",
            "NotNull.state",
            "NotNull.java.lang.String",
            "NotNull"
         ],
         "arguments":[
            {
               "codes":[
                  "location.state",
                  "state"
               ],
               "arguments":null,
               "defaultMessage":"state",
               "code":"state"
            }
         ],
         "defaultMessage":"must not be null",
         "objectName":"location",
         "field":"state",
         "rejectedValue":null,
         "bindingFailure":false,
         "code":"NotNull"
      }
   ],
   "message":"Validation failed for object='location'. Error count: 1",
   "path":"/locations/"
}
```