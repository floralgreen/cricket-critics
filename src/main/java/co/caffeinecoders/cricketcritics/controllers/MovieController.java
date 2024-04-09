package co.caffeinecoders.cricketcritics.controllers;

import co.caffeinecoders.cricketcritics.entities.Actor;
import co.caffeinecoders.cricketcritics.entities.Director;
import co.caffeinecoders.cricketcritics.entities.Movie;
import co.caffeinecoders.cricketcritics.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @PostMapping("/create")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        Movie movieCreated = movieService.createMovie(movie);
        return ResponseEntity.ok().body(movieCreated);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Movie> findMovieById(@PathVariable Long id) {
        Optional<Movie> movieOptional = movieService.getMovieById(id);
        if(movieOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(movieOptional.get());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Movie>> findAllActiveMovies() {
        List<Movie> allActiveMovies = movieService.getAllActiveMovies();
        return ResponseEntity.ok(allActiveMovies);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Movie> updateMovieById(@RequestBody Movie movie, @PathVariable Long id) {
        Optional<Movie> movieOptional = movieService.updateMovie(id, movie);
        if (movieOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(movieOptional.get());
    }

    //TODO generalizzare con classe padre director e actor
    @PutMapping("/updateDirectors/{movieId}")
    public ResponseEntity<Movie> updateDirectors(@PathVariable Long movieId, @RequestBody List<Director> directorList){
        Optional<Movie> movieOptional = movieService.addDirectorsToMovie(movieId, directorList);
        if (movieOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(movieOptional.get());
    }
    @PutMapping("/updateDirectors/{movieId}")
    public ResponseEntity<Movie> updateActors(@PathVariable Long movieId, @RequestBody List<Actor> actorList){
        Optional<Movie> movieOptional = movieService.addActorsToMovie(movieId, actorList);
        if (movieOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(movieOptional.get());
    }



    @PutMapping("/deactivate/{id}")
    public ResponseEntity<Movie> deactivateMovieById(@PathVariable Long id){
        Optional<Movie> deactivatedMovie= movieService.deactivateMovieById(id);
        if (deactivatedMovie.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deactivatedMovie.get());
    }


}
