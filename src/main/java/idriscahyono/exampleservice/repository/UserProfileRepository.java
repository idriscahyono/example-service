package idriscahyono.exampleservice.repository;

import idriscahyono.exampleservice.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}
