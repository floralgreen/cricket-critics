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
}
