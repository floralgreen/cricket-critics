package co.caffeinecoders.cricketcritics.services;

import co.caffeinecoders.cricketcritics.entities.Actor;
import co.caffeinecoders.cricketcritics.entities.Director;
import co.caffeinecoders.cricketcritics.entities.Movie;
import co.caffeinecoders.cricketcritics.entities.Review;
import co.caffeinecoders.cricketcritics.enums.RecordStatusEnum;
import co.caffeinecoders.cricketcritics.enums.UserEnum;
import co.caffeinecoders.cricketcritics.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
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

    public List<Movie> findMoviesByActorId(Long actorId) {
        return movieRepository.findMovieByActorId(actorId);
    }

    public List<Movie> findMoviesByDirectorId(Long directorId) {
        return movieRepository.findMovieByDirectorId(directorId);
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

    public Optional<Movie> updatedMovieScores(Long id) {
        Optional<Movie> movieOptional = movieRepository.findActiveMovieById(id);
        if (movieOptional.isPresent()) {
            List<Review> reviewsUsersList = movieOptional.get().getReviews().stream().filter(r -> r.getUser().getUserEnum().equals(UserEnum.BASICUSER)).toList();
            List<Review> reviewsReviewersList = movieOptional.get().getReviews().stream().filter(r -> r.getUser().getUserEnum().equals(UserEnum.REVIEWER)).toList();

            //Controlla che non sia Zero altrimenti la media non funziona
            if (reviewsUsersList.size() > 0 ) {
                movieOptional.get().setUsersScore(calculateMedian(reviewsUsersList));
            }
            if (reviewsReviewersList.size() > 0 ) {
                movieOptional.get().setReviewersScore(calculateMedian(reviewsReviewersList));
            }



            movieRepository.save(movieOptional.get());
        }
        return movieOptional;
    }

    public Optional<Movie> addDirectorsToMovie(Long id, List<Director> directorList) {
        Optional<Movie> movieOptional = movieRepository.findActiveMovieById(id);
        if (movieOptional.isPresent()) {
            for (Director director : directorList) {
                if (!movieOptional.get().getDirectors().contains(director)) {
                    movieOptional.get().getDirectors().add(director);
                }
            }
            movieRepository.save(movieOptional.get());
        }
        return movieOptional;
    }

    public Optional<Movie> addActorsToMovie(Long id, List<Actor> actorList) {
        Optional<Movie> movieOptional = movieRepository.findActiveMovieById(id);
        if (movieOptional.isPresent()) {
            for (Actor actor : actorList) {
                if (!movieOptional.get().getActors().contains(actor)) {
                    movieOptional.get().getActors().add(actor);
                }
            }
            movieRepository.save(movieOptional.get());
        }
        return movieOptional;
    }

    public List<Movie> findMovieByReviewersScore(Integer inputValue) {
        return movieRepository.findMovieByReviewersScore(inputValue);
    }

    public List<Movie> findMovieByUsersScore(Integer inputValue) {
        return movieRepository.findMovieByUsersScore(inputValue);
    }

    public List<Movie> findMovieByTitle(String inputValue) {
        return movieRepository.findMovieByTitle(inputValue);
    }

    public List<Movie> findMovieInRangeDate(OffsetDateTime startingData, OffsetDateTime endingData) {
        return movieRepository.findMovieInRangeDate(startingData, endingData);
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

    //UTILITY METHODS

    private Integer calculateMedian(List<Review> reviewsListFiltered) {
        Integer sum = 0;
        Integer median;
        for (Review review : reviewsListFiltered) {
            sum += review.getScore();
        }
        median = sum / reviewsListFiltered.size();
        return median;
    }
}
