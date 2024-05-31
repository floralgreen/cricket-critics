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

    /**
     *
     * @param movie given a Movie object as Param
     * @return the saved Object in the Database
     */
    public Movie createMovie(Movie movie) {
        Movie savedMovie = movieRepository.saveAndFlush(movie);
        return savedMovie;
    }

    /**
     *
     * @return the full list of active Movies in the Db
     * or an empty list if none is active
     */
    public List<Movie> getAllActiveMovies() {
        return movieRepository.findAllActiveMovies();
    }

    /**
     *
     * @param id given a MovieId
     * @return Optional with the Movie Object found
     * or an empty Optional if the Movie is not found or not active
     */
    public Optional<Movie> getMovieById(Long id) {
        Optional<Movie> movieOptional = movieRepository.findActiveMovieById(id);
        return movieOptional;
    }

    /**
     *
     * @param actorId given an actorId
     * @return a List with all the Movies that the Actor has partecipated in
     * or an empty list if none is found
     */
    public List<Movie> findMoviesByActorId(Long actorId) {
        return movieRepository.findMovieByActorId(actorId);
    }

    /**
     *
     * @param directorId given an directorId
     * @return a List with all the Movies that the Actor has partecipated in
     * or an empty list if none is found
     */
    public List<Movie> findMoviesByDirectorId(Long directorId) {
        return movieRepository.findMovieByDirectorId(directorId);
    }

    /**
     *
     * @param id given the movieId that has to be updated
     * @param movie the updated Movie Object
     * @return the updated Movie saved on the DB
     *  or empty Optional if the Id is not found
     */
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

    /**
     *
     * @param id given the movieId
     * @return the Movie Object with its updated Movie Score
     * divided by Reviewer score e UserScore
     * or empty Optional if the Id is not found
     */
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

    /**
     *
     * @param id given the movie Id
     * @param directorList and a List with Director objects
     * @return the movie with its director List updated
     * or empty Optional if movie doesn't exists
     */
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

    /**
     *
     * @param id given the movie Id
     * @param actorList and a List with Actor objects
     * @return the movie with its actor List updated
     * or empty Optional if movie doesn't exists
     */
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

    /**
     *
     * @param inputValue given an Integer from 1 to 100
     * @return a List of movies that has the ReviewersScore Value greater or equal than that inputValue
     */
    public List<Movie> findMovieByReviewersScore(Integer inputValue) {
        return movieRepository.findMovieByReviewersScore(inputValue);
    }

    /**
     *
     * @param inputValue given an Integer from 1 to 100
     * @return a List of movies that has the UsersScore Value greater or equal than that inputValue
     */
    public List<Movie> findMovieByUsersScore(Integer inputValue) {
        return movieRepository.findMovieByUsersScore(inputValue);
    }

    /**
     *
     * @param inputValue given a movieTitle
     * @return a list of movies with the matching title
     */
    public List<Movie> findMovieByTitle(String inputValue) {
        return movieRepository.findMovieByTitle(inputValue);
    }

    /**
     *
     * @param startingData
     * @param endingData
     *
     * given a range of date Time
     *
     * @return a list of movies released in that range
     */
    public List<Movie> findMovieInRangeDate(OffsetDateTime startingData, OffsetDateTime endingData) {
        return movieRepository.findMovieInRangeDate(startingData, endingData);
    }

    /**
     *
     * @param inputValue given a Category Input
     * @return a list of movies that match that Category
     */
    public List<Movie> findMovieByCategory(String inputValue) {
        return movieRepository.findMovieByCategory(inputValue);
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

    /**
     *
     * @param reviewsListFiltered IMPORTANT given a List already Filtered of reviews (BASICUSER / REVIEWER)
     * @return the median value of all the scores from that list
     */
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
