package co.caffeinecoders.cricketcritics.repositories;

import co.caffeinecoders.cricketcritics.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "SELECT * FROM reviews as r WHERE r.record_status = 'A'", nativeQuery = true)
    List<Review> findAllActiveReviews();

    @Query(value = "SELECT * FROM reviews as r WHERE r.id = :id and r.record_status = 'A'", nativeQuery = true)
    Optional<Review> findActiveReviewById(@Param("id") Long id);

    @Query(value = "SELECT r.id, r.description, r.score, r.likes_counter, r.review_date, r.record_status, r.user_id, r.movie_id FROM reviews as r JOIN users as u on r.user_id = u.id WHERE r.user_id = :id_param AND r.record_status = 'A'", nativeQuery = true)
    List<Review> findAllReviewsByUserId(@Param("id_param") Long idParam);
    @Query(value = "SELECT r.id, r.description, r.score, r.likes_counter, r.review_date, r.record_status, r.user_id, r.movie_id FROM reviews as r JOIN movies as m on r.user_id = m.id WHERE r.movie_id = :id_param AND r.record_status = 'A'", nativeQuery = true)
    List<Review> findAllReviewsByMovieId(@Param("id_param") Long idParam);


}
