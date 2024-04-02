package co.caffeinecoders.cricketcritics.services;

import co.caffeinecoders.cricketcritics.entities.Movie;
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
        List<Movie> movies = movieRepository.findAll();
        List<Movie> moviesActive = new ArrayList<Movie>();
        for (Movie movie : movies) {
            if (movie.getRecordStatusEnum().equals(RecordStatusEnum.A)) {
                moviesActive.add(movie);
            }
        }
        return moviesActive;
    }

    public Optional<Movie> getMovieById(Long id) {
        Optional<Movie> movieOptional = movieRepository.findById(id);
        if (movieOptional.isPresent()) {
            if (movieOptional.get().getRecordStatusEnum().equals(RecordStatusEnum.A)) {
                return movieOptional;
            }

        }
        return Optional.empty();
    }


    public Optional<Movie> updateMovie(Long id, Movie movie) {
        Optional<Movie> movieToUpdate = movieRepository.findById(id);
        if (movieToUpdate.isPresent()) {
            if (movieToUpdate.get().getRecordStatusEnum().equals(RecordStatusEnum.A)) {
                movieToUpdate.get().setTitle(movie.getTitle());
                movieToUpdate.get().setPlot(movie.getPlot());
                movieToUpdate.get().setLanguageEnum(movie.getLanguageEnum());
                movieToUpdate.get().setActors(movie.getActors());
                movieToUpdate.get().setDirectors(movie.getDirectors());
                movieToUpdate.get().setDuration(movie.getDuration());
                movieToUpdate.get().setMovieWebSite(movie.getMovieWebSite());
                movieToUpdate.get().setCategoryEnum(movie.getCategoryEnum());
                movieToUpdate.get().setReleaseDate(movie.getReleaseDate());
                movieToUpdate.get().setReviews(movie.getReviews());
                movieRepository.save(movieToUpdate.get());
                return movieToUpdate;
            }

        }
        return Optional.empty();
    }

    /**
     * @param id
     * @return an Optional containing the deactivated Movie object, or an empty Optional if the object is not found
     */
    public Optional<Movie> deactivateMovieById(Long id) {
        Optional<Movie> movieToDeactivate = movieRepository.findById(id);
        if (movieToDeactivate.isPresent()) {
            movieToDeactivate.get().setRecordStatusEnum(RecordStatusEnum.D);
            movieRepository.save(movieToDeactivate.get());
        }
        return movieToDeactivate;
    }
}
