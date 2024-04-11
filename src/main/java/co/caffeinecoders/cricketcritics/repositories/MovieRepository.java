package co.caffeinecoders.cricketcritics.repositories;

import co.caffeinecoders.cricketcritics.entities.Movie;
import co.caffeinecoders.cricketcritics.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query(value="SELECT * FROM movies as m where m.record_status = 'A'", nativeQuery = true)
    List<Movie> findAllActiveMovies();

    @Query (value="SELECT * FROM movies as m where m.id = :id and m.record_status = 'A'", nativeQuery = true)
    Optional<Movie> findActiveMovieById(@Param("id") Long id);

    @Query (value = "SELECT * FROM movies as m where m.reviewers_score >= :inputValue and m.record_status = 'A'", nativeQuery = true)
    List<Movie> findMovieByReviewersScore(@Param("inputValue") Integer inputValue);

    @Query(value = "SELECT * FROM movies as m where m.users_score >= :inputValue and m.record_status = 'A'", nativeQuery = true)
    List<Movie> findMovieByUsersScore(@Param("inputValue") Integer inputValue);

}
