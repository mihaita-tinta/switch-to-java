# Switch to Java
## Part 1 - Data access

More info at:
https://docs.spring.io/spring/docs/5.1.1.RELEASE/spring-framework-reference/data-access.html#spring-data-tier

### The Spring JdbcTemplate

The **JdbcTemplate** class is a utility wrapper over the JDBC API:
* it provides standard templates of opening and closing resources when dealing with databases
* it implements exception handling on database logic. No more SQLException, now we get: DataAccessException
* provides uniform way of dealing with transactions.

```sql
create table employee(  
id number(10),  
name varchar2(100),  
salary number(10)  
);  
```


```java
public Boolean saveEmployeeByPreparedStatement(final Employee e){  
    String query="insert into employee values(?,?,?)";  
    return jdbcTemplate.execute(query,new PreparedStatementCallback<Boolean>(){  
    @Override  
    public Boolean doInPreparedStatement(PreparedStatement ps)  
            throws SQLException, DataAccessException {  
              
        ps.setInt(1,e.getId());  
        ps.setString(2,e.getName());  
        ps.setFloat(3,e.getSalary());  
              
        return ps.execute();  
              
    }  
    });  
}  
```

### PlatformTransactionManager

This is the central interface in Spring's transaction infrastructure. 
Applications can use this directly, but it is not primarily meant as API: Typically, applications will work with either TransactionTemplate or declarative transaction demarcation through AOP.

```java
DefaultTransactionDefinition def = new DefaultTransactionDefinition();
// explicitly setting the transaction name is something that can be done only programmatically
def.setName("saveLocation");
def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

TransactionStatus status = transactionManager.getTransaction(def);
try {
	repository.save(getSomeLocation());
} catch (IllegalStateException ex) {
	transactionManager.rollback(status);
}
repository.findAll().forEach(l -> log.info(l.getAddress()));

transactionManager.commit(status);
```

### TransactionTemplate

Template class that simplifies programmatic transaction demarcation and transaction exception handling.
The central method is execute, supporting transactional code that implements the TransactionCallback interface.
This template handles the transaction lifecycle and possible exceptions such that neither the TransactionCallback implementation nor the calling code needs to explicitly handle transactions.

```java
        transactionTemplate.execute(transactionStatus -> repository.save(getSomeLocation()));
```

## Declarative transaction management
One can also enable transactions at methods level by adding *@Transactional* annotation, add *@EnableTransactionManagement* to your configuration.

```java
    @Transactional
    public Location save(Location location) {
        return repository.save(location);
    }
```

We can see that we are no longer calling directly our service. Some Spring AOP instances are involved now:
![create_tables](https://github.com/mihaita-tinta/switch-to-java/blob/master/lesson%204/spring-transactions/transactions_call_stack.PNG)

More information about how AOP works here:
https://docs.spring.io/spring/docs/5.1.1.RELEASE/spring-framework-reference/core.html#aop

Some things you need to know:
* A **join point** represents the execution of a method
* A **pointcut** is the condition to identify join points
* The **pointcut expression language** is a way of defining pointcuts programatically
* The Spring Pointcut Designators keywords are: execution() - method execution join points, within() - match join points of different type
	this() - the bean reference is of given type, target() - the target bean reference is of given type,
	@target() - join points where the class has the given annotation,
	@args() - join points where the arguments are of the given type,
	@within() - join points where the type has the given annotation
	@annotation() - join points where the subject has the given annotation

https://www.baeldung.com/spring-aop-pointcut-tutorial

## Part 2 - Spring Boot

https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/

Spring Boot's goal is to simplify the configuration of Java applications. It is built on numerous conventions which 
greatly reduce boilerplate needed to start new applications.

Some features:
* has a spring-boot-starter parent which sets up the pom.xml in a commonly used configuration
* uses special spring-boot-starter dependencies which handle commonly used dependenciesS
* comes with new auto-configuration mechanism which are used to set up some commonly used beans in sensible ways 
(DataSources, HttpServers, JSONSerialization etc)
* creates and configures beans based on properties defined in config files.

Basically, Spring Boot is a usability layer over Spring Framework. You can always disable the Spring Boot 
auto-configuration of certain beans and create them in the traditional way.

### CarPoolingAPI with Spring Boot

Bootstrap a new Spring Boot project: https://start.spring.io/

![starter](https://github.com/mihaita-tinta/switch-to-java/blob/master/lesson%204/images/spring-boot-1.PNG)

Open the project in your favorite IDE. There are several things generated for us. We will go over each. 

* application.properties (Available options: https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html)
	You may need to install the Spring Assistant plugin:
![plugin](https://github.com/mihaita-tinta/switch-to-java/blob/master/lesson%204/images/intellij-spring-assistant.PNG)

