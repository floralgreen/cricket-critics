package co.caffeinecoders.cricketcritics.repositories;

import co.caffeinecoders.cricketcritics.entities.Community;
import co.caffeinecoders.cricketcritics.entities.Post;
import co.caffeinecoders.cricketcritics.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    @Query(value="SELECT * FROM posts as p where p.record_status = 'A'", nativeQuery = true)
    List<Post> findAllActivePost();

    @Query (value="SELECT * FROM posts as p where p.id = :id and p.record_status = 'A'", nativeQuery = true)
    Optional<Post> findActivePostById(@Param("id") Long id);

    @Query(value = "SELECT p.id, p.text, p.sent_date, p.user_id, p.record_status FROM posts as p JOIN users as u on p.user_id = u.id WHERE p.user_id = :id_param AND r.record_status = 'A'", nativeQuery = true)
    List<Post> findAllPostsByUserId(@Param("id_param") Long idParam);


}
