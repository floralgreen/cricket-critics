package co.caffeinecoders.cricketcritics.services;

import co.caffeinecoders.cricketcritics.entities.Actor;
import co.caffeinecoders.cricketcritics.entities.Director;
import co.caffeinecoders.cricketcritics.entities.Movie;
import co.caffeinecoders.cricketcritics.entities.User;
import co.caffeinecoders.cricketcritics.enums.RecordStatusEnum;
import co.caffeinecoders.cricketcritics.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public Movie createMovie(Movie movie) {
        Movie savedMovie = movieRepository.saveAndFlush(movie);
        return savedMovie;
    }

    public List<Movie> getAllActiveMovies() {
        return movieRepository.findAllActiveMovies();
    }

    public Optional<Movie> getMovieById(Long id) {
        Optional<Movie> movieOptional = movieRepository.findActiveMovieById(id);
        return movieOptional;
    }


    public Optional<Movie> updateMovie(Long id, Movie movie) {
        Optional<Movie> movieToUpdate = movieRepository.findActiveMovieById(id);
        if (movieToUpdate.isPresent()) {
            movieToUpdate.get().setTitle(movie.getTitle());
            movieToUpdate.get().setPlot(movie.getPlot());
            movieToUpdate.get().setActors(movie.getActors());
            movieToUpdate.get().setDirectors(movie.getDirectors());
            movieToUpdate.get().setDuration(movie.getDuration());
            movieToUpdate.get().setMovieWebSite(movie.getMovieWebSite());
            movieToUpdate.get().setCategoryEnum(movie.getCategoryEnum());
            movieToUpdate.get().setReleaseDate(movie.getReleaseDate());
            movieToUpdate.get().setReviews(movie.getReviews());
            Movie movieUpdated = movieRepository.save(movieToUpdate.get());
            return Optional.of(movieUpdated);
        } else {

            return Optional.empty();
        }

    }

    public Optional<Movie> addDirectorsToMovie(Long id, List<Director> directorList){
        Optional<Movie> movieOptional = movieRepository.findActiveMovieById(id);
        if (movieOptional.isPresent()){
            for (Director director : directorList) {
                if (!movieOptional.get().getDirectors().contains(director)){
                    movieOptional.get().getDirectors().add(director);
                }
            }
            movieRepository.save(movieOptional.get());
        }
        return movieOptional;
    }
    public Optional<Movie> addActorsToMovie(Long id, List<Actor> actorList){
        Optional<Movie> movieOptional = movieRepository.findActiveMovieById(id);
        if (movieOptional.isPresent()){
            for (Actor actor : actorList) {
                if (!movieOptional.get().getActors().contains(actor)){
                    movieOptional.get().getActors().add(actor);
                }
            }
            movieRepository.save(movieOptional.get());
        }
        return movieOptional;
    }

    public List<Movie> findMovieByReviewersScore(Integer inputValue){
        return movieRepository.findMovieByReviewersScore(inputValue);
    }

    public List<Movie> findMovieByUsersScore(Integer inputValue){
        return movieRepository.findMovieByUsersScore(inputValue);
    }

    /**
     * @param id
     * @return an Optional containing the deactivated Movie object, or an empty Optional if the object is not found
     */
    public Optional<Movie> deactivateMovieById(Long id) {
        Optional<Movie> movieToDeactivate = movieRepository.findActiveMovieById(id);
        if (movieToDeactivate.isPresent()) {
            movieToDeactivate.get().setRecordStatusEnum(RecordStatusEnum.D);
            movieRepository.save(movieToDeactivate.get());
        }
        return movieToDeactivate;
    }
}
