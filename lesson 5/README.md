# Switch to Java
## Part 1 - Spring Web

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

We can test our *LocationController* with the *MockMvc* class. We are using Mockito to create Mock Spring Beans for our dependencies. This makes our tests
independent from database

```java
@RunWith(SpringRunner.class)
@WebMvcTest
public class LocationControllerMockTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    LocationRepository repository;

    @Test
    public void getLocations() throws Exception {
        Location a = new Location();
        a.setId(1L);
        a.setLatitude(44.4513003);
        a.setLongitude(26.0415585);
        a.setAddress("Crangasi");
        a.setCity("Bucuresti");
        a.setZip("123-123");
        a.setState("B");
        Mockito.when(repository.findAll())
                .thenReturn(asList(a));

        mvc.perform(MockMvcRequestBuilders.get("/locations/")
                                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                                        .string("[{" +
                                        "\"id\":1," +
                                        "\"latitude\":44.4513003," +
                                        "\"longitude\":26.0415585," +
                                        "\"address\":\"Crangasi\"," +
                                        "\"city\":\"Bucuresti\"," +
                                        "\"state\":\"B\"," +
                                        "\"zip\":\"123-123\"" +
                                        "}]"))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(repository).findAll();
    }
}
```

The same scenario we can test by using the real repository

```java
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LocationControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Sql("/location.sql")
    @Test
    public void getLocations() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/locations/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("[" +
                                "{" +
                                "\"id\":1," +
                                "\"latitude\":11.11," +
                                "\"longitude\":12.12," +
                                "\"address\":\"loc1 \"," +
                                "\"city\":\"buc\"," +
                                "\"state\":\"B\"," +
                                "\"zip\":\"123\"" +
                                "}," +
                                "{" +
                                "\"id\":2," +
                                "\"latitude\":13.13," +
                                "\"longitude\":14.14," +
                                "\"address\":\"loc2 \"," +
                                "\"city\":\"buc\"," +
                                "\"state\":\"B\"," +
                                "\"zip\":\"124\"" +
                                "}" +
                                "]"))
                .andDo(MockMvcResultHandlers.print());

    }
}
```

## Part 2 - Spring Security
Official documentation:
https://spring.io/guides/topicals/spring-security-architecture/
https://spring.io/guides/gs/securing-web/

### Context

In many applications we need to deal with at least two problems:
* Authentication - Who are you?
* Authorization - What can you do?

Spring Security has strategies and extension points for both:

* *AuthenticationManager* can determine the Principal (Who are you?), throw an AuthenticationException or return null to let other managers to decide.

```java
public interface AuthenticationManager {

  Authentication authenticate(Authentication authentication)
    throws AuthenticationException;

}
```

* *ProviderManager* is an implementation of *AuthenticationManager*. It delegates the authentication to a chain of *AuthenticationProvider*s.
The *ProviderManager* has an optional parent that will be used if all authentication providers return null.

```java
public interface AuthenticationProvider {

	Authentication authenticate(Authentication authentication)
			throws AuthenticationException;

	boolean supports(Class<?> authentication);

}
```

* We can customize our authentication manager using the *AuthenticationManagerBuilder*

```java
@Configuration
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

  @Autowired
  DataSource dataSource;

   ... // web stuff here

  @Override
  public configure(AuthenticationManagerBuilder builder) {
    builder.jdbcAuthentication().dataSource(dataSource).withUser("dave")
      .password("secret").roles("USER");
  }

}
```

* Once the authentication is successfully done, the core authorization strategy is used: *AccessDecisionManager* to decide the access control decision
for the given parameters. All three implementations delegates to a chain of *AccessDecisionVoter*. Usually all voters should be *AffirmativeBased*

```java
public interface AccessDecisionManager{
	boolean supports(ConfigAttribute attribute);

	boolean supports(Class<?> clazz);

	void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> attributes);
}
```

* *AccessDecisionVoter* can return a ACCESS_GRANTED, ACCESS_DENIED or ACCESS_ABSTAIN for the voting decision related to the given attributes
 
```java
public interface AccessDecisionVoter{
	boolean supports(ConfigAttribute attribute);

	boolean supports(Class<?> clazz);

	int vote(Authentication authentication, S object,
			Collection<ConfigAttribute> attributes);
}
```

* In an web application, Spring Security is activated by the *FilterChainProxy* Filter. By default it applies to all requests and it has a default order.
It is actually a *@Bean* named *springSecurityFilterChain*. It contains a list of filter chains an dispatches a request to the first chain that matched it.
In Spring Boot there are some predefined filter chains to ignore static resources: css, images or error pages

```java
@Configuration
@Order(SecurityProperties.BASIC_AUTH_ORDER - 10)
public class ApplicationConfigurerAdapter extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.antMatcher("/foo/**")
      .authorizeRequests()
        .antMatchers("/foo/bar").hasRole("BAR")
        .antMatchers("/foo/spam").hasRole("SPAM")
        .anyRequest().isAuthenticated();
  }
}
```

* Method execution can be protected with access rules too. We need to add *@EnableGlobalMethodSecurity* in our Java Config.
There are several annotations we can use: @PreAuthorize, @PostAuthorize, @Secured

```java
@Service
public class MyService {

  @Secured("ROLE_USER")
  public String secure() {
    return "Hello Security";
  }

}
```

Exercise: add @Secured("ROLE_USER") on a method annotated with @Async
