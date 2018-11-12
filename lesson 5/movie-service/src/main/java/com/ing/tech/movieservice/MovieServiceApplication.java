package com.ing.tech.movieservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.awt.*;
import java.time.Duration;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class MovieServiceApplication {

	@Bean
	public CommandLineRunner onApplicationStart(MovieRepository movieRepository) {
		return args -> Stream.of("The Avengers", "Hulk", "Matrix", "Lord of The Rings")
				.map(title -> new Movie(UUID.randomUUID().toString(), title))
				.forEach(movie -> movieRepository.insert(movie).subscribe(m -> System.out.println(m)));
	}

	public static void main(String[] args) {
	    SpringApplication.run(MovieServiceApplication.class, args);
	}
}

class MovieEvent {
    private String title;
    private String user;

    MovieEvent() {}

    public MovieEvent(String title, String user) {
        this.title = title;
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

interface MovieRepository extends ReactiveMongoRepository<Movie, String> {
	Mono<Movie> findByTitle(String title);
}

@Document(collection = "movie")
class Movie {

    @Id
    private String id;
    private String title;

    public Movie(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

@Service
class MovieService {

	private final MovieRepository movieRepository;

	MovieService(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	public Flux<Movie> getMovies() {
	    return movieRepository.findAll();
    }

    public Mono<Movie> getById(String movieId) {
	    return movieRepository.findById(movieId);
    }

    public Flux<MovieEvent> getEventsForMovie(Movie movie) {
	    Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
	    Flux<MovieEvent> events = Flux.fromStream(
	            Stream.generate(() -> new MovieEvent(movie.getTitle(), getRandomUser()))
        );

	    return Flux.zip(interval, events).map(Tuple2::getT2);
    }

    public Flux<MovieEvent> getEventById(String id) {
        return movieRepository.findById(id).flatMapMany(this::getEventsForMovie);
    }

    public String getRandomUser() {
	    String[] users = "Mihai,Ioana,Maria,George".split(",");
	    return users[new Random().nextInt(users.length)];
    }
}

@RestController
@RequestMapping("/movies")
class MovieResource {
    private final MovieService movieService;

    MovieResource(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public Flux<Movie> getMovies() {
        return movieService.getMovies();
    }

    @GetMapping("/{id}")
    public Mono<Movie> getById(@PathVariable("id") String id) {
        return movieService.getById(id);
    }

    @GetMapping(value = "/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MovieEvent> getEventsFromMovie(@PathVariable("id") String id) {
        return movieService.getEventById(id);
    }
}