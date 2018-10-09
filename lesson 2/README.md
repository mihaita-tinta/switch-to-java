# Switch to Java

## Lesson 1 - Java Building Blocks

### Before you start

You need to install on your machine:
* JDK 8 from: https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
* Git https://git-scm.com/download/win
* Java IDE: download Intellij https://www.jetbrains.com/idea/download/#section=windows

Things to know:
* JRE: Java Runtime Environment is an implementation of the Java Virtual Machine where your Java programs run on
* JDK: Besides of the JRE it contains also the Java Development Kit needed for developing java applications (java, javac ...)
* Java SE: Java Standard Edition contains all Java libraries used in any Java program (java.lang, java.util, java.math, java.io, java.net ...)
* Java EE: Java Enterprise Edition Java EE includes many components of the Java Standard Edition (Java SE).The Java EE platform consists of a set of services, APIs, and protocols that provide the functionality for developing multitiered, Web-based applications.
* More info at: https://www.java.com/en/download/faq/techinfo.xml


## Part 1:

### Things we covered:
* Why Java ? OOP, Platform independent, Robust, Simple, Secure and Data protection
* Classes - template used to model state and behavior of things
* Objects - intances of classes created by using the "new" keyword
* State - defined by the object's properties
* Behavior - defined by the object's methods. They implement an algorithm and manipulate data
* Instance Initializer
* Identifiers and keywords - name of classes, objects, methods and keywords specific to the language
* Inheritance - concept used to reuse code
* Interfaces - define behavor of a class
* Java naming convention for classes, interfaces, methods, fields and constants
* Primitive types (byte, char, short, int, long, float, double, boolean)
* Variables: local, static or instance variables
* Garbage Collector
* Operators - unary, binary, ternary
* Numeric promotion
* Statements (if-then-else, switch, while, for, for each
* String class. Available methods. Why is it so special?
* StringBuilder. What are the available methods? Homework: Read about StringBuffer. Is it different?
* Arrays, ArrayList (<> diamond operator)
* Wrapper classes
* Autoboxing

Nice to read:
https://www.oracle.com/technetwork/java/codeconventions-150003.pdf

## Part 2

### Other features in Java:
* java.time API
* Methods. Overloading rules
* Constructor. Initialization rules
* Data encapsulation
* What is an immutable class?
* Class design. Inheritance and constructor definition rules
* Override rules. Can you override static methods?
* Abstract class
* Interfaces methods and fields
* Polimorphism - references, casting
* Exceptions

### Lambda expressions

Java 8 added the *@FunctionalInterface* which can be used to decorate interfaces with exactly one abstract method. 
These interfaces (with or without the annotation) can be transformed by the compiler into lambda expressions.

You can find here a list of FunctionalInterfaces available in the jdk:
https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html

A lambda expression could use the following syntax (not the only one possible):
```
parameter -> expression body
```

### Optional
Optional is a container object used to contain not-null objects. It provides methods for dealing with nulls in a 
fluent manner.

javadoc: https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html

### Collections 

The Java collections framework gives the programmer access to prepackaged data structures as well as to algorithms for manipulating them.

A collection is an object that can hold references to other objects. The collection interfaces declare the operations that can be performed on each type of collection.

The classes and interfaces of the collections framework are in package java.util.

You can find more details here: https://www.tutorialspoint.com/java/java_collections.htm

## Streams

Stream represents a sequence of objects from a source, which supports aggregate operations. 

A stream can be created from multiple objects, an array or a collection:

```java
String[] arr = new String[]{"a", "b", "c"};
Stream<String> stream = Arrays.stream(arr);
stream = Stream.of("a", "b", "c");
stream = list.stream();
```

### Operations

There are 2 types of stream operations:
* intermediate, which return a Stream<T>
* terminal, which return a definitive result (or Void).

Stream operation do not modify the original stream. Processing is done only on the final operations.

#### Iterating

```java
list.stream().forEach(el -> System.out.println(el));
```

#### Filtering

```java
Stream<String> stream = list.stream().filter(el -> el.contains("aString"));
```

#### Mapping

```java
List<String> uris = new ArrayList<>();
uris.add("C:\\My.txt");
Stream<Path> stream = uris.stream().map(uri -> Paths.get(uri));
```

For dealing with functions which return collections of elements use flatmap

```java
List<Detail> details = new ArrayList<>();
details.add(new Detail());
Stream<String> stream = details.stream().flatMap(detail -> detail.getParts().stream());
```

#### Reducing

```java
List<Integer> integers = Arrays.asList(1, 1, 1);
Integer reduced = integers.stream().reduce(23, (a, b) -> a + b);
```

#### Collecting

```java
List<String> resultList = list.stream().map(element -> element.toUpperCase()).collect(Collectors.toList());
```

#### Parallel execution

```java
list.parallelStream().forEach(element -> doWork(element));
```