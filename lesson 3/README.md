# Switch to Java
## Part 1 - JDBC API

JDBC (Java Database Connectivity)

The ODBC implementation in Java is called JDBC. It is the lowest level on which we can interact with a database.

![spring_container](https://camo.githubusercontent.com/5274e7e9a0309a7288660d472f822ede1127c7f3/68747470733a2f2f7777772e7475746f7269616c73706f696e742e636f6d2f6a6462632f696d616765732f6a6462632d6172636869746563747572652e6a7067)

JDBC components:
* DriverManager: This class manages a list of database drivers. Matches connection requests from the java application with the proper database driver using communication sub protocol. The first driver that recognizes a certain subprotocol under JDBC will be used to establish a database Connection.
* Driver: This interface handles the communications with the database server. You will interact directly with Driver objects very rarely. Instead, you use DriverManager objects, which manages objects of this type. It also abstracts the details associated with working with Driver objects.
* Connection: This interface with all methods for contacting a database. The connection object represents communication context, i.e., all communication with database is through connection object only.
* Statement: You use objects created from this interface to submit the SQL statements to the database. Some derived interfaces accept parameters in addition to executing stored procedures.
* ResultSet: These objects hold data retrieved from a database after you execute an SQL query using Statement objects. It acts as an iterator to allow you to move through its data.
* SQLException: This class handles any errors that occur in a database application.

DataSource

The DataSource object is the standard method of connecting to a db (instead of using the DriverManager): https://docs.oracle.com/javase/8/docs/api/javax/sql/DataSource.html

Objects instantiated by classes that implement the DataSource represent a particular DBMS or some other data source, such as a file. A DataSource object represents a particular DBMS or some other data source, such as a file. If a company uses more than one data source, it will deploy a separate DataSource object for each of them. The DataSource interface is implemented by a driver vendor.

You can find more information about DataSources here: https://docs.oracle.com/javase/tutorial/jdbc/basics/sqldatasources.html


Tasks:
* Create a new project for this lesson with dependencies for junit, h2 and logger.
        
* Set up H2 database :

 Download last stable Platform-Independent Zip from here: http://www.h2database.com/html/download.html
 
 Unzip and copy h2-1.4.196.jar to /libs folder in the lesson's project. Start h2:
 java -jar libs/h2-1.4.196.jar -baseDir ~/tmp/h2dbs
 
* Check H2 console at url: http://localhost:8082, using jdbc connection url: jdbc:h2:tcp://localhost:9092/testdb

* Create database schema
* JUnit tests with database 
* Create model and implement CRUD operations in repository
* Demo (swing)

## Part 2 - Spring framework
### Spring container

https://docs.spring.io/spring/docs/current/spring-framework-reference/

The Spring container is at the core of the Spring Framework. The container will create the objects, wire them 
together, configure them, and manage their complete life cycle from creation till destruction. The Spring container uses
*Dependency Injection* to manage the components that make up an application. These objects are called Spring Beans.

The container gets its instructions on what objects to instantiate, configure, and assemble by reading the configuration
metadata provided. The configuration metadata can be represented either by XML, Java annotations, or Java code.
Configuring the container with annotations is the most common way and it is done through reflection using a classpath
scanner which has to be run on container startup.

![spring_container](https://www.tutorialspoint.com/spring/images/spring_ioc_container.jpg)

### Beans

A bean is an object that is instantiated, assembled, and otherwise managed by a Spring IoC container. Bean definitions
contains the *configuration metadata* which tell the container:
* how to create a bean
* its dependencies
* its lifecycle details.

There are multiple available bean scopes (and more can be added):
* singleton - a single instance per Spring container
* prototype - create a new bean each time one is needed (using the same bean definition)
* request - a single bean per HTTP request (only valid in the context of a web-aware Spring ApplicationContext)
* session - a single bean per HTTP session (only valid in the context of a web-aware Spring ApplicationContext)
* etc.

Beans are declared with the **@Bean** annotation. You can attach init and destroy callbacks to beans and thus control
their lifecycle.

Classes annotated with **@Configuration** are used by the Spring container as sources of beans.

Classes annotated with **@Component** and scanned with the component scanner are another valid way of providing bean
definitions

### Dependency Injection

DI is a way of writing applications so that class dependencies are explicitly via constructors or setters. This enables
loose coupling of classes and reusability.

Spring injects dependencies with the **@Autowired** annotation, but in some cases Spring can inject beans without the
annotation being present. When the context is initialized, all the bean definitions are loaded and a dependency graph
is created between all the beans, providing an order of bean construction.

The Spring container has 2 ways of resolving dependencies:
* by bean name, if specified
* by type, if there is a single bean implementing the required type in the container.

Injection can happen:
* in the constructor
* in setters
* in fields.

## Homework

We need to create our first spring application.
This represents a very simple carpooling application that alows us to do a couple of things:
* Apply CRUD operations to serveral entities: Passenger, Car, Driver, Ride, RideRequest, Location
* A driver can create a new Ride, approve or reject RideRequests received from other passengers.
* A passenger can add a RideRequest for a specific Ride.
* A driver can start a ride and move it to status completed.

In the **lesson 3/part2/springannotations** path you can find the skeleton for this project.

![main_log](https://github.com/mihaita-tinta/switch-to-java/blob/master/lesson%203/part2/main.PNG)

* Before starting the application you need to start the H2 instance: **java -jar h2-1.4.196.jar -baseDir ~/tmp/h2dbs**
	The application connects to **jdbc:h2:~/tmp/h2dbs/carpooling**
* The **com.ing.carpooling.Application** can be used to start the application
* **DatabaseConfig** contains the beans used to access the database. We will not use JDBC API directly.
	It's time for now to use Spring's abstraction done with JdbcTemplate 
* An example on how to do it can be found in LocationRepository
* You need to implement CRUD operations for the other entities. This will help you with the next tasks.
	You can find the TODOs in the panel below:

![create_tables](https://github.com/mihaita-tinta/switch-to-java/blob/master/lesson%203/part2/create_tables.PNG)

* **LocationRepositoryTest** is used to test the functionality of the LocationRepository. You need to test your code in the same way.
	Don't forget **to extend RepositoryIntegrationTest** when you do it.
* The next step is to go into the **HomeworkService** class and fix the TODOs