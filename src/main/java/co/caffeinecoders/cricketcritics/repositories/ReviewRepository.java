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

}
