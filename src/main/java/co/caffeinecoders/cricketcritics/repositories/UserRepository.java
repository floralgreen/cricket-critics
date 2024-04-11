package co.caffeinecoders.cricketcritics.repositories;

import co.caffeinecoders.cricketcritics.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query (value="SELECT * FROM users as u where u.record_status = 'A'", nativeQuery = true)
    List<User> findAllActiveUsers();

    @Query (value="SELECT * FROM users as u where u.id = :id and u.record_status = 'A'", nativeQuery = true)
    Optional<User> findActiveUserById(@Param("id") Long id);

    @Query(value = "SELECT * FROM users as u where u.user_name = :username", nativeQuery = true)
    Optional<User> findActiveUserByUsername(String username);
}
