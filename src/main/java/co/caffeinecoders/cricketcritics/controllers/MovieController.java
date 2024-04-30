package co.caffeinecoders.cricketcritics.controllers;

import co.caffeinecoders.cricketcritics.entities.Actor;
import co.caffeinecoders.cricketcritics.entities.Director;
import co.caffeinecoders.cricketcritics.entities.Movie;
import co.caffeinecoders.cricketcritics.services.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @PostMapping("/create")
    @Operation(summary = "This API creates a Movie on the DB based And returns the object created for confirmation")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        Movie movieCreated = movieService.createMovie(movie);
        return ResponseEntity.ok().body(movieCreated);
    }


    @GetMapping("/{id}")
    @Operation(summary = "This API retrives the Movie object by the given ID, it returns notFound if the resource is not present in the DB")
    public ResponseEntity<Movie> findMovieById(@PathVariable Long id) {
        Optional<Movie> movieOptional = movieService.getMovieById(id);
        if(movieOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(movieOptional.get());
    }

    @GetMapping("/all")
    @Operation(summary = "This API retrives a List with all the Movie objects setted as 'Active' in the DB, it returns an empty List if none of it is Active")
    public ResponseEntity<List<Movie>> findAllActiveMovies() {
        List<Movie> allActiveMovies = movieService.getAllActiveMovies();
        return ResponseEntity.ok(allActiveMovies);
    }

    @GetMapping("/byReviewersScore")
    @Operation(summary = "This API retrieves a List with all the movies setted by reviewersScore")
    public ResponseEntity<List<Movie>> findMovieByReviewersScore(@RequestParam Integer inputValue){
        List<Movie> movieList = movieService.findMovieByReviewersScore(inputValue);
        return ResponseEntity.ok(movieList);
    }

    @GetMapping("/byUsersScore")
    @Operation(summary = "This API retrieves a List with all the movies setted by usersScore")
    public ResponseEntity<List<Movie>> findMovieByUsersScore(@RequestParam Integer inputValue){
        List<Movie> movieList = movieService.findMovieByUsersScore(inputValue);
        return ResponseEntity.ok(movieList);
    }
    @GetMapping("/findMovieByActor")
    @Operation(summary = "This API retrieves a List with all the movies where a certain actor given in input plays")
    public ResponseEntity<List<Movie>> findMoviesByActor(@RequestParam Long actorId) {
        return ResponseEntity.ok(movieService.findMoviesByActorId(actorId));
    }
    @GetMapping("/findMovieByDirector")
    @Operation(summary = "This API retrieves a List with all the movies where a certain director given in input directs")
    public ResponseEntity<List<Movie>> findMoviesByDirector(@RequestParam Long directorId) {
        return ResponseEntity.ok(movieService.findMoviesByDirectorId(directorId));
    }

    @GetMapping("/byTitle")
    @Operation(summary = "This API retrieves a List with all the movies where a certain title given in input")
    public ResponseEntity<List<Movie>> findMovieByTitle(@RequestParam String inputValue){
        List<Movie> movieList = movieService.findMovieByTitle(inputValue);
        return ResponseEntity.ok(movieList);
    }

    @GetMapping("/byCategory")
    @Operation(summary = "This API retrieves a List with all the movies where a certain title given in input")
    public ResponseEntity<List<Movie>> findMovieByCategory(@RequestParam String inputValue){
        List<Movie> movieList = movieService.findMovieByCategory(inputValue);
        return ResponseEntity.ok(movieList);
    }

    @GetMapping("/byRangeDate")
    @Operation(summary = "This API retrieves a List with all the movies situated in a specific range date given in input")
    public ResponseEntity<List<Movie>> findMovieByRangeDate(@RequestParam OffsetDateTime startingDate, @RequestParam OffsetDateTime endingDate){
        List<Movie> movieList = movieService.findMovieInRangeDate(startingDate, endingDate);
        return ResponseEntity.ok(movieList);
    }

    @PutMapping("/edit/{id}")
    @Operation(summary = "This API updates the Movie matching by the given ID with the new Review given by RequestBody, only updatable fields are Description and Score, it returns notFound if the resource is not present or set as status 'Deactivated'")
    public ResponseEntity<Movie> updateMovieById(@RequestBody Movie movie, @PathVariable Long id) {
        Optional<Movie> movieOptional = movieService.updateMovie(id, movie);
        if (movieOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(movieOptional.get());
    }

    //TODO generalizzare con classe padre director e actor
    @PutMapping("/updateDirectors/{movieId}")
    @Operation(summary = "This API updates the Movie matching by director the given ID, adding him to the movie matched")
    public ResponseEntity<Movie> updateDirectors(@PathVariable Long movieId, @RequestBody List<Director> directorList){
        Optional<Movie> movieOptional = movieService.addDirectorsToMovie(movieId, directorList);
        if (movieOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(movieOptional.get());
    }
    @PutMapping("/updateActors/{movieId}")
    @Operation(summary = "This API updates the Movie matching by actor the given ID, adding him to the movie matched")
    public ResponseEntity<Movie> updateActors(@PathVariable Long movieId, @RequestBody List<Actor> actorList){
        Optional<Movie> movieOptional = movieService.addActorsToMovie(movieId, actorList);
        if (movieOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(movieOptional.get());
    }

    @PutMapping("/updateScores/{id}")
    @Operation(summary = "This API updates the scores every time it's gonna be recalculated to find the new mediaScore")
    public ResponseEntity<Movie> updateScores(@PathVariable Long id){
        Optional<Movie> movieOptional = movieService.updatedMovieScores(id);
        if (movieOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(movieOptional.get());
    }



    @PutMapping("/delete/{id}")
    @Operation(summary = "This API deactivates the Movie by the given ID, and set the status as ìDeactivated', it returns the deactivated object or notFound if the resource is Absent or already deactivated")
    public ResponseEntity<Movie> deleteMovieById(@PathVariable Long id){
        Optional<Movie> deactivatedMovie= movieService.deactivateMovieById(id);
        if (deactivatedMovie.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deactivatedMovie.get());
    }


}
