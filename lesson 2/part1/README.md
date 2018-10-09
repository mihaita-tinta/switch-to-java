# Switch to Java

## Exercise

We need to create an application that can be configured to lookup for any new files in the input folder.
Once a file is detected, the program reads the content and converts it to uppercase.
The result is saved into a new file with the extension .transformed.
Both files are moved to the output directory.
If many files are received, the application can process many of them in parallel.


```java
java -cp lesson2-1.0-SNAPSHOT.jar App input/ output/
```