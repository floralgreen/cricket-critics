package co.caffeinecoders.cricketcritics.repositories;

import co.caffeinecoders.cricketcritics.entities.Actor;
import co.caffeinecoders.cricketcritics.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<Actor,Long> {

    @Query(value="SELECT * FROM actors as a where a.record_status = 'A'", nativeQuery = true)
    List<Actor> findAllActiveActor();

    @Query (value="SELECT * FROM actors as a where a.id = :id and a.record_status = 'A'", nativeQuery = true)
    Optional<Actor> findActiveActorById(@Param("id") Long id);
}
