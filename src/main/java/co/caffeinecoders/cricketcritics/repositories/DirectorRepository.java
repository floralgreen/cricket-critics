package co.caffeinecoders.cricketcritics.repositories;

import co.caffeinecoders.cricketcritics.entities.Director;
import co.caffeinecoders.cricketcritics.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
    @Query(value="SELECT * FROM directors as d where d.record_status = 'A'", nativeQuery = true)
    List<Director> findAllActiveDirector();

    @Query (value="SELECT * FROM directors as d where d.id = :id and d.record_status = 'A'", nativeQuery = true)
    Optional<Director> findActiveDirectorById(@Param("id") Long id);
}
