## Part 1 - Reactive Programming
https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html

![web](https://github.com/mihaita-tinta/switch-to-java/blob/master/lesson%206/web.PNG)

## Part 2 - Realtime car tracking

### Dummy data for car tracking.

We can generate gpx files from: https://mapstogpx.com/ to track a moving car.
We can use JAXB to load XML files in 2 steps:

* Add the dependency to map the xml tags to some Java classes
```xml
<dependency>
    <groupId>org.vesalainen.gpx</groupId>
    <artifactId>GPX11</artifactId>
    <version>1.0.2</version>
</dependency>
```

* Use JAXB to map the xml to the gpx library classes:
```java
File file = new ClassPathResource("/routes/route0.gpx").getFile();
JAXBContext jc = JAXBContext.newInstance(GpxType.class);

GpxType route = ((JAXBElement<GpxType>) jc.createUnmarshaller().unmarshal(file)).getValue();
```

### Expose car position - SSE
We need to expose the data to any client that is interested in the driver's position.

```string
Server-Sent Events specification defines an API for opening an HTTP connection for receiving push notifications from a server in the form of DOM events. The API is designed such that it can be extended to work with other push notification schemes such as Push SMS.
```
https://www.w3.org/TR/eventsource/

We can use *SseEmitter* - a specialization of ResponseBodyEmitter for sending Server-Sent Events when a car is moving.

```java
SseEmitter.SseEventBuilder event = SseEmitter.event()
                                .data(mapper.writeValueAsString(position) +"\n")
                                .id(String.valueOf(i))
                                .name("RawPosition for " + id);
emitter.send(event);
```

We can return an event stream in Spring MVC

```java
@GetMapping("/rides/{id}/tracking")
public SseEmitter streamSseMvc(@PathVariable Long id) {
SseEmitter emitter = ...// get the object from somewhere related to a specific ride
return emitter;
}        
```

### Display the map

There are some sample files to display a simple google maps tracking a car's position.
* index.html - main page
* tracker.js - consuming the events stream
* car.png - customize the marker in google maps.

![maps](https://github.com/mihaita-tinta/switch-to-java/blob/master/lesson%206/map.PNG)

