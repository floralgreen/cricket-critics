package co.caffeinecoders.cricketcritics.controllers;

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

    @PutMapping("/edit/{id}")
    public ResponseEntity<Movie> updateMovieById(@RequestBody Movie movie, @PathVariable Long id) {
        Optional<Movie> movieOptional = movieService.updateMovie(id, movie);
        if (movieOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(movieOptional.get());
    }

    @GetMapping("/findActive")
    public ResponseEntity<List<Movie>> findAllActiveMovies() {
        List<Movie> allActiveMovies = movieService.getAllActiveMovies();
        return ResponseEntity.ok(allActiveMovies);
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
