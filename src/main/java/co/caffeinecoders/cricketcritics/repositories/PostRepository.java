package co.caffeinecoders.cricketcritics.repositories;

import co.caffeinecoders.cricketcritics.entities.Post;
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

    @Query(value = "SELECT p.id, p.text, p.sent_date, p.user_id, p.community_id, p.record_status FROM posts as p JOIN users as u on p.user_id = u.id WHERE p.user_id = :id_param AND p.record_status = 'A' AND u.record_status = 'A'", nativeQuery = true)
    List<Post> findAllPostsByUserId(@Param("id_param") Long idParam);

    @Query(value = "SELECT p.id, p.text, p.sent_date, p.user_id, p.community_id, p.record_status FROM posts as p JOIN communities as c on p.community_id = c.id WHERE p.community_id = :id_param AND p.record_status = 'A' AND c.record_status = 'A'", nativeQuery = true)
    List<Post> findAllPostsByCommunityId(@Param("id_param") Long idParam);

    @Query (value="SELECT * FROM posts as p where p.text LIKE :keyword and p.record_status = 'A'", nativeQuery = true)
    List<Post> findPostByKeyword(@Param("keyword") String keyword);


}
