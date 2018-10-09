# Switch to Java

## Java Multithreading

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
