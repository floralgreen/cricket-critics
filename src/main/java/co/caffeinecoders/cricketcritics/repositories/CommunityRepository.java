package co.caffeinecoders.cricketcritics.repositories;

import co.caffeinecoders.cricketcritics.entities.Actor;
import co.caffeinecoders.cricketcritics.entities.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityRepository extends JpaRepository<Community,Long> {

    @Query(value="SELECT * FROM communities as c where c.record_status = 'A'", nativeQuery = true)
    List<Community> findAllActiveCommunity();

    @Query (value="SELECT * FROM communities as c where c.id = :id and c.record_status = 'A'", nativeQuery = true)
    Optional<Community> findActiveCommunityById(@Param("id") Long id);

    @Query (value="SELECT * FROM communities as c where c.name = :name and c.record_status = 'A'", nativeQuery = true)
    List<Community> findAllActiveCommunityByName(@Param("name") String name);

    @Query (value="SELECT c.id, c.name, c.record_status, c.movie_id, c.user_id FROM communities as c JOIN users as u on u.id = c.user_id where c.user_id = :id_param and c.record_status = 'A'", nativeQuery = true)
    List<Community> findCommunitiesByUser(@Param("id_param") Long userId);

    @Query (value="SELECT c.id, c.name, c.record_status, c.movie_id, c.user_id FROM communities as c JOIN movies as m on m.id = c.movie_id where c.movie_id = :id_param and c.record_status = 'A'", nativeQuery = true)
    List<Community> findCommunitiesByMovie(@Param("id_param") Long movieId);

}
