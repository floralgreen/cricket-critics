package co.caffeinecoders.cricketcritics.repositories;

import co.caffeinecoders.cricketcritics.entities.Actor;
import co.caffeinecoders.cricketcritics.entities.Movie;
import co.caffeinecoders.cricketcritics.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query(value = "SELECT * FROM movies as m where m.record_status = 'A'", nativeQuery = true)
    List<Movie> findAllActiveMovies();

    @Query(value = "SELECT * FROM movies as m where m.id = :id and m.record_status = 'A'", nativeQuery = true)
    Optional<Movie> findActiveMovieById(@Param("id") Long id);

    @Query(value = "SELECT * FROM movies as m where m.reviewers_score >= :inputValue and m.record_status = 'A'", nativeQuery = true)
    List<Movie> findMovieByReviewersScore(@Param("inputValue") Integer inputValue);

    @Query(value = "SELECT * FROM movies as m where m.users_score >= :inputValue and m.record_status = 'A'", nativeQuery = true)
    List<Movie> findMovieByUsersScore(@Param("inputValue") Integer inputValue);

    @Query(value = "SELECT m.id,m.title,m.plot,m.duration,m.users_score,m.reviewers_score,m.release_date,m.movie_website,m.record_status,m.category FROM actors_movies as am JOIN movies as m ON am.movie_id = m.id JOIN actors as a ON am.actor_id = a.id WHERE a.id = :actorId AND m.record_status = 'A' AND a.record_status = 'A'", nativeQuery = true)
    List<Movie> findMovieByActorId(@Param("actorId") Long actorId);

    @Query(value = "SELECT m.id,m.title,m.plot,m.duration,m.users_score,m.reviewers_score,m.release_date,m.movie_website,m.record_status,m.category FROM directors_movies as dm JOIN movies as m ON dm.movie_id = m.id JOIN directors as d ON dm.director_id = d.id WHERE d.id = :directorId AND m.record_status = 'A' AND d.record_status = 'A'", nativeQuery = true)
    List<Movie> findMovieByDirectorId(@Param("directorId") Long directorId);

    @Query(value = "SELECT * FROM movies as m where m.title = :inputValue and m.record_status = 'A'", nativeQuery = true)
    List<Movie> findMovieByTitle(@Param("inputValue") String inputValue);

    @Query(value = "SELECT * FROM movies as m where m.release_date >= :startingData and m.release_date <= :endingData and m.record_status = 'A'", nativeQuery = true)
    List<Movie> findMovieInRangeDate(@Param("startingData") OffsetDateTime startingData, @Param("endingData") OffsetDateTime endingData);

    @Query(value = "SELECT * FROM movies as m where m.category = :inputValue and m.record_status = 'A'", nativeQuery = true)
    List<Movie> findMovieByCategory(@Param("inputValue") String inputValue);
}
