package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.entiti.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getUserByEmail(String username);
}