# Switch to Java

## Part 1 - Java Multithreading

### Threads

The jvm encapsulates threading functionality in the Thread class:
https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html

There are 2 ways in which to give a new thread some code to run:

1. Provide a Runnable object to the Thread constructor
2. Override the Thread's run() method.

To start the Thread object just call the start() method. In order to wait for it to finish you have to call join().

E.g. :

```java
class RunnableDemo implements Runnable {
   private Thread t;
   private String threadName;
   
   RunnableDemo( String name) {
      threadName = name;
      System.out.println("Creating " +  threadName );
   }
   
   public void run() {
      System.out.println("Running " +  threadName );
      try {
         for(int i = 4; i > 0; i--) {
            System.out.println("Thread: " + threadName + ", " + i);
            // Let the thread sleep for a while.
            Thread.sleep(50);
         }
      } catch (InterruptedException e) {
         System.out.println("Thread " +  threadName + " interrupted.");
      }
      System.out.println("Thread " +  threadName + " exiting.");
   }
   
   public void start () {
      System.out.println("Starting " +  threadName );
      if (t == null) {
         t = new Thread (this, threadName);
         t.start ();
      }
   }
}

public class TestThread {

   public static void main(String args[]) {
      RunnableDemo R1 = new RunnableDemo( "Thread-1");
      R1.start();
      
      RunnableDemo R2 = new RunnableDemo( "Thread-2");
      R2.start();
      
      R1.join();
      R2.join();
   }   
}
```

### Synchronization

When running in a multithreading environment there is always the need for synchronization mechanism. The jdk provides
all the classical synchronization primitives through objects like Locks, Mutexes, Semaphores etc.

More than this, Java has locks built into the language itself with the **synchronized** keyword.

The keyword acquires an objects lock and can be used inside a codeblock:
```java
synchronized(objectidentifier) {
   // Access shared variables and other shared resources
}
```

or in a method declaration:
```java
public synchronized void doExclusively() {
    
}
```

The second is equivalent to running the method inside a **synchronized(this)** block. In case of static methods, the
lock of the class object is acquired instead (since there is no **this** object accessible).

## CompletableFuture

The CompletableFuture class represents a framework of dealing with asynchronous computation in Java.
It provides numerous methods for composing, combining and executing async tasks.

Javadoc: https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html

```java
CompletableFuture<String> future
  = CompletableFuture.supplyAsync(() -> "Hello");
 
assertEquals("Hello", future.get());
```

### Chaining

```java
CompletableFuture<String> completableFuture
  = CompletableFuture.supplyAsync(() -> "Hello");
 
CompletableFuture<String> future = completableFuture
  .thenApply(s -> s + " World");
 
assertEquals("Hello World", future.get());
```

### Composing

But what happens if the next stage is also an async task and you don't want to block the running thread?
You return a CompletableFuture, but you would get CompletableFuture<CompletableFuture<...>>. Luckily, we have 
thenCompose():

```java
CompletableFuture<String> completableFuture 
  = CompletableFuture.supplyAsync(() -> "Hello")
    .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"));
 
assertEquals("Hello World", completableFuture.get());
```

### Exceptions

With handle():

```java
CompletableFuture<String> completableFuture  
  =  CompletableFuture.supplyAsync(() -> {
      if (name == null) {
          throw new RuntimeException("Computation error!");
      }
      return "Hello, " + name;
  })}).handle((s, t) -> s != null ? s : "Hello, Stranger!");
```

With completeExceptionally() :

```java

CompletableFuture<String> completableFuture = new CompletableFuture<>();

CompletableFuture<String> exceptionalFuture = completableFuture.exceptionally(ex -> "oh noes!");

completableFuture.completeExceptionally(
  new RuntimeException("Calculation failed!"));
 
 
completableFuture.get(); // ExecutionException
System.out.println(exceptionalFuture.get());
```

### Async execution 

Each of the chaining methods in CompletableFuture have three forms:

```java
completableFuture.thenAccept(consumer)
completableFuture.thenAcceptAsync(consumer)
completableFuture.thenAcceptAssync(consumer, executor)
```

The async forms are similar, with the difference that without specifying the executor, the *ForkJoinPool.commonPool()* 
will be chosen.

In the non-async form we only have 2 participating threads:
* the one which calls *thenAccept* on the future
* the one which completes the future.

The chained future can be executed by any of these 2 threads:
* if the future is completed at the moment of chaining, the thread which performs the chaining will execute the next 
stage
* otherwise, the thread which completes the first stage will execute the second one.

The asyn form makes sure that the next thread will be executed on a thread belonging to the specified *Executor*.

## Streams

A stream is a sequence of data. The **InputStream** is used to read data from a source and the **OutputStream** is used 
for writing data to a destination.

Stream hierarchy of classes:

![Streams](https://www.tutorialspoint.com/java/images/file_io.jpg)

## Part 2 - Testing

### Unit tests

Unit tests are meant to test the contract of a class. The dependencies of a class under test can be replaced by mock 
objects. Maven runs unit tests during the *test* phase using junit. By default, all the classes which have *Test* in 
their name are run.

The developers run unit tests locally on every change. They should run very fast. 

### Integration tests

Integration tests are meant to test the boundaries of an application with its external dependencies (other 
applications, the OS, etc). Focus on testing only the boundaries and don't test functionality already covered by the 
unit tests.

The failsafe maven plugin runs test classes with *IT* in their name, usually during the *integration-test* phase.  

Integration tests should be run locally when possible and on every change. Even though they will take more time than 
unit tests, they should have a reasonable duration.

### System tests (End to End tests)

These are functional tests meant to test the application as a whole in an environment as close to production as 
possible (usually a test environment). The test application should share as little code as possible with the system 
under test.

Usually, regression and user acceptance tests are included here. These tests can also provide ample documentation, 
with numerous frameworks available: [FitNesse](http://docs.fitnesse.org/FrontPage), [cucumber](https://cucumber.io/).

### Performance tests

Performance tests are meant to test how the application handles high load and determine its breaking point. These are
usually run on a test environment.
 
Frameworks: [JMeter](https://jmeter.apache.org/), [Gatling](https://gatling.io/)

### Mockito

Mockito is a mocking framework for java tests. It mocks classes by implementing their interfaces, mocks which are 
then controlled using the extensive Mockito toolset. 

Mockito can has 2 ways of defining mocks:
* programmatic
* declarative using annotations.

```java
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

// @RunWith attaches a runner with the test class to initialize the test data
@RunWith(MockitoJUnitRunner.class)
public class MathApplicationTester {
	
   //@InjectMocks annotation is used to create and inject the mock object
   @InjectMocks 
   MathApplication mathApplication;

   //@Mock annotation is used to create the mock object to be injected
   @Mock
   CalculatorService calcService;

   @Test
   public void testAdd(){
      //add the behavior of calc service to add two numbers
      when(calcService.add(10.0,20.0)).thenReturn(30.00);
		
      //test the add functionality
      Assert.assertEquals(mathApplication.add(10.0, 20.0),30.0,0);
   }
}
```

Besides mocking behavior, Mockito can also check any interactions with mocks using the [verify functionality](https://static.javadoc.io/org.mockito/mockito-core/2.15.0/org/mockito/Mockito.html#verification).

You can find more extensive docs here: [Mockito](https://static.javadoc.io/org.mockito/mockito-core/2.15.0/org/mockito/Mockito.html)

## Excercise