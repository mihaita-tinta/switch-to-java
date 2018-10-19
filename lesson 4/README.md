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
## Part 2 - Spring Boot

https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/
