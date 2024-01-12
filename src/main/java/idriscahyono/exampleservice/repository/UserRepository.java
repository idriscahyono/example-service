package idriscahyono.exampleservice.repository;

import idriscahyono.exampleservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByUsername(String string);
    Optional<User> findFirstByPhone(String string);
    Optional<User> findFirstByEmail(String string);

    @Transactional
    int deleteUserByUsername(String name);

}
