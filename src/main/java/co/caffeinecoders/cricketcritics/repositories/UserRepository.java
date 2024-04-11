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

    @Query(value= "SELECT u.id,u.user_name,u.name,u.last_name,u.email,u.password,u.record_status,u.user_enum FROM users as u JOIN reviews as r ON u.id = r.user_id GROUP BY(u.id) HAVING COUNT(r.id) >= :n_reviews",nativeQuery = true)
    List<User> findAllUsersWithReviews(@Param("n_reviews") Integer nReviews);

}
