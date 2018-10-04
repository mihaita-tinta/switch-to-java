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

A lambda expression has the following syntax:
```
parameter -> expression body
```