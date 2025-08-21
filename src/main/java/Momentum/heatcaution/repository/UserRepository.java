package Momentum.heatcaution.repository;

import Momentum.heatcaution.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);  // 아이디 중복 체크용

    Optional<User> findByUsername(String username);
}
